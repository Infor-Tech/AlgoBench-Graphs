package eu.ksliwinski.datastructures;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UnionFindTest {

    @Test
    void testInitialState() {
        UnionFind uf = new UnionFind(5);
        for (int i = 0; i < 5; i++) {
            assertEquals(i, uf.find(i), "Initially, each element should be its own root.");
        }
    }

    @Test
    void testUnionSuccessAndCycleDetection() {
        UnionFind uf = new UnionFind(4);

        assertTrue(uf.union(0, 1), "Union should succeed for disjoint sets.");
        assertTrue(uf.union(1, 2), "Union should succeed for disjoint sets.");
        assertFalse(uf.union(0, 2), "Union should fail (return false) because it forms a cycle.");
    }

    @Test
    void testPathCompression() {
        UnionFind uf = new UnionFind(5);

        uf.union(0, 1);
        uf.union(1, 2);
        uf.union(2, 3);
        uf.union(3, 4);

        int root = uf.find(4);

        assertEquals(uf.find(0), root, "Element 4 should belong to the same set as 0.");
        assertEquals(root, uf.find(3));
        assertEquals(root, uf.find(2));
    }
}