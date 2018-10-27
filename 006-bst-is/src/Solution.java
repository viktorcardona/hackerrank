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
 */
public class Solution {

    boolean checkBST(Node root) {
        BSTInorderList bstInorderList = new BSTInorderList();
        java.util.List<Integer> numbersInOrder = bstInorderList.inOrder(root);
        java.util.Set<Integer> numbersInOrderSet = new java.util.HashSet<>(numbersInOrder);
        boolean hasDuplicates = numbersInOrder.size() != numbersInOrderSet.size();
        if (hasDuplicates) {
            return false; //It is not allowed duplicates based on the description of the problem
        }
        java.util.List<Integer> numbersSorted = numbersInOrder.stream().sorted().collect(java.util.stream.Collectors.toList());
        return java.util.stream.IntStream.range(0, numbersInOrder.size())
                .boxed()
                .map(i -> numbersInOrder.get(i).intValue() == numbersSorted.get(i).intValue())
                .allMatch(v -> v == true);
    }

    class BSTInorderList {
        private final java.util.List<Integer> numbers;
        BSTInorderList (){
            numbers = new java.util.ArrayList<>();
        }
        java.util.List<Integer> inOrder(Node node) { // In Order Traversal: Natural Traversal for a Binary Search Tree
            if (node == null) {
                return numbers;
            }
            inOrder(node.left);
            numbers.add(node.data);
            inOrder(node.right);
            return numbers;
        }
    }

    class Node {
        int data;
        Node left;
        Node right;
    }
}
