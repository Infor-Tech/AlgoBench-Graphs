package eu.ksliwinski.utils;

import eu.ksliwinski.models.*;
import eu.ksliwinski.proto.Graph;
import eu.ksliwinski.proto.GraphRepresentation;
import org.junit.jupiter.api.Test;
import java.util.LinkedList;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

class GraphGeneratorTest {

    @Test
    void testListGraphCreation() {
        int v = 10;
        int density = 50;

        Graph graph = GraphGenerator.generate(v, density, GraphRepresentation.ADJACENCY_LIST, false);

        assertInstanceOf(AdjacencyListGraph.class, graph, "Generator should return an instance of AdjacencyListGraph");
        assertEquals(v, graph.getVertexCount(), "Number of vertices should equal 10");
    }

    @Test
    void testMatrixGraphCreation() {
        int v = 10;
        int density = 25;

        Graph graph = GraphGenerator.generate(v, density, GraphRepresentation.INCIDENCE_MATRIX, false);

        assertInstanceOf(IncidenceMatrixGraph.class, graph, "Generator should return an instance of IncidenceMatrixGraph");
        assertEquals(v, graph.getVertexCount(), "Number of vertices should equal 10");
    }

    @Test
    void testEdgeCountForDirectedGraph() {
        int v = 10;
        int density = 50;
        // Max edges = V * (V - 1) = 10 * 9 = 90 => 50% of it = 45

        Graph graph = GraphGenerator.generate(v, density, GraphRepresentation.INCIDENCE_MATRIX, false);
        assertEquals(22, graph.getEdgeCount(), "For V=10, density = 50%, directed graph should have 45 edges (Incidence Matrix)");

        graph = GraphGenerator.generate(v, density, GraphRepresentation.ADJACENCY_LIST, true);
        assertEquals(45, graph.getEdgeCount(), "For V=10, density = 50%, directed graph should have 45 edges (Adjacency List)");
    }

    @Test
    void testEdgeCountForUndirectedGraph() {
        int v = 10;
        int density = 50;
        // Max edges = V * (V - 1) / 2 = 45.
        // 50% of 45 = 22 (floor)

        Graph graph = GraphGenerator.generate(v, density, GraphRepresentation.INCIDENCE_MATRIX, false);
        assertEquals(22, graph.getEdgeCount(), "For V=10, density = 50%, directed graph should have 45 edges (Incidence Matrix)");

        graph = GraphGenerator.generate(v, density, GraphRepresentation.ADJACENCY_LIST, false);
        assertEquals(22, graph.getEdgeCount(), "For V=10, density = 50%, directed graph should have 22 edges (Adjacency List)");
    }

    @Test
    void testMinimumEdgesForConnectedness() {
        int v = 100;
        int density = 0;

        Graph graph = GraphGenerator.generate(v, density, GraphRepresentation.INCIDENCE_MATRIX, false);
        assertEquals(99, graph.getEdgeCount(), "For 0% density, graph must have V-1 edges to be connected (Incidence Matrix)");

        graph = GraphGenerator.generate(v, density, GraphRepresentation.ADJACENCY_LIST, false);
        assertEquals(99, graph.getEdgeCount(), "For 0% density, graph must have V-1 edges to be connected (Adjacency List)");
    }

    @Test
    void testGraphIsConnected() {
        int v = 50;
        int density = 25;


        Graph graph = GraphGenerator.generate(v, density, GraphRepresentation.INCIDENCE_MATRIX, false);
        assertTrue(isConnected(graph), "Generated undirected graph must be connected (Incidence Matrix)");

        graph = GraphGenerator.generate(v, density, GraphRepresentation.ADJACENCY_LIST, false);
        assertTrue(isConnected(graph), "Generated undirected graph must be connected (Adjacency List)");
    }

    @Test
    void testInvalidRepresentationThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> GraphGenerator.generate(10, 50, null, false));
    }

    /**
     * Checks the graph's connectivity using a breadth-first search (BFS).
     */
    private boolean isConnected(Graph graph) {
        int v = graph.getVertexCount();
        boolean[] visited = new boolean[v];
        Queue<Integer> queue = new LinkedList<>();

        queue.add(0);
        visited[0] = true;
        int visitedCount = 1;

        while (!queue.isEmpty()) {
            int current = queue.poll();

            for (Edge edge : graph.getNeighbours(current)) {
                int neighbor = edge.dest();
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    visitedCount++;
                    queue.add(neighbor);
                }
            }
        }

        return visitedCount == v;
    }
}