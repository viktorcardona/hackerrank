/**
 *
 *  Given a number return the closest node's value from a ninary search tree.
 *
 *      It is implemented 2 versions:
 *          - Recursive version.
 *          - Iterative version.
 *      Iterative version is better since it is simpler.
 *
 * http://codercareer.blogspot.com/2013/03/no-45-closest-node-in-binary-search-tree_2.html
 *
 */
public class Solution {


    static Integer findClosestValueRecursive(BinarySearchTree tree, int value) {
        if (tree == null || tree.root == null) {
            return null;
        }
        return findClosestValueRecursive(tree.root, value, Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    private static int findClosestValueRecursive(Node node, final int value, int closestValue, int distance) {

        if (node == null) return closestValue;

        int newDistance = Math.abs(node.data - value);

        if (newDistance < distance) {
            closestValue = node.data;
            distance = newDistance;
        }

        if (newDistance == 0) {
            return closestValue;
        }

        if (value < node.data) {
            return findClosestValueRecursive(node.left, value, closestValue, distance);
        }

        return findClosestValueRecursive(node.right, value, closestValue, distance);
    }

    private static Integer findClosestValueIterative(Node node, final int value) {

        if (node == null) return null;

        int closestValue = node.data;
        int distance = Integer.MAX_VALUE;

        while (node != null) {

            int newDistance = Math.abs(node.data - value);

            if (newDistance == 0) {
                return closestValue;
            }

            if (newDistance < distance) {
                distance = newDistance;
                closestValue = node.data;
            }

            if (value < node.data) {
                node = node.left;
            } else {
                node = node.right;
            }
        }

        return closestValue;
    }

    static class BinarySearchTree {

        Node root;

        public void insert(int value) {
            root = insert(root, value);
        }

        private Node insert(Node node, int value) {
            if (node == null) return new Node(value);

            if (value > node.data) {
                node.right = insert(node.right, value);
            } else {
                node.left = insert(node.left, value);
            }

            return node;
        }

    }

    public static void main(String[] args) {
        BinarySearchTree tree = new BinarySearchTree();
        tree.insert(32);
        tree.insert(24);
        tree.insert(41);
        tree.insert(21);
        tree.insert(28);
        tree.insert(36);
        tree.insert(47);
        tree.insert(14);
        tree.insert(25);
        tree.insert(31);
        tree.insert(39);

        BTreePrinter.printNode(tree.root);

        int targetValue = 29;
        System.out.println("Recursive Closest value to " + targetValue + " is: ");
        System.out.println(findClosestValueRecursive(tree, targetValue));

        System.out.println("Iterative Closest value to " + targetValue + " is: ");
        System.out.println(findClosestValueIterative(tree.root, targetValue));
    }

}

class Node {
    int data;
    Node left;
    Node right;
    public Node(int value) {
        this.data = value;
    }
}
