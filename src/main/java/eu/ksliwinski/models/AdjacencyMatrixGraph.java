package eu.ksliwinski.models;

import eu.ksliwinski.datastructures.DynamicArray;
import eu.ksliwinski.proto.Graph;

public class AdjacencyMatrixGraph implements Graph {
    private final int numVertices;
    private int numEdges;
    private final int[][] matrix;
    private Boolean isDirected = null;

    public AdjacencyMatrixGraph(int numVertices) {
        this.numVertices = numVertices;
        this.numEdges = 0;
        this.matrix = new int[numVertices][numVertices];
    }

    @Override
    public void addEdge(int src, int dest, int weight, boolean isDirected) {
        if (this.isDirected == null) {
            this.isDirected = isDirected;
        }

        if (matrix[src][dest] == 0) {
            numEdges++;
        }

        matrix[src][dest] = weight;

        if (!isDirected) {
            matrix[dest][src] = weight;
        }
    }

    @Override
    public DynamicArray<Edge> getNeighbours(int vertex) {
        DynamicArray<Edge> neighbors = new DynamicArray<>();
        for (int dest = 0; dest < numVertices; dest++) {
            if (matrix[vertex][dest] > 0) {
                neighbors.add(new Edge(vertex, dest, matrix[vertex][dest]));
            }
        }
        return neighbors;
    }

    @Override
    public DynamicArray<Edge> getAllEdges() {
        DynamicArray<Edge> edges = new DynamicArray<>(numEdges);
        boolean directed = isDirected != null && isDirected;

        for (int i = 0; i < numVertices; i++) {
            int startJ = directed ? 0 : i;
            for (int j = 0; j < numVertices; j++) {
                if (matrix[i][j] > 0) {
                    edges.add(new Edge(startJ, j, matrix[i][j]));
                }
            }
        }
        return edges;
    }

    @Override
    public int getVertexCount() {
        return numVertices;
    }

    @Override
    public int getEdgeCount() {
        return numEdges;
    }

    public int[][] getMatrix() {
        return matrix;
    }
}