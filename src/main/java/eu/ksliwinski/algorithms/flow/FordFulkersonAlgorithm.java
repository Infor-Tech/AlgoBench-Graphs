package eu.ksliwinski.algorithms.flow;

import eu.ksliwinski.datastructures.DynamicArray;
import eu.ksliwinski.models.AdjacencyListGraph;
import eu.ksliwinski.models.AdjacencyMatrixGraph;
import eu.ksliwinski.models.Edge;

public class FordFulkersonAlgorithm {

    public static long run(AdjacencyMatrixGraph graph, int source, int sink) {
        int vCount = graph.getVertexCount();
        int[][] originalMatrix = graph.getMatrix();
        int[][] rGraph = new int[vCount][vCount];

        for (int i = 0; i < vCount; i++) {
            for (int j = 0; j < vCount; j++) {
                rGraph[i][j] = originalMatrix[i][j];
            }
        }

        int[] parent = new int[vCount];
        long maxFlow = 0;

        while (true) {
            boolean[] visited = new boolean[vCount];

            if (!dfsMatrix(rGraph, source, sink, visited, parent, vCount)) {
                break;
            }

            int pathFlow = Integer.MAX_VALUE;
            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                pathFlow = Math.min(pathFlow, rGraph[u][v]);
            }

            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                rGraph[u][v] -= pathFlow;
                rGraph[v][u] += pathFlow;
            }

            maxFlow += pathFlow;
        }

        return maxFlow;
    }

    private static boolean dfsMatrix(int[][] rGraph, int u, int t, boolean[] visited, int[] parent, int vCount) {
        visited[u] = true;
        if (u == t) return true;

        for (int dest = 0; dest < vCount; dest++) {
            if (!visited[dest] && rGraph[u][dest] > 0) {
                parent[dest] = u;
                if (dfsMatrix(rGraph, dest, t, visited, parent, vCount)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static class ResEdge {
        int dest;
        int capacity;
        int revIndex;

        public ResEdge(int dest, int capacity, int revIndex) {
            this.dest = dest;
            this.capacity = capacity;
            this.revIndex = revIndex;
        }
    }

    public static long run(AdjacencyListGraph graph, int source, int sink) {
        int vCount = graph.getVertexCount();

        DynamicArray<DynamicArray<ResEdge>> rGraph = new DynamicArray<>(vCount);
        for (int i = 0; i < vCount; i++) {
            rGraph.add(new DynamicArray<>());
        }

        DynamicArray<DynamicArray<Edge>> adjList = graph.getAdjList();
        for (int u = 0; u < vCount; u++) {
            DynamicArray<Edge> neighbors = adjList.get(u);
            for (int i = 0; i < neighbors.size(); i++) {
                Edge e = neighbors.get(i);
                int dest = e.dest();
                int capacity = e.weight();

                int uRevIndex = rGraph.get(dest).size();
                int destRevIndex = rGraph.get(u).size();

                if (u == dest) destRevIndex++;

                rGraph.get(u).add(new ResEdge(dest, capacity, uRevIndex));
                rGraph.get(dest).add(new ResEdge(u, 0, destRevIndex));
            }
        }

        int[] parent = new int[vCount];
        ResEdge[] edgeTo = new ResEdge[vCount];
        long maxFlow = 0;

        while (true) {
            boolean[] visited = new boolean[vCount];
            if (!dfsList(rGraph, source, sink, visited, parent, edgeTo)) {
                break;
            }

            int pathFlow = Integer.MAX_VALUE;
            for (int v = sink; v != source; v = parent[v]) {
                pathFlow = Math.min(pathFlow, edgeTo[v].capacity);
            }

            for (int v = sink; v != source; v = parent[v]) {
                ResEdge forwardEdge = edgeTo[v];
                ResEdge reverseEdge = rGraph.get(v).get(forwardEdge.revIndex); // Dostęp O(1)

                forwardEdge.capacity -= pathFlow;
                reverseEdge.capacity += pathFlow;
            }

            maxFlow += pathFlow;
        }

        return maxFlow;
    }

    private static boolean dfsList(DynamicArray<DynamicArray<ResEdge>> rGraph, int u, int t, boolean[] visited, int[] parent, ResEdge[] edgeTo) {
        visited[u] = true;
        if (u == t) return true;

        DynamicArray<ResEdge> neighbors = rGraph.get(u);
        for (int i = 0; i < neighbors.size(); i++) {
            ResEdge e = neighbors.get(i);
            if (!visited[e.dest] && e.capacity > 0) {
                parent[e.dest] = u;
                edgeTo[e.dest] = e;
                if (dfsList(rGraph, e.dest, t, visited, parent, edgeTo)) {
                    return true;
                }
            }
        }
        return false;
    }
}