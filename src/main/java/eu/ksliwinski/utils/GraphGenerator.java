package eu.ksliwinski.utils;

import eu.ksliwinski.models.AdjacencyListGraph;
import eu.ksliwinski.models.Graph;
import eu.ksliwinski.models.GraphRepresentation;
import eu.ksliwinski.models.IncidenceMatrixGraph;

import java.util.Random;

public class GraphGenerator {

    /**
     * Generates a consistent graph with specified parameters.
     *
     * @param v number of vertices
     * @param density density in percents [%]
     * @param representation Graph representation
     * @param directed Whether a graph shall be directed or not
     * @return Generated graph object
     */
    public static Graph generate(int v, int density, GraphRepresentation representation, boolean directed) {
        long maxEdges = directed ? (long) v * (v - 1) : (long) v * (v - 1) / 2;
        int targetEdges = (int) (maxEdges * (density / 100.0));

        // consistent graph must have V - 1 vertices
        if (targetEdges < v - 1) {
            targetEdges = v - 1;
        }

        Graph graph = switch (representation) {
            case INCIDENCE_MATRIX -> new IncidenceMatrixGraph(v, targetEdges);
            case ADJACENCY_LIST -> new AdjacencyListGraph(v);
            case null -> throw new IllegalArgumentException("Representation should not be null");
        };

        Random random = new Random();
        boolean[][] hasEdge = new boolean[v][v];
        int currentEdges = 0;

        // Spanning tree generation
        int[] vertices = new int[v];
        for (int i = 0; i < v; i++) {
            vertices[i] = i;
        }
        for (int i = v - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            int temp = vertices[index];
            vertices[index] = vertices[i];
            vertices[i] = temp;
        }

        for (int i = 1; i < v; i++) {
            int u = vertices[random.nextInt(i)];
            int dest = vertices[i];
            int weight = random.nextInt(100) + 1;

            graph.addEdge(u, dest, weight, directed);
            hasEdge[u][dest] = true;
            if (!directed) {
                hasEdge[dest][u] = true;
            }
            currentEdges++;
        }

        while (currentEdges < targetEdges) {
            int u = random.nextInt(v);
            int dest = random.nextInt(v);

            if (u != dest && !hasEdge[u][dest]) {
                int weight = random.nextInt(100) + 1;

                graph.addEdge(u, dest, weight, directed);
                hasEdge[u][dest] = true;
                if (!directed) {
                    hasEdge[dest][u] = true;
                }
                currentEdges++;
            }
        }

        return graph;
    }
}