package eu.ksliwinski.algorithms.shortestpath;

import eu.ksliwinski.models.AdjacencyListGraph;
import eu.ksliwinski.models.ShortestPathResult;
import eu.ksliwinski.proto.Graph;
import eu.ksliwinski.proto.GraphRepresentation;
import eu.ksliwinski.utils.GraphGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ShortestPathAlgorithmsTest {

    private Graph testGraph;

    @BeforeEach
    void setUp() {
        testGraph = new AdjacencyListGraph(5);

        testGraph.addEdge(0, 1, 10, true);
        testGraph.addEdge(0, 2, 3, true);
        testGraph.addEdge(1, 2, 1, true);
        testGraph.addEdge(1, 3, 2, true);
        testGraph.addEdge(2, 1, 4, true);
        testGraph.addEdge(2, 3, 8, true);
        testGraph.addEdge(2, 4, 2, true);
        testGraph.addEdge(3, 4, 7, true);
        testGraph.addEdge(4, 3, 9, true);
    }

    @Test
    void testDijkstraAlgorithm() {
        ShortestPathResult result = DijkstraAlgorithm.run(testGraph, 0);

        long[] expectedDistances = {0L, 7L, 3L, 9L, 5L};
        int[] expectedPredecessors = {-1, 2, 0, 1, 2};

        assertArrayEquals(expectedDistances, result.distances(), "Incorrect distances returned by Dijkstra algorithm");
        assertArrayEquals(expectedPredecessors, result.predecessors(), "Incorrect predecessors returned by Dijkstra algorithm");
    }

    @Test
    void testBellmanFordAlgorithm() {
        ShortestPathResult result = BellmanFordAlgorithm.run(testGraph, 0);

        long[] expectedDistances = {0L, 7L, 3L, 9L, 5L};
        int[] expectedPredecessors = {-1, 2, 0, 1, 2};

        assertArrayEquals(expectedDistances, result.distances(), "Incorrect distances returned by Bellman-Ford algorithm");
        assertArrayEquals(expectedPredecessors, result.predecessors(), "Incorrect predecessors returned by Bellman-Ford algorithm");
    }

    @Test
    void testUnreachableVertex() {
        Graph disconnectedGraph = new AdjacencyListGraph(3);
        disconnectedGraph.addEdge(0, 1, 5, true);

        ShortestPathResult result = DijkstraAlgorithm.run(disconnectedGraph, 0);

        assertEquals(Long.MAX_VALUE, result.distances()[2], "Distance to unreachable vertex should be Long.MAX_VALUE");
        assertEquals(-1, result.predecessors()[2], "Predecessor of an unreachable vertex should be -1");
    }

    @Test
    void testBothAlgorithmsYieldSameResultForRandomGraph() {
        Graph randomGraph = GraphGenerator.generate(50, 50, GraphRepresentation.ADJACENCY_LIST, true);

        ShortestPathResult dijkstraResult = DijkstraAlgorithm.run(randomGraph, 0);
        ShortestPathResult bellmanFordResult = BellmanFordAlgorithm.run(randomGraph, 0);

        assertArrayEquals(dijkstraResult.distances(), bellmanFordResult.distances(),
                "Both algorithms should return equal distances for a given graph");
    }
}