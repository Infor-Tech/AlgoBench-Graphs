package eu.ksliwinski.models;

import eu.ksliwinski.proto.Graph;

import java.util.ArrayList;
import java.util.List;

public class AdjacencyListGraph implements Graph {
    private final int numVertices;
    private int numEdges;
    private final List<List<Edge>> adjList;
    private final List<Edge> allEdges;

    public AdjacencyListGraph(int numVertices)
    {
        this.numVertices = numVertices;
        this.numEdges = 0;
        this.adjList = new ArrayList<>(numVertices);
        this.allEdges = new ArrayList<>();

        for (int i = 0; i < numVertices; i++) adjList.add(new ArrayList<>());
    }

    @Override
    public int getVertexCount() {
        return numVertices;
    }

    @Override
    public int getEdgeCount() {
        return numEdges;
    }

    @Override
    public void addEdge(int src, int dest, int weight, boolean directed) {
        Edge edge = new Edge(src, dest, weight);
        adjList.get(src).add(edge);
        allEdges.add(edge);
        numEdges++;

        if(!directed) {
            Edge reverseEdge = new Edge(dest, src, weight);
            adjList.get(dest).add(reverseEdge);
        }
    }

    @Override
    public List<Edge> getNeighbours(int vertex) {
        return adjList.get(vertex);
    }

    @Override
    public List<Edge> getAllEdges() {
        return allEdges;
    }
}
