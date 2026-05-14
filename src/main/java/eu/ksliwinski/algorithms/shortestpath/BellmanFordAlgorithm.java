package eu.ksliwinski.algorithms.shortestpath;

import eu.ksliwinski.models.Edge;
import eu.ksliwinski.models.ShortestPathResult;
import eu.ksliwinski.proto.Graph;

import java.util.List;

/**
 * Implementation of the Bellman-Ford Algorithm for finding the shortest paths.
 */
public class BellmanFordAlgorithm {
    public static ShortestPathResult run(Graph graph, int startVertex) {
        int v = graph.getVertexCount();
        long[] distances = new long[v];
        int[] predecessors = new int[v];

        for (int i = 0; i < v; i++) {
            distances[i] = Long.MAX_VALUE;
            predecessors[i] = -1;
        }

        distances[startVertex] = 0;
        List<Edge> allEdges = graph.getAllEdges();

        for (int i = 1; i < v; i++) {
            boolean anyDistanceUpdated = false;

            for (Edge edge : allEdges) {
                int u = edge.src();
                int dest = edge.dest();
                int weight = edge.weight();

                if (distances[u] != Long.MAX_VALUE && distances[u] + weight < distances[dest]) {
                    distances[dest] = distances[u] + weight;
                    predecessors[dest] = u;
                    anyDistanceUpdated = true;
                }
            }

            if (!anyDistanceUpdated) {
                break;
            }
        }

        return new ShortestPathResult(distances, predecessors);
    }
}
