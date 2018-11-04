import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 *
 *  Trie:
 *
 *  https://www.hackerrank.com/challenges/no-prefix-set/problem
 *
 */
public class Solution {

    static List<String> noPrefixSet(String[] words) {
        Trie trie = new Trie();

        for (String word: words) {
            String prefixed = trie.addNoPrefixSet(word);
            if (prefixed != null) {
                return Arrays.asList("BAD SET", prefixed);
            }
        }

        return Arrays.asList("GOOD SET");
    }

    static class Trie {

        private TrieNode root;

        public Trie() {
            root = new TrieNode();
        }

        private class TrieNode {

            private final Map<Character, TrieNode> children;
            private boolean isEndOfWord;
            private int size;

            public TrieNode() {
                children = new HashMap<>();
                isEndOfWord = false;
                size = 0;
            }
        }

        public String addNoPrefixSet(String key) {
            TrieNode node = root;
            boolean fullWordPrefixed = true;
            for (int level = 0; level < key.length(); level++) {
                Character character = key.charAt(level);
                node = node.children.computeIfAbsent(character, k ->  new TrieNode());
                node.size++;
                fullWordPrefixed = fullWordPrefixed && node.size >= 2;
                final boolean isNodeEndOfWord = fullWordPrefixed && node.isEndOfWord; // Prefixed by smaller word
                if (isNodeEndOfWord) {
                    return key;
                }
            }

            node.isEndOfWord = true;

            //fullWordPrefixed true => Prefixed by a bigger word
            return fullWordPrefixed ? key : null;
        }
    }

    private static final String OUTPUT_PATH = "output.txt";

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(OUTPUT_PATH));
        //BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int size = Integer.parseInt(scanner.nextLine().trim());

        String[] words = new String[size];

        for (int i = 0; i < size; i++) {
            words[i] = scanner.nextLine().trim();
        }

        List<String> result = noPrefixSet(words);

        for (int resultItr = 0; resultItr < result.size(); resultItr++) {
            bufferedWriter.write(result.get(resultItr));

            bufferedWriter.write("\n");
        }

        bufferedWriter.newLine();

        bufferedWriter.close();
    }

}
