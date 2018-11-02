import java.io.*;
import java.math.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;
import java.util.stream.Stream;

/**
 *  Tries
 *
 *  https://www.hackerrank.com/challenges/contacts/problem
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

        List<Integer> numberOfContacts = new ArrayList<>();

        for (int i = 0; i < queries.length; i++) {
            String contact = queries[i][1];
            switch (queries[i][0]) {
                case "add":
                    contacts.insert(contact);
                    break;
                case "find":
                    Trie.TrieNode node = contacts.search(contact);
                    numberOfContacts.add(countContacts(node));
                    break;
            }
        }

        return numberOfContacts.stream().mapToInt(i -> i).toArray();
    }

    private static Integer countContacts(Trie.TrieNode node) {
        if (node == null) {
            return 0;
        }

        int count = node.isEndOfWord ? 1 : 0;

        for (Trie.TrieNode children: node.children) {
            if (children != null) {
                count += children.isEndOfWord ? 1 : 0;
                count += countContacts(children);
            }
        }

        return count;
    }

    static class Trie {

        static final int ALPHABET_SIZE = 26;
        private TrieNode root;

        public Trie() {
            this.root = new TrieNode();
        }

        class TrieNode {

            private TrieNode[] children = new TrieNode[ALPHABET_SIZE];
            private boolean isEndOfWord;

            public TrieNode() {
                for (int i = 0; i < ALPHABET_SIZE; i++) {
                    children[i] = null;
                }
            }
        }

        public void insert(String key) {

            TrieNode trieNode = search(key);

            if (trieNode != null) {
                trieNode.isEndOfWord = true;
                return;
            }

            TrieNode node = root;

            for (int level = 0; level < key.length(); level++) {
                int index = key.charAt(level) - 'a';
                if (node.children[index] == null) {
                    node.children[index] = new TrieNode();
                }
                node = node.children[index];
            }

            node.isEndOfWord = true;
        }

        public TrieNode search(String key) {

            TrieNode node = root;

            for (int level = 0; level < key.length(); level++) {
                int index = key.charAt(level) - 'a';
                if (node.children[index] == null) {
                    return null;
                }
                node = node.children[index];
            }

            return node;
        }

    }

    private static final String OUTPUT_PATH = "/Users/viccardo/Documents/courses/hackerrank/src/hackerrank/011-trie-contacts/output.txt";

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(OUTPUT_PATH));

        int queriesRows = Integer.parseInt(scanner.nextLine().trim());

        String[][] queries = new String[queriesRows][2];

        for (int queriesRowItr = 0; queriesRowItr < queriesRows; queriesRowItr++) {
            String[] queriesRowItems = scanner.nextLine().split(" ");

            for (int queriesColumnItr = 0; queriesColumnItr < 2; queriesColumnItr++) {
                String queriesItem = queriesRowItems[queriesColumnItr];
                queries[queriesRowItr][queriesColumnItr] = queriesItem;
            }
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
