package eu.ksliwinski.models;

import java.util.ArrayList;
import java.util.List;

public class IncidenceMatrixGraph implements Graph {
    private final int numVertices;
    private final int maxEdges;
    private int currentEdgeIndex;

    private final int[][] matrix;
    private final List<Edge> allEdges;

    public IncidenceMatrixGraph(int numVertices, int maxEdges) {
        this.numVertices = numVertices;
        this.maxEdges = maxEdges;
        this.currentEdgeIndex = 0;
        this.matrix = new int[numVertices][maxEdges];
        this.allEdges = new ArrayList<>(maxEdges);
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

        allEdges.add(new Edge(src, dest, weight));
        currentEdgeIndex++;
    }

    @Override
    public List<Edge> getNeighbours(int vertex) {
        List<Edge> neighbors = new ArrayList<>();
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
    public List<Edge> getAllEdges() {
        return allEdges;
    }
}
