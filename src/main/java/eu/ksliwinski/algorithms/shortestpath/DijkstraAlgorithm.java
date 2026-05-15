package eu.ksliwinski.algorithms.shortestpath;

import eu.ksliwinski.datastructures.DistanceMinHeap;
import eu.ksliwinski.datastructures.DynamicArray;
import eu.ksliwinski.models.AdjacencyListGraph;
import eu.ksliwinski.models.AdjacencyMatrixGraph;
import eu.ksliwinski.models.Edge;
import eu.ksliwinski.models.ShortestPathResult;
import eu.ksliwinski.proto.Graph;

/**
 * Implementation of Dijkstra's Algorithm for finding the shortest paths
 */
public class DijkstraAlgorithm {

    public static ShortestPathResult run(AdjacencyMatrixGraph graph, int startVertex) {
        int vCount = graph.getVertexCount();
        int[][] matrix = graph.getMatrix();

        long[] distances = new long[vCount];
        int[] predecessors = new int[vCount];
        boolean[] visited = new boolean[vCount];

        for (int i = 0; i < vCount; i++) {
            distances[i] = Long.MAX_VALUE;
            predecessors[i] = -1;
        }

        distances[startVertex] = 0;

        DistanceMinHeap minHeap = new DistanceMinHeap(graph.getEdgeCount() + 1);
        minHeap.insert(startVertex, 0);

        while (!minHeap.isEmpty()) {
            DistanceMinHeap.Node currentNode = minHeap.extractMin();
            int u = currentNode.vertex;

            if (visited[u]) continue;
            visited[u] = true;

            for (int dest = 0; dest < vCount; dest++) {
                int weight = matrix[u][dest];
                if (weight > 0 && !visited[dest]) {
                    long newDistance = distances[u] + weight;
                    if (newDistance < distances[dest]) {
                        distances[dest] = newDistance;
                        predecessors[dest] = u;
                        minHeap.insert(dest, newDistance);
                    }
                }
            }
        }
        return new ShortestPathResult(distances, predecessors);
    }

    public static ShortestPathResult run(AdjacencyListGraph graph, int startVertex) {
        int vCount = graph.getVertexCount();
        DynamicArray<DynamicArray<Edge>> adjList = graph.getAdjList();

        long[] distances = new long[vCount];
        int[] predecessors = new int[vCount];
        boolean[] visited = new boolean[vCount];

        for (int i = 0; i < vCount; i++) {
            distances[i] = Long.MAX_VALUE;
            predecessors[i] = -1;
        }

        distances[startVertex] = 0;
        DistanceMinHeap minHeap = new DistanceMinHeap(graph.getEdgeCount() + 1);
        minHeap.insert(startVertex, 0);

        while (!minHeap.isEmpty()) {
            DistanceMinHeap.Node currentNode = minHeap.extractMin();
            int u = currentNode.vertex;

            if (visited[u]) continue;
            visited[u] = true;

            DynamicArray<Edge> neighbors = adjList.get(u);
            for (int i = 0; i < neighbors.size(); i++) {
                Edge edge = neighbors.get(i);
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