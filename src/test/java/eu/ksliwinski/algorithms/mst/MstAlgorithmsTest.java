package eu.ksliwinski.algorithms.mst;

import eu.ksliwinski.models.AdjacencyListGraph;
import eu.ksliwinski.models.AdjacencyMatrixGraph;
import eu.ksliwinski.models.MstResult;
import eu.ksliwinski.proto.GraphRepresentation;
import eu.ksliwinski.utils.GraphGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MstAlgorithmsTest {

    private AdjacencyListGraph testAdjacencyListGraph;
    private AdjacencyMatrixGraph testAdjacencyMatrixGraph;

    @BeforeEach
    void setUp() {
        testAdjacencyListGraph = new AdjacencyListGraph(4);

        testAdjacencyListGraph.addEdge(0, 1, 10, false);
        testAdjacencyListGraph.addEdge(0, 2, 6, false);
        testAdjacencyListGraph.addEdge(0, 3, 5, false);
        testAdjacencyListGraph.addEdge(1, 3, 15, false);
        testAdjacencyListGraph.addEdge(2, 3, 4, false);

        testAdjacencyMatrixGraph = new AdjacencyMatrixGraph(4);
        testAdjacencyMatrixGraph.addEdge(0, 1, 10, false);
        testAdjacencyMatrixGraph.addEdge(0, 2, 6, false);
        testAdjacencyMatrixGraph.addEdge(0, 3, 5, false);
        testAdjacencyMatrixGraph.addEdge(1, 3, 15, false);
        testAdjacencyMatrixGraph.addEdge(2, 3, 4, false);
    }

    @Test
    void testKruskalAlgorithm() {
        MstResult result = KruskalAlgorithm.run(testAdjacencyListGraph);
        assertEquals(19, result.totalWeight(), "Kruskal's MST total weight should be 19. (testAdjacencyListGraph)");
        assertEquals(3, result.edges().size(), "MST should have exactly V - 1 edges. (testAdjacencyListGraph)");

        result = KruskalAlgorithm.run(testAdjacencyMatrixGraph);
        assertEquals(19, result.totalWeight(), "Kruskal's MST total weight should be 19. (testAdjacencyMatrixGraph)");
        assertEquals(3, result.edges().size(), "MST should have exactly V - 1 edges. (testAdjacencyMatrixGraph)");
    }

    @Test
    void testPrimAlgorithm() {
        MstResult result = PrimAlgorithm.run(testAdjacencyListGraph);
        assertEquals(19, result.totalWeight(), "Prim's MST total weight should be 19. (testAdjacencyListGraph)");
        assertEquals(3, result.edges().size(), "MST should have exactly V - 1 edges. (testAdjacencyListGraph)");

        result = PrimAlgorithm.run(testAdjacencyMatrixGraph);
        assertEquals(19, result.totalWeight(), "Prim's MST total weight should be 19. (testAdjacencyMatrixGraph)");
        assertEquals(3, result.edges().size(), "MST should have exactly V - 1 edges. (testAdjacencyMatrixGraph)");
    }

    @Test
    void testBothAlgorithmsYieldSameResultForRandomGraph() {
        AdjacencyListGraph randomListGraph = (AdjacencyListGraph) GraphGenerator.generate(50, 50, GraphRepresentation.ADJACENCY_LIST, false);

        MstResult kruskalResult = KruskalAlgorithm.run(randomListGraph);
        MstResult primResult = PrimAlgorithm.run(randomListGraph);
        assertEquals(kruskalResult.totalWeight(), primResult.totalWeight(),
                "Prim and Kruskal must compute the identical minimum spanning tree weight for the same graph. (AdjacencyListGraph)");

        AdjacencyMatrixGraph randomMatrixGraph = (AdjacencyMatrixGraph) GraphGenerator.generate(50, 50, GraphRepresentation.ADJACENCY_MATRIX, false);
        kruskalResult = KruskalAlgorithm.run(randomMatrixGraph);
        primResult = PrimAlgorithm.run(randomMatrixGraph);
        assertEquals(kruskalResult.totalWeight(), primResult.totalWeight(),
                "Prim and Kruskal must compute the identical minimum spanning tree weight for the same graph. (AdjacencyMatrixGraph)");
    }
}
