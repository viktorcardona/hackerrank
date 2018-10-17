package findunion;

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
 *  is Cycle Formed ? Using Find-Union algorithm (disjoint data structure)
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
             * is Cycle Formed ? Using Find-Union algorithm (disjoint data structure)
             *
             */
            if (isCycleFormed(edge, disjointSet)) {
                continue;
            }

            mst.add(edge);

        }

        return mst.stream()
                .mapToInt(Edge::getWeight)
                .sum();
    }

    static int findSubSet(int[] disjointSet, int x) {

        int parent = disjointSet[x];

        if (parent == -1) {
            return x;
        }

        return findSubSet(disjointSet, parent);
    }

    static int[] unionSubSets(int[] disjointSet, int x, int y) {

        int subSetX = findSubSet(disjointSet, x);
        int subSetY = findSubSet(disjointSet, y);

        disjointSet[subSetX] = subSetY;

        return disjointSet;
    }

    static boolean isCycleFormed(Edge edge, int[] disjointSet) {

        int subSetFrom = findSubSet(disjointSet, edge.getFrom());
        int subSetTo = findSubSet(disjointSet, edge.getTo());

        if (subSetFrom == subSetTo) {
            return true;
        }

        unionSubSets(disjointSet, subSetFrom, subSetTo);

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