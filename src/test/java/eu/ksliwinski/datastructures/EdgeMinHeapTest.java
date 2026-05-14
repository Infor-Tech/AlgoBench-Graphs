package eu.ksliwinski.datastructures;

import eu.ksliwinski.models.Edge;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EdgeMinHeapTest {

    @Test
    void testHeapOrdering() {
        EdgeMinHeap heap = new EdgeMinHeap(10);

        heap.insert(new Edge(0, 1, 15));
        heap.insert(new Edge(0, 2, 5));
        heap.insert(new Edge(1, 2, 10));
        heap.insert(new Edge(2, 3, 2));
        heap.insert(new Edge(3, 4, 8));

        // Extracting minimums should yield strictly ascending weights
        assertEquals(2, heap.extractMin().weight());
        assertEquals(5, heap.extractMin().weight());
        assertEquals(8, heap.extractMin().weight());
        assertEquals(10, heap.extractMin().weight());
        assertEquals(15, heap.extractMin().weight());

        assertTrue(heap.isEmpty(), "Heap should be empty after extracting all elements.");
    }

    @Test
    void testExtractFromEmptyHeapThrowsException() {
        EdgeMinHeap heap = new EdgeMinHeap(5);

        IllegalStateException exception = assertThrows(IllegalStateException.class, heap::extractMin);
        assertEquals("Heap is empty", exception.getMessage());
    }
}