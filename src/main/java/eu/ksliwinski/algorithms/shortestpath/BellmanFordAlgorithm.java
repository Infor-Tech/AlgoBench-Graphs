package eu.ksliwinski.algorithms.shortestpath;

import eu.ksliwinski.datastructures.DynamicArray;
import eu.ksliwinski.models.AdjacencyListGraph;
import eu.ksliwinski.models.AdjacencyMatrixGraph;
import eu.ksliwinski.models.Edge;
import eu.ksliwinski.models.ShortestPathResult;
import eu.ksliwinski.proto.Graph;

import java.util.List;

/**
 * Implementation of the Bellman-Ford Algorithm for finding the shortest paths.
 */
public class BellmanFordAlgorithm {
    public static ShortestPathResult run(AdjacencyMatrixGraph graph, int startVertex) {
        int v = graph.getVertexCount();
        int[][] matrix = graph.getMatrix();

        long[] distances = new long[v];
        int[] predecessors = new int[v];

        for (int i = 0; i < v; i++) {
            distances[i] = Long.MAX_VALUE;
            predecessors[i] = -1;
        }
        distances[startVertex] = 0;

        for (int i = 1; i < v; i++) {
            boolean anyDistanceUpdated = false;

            for (int u = 0; u < v; u++) {
                for (int dest = 0; dest < v; dest++) {
                    int weight = matrix[u][dest];

                    if (weight != 0) {
                        if (distances[u] != Long.MAX_VALUE && distances[u] + weight < distances[dest]) {
                            distances[dest] = distances[u] + weight;
                            predecessors[dest] = u;
                            anyDistanceUpdated = true;
                        }
                    }
                }
            }

            if (!anyDistanceUpdated) {
                break;
            }
        }

        return new ShortestPathResult(distances, predecessors);
    }

    public static ShortestPathResult run(AdjacencyListGraph graph, int startVertex) {
        int v = graph.getVertexCount();
        DynamicArray<DynamicArray<Edge>> adjList = graph.getAdjList();

        long[] distances = new long[v];
        int[] predecessors = new int[v];

        for (int i = 0; i < v; i++) {
            distances[i] = Long.MAX_VALUE;
            predecessors[i] = -1;
        }
        distances[startVertex] = 0;

        for (int i = 1; i < v; i++) {
            boolean anyDistanceUpdated = false;

            for (int u = 0; u < v; u++) {
                DynamicArray<Edge> neighbors = adjList.get(u);

                for (int j = 0; j < neighbors.size(); j++) {
                    Edge edge = neighbors.get(j);
                    int dest = edge.dest();
                    int weight = edge.weight();

                    if (distances[u] != Long.MAX_VALUE && distances[u] + weight < distances[dest]) {
                        distances[dest] = distances[u] + weight;
                        predecessors[dest] = u;
                        anyDistanceUpdated = true;
                    }
                }
            }

            if (!anyDistanceUpdated) {
                break;
            }
        }

        return new ShortestPathResult(distances, predecessors);
    }
}
