package eu.ksliwinski.algorithms.shortestpath;

import eu.ksliwinski.models.AdjacencyListGraph;
import eu.ksliwinski.models.AdjacencyMatrixGraph;
import eu.ksliwinski.models.ShortestPathResult;
import eu.ksliwinski.proto.GraphRepresentation;
import eu.ksliwinski.utils.GraphGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ShortestPathAlgorithmsTest {

    private AdjacencyListGraph testAdjacencyListGraph;
    private AdjacencyMatrixGraph testAdjacencyMatrixGraph;

    @BeforeEach
    void setUp() {
        testAdjacencyListGraph = new AdjacencyListGraph(5);

        testAdjacencyListGraph.addEdge(0, 1, 10, true);
        testAdjacencyListGraph.addEdge(0, 2, 3, true);
        testAdjacencyListGraph.addEdge(1, 2, 1, true);
        testAdjacencyListGraph.addEdge(1, 3, 2, true);
        testAdjacencyListGraph.addEdge(2, 1, 4, true);
        testAdjacencyListGraph.addEdge(2, 3, 8, true);
        testAdjacencyListGraph.addEdge(2, 4, 2, true);
        testAdjacencyListGraph.addEdge(3, 4, 7, true);
        testAdjacencyListGraph.addEdge(4, 3, 9, true);

        testAdjacencyMatrixGraph = new AdjacencyMatrixGraph(5);

        testAdjacencyMatrixGraph.addEdge(0, 1, 10, true);
        testAdjacencyMatrixGraph.addEdge(0, 2, 3, true);
        testAdjacencyMatrixGraph.addEdge(1, 2, 1, true);
        testAdjacencyMatrixGraph.addEdge(1, 3, 2, true);
        testAdjacencyMatrixGraph.addEdge(2, 1, 4, true);
        testAdjacencyMatrixGraph.addEdge(2, 3, 8, true);
        testAdjacencyMatrixGraph.addEdge(2, 4, 2, true);
        testAdjacencyMatrixGraph.addEdge(3, 4, 7, true);
        testAdjacencyMatrixGraph.addEdge(4, 3, 9, true);
    }

    @Test
    void testDijkstraAlgorithm() {
        long[] expectedDistances = {0L, 7L, 3L, 9L, 5L};
        int[] expectedPredecessors = {-1, 2, 0, 1, 2};

        ShortestPathResult result = DijkstraAlgorithm.run(testAdjacencyListGraph, 0);
        assertArrayEquals(expectedDistances, result.distances(), "Incorrect distances returned by Dijkstra algorithm (AdjacencyListGraph)");
        assertArrayEquals(expectedPredecessors, result.predecessors(), "Incorrect predecessors returned by Dijkstra algorithm (AdjacencyListGraph)");

        result = DijkstraAlgorithm.run(testAdjacencyMatrixGraph, 0);
        assertArrayEquals(expectedDistances, result.distances(), "Incorrect distances returned by Dijkstra algorithm (AdjacencyMatrixGraph)");
        assertArrayEquals(expectedPredecessors, result.predecessors(), "Incorrect predecessors returned by Dijkstra algorithm (AdjacencyMatrixGraph)");
    }

    @Test
    void testBellmanFordAlgorithm() {
        long[] expectedDistances = {0L, 7L, 3L, 9L, 5L};
        int[] expectedPredecessors = {-1, 2, 0, 1, 2};

        ShortestPathResult result = BellmanFordAlgorithm.run(testAdjacencyListGraph, 0);
        assertArrayEquals(expectedDistances, result.distances(), "Incorrect distances returned by Bellman-Ford algorithm (AdjacencyListGraph)");
        assertArrayEquals(expectedPredecessors, result.predecessors(), "Incorrect predecessors returned by Bellman-Ford algorithm (AdjacencyListGraph)");

        result = BellmanFordAlgorithm.run(testAdjacencyMatrixGraph, 0);
        assertArrayEquals(expectedDistances, result.distances(), "Incorrect distances returned by Bellman-Ford algorithm (AdjacencyMatrixGraph)");
        assertArrayEquals(expectedPredecessors, result.predecessors(), "Incorrect predecessors returned by Bellman-Ford algorithm (AdjacencyMatrixGraph)");
    }

    @Test
    void testUnreachableVertex() {
        AdjacencyListGraph disconnectedListGraph = new AdjacencyListGraph(3);
        disconnectedListGraph.addEdge(0, 1, 5, true);
        AdjacencyMatrixGraph disconnectedMatrixGraph = new AdjacencyMatrixGraph(3);
        disconnectedListGraph.addEdge(0, 1, 5, true);

        ShortestPathResult result = DijkstraAlgorithm.run(disconnectedListGraph, 0);
        assertEquals(Long.MAX_VALUE, result.distances()[2], "Distance to unreachable vertex should be Long.MAX_VALUE (AdjacencyListGraph)");
        assertEquals(-1, result.predecessors()[2], "Predecessor of an unreachable vertex should be -1 (AdjacencyListGraph)");

        result = DijkstraAlgorithm.run(disconnectedMatrixGraph, 0);
        assertEquals(Long.MAX_VALUE, result.distances()[2], "Distance to unreachable vertex should be Long.MAX_VALUE (AdjacencyMatrixGraph)");
        assertEquals(-1, result.predecessors()[2], "Predecessor of an unreachable vertex should be -1 (AdjacencyMatrixGraph)");
    }

    @Test
    void testBothAlgorithmsYieldSameResultForRandomGraph() {
        AdjacencyListGraph randomListGraph = (AdjacencyListGraph) GraphGenerator.generate(50, 50, GraphRepresentation.ADJACENCY_LIST, true);
        ShortestPathResult dijkstraResult = DijkstraAlgorithm.run(randomListGraph, 0);
        ShortestPathResult bellmanFordResult = BellmanFordAlgorithm.run(randomListGraph, 0);

        assertArrayEquals(dijkstraResult.distances(), bellmanFordResult.distances(),
                "Both algorithms should return equal distances for a given graph (AdjacencyListGraph)");

        AdjacencyMatrixGraph randomMatrixGraph = (AdjacencyMatrixGraph) GraphGenerator.generate(50, 50, GraphRepresentation.ADJACENCY_MATRIX, true);
        dijkstraResult = DijkstraAlgorithm.run(randomMatrixGraph, 0);
        bellmanFordResult = BellmanFordAlgorithm.run(randomMatrixGraph, 0);

        assertArrayEquals(dijkstraResult.distances(), bellmanFordResult.distances(),
                "Both algorithms should return equal distances for a given graph (AdjacencyListGraph)");
    }
}