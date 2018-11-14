import java.util.Arrays;

/**
 *
 *  Returns true if the given array match a binary search tree in post-order traversal.
 *
 *      http://codercareer.blogspot.com/2011/09/no-06-post-order-traversal-sequences-of.html
 */
public class Solution {

    static boolean isPostOrderTraversal(int[] array) {

        if (array == null || array.length < 1) return false;
        if (array.length == 1) return true;

        int root = array[array.length - 1];

        int indexRightSubTree = 0;

        while (array[indexRightSubTree] < root) {
            indexRightSubTree++;
        }

        for (int i = indexRightSubTree; i < array.length - 1; i++) {
            if (array[i] < root) return false; // if one node in the right side is smaller than the root it means this is not a binary search tree
        }

        boolean isLetfSubTreeInPostOrder = true;

        if (indexRightSubTree > 0) {
            int[] leftSubTree = Arrays.copyOfRange(array, 0, indexRightSubTree);
            isLetfSubTreeInPostOrder = isPostOrderTraversal(leftSubTree);
        }

        boolean isRightSubTreeInPostOrder = true;

        if (array.length - 1 > indexRightSubTree) {
            int[] rightSubTree = Arrays.copyOfRange(array, indexRightSubTree, array.length - 1);
            isRightSubTreeInPostOrder = isPostOrderTraversal(rightSubTree);
        }

        return isLetfSubTreeInPostOrder && isRightSubTreeInPostOrder;
    }

    public static void main(String[] args) {

        System.out.println("Is the array in the post order traversal of a binary tree? ");

        // true
        System.out.println(isPostOrderTraversal(new int[]{5, 7, 6, 9, 11, 10, 8}));

        // false
        //System.out.println(isPostOrderTraversal(new int[]{5, 7, 6, 9, 7, 10, 8}));

        // false
        //System.out.println(isPostOrderTraversal(new int[]{7, 4, 6, 5}));

    }

}
