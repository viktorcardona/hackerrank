import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

/**
 *
 * Breath First Search
 *
 * https://www.hackerrank.com/challenges/bfsshortreach/problem
 */
public class Solution {

    private static final String OUTPUT_PATH = "/output.txt";


    // Complete the bfs function below.
    static int[] bfs(final int n, final int m, int[][] edges, final int s) {

        edges = removeDuplicates(edges);


        int[] distances = new int[n];
        boolean[] visited = new boolean[n];

        Queue<List<Integer>> queue = new LinkedList<>();

        List<Integer> pathS = new ArrayList<>();
        pathS.add(s);

        final int distanceS = -5;

        distances[pos(s)] = distanceS;

        queue.add(pathS);
        visited[pos(s)] = true;

        while (!queue.isEmpty()) {

            final List<Integer> path = queue.poll();

            int node = path.get(path.size() - 1);

            if (node != s && distances[pos(node)] == 0) {
                int newValue = (path.size() - 1) * 6;
                distances[pos(node)] = newValue;
            }

            Set<Integer> neighbours = neighbours(node, edges);

            neighbours.forEach(nextNode ->{
                boolean nextNodeVisited = visited[pos(nextNode)];
                if (!nextNodeVisited) {
                    List<Integer> pathNextNode = new ArrayList<>(path);
                    pathNextNode.add(nextNode);
                    visited[pos(nextNode)] = true;
                    queue.add(pathNextNode);
                }
            });

        }

        return IntStream.of(distances)
                .filter(v -> v != distanceS)
                .map(o -> (o == 0) ? -1 : o )
                .toArray();
    }


    private static int[][] removeDuplicates(int[][] edges) {
        Set<String> edgesSet = new HashSet<>();
        for (int[] edge: edges) {
            edgesSet.add(edge[0] + "," + edge[1]);
        }
        int[][] newEdges = new int[edgesSet.size()][2];
        int index = 0;
        for (String edgeString: edgesSet) {
            String[] numbers = edgeString.split(",");
            newEdges[index][0] = Integer.valueOf(numbers[0]);
            newEdges[index][1] = Integer.valueOf(numbers[1]);
            index++;
        }
        return newEdges;
    }

    private static int pos(int node) {
        return node - 1;
    }

    private static Set<Integer> neighbours(int node, int[][] edges) {
        Set<Integer> neighbours = new HashSet<>();
        for (int[] edge: edges) {

            int x = edge[0];
            int y = edge[1];

            if (x == node) {
                neighbours.add(y);
            } else if (y == node) {
                neighbours.add(x);
            }

        }
        return neighbours;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(OUTPUT_PATH));

        int q = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int qItr = 0; qItr < q; qItr++) {
            String[] nm = scanner.nextLine().split(" ");

            int n = Integer.parseInt(nm[0]);

            int m = Integer.parseInt(nm[1]);

            int[][] edges = new int[m][2];

            for (int i = 0; i < m; i++) {
                String[] edgesRowItems = scanner.nextLine().split(" ");
                scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

                for (int j = 0; j < 2; j++) {
                    int edgesItem = Integer.parseInt(edgesRowItems[j]);
                    edges[i][j] = edgesItem;
                }
            }

            int s = scanner.nextInt();
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            int[] result = bfs(n, m, edges, s);

            for (int i = 0; i < result.length; i++) {
                bufferedWriter.write(String.valueOf(result[i]));

                if (i != result.length - 1) {
                    bufferedWriter.write(" ");
                }
            }

            bufferedWriter.newLine();
        }

        bufferedWriter.close();

        scanner.close();
    }

}
