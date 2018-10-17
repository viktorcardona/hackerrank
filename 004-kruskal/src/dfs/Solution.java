package dfs;

import java.io.*;
import java.util.*;
import java.util.stream.*;
import static java.util.stream.Collectors.toList;

/**
 * https://www.hackerrank.com/challenges/kruskalmstrsub/problem
 *
 * MST: Minimum Spanning Tree
 *
 * Kruskal's Algorithm
 *
 *  is Cycle Formed ? Using DFS algorithm
 *
 * https://www.youtube.com/watch?v=3rrNH_AizMA
 *
 */

class Result {

    /*
     * Complete the 'kruskals' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts WEIGHTED_INTEGER_GRAPH g as parameter.
     */

    /*
     * For the weighted graph, <name>:
     *
     * 1. The number of nodes is <name>Nodes.
     * 2. The number of edges is <name>Edges.
     * 3. An edge exists between <name>From[i] and <name>To[i]. The weight of the edge is <name>Weight[i].
     *
     */
    public static int kruskals(int gNodes, List<Integer> gFrom, List<Integer> gTo, List<Integer> gWeight) {

        List<Edge> edges = IntStream.range(0, gFrom.size())
                .mapToObj(index -> new Edge(gFrom.get(index), gTo.get(index), gWeight.get(index)))
                .sorted(Comparator.comparingInt(Edge::getWeight))
                .collect(toList());

        int edgesTarget = gNodes - 1;

        Set<Edge> mst = new HashSet<>();
        Map<Integer, Set<Integer>> nodes = new HashMap<>();

        int sizeDisjointSet = gNodes + 1;
        int[] disjointSet = new int[sizeDisjointSet];
        for (int i = 0; i < sizeDisjointSet; i++) {
            disjointSet[i] = -1;
        }

        for (Edge edge: edges){

            if (mst.size() == edgesTarget) {
                break;
            }

            /**
             *
             * is Cycle Formed ? Using DFS algorithm
             *
             */
            if (isCycleFormed(edge.getFrom(), edge.getTo(), nodes, new HashSet<>())) {
                continue;
            }

            mst.add(edge);

            nodes.compute(edge.getFrom(), (k, v) -> addNode(v, edge.getTo()));
            nodes.compute(edge.getTo(),   (k, v) -> addNode(v, edge.getFrom()));

        }

        return mst.stream()
                .mapToInt(Edge::getWeight)
                .sum();
    }

    private static Set<Integer> addNode(Set<Integer> v, Integer to) {
        if (v == null) {
            return new HashSet<>(Arrays.asList(to));
        }
        v.add(to);
        return v;
    }

    /**
     * This is a DFS: Depth First Traversal
     */
    static boolean isCycleFormed(Integer from, Integer to, Map<Integer, Set<Integer>> nodes, Set<Integer> visited) {


        if (visited.contains(from)) {
            return false;
        }

        visited.add(from);

        Set<Integer> adjacent = nodes.getOrDefault(from, Collections.emptySet());

        if (adjacent.isEmpty()) {
            return false;
        }

        if (adjacent.contains(to)) {
            return true;
        }

        for (Integer newFrom: adjacent) {
            boolean isCycle = isCycleFormed(newFrom, to, nodes, visited);
            if (isCycle) {
                return true;
            }
        }

        return false;
    }

    static class Edge {

        Integer from;
        Integer to;
        Integer weight;

        public Edge(Integer from, Integer to, Integer weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        public Integer getFrom() {
            return from;
        }

        public Integer getTo() {
            return to;
        }

        public Integer getWeight() {
            return weight;
        }
    }

}

public class Solution {

    private static final String OUTPUT_PATH = "/output.txt";

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(OUTPUT_PATH));

        String[] gNodesEdges = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int gNodes = Integer.parseInt(gNodesEdges[0]);
        int gEdges = Integer.parseInt(gNodesEdges[1]);

        List<Integer> gFrom = new ArrayList<>();
        List<Integer> gTo = new ArrayList<>();
        List<Integer> gWeight = new ArrayList<>();

        IntStream.range(0, gEdges).forEach(i -> {
            try {
                String[] gFromToWeight = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

                gFrom.add(Integer.parseInt(gFromToWeight[0]));
                gTo.add(Integer.parseInt(gFromToWeight[1]));
                gWeight.add(Integer.parseInt(gFromToWeight[2]));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        int res = Result.kruskals(gNodes, gFrom, gTo, gWeight);

        // Write your code here.
        bufferedWriter.write(String.valueOf(res));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
