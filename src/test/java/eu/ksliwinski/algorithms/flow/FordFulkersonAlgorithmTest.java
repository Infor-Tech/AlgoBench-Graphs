package eu.ksliwinski.algorithms.flow;

import eu.ksliwinski.models.AdjacencyListGraph;
import eu.ksliwinski.models.AdjacencyMatrixGraph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FordFulkersonAlgorithmTest {

    private AdjacencyMatrixGraph matrixGraph;
    private AdjacencyListGraph listGraph;

    @BeforeEach
    void setUp() {
        int v = 6;
        matrixGraph = new AdjacencyMatrixGraph(v);
        listGraph = new AdjacencyListGraph(v);

        int[][] edges = {
                {0, 1, 16}, {0, 2, 13},
                {1, 2, 10}, {1, 3, 12},
                {2, 1, 4},  {2, 4, 14},
                {3, 2, 9},  {3, 5, 20},
                {4, 3, 7},  {4, 5, 4}
        };

        for (int[] edge : edges) {
            matrixGraph.addEdge(edge[0], edge[1], edge[2], true);
            listGraph.addEdge(edge[0], edge[1], edge[2], true);
        }
    }

    @Test
    void testMaxFlowOnMatrixRepresentation() {
        long maxFlow = FordFulkersonAlgorithm.run(matrixGraph, 0, 5);
        assertEquals(23, maxFlow, "Maximum flow for a AdjacencyMatrixGraph should equal 23.");
    }

    @Test
    void testMaxFlowOnListRepresentation() {
        long maxFlow = FordFulkersonAlgorithm.run(listGraph, 0, 5);
        assertEquals(23, maxFlow, "Maximum flow for a AdjacencyListGraph should equal 23.");
    }

    @Test
    void testNoPathToSinkReturnsZeroFlow() {
        AdjacencyMatrixGraph mg = new AdjacencyMatrixGraph(4);
        AdjacencyListGraph lg = new AdjacencyListGraph(4);

        mg.addEdge(0, 1, 10, true);
        mg.addEdge(1, 2, 10, true);

        lg.addEdge(0, 1, 10, true);
        lg.addEdge(1, 2, 10, true);

        assertEquals(0, FordFulkersonAlgorithm.run(mg, 0, 3), "If there's no path, flow in AdjacencyMatrixGraph should equal 0.");
        assertEquals(0, FordFulkersonAlgorithm.run(lg, 0, 3), "If there's no path, flow in AdjacencyListGraph should equal 0");
    }

    @Test
    void testLinearGraph() {
        AdjacencyMatrixGraph mg = new AdjacencyMatrixGraph(4);
        AdjacencyListGraph lg = new AdjacencyListGraph(4);

        mg.addEdge(0, 1, 10, true);
        mg.addEdge(1, 2, 5, true);
        mg.addEdge(2, 3, 15, true);

        lg.addEdge(0, 1, 10, true);
        lg.addEdge(1, 2, 5, true);
        lg.addEdge(2, 3, 15, true);

        assertEquals(5, FordFulkersonAlgorithm.run(mg, 0, 3), "Edge with a flow of 5 should be a bottleneck (AdjacencyMatrixGraph)");
        assertEquals(5, FordFulkersonAlgorithm.run(lg, 0, 3), "Edge with a flow of 5 should be a bottleneck (AdjacencyListGraph)");
    }
}