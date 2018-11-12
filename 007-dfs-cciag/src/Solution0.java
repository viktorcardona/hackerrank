import java.io.*;
import java.util.*;

/**
 *
 * This better solution comes from:
 * https://www.youtube.com/watch?v=R4Nh-EgWjyQ&t=305s
 *
 */
public class Solution0 {

    // Complete the maxRegion function below.
    static int maxRegion(int[][] grid) {

        int rows = grid.length;
        int columns = rows > 0 ? grid[0].length : 0;

        int maxRegion = 0;

        boolean[][] visited = new boolean[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                int sizeRegion = regionSize(i, j, grid, visited);
                maxRegion = Math.max(sizeRegion, maxRegion);
            }
        }

        return maxRegion;
    }

    private static int regionSize(int row, int column, int[][] grid, boolean[][] visited) {

        final boolean notSafe = row < 0 || column < 0 || row >= grid.length || column >= grid[row].length || grid[row][column] == 0 || visited[row][column];
        if (notSafe) return 0;

        visited[row][column] = true;

        int regionSize = 1;

        for (int r = row - 1; r <= row + 1; r ++) {
            for (int c = column - 1; c <= column + 1; c ++) {
                if (r != row || c != column) {
                    regionSize += regionSize(r, c, grid, visited);
                }
            }
        }

        return regionSize;
    }

    private static final String OUTPUT_PATH = "/Users/victorcardona/Documents/code/hackerrank/007-dfs-cciag/output.txt";

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
