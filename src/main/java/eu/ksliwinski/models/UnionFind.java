package eu.ksliwinski.models;

/**
 * Disjoint-set data structure
 */
public class UnionFind {
    private final int[] parent;
    private final int[] rank;

    /**
     * Initializes data structure for a given number of vertices
     * @param v number of vertices
     */
    public UnionFind(int v) {
        parent = new int[v];
        rank = new int[v];

        for (int i = 0; i < v; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    /**
     * Finds the representative (root) of the set containing a given element.
     * Uses path compression optimization.
     * @param i element
     */
    public int find(int i) {
        int root = i;

        while (root != parent[root]) {
            root = parent[root];
        }

        int curr = i;
        while (curr != root) {
            int next = parent[curr];
            parent[curr] = root;
            curr = next;
        }

        return root;
    }

    /**
     * Unites two disjoint sets containing 2 elements.
     * Uses union by rank optimization.
     * @param x element
     * @param y element
     * @return true if union was successful, false if they are already in the same set.
     */
    public boolean union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);

        if (rootX == rootY) return false;

        if (rank[rootX] < rank[rootY]) {
            parent[rootX] = rootY;
        } else if (rank[rootX] > rank[rootY]) {
            parent[rootY] = rootX;
        } else {
            parent[rootY] = rootX;
            rank[rootX]++;
        }

        return true;
    }

}
