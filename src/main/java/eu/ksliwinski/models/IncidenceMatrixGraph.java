package eu.ksliwinski.models;

import eu.ksliwinski.datastructures.DynamicArray;
import eu.ksliwinski.proto.Graph;

public class IncidenceMatrixGraph implements Graph {
    private final int numVertices;
    private final int maxEdges;
    private int currentEdgeIndex;

    private final int[][] matrix;

    public IncidenceMatrixGraph(int numVertices, int maxEdges) {
        this.numVertices = numVertices;
        this.maxEdges = maxEdges;
        this.currentEdgeIndex = 0;
        this.matrix = new int[numVertices][maxEdges];
    }

    @Override
    public int getVertexCount() {
        return numVertices;
    }

    @Override
    public int getEdgeCount() {
        return currentEdgeIndex;
    }

    @Override
    public void addEdge(int src, int dest, int weight, boolean directed) {
        if (currentEdgeIndex >= maxEdges) {
            throw new IllegalStateException("Exceeded maximum number of edges");
        }

        matrix[src][currentEdgeIndex] = weight;

        if (directed) {
            matrix[dest][currentEdgeIndex] = -weight;
        } else {
            matrix[dest][currentEdgeIndex] = weight;
        }

        currentEdgeIndex++;
    }

    @Override
    public DynamicArray<Edge> getNeighbours(int vertex) {
        DynamicArray<Edge> neighbors = new DynamicArray<>();
        for (int e = 0; e < currentEdgeIndex; e++) {
            if (matrix[vertex][e] > 0) {
                int weight = matrix[vertex][e];
                int dest = -1;

                for (int v = 0; v < numVertices; v++) {
                    if (v != vertex && matrix[v][e] != 0) {
                        dest = v;
                        break;
                    }
                }

                if (dest != -1) {
                    neighbors.add(new Edge(vertex, dest, weight));
                }
            }
        }
        return neighbors;
    }

    @Override
    public DynamicArray<Edge> getAllEdges() {
        DynamicArray<Edge> edges = new DynamicArray<>(currentEdgeIndex);

        for (int e = 0; e < currentEdgeIndex; e++) {
            int src = -1;
            int dest = -1;
            int weight = 0;

            for (int v = 0; v < numVertices; v++) {
                if (matrix[v][e] > 0) {
                    if (src == -1) {
                        src = v;
                        weight = matrix[v][e];
                    } else {
                        dest = v;
                    }
                } else if (matrix[v][e] < 0) {
                    dest = v;
                }
            }

            if (src != -1 && dest != -1) {
                edges.add(new Edge(src, dest, weight));
            }
        }
        return edges;
    }
}
