import apple.laf.JRSUIConstants;

import java.util.*;

/**
 *  My solution using a Trie
 *
 *  Another solution using a Trie: https://www.geeksforgeeks.org/boggle-set-2-using-trie/
 *
 *
 */
public class SolutionTrie {

    private static final int M = 3;
    private static final int N = 3;

    private static final String[] dictionary = {"GEEKS", "FOR", "QUIZ", "GO", "SEEK", "GUI"};

    private static char[][] boggle =   {{'G','I','Z'},
                                        {'U','E','K'},
                                        {'Q','S','E'}};

    private static List<String> findWords(char[][] boggle, String[] dictionary) {

        Trie trie = new Trie();
        for (String word: dictionary) {
            trie.insert(word);
        }

        Set<String> wordsFound = new HashSet<>();

        for (int row = 0; row < M; row++) {
            for (int column = 0; column < N; column++) {
                Character character = boggle[row][column];
                boolean isFirstLetterOfWord = trie.root.characters.get(character) != null;
                if (isFirstLetterOfWord) {
                    String word = "";
                    boolean[][] visited = new boolean[M][N];
                    wordsFound = searchWords(wordsFound, row, column, boggle, trie.root, word, visited);
                }
            }
        }

        return new ArrayList<>(wordsFound);
    }

    private static Set<String> searchWords(Set<String> wordsFound, int row, int column, char[][] boggle, Trie.Node node, String word, boolean[][] visited) {

        if (!isSafe(row, column, visited, node)) {
            return wordsFound;
        }

        if (node.characters.get(boggle[row][column]) != null) {
            word += boggle[row][column];
        }

        if (node.isEndOfWord) {
            wordsFound.add(word);
        }

        node = node.characters.get(boggle[row][column]);

        visited[row][column] = true;

        for (int r = row - 1; r <= row + 1; r++) {
            for (int c = column - 1; c <= column + 1; c++) {
                if (r != row || c != column) {
                    wordsFound = searchWords(wordsFound, r, c, boggle, node, word, visited);
                }
            }
        }

        // make current element unvisited
        // Key Step!!!!
        visited[row][column] = false;

        return wordsFound;
    }

    private static boolean isSafe(int r, int c, boolean[][] visited, Trie.Node node) {
        return node != null && r >= 0 && r < M && c >= 0 && c < N && !visited[r][c];
    }

    private static class Trie {

        Node root;

        public Trie() {
            root = new Node();
        }

        public void insert(String word) {
            Node node = root;
            for (Character character: word.toCharArray()) {
                if (node.characters.get(character) == null) {
                    node.characters.put(character, new Node());
                }
                node = node.characters.get(character);
            }
            node.isEndOfWord = true;
        }

        static class Node {

            Map<Character, Node> characters;
            boolean isEndOfWord;

            public Node() {
                characters = new HashMap<>();
                isEndOfWord = false;
            }

        }

    }

    public static void main(String[] args) {
        System.out.println("Following words of dictionary are present:");
        long start = System.currentTimeMillis();
        findWords(boggle, dictionary).forEach(word -> System.out.println(word));
        long end = System.currentTimeMillis();
        System.out.println("Time in ms: " + (end - start));
    }
}
