package eu.ksliwinski.algorithms.mst;

import eu.ksliwinski.datastructures.DynamicArray;
import eu.ksliwinski.models.*;
import eu.ksliwinski.datastructures.UnionFind;
import eu.ksliwinski.utils.QuickSort;

public class KruskalAlgorithm {

    /**
     * Executes a Kruskal's algorithm on a provided graph.
     *
     * @param graph input graph (AdjacencyMatrixGraph).
     * @return MstResult containing total weight and list of edges of mst.
     */
    public static MstResult run(AdjacencyMatrixGraph graph) {
        DynamicArray<Edge> edges = graph.getAllEdges();
        return execute(graph.getVertexCount(), edges);
    }

    /**
     * Executes a Kruskal's algorithm on a provided graph.
     *
     * @param graph input graph (AdjacencyListGraph).
     * @return MstResult containing total weight and list of edges of mst.
     */
    public static MstResult run(AdjacencyListGraph graph) {
        DynamicArray<Edge> edges = graph.getAllEdges();
        return execute(graph.getVertexCount(), edges);
    }

    /**
     * Executes a Kruskal's algorithm on a provided graph.
     *
     * @param graph input graph (AdjacencyListGraph).
     * @return MstResult containing total weight and list of edges of mst.
     */
    public static MstResult run(IncidenceMatrixGraph graph) {
        DynamicArray<Edge> edges = graph.getAllEdges();
        return execute(graph.getVertexCount(), edges);
    }

    private static MstResult execute(int v, DynamicArray<Edge> edges) {
        QuickSort.sort(edges);

        UnionFind uf = new UnionFind(v);
        long mstWeight = 0;
        int edgesInMst = 0;

        DynamicArray<Edge> mstEdges = new DynamicArray<>(v - 1);

        for (int i = 0; i < edges.size(); i++) {
            Edge e = edges.get(i);
            if (uf.union(e.src(), e.dest())) {
                mstWeight += e.weight();
                edgesInMst++;
                mstEdges.add(e);

                if(edgesInMst == v - 1) break;
            }
        }

        return new MstResult(mstWeight, mstEdges);
    }
}
