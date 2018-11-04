import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 *  Tries
 *
 *  https://www.hackerrank.com/challenges/contacts/problem
 *
 *  Solution Using a Trie:
 *      https://www.hackerrank.com/challenges/contacts/forum/comments/255027
 *
 *
 */
public class Solution {

    /*
     * Complete the contacts function below.
     */
    static int[] contacts(String[][] queries) {
        /*
         * Write your code here.
         */
        Trie contacts = new Trie();

        int[] numberOfContacts = new int[queries.length];
        int index = 0;

        for (int i = 0; i < queries.length; i++) {
            String contact = queries[i][1];
            String action  = queries[i][0];

            if ("add".equals(action)) {
                contacts.add(contact);
            } else { // find
                numberOfContacts[index] = contacts.countWordsWithPrefix(contact);
                index++;
            }
        }

        return Arrays.copyOfRange(numberOfContacts, 0, index);
    }

    static class Trie {

        private TrieNode root;

        public Trie() {
            root = new TrieNode();
        }

        private class TrieNode {

            private final Map<Character, TrieNode> children;
            private boolean isEndOfWord;
            private int size; // This is the KEY: https://www.hackerrank.com/challenges/contacts/forum/comments/255027

            public TrieNode() {
                children = new HashMap<>();
                isEndOfWord = false;
                size = 0;
            }
        }

        public TrieNode find(String key) {

            TrieNode node = root;

            for (int level = 0; level < key.length(); level++) {
                Character character = key.charAt(level);
                node = node.children.get(character);
                if ( node == null ) return null;
            }

            return node;
        }

        public void add(String key) {
            TrieNode node = root;

            for (int level = 0; level < key.length(); level++) {
                Character character = key.charAt(level);
                node = node.children.computeIfAbsent(character, k ->  new TrieNode());
                node.size++;
            }

            node.isEndOfWord = true;
        }

        public Integer countWordsWithPrefix(String prefix) {
            TrieNode node = find(prefix);
            return node == null ? 0 : node.size;
        }
    }

    private static final String OUTPUT_PATH = "/output.txt";

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(OUTPUT_PATH));

        String fileName = "/input3.txt";

        List<String> lines = Files.readAllLines(Paths.get(fileName));
        String[][] queries = new String[lines.size() - 1][2];
        lines.remove(0);

        int index = 0;
        for (String line: lines) {
            String[] split = line.split(" ");
            queries[index][0] = split[0];
            queries[index][1] = split[1];
            index++;
        }

        int[] result = contacts(queries);

        for (int resultItr = 0; resultItr < result.length; resultItr++) {
            bufferedWriter.write(String.valueOf(result[resultItr]));

            if (resultItr != result.length - 1) {
                bufferedWriter.write("\n");
            }
        }

        bufferedWriter.newLine();

        bufferedWriter.close();
    }

}
