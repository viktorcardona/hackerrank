/**
 * @author Victor Cardona
 */
public class Trie {
    // Alphabet size (# of symbols)
    static final int ALPHABET_SIZE = 26;

    // trie node
    static class TrieNode
    {
        TrieNode[] children = new TrieNode[ALPHABET_SIZE];

        // isEndOfWord is true if the node represents
        // end of a word
        boolean isEndOfWord;

        TrieNode(){
            isEndOfWord = false;
            for (int i = 0; i < ALPHABET_SIZE; i++)
                children[i] = null;
        }
    };

    static TrieNode root;

    // If not present, inserts key into trie
    // If the key is prefix of trie node, just marks leaf node
    static void insert(String key) {
        TrieNode node = find(key);
        if (node != null) {
            node.isEndOfWord = true;
            return;
        }
        if (root == null) {
            root = new TrieNode();
        }

        TrieNode node1 = root;

        for (char character: key.toCharArray()) {
            TrieNode children = node1.children[character - FIRST_LETTER_A];
            if (children == null) {
                children = new TrieNode();
            }
            node1 = children;
        }

        node1.isEndOfWord = true;
    }

    static final int FIRST_LETTER_A = 97;

    private static TrieNode find(String key) {
        if (root == null) {
            return null;
        }
        TrieNode node = root;
        int count = 0;
        for (char character: key.toCharArray()) {
            count++;
            TrieNode children = node.children[character - FIRST_LETTER_A];
            if (children != null) {
                node = children;
            } else {
                break;
            }
        }
        return count == key.length() ? node : null;
    }

}
