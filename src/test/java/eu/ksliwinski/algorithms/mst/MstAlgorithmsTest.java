package eu.ksliwinski.algorithms.mst;

import eu.ksliwinski.models.AdjacencyListGraph;
import eu.ksliwinski.models.MstResult;
import eu.ksliwinski.proto.Graph;
import eu.ksliwinski.proto.GraphRepresentation;
import eu.ksliwinski.utils.GraphGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MstAlgorithmsTest {

    private Graph testGraph;

    @BeforeEach
    void setUp() {
        testGraph = new AdjacencyListGraph(4);

        testGraph.addEdge(0, 1, 10, false);
        testGraph.addEdge(0, 2, 6, false);
        testGraph.addEdge(0, 3, 5, false);
        testGraph.addEdge(1, 3, 15, false);
        testGraph.addEdge(2, 3, 4, false);
    }

    @Test
    void testKruskalAlgorithm() {
        MstResult result = KruskalAlgorithm.run(testGraph);
        assertEquals(19, result.totalWeight(), "Kruskal's MST total weight should be 19.");
        assertEquals(3, result.edges().size(), "MST should have exactly V - 1 edges.");
    }

    @Test
    void testPrimAlgorithm() {
        MstResult result = PrimAlgorithm.run(testGraph);
        assertEquals(19, result.totalWeight(), "Prim's MST total weight should be 19.");
        assertEquals(3, result.edges().size(), "MST should have exactly V - 1 edges.");
    }

    @Test
    void testBothAlgorithmsYieldSameResultForRandomGraph() {
        Graph randomGraph = GraphGenerator.generate(50, 50, GraphRepresentation.ADJACENCY_LIST, false);

        MstResult kruskalResult = KruskalAlgorithm.run(randomGraph);
        MstResult primResult = PrimAlgorithm.run(randomGraph);

        assertEquals(kruskalResult.totalWeight(), primResult.totalWeight(),
                "Prim and Kruskal must compute the identical minimum spanning tree weight for the same graph.");
    }
}
