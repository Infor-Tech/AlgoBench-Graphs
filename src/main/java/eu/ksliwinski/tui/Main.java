package eu.ksliwinski.tui;

import eu.ksliwinski.algorithms.flow.FordFulkersonAlgorithm;
import eu.ksliwinski.algorithms.mst.KruskalAlgorithm;
import eu.ksliwinski.algorithms.mst.PrimAlgorithm;
import eu.ksliwinski.algorithms.shortestpath.BellmanFordAlgorithm;
import eu.ksliwinski.algorithms.shortestpath.DijkstraAlgorithm;
import eu.ksliwinski.benchmarks.GraphAlgorithmsBenchmark;
import eu.ksliwinski.models.AdjacencyListGraph;
import eu.ksliwinski.models.AdjacencyMatrixGraph;
import eu.ksliwinski.models.MstResult;
import eu.ksliwinski.models.ShortestPathResult;
import eu.ksliwinski.proto.GraphRepresentation;
import eu.ksliwinski.utils.GraphFileReader;
import eu.ksliwinski.utils.GraphGenerator;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {

    private static AdjacencyMatrixGraph currentMatrixGraph = null;
    private static AdjacencyListGraph currentListGraph = null;
    private static boolean isDirected = false;

    static void main() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("=================================================");
        System.out.println("Badanie efektywności algorytmów grafowych");
        System.out.println("=================================================");

        while (running) {
            System.out.println("\n-----------------------");
            System.out.println("1. Wygeneruj losowy graf");
            System.out.println("2. Wczytaj graf z pliku");
            System.out.println("3. Wyświetl graf (Macierz i Lista)");
            System.out.println("4. Uruchom algorytmy MST (Kruskal & Prim)");
            System.out.println("5. Uruchom algorytmy Najkrótszej Ścieżki (Dijkstra & Bellman-Ford)");
            System.out.println("6. Uruchom algorytm Maksymalnego Przepływu (Ford-Fulkerson)");
            System.out.println("7. Uruchom benchmarki JMH");
            System.out.println("0. Wyjście");
            System.out.print("Wybierz opcję: ");

            int choice = -1;
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
            } else {
                scanner.next();
            }

            switch (choice) {
                case 1 -> generateGraphMenu(scanner);
                case 2 -> loadGraphFromFileMenu(scanner);
                case 3 -> displayGraphs();
                case 4 -> runMstAlgorithms();
                case 5 -> runShortestPathAlgorithms(scanner);
                case 6 -> runMaxFlowAlgorithm(scanner); // <-- PODPIĘCIE
                case 7 -> {
                    runJmhBenchmarks();
                    return;
                }
                case 0 -> running = false;
                default -> System.out.println("Nieprawidłowa opcja. Spróbuj ponownie.");
            }
        }
        scanner.close();
    }

    private static void generateGraphMenu(Scanner scanner) {
        System.out.print("Podaj liczbę wierzchołków (V): ");
        int v = scanner.nextInt();
        System.out.print("Podaj gęstość w procentach (np. 20, 60, 99): ");
        int density = scanner.nextInt();
        System.out.print("Czy graf ma być skierowany? (1 - tak, 0 - nie): ");
        isDirected = scanner.nextInt() == 1;

        currentMatrixGraph = (AdjacencyMatrixGraph) GraphGenerator.generate(v, density, GraphRepresentation.ADJACENCY_MATRIX, isDirected);
        currentListGraph = (AdjacencyListGraph) GraphGenerator.generate(v, density, GraphRepresentation.ADJACENCY_LIST, isDirected);

        System.out.println("Grafy zostały wygenerowane pomyślnie!");
    }

    private static void displayGraphs() {
        if (currentMatrixGraph == null) {
            System.out.println("Najpierw wygeneruj graf!");
            return;
        }
        currentMatrixGraph.print();
        System.out.println();
        currentListGraph.print();
    }

    private static void runMstAlgorithms() {
        if (currentMatrixGraph == null) {
            System.out.println("Najpierw wygeneruj graf!");
            return;
        }
        if (isDirected) {
            System.out.println("BŁĄD: Algorytmy MST wymagają grafu NIESKIEROWANEGO.");
            return;
        }

        System.out.println("\n--- Wyniki dla Macierzy Sąsiedztwa ---");
        MstResult kruskalMatrix = KruskalAlgorithm.run(currentMatrixGraph);
        System.out.println("Kruskal waga: " + kruskalMatrix.totalWeight());

        MstResult primMatrix = PrimAlgorithm.run(currentMatrixGraph);
        System.out.println("Prim waga: " + primMatrix.totalWeight());

        System.out.println("\n--- Wyniki dla Listy Sąsiedztwa ---");
        MstResult kruskalList = KruskalAlgorithm.run(currentListGraph);
        System.out.println("Kruskal waga: " + kruskalList.totalWeight());

        MstResult primList = PrimAlgorithm.run(currentListGraph);
        System.out.println("Prim waga: " + primList.totalWeight());
    }

    private static void runShortestPathAlgorithms(Scanner scanner) {
        if (currentMatrixGraph == null) {
            System.out.println("Najpierw wygeneruj graf!");
            return;
        }

        System.out.print("Podaj wierzchołek startowy (0 - " + (currentMatrixGraph.getVertexCount() - 1) + "): ");
        int startVertex = scanner.nextInt();

        System.out.println("\n--- Wyniki dla Macierzy Sąsiedztwa ---");
        ShortestPathResult dijkstraMatrix = DijkstraAlgorithm.run(currentMatrixGraph, startVertex);
        System.out.println("Dijkstra dystans do ost. wierzchołka: " + dijkstraMatrix.distances()[currentMatrixGraph.getVertexCount() - 1]);

        ShortestPathResult bellmanMatrix = BellmanFordAlgorithm.run(currentMatrixGraph, startVertex);
        System.out.println("Bellman-Ford dystans do ost. wierzchołka: " + bellmanMatrix.distances()[currentMatrixGraph.getVertexCount() - 1]);

        System.out.println("\n--- Wyniki dla Listy Sąsiedztwa ---");
        ShortestPathResult dijkstraList = DijkstraAlgorithm.run(currentListGraph, startVertex);
        System.out.println("Dijkstra dystans do ost. wierzchołka: " + dijkstraList.distances()[currentListGraph.getVertexCount() - 1]);

        ShortestPathResult bellmanList = BellmanFordAlgorithm.run(currentListGraph, startVertex);
        System.out.println("Bellman-Ford dystans do ost. wierzchołka: " + bellmanList.distances()[currentListGraph.getVertexCount() - 1]);
    }

    private static void runMaxFlowAlgorithm(Scanner scanner) {
        if (currentMatrixGraph == null) {
            System.out.println("Najpierw wygeneruj lub wczytaj graf!");
            return;
        }

        if (!isDirected) {
            System.out.println("UWAGA: Algorytm maksymalnego przepływu zazwyczaj stosuje się dla sieci skierowanych.");
            System.out.println("Dla grafu nieskierowanego każda krawędź działa jak rura dwukierunkowa.");
        }

        int maxV = currentMatrixGraph.getVertexCount() - 1;
        System.out.print("Podaj wierzchołek źródłowy (source) (0 - " + maxV + "): ");
        int source = scanner.nextInt();

        System.out.print("Podaj wierzchołek docelowy (sink) (0 - " + maxV + "): ");
        int sink = scanner.nextInt();

        if (source < 0 || source > maxV || sink < 0 || sink > maxV) {
            System.out.println("BŁĄD: Podano wierzchołki spoza zakresu!");
            return;
        }

        if (source == sink) {
            System.out.println("BŁĄD: Źródło i ujście nie mogą być tym samym wierzchołkiem.");
            return;
        }

        System.out.println("\n--- Wyniki dla Macierzy Sąsiedztwa ---");
        long maxFlowMatrix = FordFulkersonAlgorithm.run(currentMatrixGraph, source, sink);
        System.out.println("Maksymalny przepływ: " + maxFlowMatrix);

        System.out.println("\n--- Wyniki dla Listy Sąsiedztwa ---");
        long maxFlowList = FordFulkersonAlgorithm.run(currentListGraph, source, sink);
        System.out.println("Maksymalny przepływ: " + maxFlowList);
    }

    private static void runJmhBenchmarks() {
        System.out.println("\nRozpoczynam benchmarki JMH... (To może potrwać bardzo długo!)");
        System.out.println("Upewnij się, że laptop jest podłączony do zasilania.");
        try {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = "graph_benchmark_" + timestamp + ".csv";

            Options opt = new OptionsBuilder()
                    .include(GraphAlgorithmsBenchmark.class.getSimpleName())
                    .resultFormat(ResultFormatType.CSV)
                    .result(fileName)
                    .forks(1)
                    .build();

            new Runner(opt).run();
        } catch (Exception e) {
            System.out.println("Wystąpił błąd podczas uruchamiania JMH: " + e.getMessage());
        }
    }

    private static void loadGraphFromFileMenu(Scanner scanner) {
        System.out.print("Podaj ścieżkę do pliku (np. graph.txt lub C:\\ścieżka\\do\\graph.txt): ");
        String filePath = scanner.next();

        System.out.print("Czy graf ma być skierowany? (1 - tak, 0 - nie): ");
        isDirected = scanner.nextInt() == 1;

        try {
            GraphFileReader.GraphPair pair = GraphFileReader.readFromFile(filePath, isDirected);
            currentMatrixGraph = pair.matrixGraph();
            currentListGraph = pair.listGraph();

            System.out.println("Pomyślnie wczytano graf z pliku: " + filePath);
        } catch (java.io.FileNotFoundException e) {
            System.out.println("BŁĄD: Nie znaleziono pliku pod podaną ścieżką!");
        } catch (Exception e) {
            System.out.println("BŁĄD: Wystąpił problem podczas przetwarzania pliku: " + e.getMessage());
        }
    }
}
