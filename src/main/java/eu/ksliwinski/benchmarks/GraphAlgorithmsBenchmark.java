package eu.ksliwinski.benchmarks;

import eu.ksliwinski.algorithms.mst.KruskalAlgorithm;
import eu.ksliwinski.algorithms.mst.PrimAlgorithm;
import eu.ksliwinski.algorithms.shortestpath.BellmanFordAlgorithm;
import eu.ksliwinski.algorithms.shortestpath.DijkstraAlgorithm;
import eu.ksliwinski.models.AdjacencyListGraph;
import eu.ksliwinski.models.AdjacencyMatrixGraph;
import eu.ksliwinski.proto.GraphRepresentation;
import eu.ksliwinski.utils.GraphGenerator;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Fork(value = 1, warmups = 1)
@Warmup(iterations = 1, time = 1)
@Measurement(iterations = 2, time = 1)
@OperationsPerInvocation(GraphAlgorithmsBenchmark.INSTANCES)
public class GraphAlgorithmsBenchmark {

    public static final int INSTANCES = 50;
    @Param({"300"})
    private int v;

    @Param({"20", "60", "99"})
    private int density;

    private AdjacencyMatrixGraph[] matrixGraphsUndirected;
    private AdjacencyListGraph[] listGraphsUndirected;
    private AdjacencyMatrixGraph[] matrixGraphsDirected;
    private AdjacencyListGraph[] listGraphsDirected;

    @Setup(Level.Trial)
    public void setupGraphs() {
        matrixGraphsUndirected = new AdjacencyMatrixGraph[INSTANCES];
        listGraphsUndirected = new AdjacencyListGraph[INSTANCES];
        matrixGraphsDirected = new AdjacencyMatrixGraph[INSTANCES];
        listGraphsDirected = new AdjacencyListGraph[INSTANCES];

        for (int i = 0; i < INSTANCES; i++) {
            matrixGraphsUndirected[i] = (AdjacencyMatrixGraph) GraphGenerator.generate(
                    v, density, GraphRepresentation.ADJACENCY_MATRIX, false);
            listGraphsUndirected[i] = (AdjacencyListGraph) GraphGenerator.generate(
                    v, density, GraphRepresentation.ADJACENCY_LIST, false);

            matrixGraphsDirected[i] = (AdjacencyMatrixGraph) GraphGenerator.generate(
                    v, density, GraphRepresentation.ADJACENCY_MATRIX, true);
            listGraphsDirected[i] = (AdjacencyListGraph) GraphGenerator.generate(
                    v, density, GraphRepresentation.ADJACENCY_LIST, true);
        }
    }

    @Benchmark
    public void kruskalMatrix(Blackhole bh) {
        for (int i = 0; i < INSTANCES; i++) {
            bh.consume(KruskalAlgorithm.run(matrixGraphsUndirected[i]));
        }
    }

    @Benchmark
    public void kruskalList(Blackhole bh) {
        for (int i = 0; i < INSTANCES; i++) {
            bh.consume(KruskalAlgorithm.run(listGraphsUndirected[i]));
        }
    }

    @Benchmark
    public void primMatrix(Blackhole bh) {
        for (int i = 0; i < INSTANCES; i++) {
            bh.consume(PrimAlgorithm.run(matrixGraphsUndirected[i]));
        }
    }

    @Benchmark
    public void primList(Blackhole bh) {
        for (int i = 0; i < INSTANCES; i++) {
            bh.consume(PrimAlgorithm.run(listGraphsUndirected[i]));
        }
    }
    @Benchmark
    public void dijkstraMatrix(Blackhole bh) {
        for (int i = 0; i < INSTANCES; i++) {
            bh.consume(DijkstraAlgorithm.run(matrixGraphsDirected[i], 0));
        }
    }

    @Benchmark
    public void dijkstraList(Blackhole bh) {
        for (int i = 0; i < INSTANCES; i++) {
            bh.consume(DijkstraAlgorithm.run(listGraphsDirected[i], 0));
        }
    }

    @Benchmark
    public void bellmanFordMatrix(Blackhole bh) {
        for (int i = 0; i < INSTANCES; i++) {
            bh.consume(BellmanFordAlgorithm.run(matrixGraphsDirected[i], 0));
        }
    }

    @Benchmark
    public void bellmanFordList(Blackhole bh) {
        for (int i = 0; i < INSTANCES; i++) {
            bh.consume(BellmanFordAlgorithm.run(listGraphsDirected[i], 0));
        }
    }
}