import java.util.*;

/**
 * https://www.hackerrank.com/challenges/tree-height-of-a-binary-tree/problem
 */
public class Solution {

    public static int height(Node root) {
        // Write your code here.
        if (root == null) {
            return 0;
        }
        boolean isLeaf = root.left == null && root.right == null;
        int height = isLeaf ? 0 : 1;
        return Math.max(height(root.left), height(root.right)) + height;
    }

    static class Node {
        Node left;
        Node right;
        int data;

        Node(int data) {
            this.data = data;
            left = null;
            right = null;
        }
    }

    public static Node insert(Node root, int data) {
        if(root == null) {
            return new Node(data);
        } else {
            Node cur;
            if(data <= root.data) {
                cur = insert(root.left, data);
                root.left = cur;
            } else {
                cur = insert(root.right, data);
                root.right = cur;
            }
            return root;
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int t = scan.nextInt();
        Node root = null;
        while(t-- > 0) {
            int data = scan.nextInt();
            root = insert(root, data);
        }
        scan.close();
        int height = height(root);
        System.out.println(height);
    }

}
