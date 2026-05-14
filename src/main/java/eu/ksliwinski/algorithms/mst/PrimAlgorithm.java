package eu.ksliwinski.algorithms.mst;

import eu.ksliwinski.models.Edge;
import eu.ksliwinski.datastructures.EdgeMinHeap;
import eu.ksliwinski.models.MstResult;
import eu.ksliwinski.proto.Graph;

import java.util.List;
import java.util.ArrayList;

public class PrimAlgorithm {

    public static MstResult run(Graph graph) {
        int v = graph.getVertexCount();
        boolean[] inMST = new boolean[v];
        long mstWeight = 0;
        int edgesInMst = 0;
        List<Edge> mstEdges = new ArrayList<>(v - 1);

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

    private static void addEdgesToHeap(Graph graph, int vertex, EdgeMinHeap heap, boolean[] inMST) {
        List<Edge> neighbors = graph.getNeighbours(vertex);
        for (Edge edge : neighbors) {
            if (!inMST[edge.dest()]) {
                heap.insert(edge);
            }
        }
    }
}