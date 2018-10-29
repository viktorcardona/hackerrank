import java.io.*;
import java.util.*;

/**
 *  https://www.hackerrank.com/challenges/find-the-running-median/problem
 *
 *  Find the Running Median.
 *
 *  Using Data Structure: Priority Queue based on Heaps
 *
 */
public class Solution {

    /*
     * Complete the runningMedian function below.
     */
    static double[] runningMedian(int[] a) {
        /*
         * Write your code here.
         */
        double[] medians = new double[a.length];

        PriorityQueue<Integer> heapMin = new PriorityQueue <> ();
        PriorityQueue<Integer> heapMax = new PriorityQueue<>((x,y) -> y - x);

        for (int i = 0; i < a.length; i++) {
            int value = a[i];
            updateHeaps(value, heapMax, heapMin);
            medians[i] = median(heapMax, heapMin);
        }

        return medians;
    }

    private static void updateHeaps(int value, PriorityQueue<Integer> heapMax, PriorityQueue<Integer> heapMin) {
        if (heapMax.isEmpty()) {
            heapMax.add(value);
        } else if (heapMax.size() > heapMin.size()) {
            if (value > heapMax.peek()) {
                heapMin.add(value);
            } else {
                heapMin.add(heapMax.poll());
                heapMax.add(value);
            }
        } else {
            if (value < heapMin.peek()) {
                heapMax.add(value);
            } else {
                heapMax.add(heapMin.poll());
                heapMin.add(value);
            }
        }
    }

    private static double median(PriorityQueue<Integer> heapMax, PriorityQueue<Integer> heapMin) {
        boolean equalSize = heapMax.size() == heapMin.size();
        if (equalSize) {
            return (heapMax.peek() + heapMin.peek()) / 2d;
        }
        if (heapMax.size() > heapMin.size()) {
            return heapMax.peek();
        }
        return heapMin.peek();
    }

    private static final String OUTPUT_PATH = "/output.txt";

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(OUTPUT_PATH));

        int aCount = Integer.parseInt(scanner.nextLine().trim());

        int[] a = new int[aCount];

        for (int aItr = 0; aItr < aCount; aItr++) {
            int aItem = Integer.parseInt(scanner.nextLine().trim());
            a[aItr] = aItem;
        }

        double[] result = runningMedian(a);

        for (int resultItr = 0; resultItr < result.length; resultItr++) {
            bufferedWriter.write(String.valueOf(result[resultItr]));

            if (resultItr != result.length - 1) {
                bufferedWriter.write("\n");
            }
        }

        bufferedWriter.newLine();

        bufferedWriter.close();
    }

}
