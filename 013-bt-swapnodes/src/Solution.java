import java.io.*;
import java.util.*;

/**
 *
 *  Swap Nodes Algorithm
 *
 *      Swap operations should be performed: the left becomes the right and the right becomes the left
 *
 *      int[][] indexes: It is a matrix of n x 2:
 *                       The root is always 1
 *                       The first couple of integers are the left and the right elements of the element 1 <root>
 *                       The next  couple of integers are the left and the right elements of the element 2
 *                       The next  couple of integers are the left and the right elements of the element 3
 *                       .
 *                       .
 *                       .
 *
 *      int[] queries: represents the swap operations to be executed.
 *                      If n is the size it means we have to perform n swap operations
 *                      The integer value represents the depth
 *                      Depth of root is 1
 *                      Depth of left and right nodes of root is 2
 *                          and so on.
 *                      The swap operation should be performed to all the children of the nodes that represents the depth value
 *
 *      int[][]: Returned value. It is the in order traversal of the tree after each swap operation
 *
 *
 *  https://www.hackerrank.com/challenges/swap-nodes-algo/problem
 *
 */
public class Solution {

    /*
     * Complete the swapNodes function below.
     */
    static int[][] swapNodes(int[][] indexes, int[] queries) {
        /*
         * Write your code here.
         */

        Tree tree = buildTree(indexes);

        int[][] result = new int[queries.length][tree.size];
        for (int indexSwap = 0; indexSwap < queries.length; indexSwap++) {
            int depth = queries[indexSwap];
            tree.swap(depth);
            result = tree.inOrder(result, indexSwap);
        }
        return result;
    }

    private static Tree buildTree(int[][] indexes) {
        Tree tree = new Tree(1);
        for (int i = 0; i < indexes.length; i++) {
            tree.add(i + 1, indexes[i][0], indexes[i][1]);
        }
        return tree;
    }

    static class Tree {

        Node root;
        int size; // number of nodes
        int maxDepth;

        public Tree(int rootData) {
            int depth = 1;
            root = new Node(rootData, depth);
            size = 1;
            maxDepth = depth;
        }

        int pos;

        int[][] inOrder(int[][] result, final int indexSwap) {
            pos = 0;
            return inOrder(root, result, indexSwap);
        }

        int[][] inOrder(Node node, int[][] result, final int indexSwap) {
            if (node == null) {
                return result;
            }
            result = inOrder(node.left, result, indexSwap);
            result[indexSwap][pos] = node.data;
            pos++;
            result = inOrder(node.right, result, indexSwap);
            return result;
        }

        void add(int parentData, int leftData, int rightData) {
            if (leftData == -1 && rightData == -1) {
                return;
            }
            Node node = findNodeByValue(parentData);
            if (node == null) {
                return;
            }
            if (leftData != -1) {
                node.left = new Node(leftData, node.depth + 1);
                size++;
                maxDepth = Math.max(maxDepth, node.depth + 1);
            }
            if (rightData != -1) {
                node.right = new Node(rightData, node.depth + 1);
                size++;
                maxDepth = Math.max(maxDepth, node.depth + 1);
            }
        }

        private Node findNodeByValue(int parentData) {

            Queue<Node> queue = new LinkedList<>();
            queue.add(root);

            while (!queue.isEmpty()) {

                Node node = queue.poll();

                if (node.data == parentData) {
                    return node;
                }

                if (node.left != null) queue.add(node.left);
                if (node.right != null) queue.add(node.right);

            }

            return null;
        }

        public void swap(final int depth) {
            int multiple = 1;
            int depthIndex = depth * multiple;
            while (depthIndex <= maxDepth) {
                List<Node> depthNodes = depthNodes(depthIndex);
                for (Node node: depthNodes) {
                    swap(node);
                }
                multiple++;
                depthIndex = depth * multiple;
            }
        }

        private List<Node> depthNodes(int depth) {
            Queue<Node> queue = new LinkedList<>();
            queue.add(root);

            List<Node> nodesDepth = new ArrayList<>();

            while (!queue.isEmpty()) {
                Node node = queue.poll();
                int currentDepth = node.depth;

                if (node.left != null) queue.add(node.left);
                if (node.right != null) queue.add(node.right);

                if (currentDepth == depth) {
                    nodesDepth.add(node);
                } else if (currentDepth > depth) { // in order to avoid traverse all the tree if not necessary
                    return nodesDepth;
                }
            }

            return nodesDepth;
        }

        private void swap(Node node) {
            if (node == null) return;
            Node oldLeft = node.left;
            node.left = node.right;
            node.right = oldLeft;
        }

        static class Node {
            int data;
            Node left;
            Node right;
            int depth;

            public Node(int data, int depth) {
                this.data = data;
                this.depth = depth;
                left = right = null;
            }
        }
    }


    private static final String OUTPUT_PATH = "output.txt";
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        //BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(OUTPUT_PATH));

        int n = Integer.parseInt(scanner.nextLine().trim());

        int[][] indexes = new int[n][2];

        for (int indexesRowItr = 0; indexesRowItr < n; indexesRowItr++) {
            String[] indexesRowItems = scanner.nextLine().split(" ");

            for (int indexesColumnItr = 0; indexesColumnItr < 2; indexesColumnItr++) {
                int indexesItem = Integer.parseInt(indexesRowItems[indexesColumnItr].trim());
                indexes[indexesRowItr][indexesColumnItr] = indexesItem;
            }
        }

        int queriesCount = Integer.parseInt(scanner.nextLine().trim());

        int[] queries = new int[queriesCount];

        for (int queriesItr = 0; queriesItr < queriesCount; queriesItr++) {
            int queriesItem = Integer.parseInt(scanner.nextLine().trim());
            queries[queriesItr] = queriesItem;
        }

        int[][] result = swapNodes(indexes, queries);

        for (int resultRowItr = 0; resultRowItr < result.length; resultRowItr++) {
            for (int resultColumnItr = 0; resultColumnItr < result[resultRowItr].length; resultColumnItr++) {
                bufferedWriter.write(String.valueOf(result[resultRowItr][resultColumnItr]));

                if (resultColumnItr != result[resultRowItr].length - 1) {
                    bufferedWriter.write(" ");
                }
            }

            if (resultRowItr != result.length - 1) {
                bufferedWriter.write("\n");
            }
        }

        bufferedWriter.newLine();

        bufferedWriter.close();
    }
}