package eu.ksliwinski.models;

import eu.ksliwinski.proto.Graph;

import java.util.ArrayList;
import java.util.List;

public class AdjacencyMatrixGraph implements Graph {
    private final int numVertices;
    private int numEdges;
    private final int[][] matrix;
    private final List<Edge> allEdges;

    public AdjacencyMatrixGraph(int numVertices) {
        this.numVertices = numVertices;
        this.numEdges = 0;
        this.matrix = new int[numVertices][numVertices];
        this.allEdges = new ArrayList<>();
    }

    @Override
    public void addEdge(int src, int dest, int weight, boolean isDirected) {
        if (matrix[src][dest] == 0) {
            numEdges++;
        }

        matrix[src][dest] = weight;

        if (!isDirected) {
            matrix[dest][src] = weight;
        }

        allEdges.add(new Edge(src, dest, weight));
    }

    @Override
    public List<Edge> getNeighbours(int vertex) {
        List<Edge> neighbors = new ArrayList<>();
        for (int dest = 0; dest < numVertices; dest++) {
            if (matrix[vertex][dest] > 0) {
                neighbors.add(new Edge(vertex, dest, matrix[vertex][dest]));
            }
        }
        return neighbors;
    }

    @Override
    public List<Edge> getAllEdges() {
        return allEdges;
    }

    @Override
    public int getVertexCount() {
        return numVertices;
    }

    @Override
    public int getEdgeCount() {
        return numEdges;
    }
}