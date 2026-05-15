package eu.ksliwinski.algorithms.mst;

import eu.ksliwinski.datastructures.DynamicArray;
import eu.ksliwinski.models.AdjacencyListGraph;
import eu.ksliwinski.models.AdjacencyMatrixGraph;
import eu.ksliwinski.models.Edge;
import eu.ksliwinski.datastructures.EdgeMinHeap;
import eu.ksliwinski.models.MstResult;

public class PrimAlgorithm {

    public static MstResult run(AdjacencyMatrixGraph graph) {
        int v = graph.getVertexCount();
        boolean[] inMST = new boolean[v];
        long mstWeight = 0;
        int edgesInMst = 0;
        DynamicArray<Edge> mstEdges = new DynamicArray<>(v - 1);

        EdgeMinHeap minHeap = new EdgeMinHeap(graph.getEdgeCount());

        int startVertex = 0;
        inMST[startVertex] = true;
        addEdgesToHeap(graph, startVertex, minHeap, inMST);

        while (!minHeap.isEmpty() && edgesInMst < v - 1) {
            Edge minEdge = minHeap.extractMin();

            if (inMST[minEdge.dest()]) {
                continue;
            }

            inMST[minEdge.dest()] = true;
            mstWeight += minEdge.weight();
            mstEdges.add(minEdge);
            edgesInMst++;

            addEdgesToHeap(graph, minEdge.dest(), minHeap, inMST);
        }

        return new MstResult(mstWeight, mstEdges);
    }

    private static void addEdgesToHeap(AdjacencyMatrixGraph graph, int vertex, EdgeMinHeap heap, boolean[] inMST) {
        int[][] matrix = graph.getMatrix();
        int v = graph.getVertexCount();

        for (int dest = 0; dest < v; dest++) {
            int weight = matrix[vertex][dest];
            if (weight > 0 && !inMST[dest]) {
                heap.insert(new Edge(vertex, dest, weight));
            }
        }
    }

    public static MstResult run(AdjacencyListGraph graph) {
        int v = graph.getVertexCount();
        boolean[] inMST = new boolean[v];
        long mstWeight = 0;
        int edgesInMst = 0;
        DynamicArray<Edge> mstEdges = new DynamicArray<>(v - 1);

        EdgeMinHeap minHeap = new EdgeMinHeap(graph.getEdgeCount());

        int startVertex = 0;
        inMST[startVertex] = true;
        addEdgesToHeap(graph, startVertex, minHeap, inMST);

        while (!minHeap.isEmpty() && edgesInMst < v - 1) {
            Edge minEdge = minHeap.extractMin();

            if (inMST[minEdge.dest()]) {
                continue;
            }

            inMST[minEdge.dest()] = true;
            mstWeight += minEdge.weight();
            mstEdges.add(minEdge);
            edgesInMst++;

            addEdgesToHeap(graph, minEdge.dest(), minHeap, inMST);
        }

        return new MstResult(mstWeight, mstEdges);
    }

    private static void addEdgesToHeap(AdjacencyListGraph graph, int vertex, EdgeMinHeap heap, boolean[] inMST) {
        DynamicArray<Edge> neighbors = graph.getAdjList().get(vertex);

        for (int i = 0; i < neighbors.size(); i++) {
            Edge edge = neighbors.get(i);
            if (!inMST[edge.dest()]) {
                heap.insert(edge);
            }
        }
    }
}