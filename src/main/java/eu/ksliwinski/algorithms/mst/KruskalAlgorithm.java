package eu.ksliwinski.algorithms.mst;

import eu.ksliwinski.models.Edge;
import eu.ksliwinski.models.MstResult;
import eu.ksliwinski.models.UnionFind;
import eu.ksliwinski.proto.Graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KruskalAlgorithm {

    /**
     * Executes a Kruskal's algorithm on a provided graph.
     *
     * @param graph input graph (cannot be undirected).
     * @return MstResult containing total weight and list of edges of mst.
     */
    public static MstResult run(Graph graph) {
        int v = graph.getVertexCount();
        List<Edge> edges = new ArrayList<>(graph.getAllEdges());

        Collections.sort(edges);

        UnionFind uf = new  UnionFind(v);
        long mstWeight = 0;
        int edgesInMst = 0;
        List<Edge> mstEdges = new ArrayList<>(v - 1);


        for (Edge e : edges) {
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
