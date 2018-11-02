/**
 *      Is This a Binary Search Tree?
 *  https://www.hackerrank.com/challenges/is-binary-search-tree/problem
 *
 *  It must return a boolean denoting whether or not the binary tree is a binary search tree.
 *
 *  A binary search tree has the following ordering requirements:
 *
 *      - The  value of every node in a node's left subtree is less than the data value of that node.
 *      - The  value of every node in a node's right subtree is greater than the data value of that node.
 *
 *  This solutions is from:
 *      14. isBST2() Solution (Java)
 *      http://cslibrary.stanford.edu/110/BinaryTrees.html#java
 */
public class Solution2 {

    /**
     Tests if a tree meets the conditions to be a
     binary search tree (BST). Uses the efficient
     recursive helper.
     */
    boolean checkBST(Node root) {
        return( isBST2(root, Integer.MIN_VALUE, Integer.MAX_VALUE) );
    }

    /**
     Efficient BST helper -- Given a node, and min and max values,
     recurs down the tree to verify that it is a BST, and that all
     its nodes are within the min..max range. Works in O(n) time --
     visits each node only once.
     */
    private boolean isBST2(Node node, int min, int max) {
        if (node==null) {
            return(true);
        }
        else {

            boolean amIok = node.data > min && node.data < max;

            if (!amIok) {
                return false;
            }

            // left should be in range  min...node.data
            boolean leftOk = isBST2(node.left, min, node.data);

            // if the left is not ok, bail out
            if (!leftOk) return(false);

            // right should be in range node.data+1..max
            boolean rightOk = isBST2(node.right, node.data, max);

            return(rightOk);
        }
    }

    class Node {
        int data;
        Node left;
        Node right;
    }
}
