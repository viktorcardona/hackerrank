import java.util.*;

/**
 *  Boggle Board Algorithm: Using Depth First Search
 *
 *  Boggle (Find all possible words in a board of characters)
 *
 *      https://www.geeksforgeeks.org/boggle-find-possible-words-board-characters/
 *
 */
public class SolutionDFS {

    private static final int M = 3;
    private static final int N = 3;

    private static final String[] dictionary = {"GEEKS", "FOR", "QUIZ", "GO", "SEEK", "GUI"};

    private static char[][] boggle =   {{'G','I','Z'},
                                        {'U','E','K'},
                                        {'Q','S','E'}};

    private static List<String> findWords(char[][] boggle, String[] dictionary) {
        final Set<String> dictionarySet = new HashSet<>(Arrays.asList(dictionary));
        Set<String> words = new HashSet<>();

        final int rows = boggle.length;
        final int columns = (rows > 0) ? boggle[0].length : 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                boolean[][] visited = new boolean[rows][columns];
                String word = "";
                words.addAll(dfs(words, boggle, rows, columns, word, i, j, visited, dictionarySet));
            }
        }

        return new ArrayList<>(words);
    }

    private static boolean isWord(String word, Set<String> dictionary) {
        return dictionary.contains(word);
    }

    private static Set<String> dfs(Set<String> words, char[][] boggle, int rows, int columns, String word, int i, int j, boolean[][] visited, final Set<String> dictionary) {

        visited[i][j] = true;

        word += String.valueOf(boggle[i][j]);

        if (isWord(word, dictionary)) {
            words.add(word);
        }

        final int[] vertexX = {-1, -1, -1,  1, 1, 1,  0, 0};
        final int[] vertexY = {-1,  0,  1, -1, 0, 1, -1, 1};

        for (int k = 0; k < vertexX.length; k++) {
            int x = i + vertexX[k];
            int y = j + vertexY[k];
            if (isSafe(x, y, visited, rows, columns)) {
                words = dfs(words, boggle, rows, columns, word, x, y, visited, dictionary);
            }
        }

        // put false in order to be able to combine this letter with their neighbors
        // let's say the neighbors of 'A' is 'B' it means it will process 'B'
        // let's say the neighbors of 'B' is 'C' it means it will combine
        // BC
        // since the neighbors of 'C' is 'B' it means it will NOT combine
        // CB
        // if 'B' is visited
        visited[i][j] = false;

        return words;
    }

    private static boolean isSafe(int x, int y, boolean[][] visited, int rows, int columns) {
        return  x >= 0 && x < rows &&
                y >= 0 && y < columns &&
                !visited[x][y];
    }

    public static void main(String[] args) {
        System.out.println("Following words of dictionary are present:");
        long start = System.currentTimeMillis();
        findWords(boggle, dictionary).forEach(word -> System.out.println(word));
        long end = System.currentTimeMillis();
        System.out.println("Time in ms: " + (end - start));
    }

}
