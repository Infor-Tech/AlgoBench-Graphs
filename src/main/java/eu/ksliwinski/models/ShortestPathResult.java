package eu.ksliwinski.models;

/**
 * Result wrapper for Shortest Path algorithms.
 */
public record ShortestPathResult(long[] distances, int[] predecessors) {
}
