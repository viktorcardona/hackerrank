import java.util.ArrayList;
import java.util.List;

/**
 *
 *  Paths with Specified Sum in Binary Tree
 *  http://codercareer.blogspot.com/2011/09/no-04-paths-with-specified-sum-in.html
 *
 */
public class Solution {

    public static void main(String[] args) {

        BinaryTree bt = new BinaryTree(10);
        bt.root.left = new Node(5);
        bt.root.left.left = new Node(4);
        bt.root.left.left.left = new Node(1);
        bt.root.left.left.left.left = new Node(1);
        bt.root.left.left.left.left.left = new Node(1);
        bt.root.left.right = new Node(7);
        bt.root.right = new Node(12);

        final int sum = 22;

        bt.printPathsWhereSum(sum);
    }

}

class BinaryTree {

    Node root;

    public BinaryTree(int dataRoot) {
        root = new Node(dataRoot);
    }

    public void printPathsWhereSum(final int sum) {
        List<List<Node>> listOfpaths = getPathsForGivenSum(root, sum, new ArrayList<>(), new ArrayList<>());

        System.out.println("Paths Found for the Given Sum : " + sum);
        listOfpaths.forEach(path -> {
            System.out.println();
            System.out.println("Path: ");
            path.forEach(node -> {
                System.out.print(node.data + " ");
            });
            System.out.println();
        });

    }

    /**
     * It does a PreOrder Traversal
     */
    private List<List<Node>> getPathsForGivenSum(Node node, int sum, List<Node> path, List<List<Node>> listOfpaths) {

        if (node == null) return listOfpaths;

        sum = sum - node.data;

        if (sum < 0) return listOfpaths;

        boolean isLeaf = node.left == null && node.right == null;
        boolean findPath = sum == 0 && isLeaf;

        path.add(node);

        if (findPath) {
            listOfpaths.add(clone(path));
            return listOfpaths;
        }

        listOfpaths = getPathsForGivenSum(node.left, sum, clone(path), listOfpaths);
        listOfpaths = getPathsForGivenSum(node.right, sum, clone(path), listOfpaths);
        return listOfpaths;
    }

    private List<Node> clone(List<Node> path) {
        return new ArrayList<>(path);
    }

}

class Node {

    int data;
    Node left;
    Node right;

    public Node(int data) {
        this.data = data;
    }
}
