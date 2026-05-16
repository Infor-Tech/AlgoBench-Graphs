package eu.ksliwinski.models;

import eu.ksliwinski.datastructures.DynamicArray;
import eu.ksliwinski.proto.Graph;

public class AdjacencyListGraph implements Graph {
    private final int numVertices;
    private int numEdges;
    private final DynamicArray<DynamicArray<Edge>> adjList;
    private Boolean isDirected = null;

    public AdjacencyListGraph(int numVertices)
    {
        this.numVertices = numVertices;
        this.numEdges = 0;
        this.adjList = new DynamicArray<>(numVertices);

        for (int i = 0; i < numVertices; i++) adjList.add(new DynamicArray<>());
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
        if (isDirected == null) {
            isDirected = directed;
        }

        Edge edge = new Edge(src, dest, weight);
        adjList.get(src).add(edge);
        numEdges++;

        if(!directed) {
            Edge reverseEdge = new Edge(dest, src, weight);
            adjList.get(dest).add(reverseEdge);
        }
    }

    @Override
    public DynamicArray<Edge> getNeighbours(int vertex) {
        return adjList.get(vertex);
    }

    @Override
    public DynamicArray<Edge> getAllEdges() {
        DynamicArray<Edge> edges = new DynamicArray<>(numEdges);
        boolean directed = isDirected != null && isDirected;

        for (int i = 0; i < numVertices; i++) {
            DynamicArray<Edge> neighbours = adjList.get(i);
            for (int j = 0; j < neighbours.size(); j++) {
                Edge edge = neighbours.get(j);
                if (directed || edge.src() <= edge.dest()) edges.add(edge);
            }
        }
        return edges;
    }

    public DynamicArray<DynamicArray<Edge>> getAdjList() {
        return adjList;
    }

    public void print() {
        System.out.println("--- Lista Sąsiedztwa ---");
        for (int i = 0; i < numVertices; i++) {
            System.out.print("[" + i + "] -> ");
            DynamicArray<Edge> neighbors = adjList.get(i);
            for (int j = 0; j < neighbors.size(); j++) {
                Edge edge = neighbors.get(j);
                System.out.print("(" + edge.dest() + ", w:" + edge.weight() + ") ");
            }
            System.out.println();
        }
    }
}
