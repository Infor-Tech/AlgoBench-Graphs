package eu.ksliwinski.proto;

import eu.ksliwinski.datastructures.DynamicArray;
import eu.ksliwinski.models.Edge;

import java.util.List;

public interface Graph {
    int getVertexCount();
    int getEdgeCount();
    void addEdge(int src, int dest, int weight, boolean directed);

    DynamicArray<Edge> getNeighbours(int vertex);
    DynamicArray<Edge> getAllEdges();
}
