package eu.ksliwinski.models;

public class Edge implements Comparable<Edge> {
    public final int src;
    public final int dest;
    public final int weight;

    public Edge(int src, int dest, int weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }

    @Override
    public int compareTo(Edge o) {
        return Integer.compare(this.weight, o.weight);
    }
}
