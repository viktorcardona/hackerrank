

/**
 * Convert a given Binary Tree to Doubly Linked List
 *
 * This is really simple
 *
 *  One Solution:
 *  https://www.geeksforgeeks.org/convert-given-binary-tree-doubly-linked-list-set-3/
 *
 */
public class Solution {

    static class BinaryTree {

        Node head;
        Node root;

        public void binaryTree2DoubleLinkedList() {
            bt2dll(root, null);
        }

        public Node bt2dll(Node node, Node prevNew) {

            if (node == null) return prevNew;

            prevNew = bt2dll(node.left, prevNew);

            if (head == null) {
                head = node;
            }

            if (prevNew == null) {
                prevNew = node;
            } else {
                node.left = prevNew;
                prevNew.right = node;
                prevNew = node;
            }

            prevNew = bt2dll(node.right, prevNew);

            return prevNew;
        }
    }

    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree();
        tree.root = new Node(10);
        tree.root.left = new Node(6);
        tree.root.left.left = new Node(4);
        tree.root.left.left.right = new Node(5);
        tree.root.left.right = new Node(8);

        tree.root.right = new Node(14);
        tree.root.right.left = new Node(12);
        tree.root.right.right = new Node(16);

        // convert to DLL
        tree.binaryTree2DoubleLinkedList();

        // Print the converted List
        printDoubleLinkedList(tree.head);
    }

    static void printDoubleLinkedList(Node node) {
        while (node != null) {
            System.out.print(node.data + " ");
            node = node.right;
        }
    }

}

class Node {
    int data;
    Node left, right;

    public Node(int data) {
        this.data = data;
        left = right = null;
    }
}
