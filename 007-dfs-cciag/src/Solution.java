import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 *  DFS: Connected Cell in a Grid
 *
 *  https://www.hackerrank.com/challenges/ctci-connected-cell-in-a-grid/problem
 *
 *
 */
public class Solution {

    // Complete the maxRegion function below.
    static int maxRegion(int[][] grid) {
        int maxRegion = 0;
        int rows = grid.length;
        int columns = grid.length > 0 ? grid[0].length : 0;
        boolean[][] visited = new boolean[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (!visited[i][j] && grid[i][j] == 1) {
                    int region = dfs(i, j, grid, visited, rows, columns);
                    if (region > maxRegion) {
                        maxRegion = region;
                    }
                }
            }
        }
        return maxRegion;
    }

    /**
     * Returns the number of vertices connected, reacheable from the vertex i, j
     */
    private static int dfs(int i, int j, int[][] grid, boolean[][] visited, int rows, int columns) {

        Node node = new Node(i, j);
        List<Node> adjacent = adjacent(rows, columns, node, grid);

        visited[i][j] = true;
        int region = 1;

        for (Node nexNode: adjacent) {
            if (!visited[nexNode.getI()][nexNode.getJ()]) {
                region+=dfs(nexNode.getI(), nexNode.getJ(), grid, visited, rows, columns);
            }
        }

        return region;
    }

    private static List<Node> adjacent(int rows, int columns, Node node, int[][] grid) {
        List<Node> adjacent = new ArrayList<>();

        int xVector[] = new int[] {0,  0, -1, -1, -1,  1, 1, 1};
        int yVector[] = new int[] {-1, 1, -1,  0,  1, -1, 0, 1};

        for (int q = 0; q < 8; q++) {
            int x = node.getI() + xVector[q];
            int y = node.getJ() + yVector[q];

            if (x < 0 || x >= rows || y < 0 || y >= columns) continue;
            if (grid[x][y] != 1) continue;

            adjacent.add(new Node(x, y));
        }
        return adjacent;
    }

    private static class Node {
        private int i;
        private int j;

        public Node(int i, int j) {
            this.i = i;
            this.j = j;
        }

        public int getI() {
            return i;
        }

        public int getJ() {
            return j;
        }
    }

    private static final String OUTPUT_PATH = "/output.txt";

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(OUTPUT_PATH));

        int n = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        int m = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        int[][] grid = new int[n][m];

        for (int i = 0; i < n; i++) {
            String[] gridRowItems = scanner.nextLine().split(" ");
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            for (int j = 0; j < m; j++) {
                int gridItem = Integer.parseInt(gridRowItems[j]);
                grid[i][j] = gridItem;
            }
        }

        int res = maxRegion(grid);

        bufferedWriter.write(String.valueOf(res));
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}
