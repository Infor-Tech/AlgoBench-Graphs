package eu.ksliwinski.models;

import eu.ksliwinski.datastructures.DynamicArray;

/**
 * Result wrapper for Minimum Spanning Tree algorithms.
 */
public record MstResult(long totalWeight, DynamicArray<Edge> edges) {
}
