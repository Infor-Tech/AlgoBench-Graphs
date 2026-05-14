package eu.ksliwinski.algorithms.shortestpath;

import eu.ksliwinski.datastructures.DistanceMinHeap;
import eu.ksliwinski.models.Edge;
import eu.ksliwinski.models.ShortestPathResult;
import eu.ksliwinski.proto.Graph;

import java.util.List;

/**
 * Implementation of Dijkstra's Algorithm for finding the shortest paths
 */
public class DijkstraAlgorithm {

    public static ShortestPathResult run(Graph graph, int startVertex) {
        int v = graph.getVertexCount();
        long[] distances = new long[v];
        int[] predecessors = new int[v];
        boolean[] visited = new boolean[v];

        for (int i = 0; i < v; i++) {
            distances[i] = Long.MAX_VALUE;
            predecessors[i] = -1;
        }

        distances[startVertex] = 0;

        DistanceMinHeap minHeap = new DistanceMinHeap(graph.getEdgeCount() + 1);
        minHeap.insert(startVertex, 0);

        while (!minHeap.isEmpty()) {
            DistanceMinHeap.Node currentNode = minHeap.extractMin();
            int u = currentNode.vertex;

            if (visited[u]) {
                continue;
            }
            visited[u] = true;

            List<Edge> neighbors = graph.getNeighbours(u);
            for (Edge edge : neighbors) {
                int dest = edge.dest();
                long newDistance = distances[u] + edge.weight();

                if (!visited[dest] && newDistance < distances[dest]) {
                    distances[dest] = newDistance;
                    predecessors[dest] = u;
                    minHeap.insert(dest, newDistance);
                }
            }
        }

        return new ShortestPathResult(distances, predecessors);
    }
}