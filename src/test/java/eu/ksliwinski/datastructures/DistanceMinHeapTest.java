package eu.ksliwinski.datastructures;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DistanceMinHeapTest {

    @Test
    void testHeapOrderingByDistance() {
        DistanceMinHeap heap = new DistanceMinHeap(10);

        heap.insert(0, 15L);
        heap.insert(1, 5L);
        heap.insert(2, 10L);
        heap.insert(3, 2L);
        heap.insert(4, 8L);

        assertEquals(3, heap.extractMin().vertex);
        assertEquals(1, heap.extractMin().vertex);
        assertEquals(4, heap.extractMin().vertex);
        assertEquals(2, heap.extractMin().vertex);
        assertEquals(0, heap.extractMin().vertex);

        assertTrue(heap.isEmpty(), "Heap should be empty");
    }

    @Test
    void testDuplicateDistances() {
        DistanceMinHeap heap = new DistanceMinHeap(5);

        heap.insert(1, 10L);
        heap.insert(2, 10L);
        heap.insert(3, 5L);

        assertEquals(3, heap.extractMin().vertex);
        assertEquals(10L, heap.extractMin().distance);
        assertEquals(10L, heap.extractMin().distance);
    }
}