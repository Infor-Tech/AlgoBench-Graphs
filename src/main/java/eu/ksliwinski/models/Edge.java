package eu.ksliwinski.models;

public record Edge(int src, int dest, int weight) implements Comparable<Edge> {

    @Override
    public int compareTo(Edge o) {
        return Integer.compare(this.weight, o.weight);
    }
}
