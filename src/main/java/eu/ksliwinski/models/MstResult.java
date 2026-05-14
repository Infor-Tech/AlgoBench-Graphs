package eu.ksliwinski.models;

import java.util.List;

/**
 * Result wrapper for Minimum Spanning Tree algorithms.
 */
public record MstResult(long totalWeight, List<Edge> edges) {
}
