package eu.ksliwinski.utils;

import eu.ksliwinski.models.AdjacencyListGraph;
import eu.ksliwinski.models.AdjacencyMatrixGraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GraphFileReader {

    public record GraphPair(AdjacencyMatrixGraph matrixGraph, AdjacencyListGraph listGraph) {}

    public static GraphPair readFromFile(String filePath, boolean isDirected) throws FileNotFoundException {
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);

        if (!scanner.hasNextInt()) {
            scanner.close();
            throw new IllegalArgumentException("File is empty or is incorrectly formatted");
        }

        int v = scanner.nextInt();
        int e = scanner.nextInt();

        AdjacencyMatrixGraph matrixGraph = new AdjacencyMatrixGraph(v);
        AdjacencyListGraph listGraph = new AdjacencyListGraph(v);

        for (int i = 0; i < e; i++) {
            int src = scanner.nextInt();
            int dest = scanner.nextInt();
            int weight = scanner.nextInt();

            if (src >= v || dest >= v || src < 0 || dest < 0) {
                System.out.println("Warning: Edge (" + src + ", " + dest + ") is ignored (index out of bounds V=" + v + ").");
                continue;
            }

            matrixGraph.addEdge(src, dest, weight, isDirected);
            listGraph.addEdge(src, dest, weight, isDirected);
        }

        scanner.close();
        return new GraphPair(matrixGraph, listGraph);
    }
}
