import java.io.*;
import java.util.*;

/**
 *
 * Game of Thrones - I
 *
 * https://www.hackerrank.com/challenges/game-of-thrones/problem
 *
 *  Palindrome: https://en.wikipedia.org/wiki/Palindrome
 *
 */
public class Solution {

    private static final String YES = "YES";
    private static final String NO = "NO";
    private static final char FIRST_LETTER = 'a';

    // Complete the gameOfThrones function below.
    static String gameOfThrones(String s) {
        if (s == null) return NO;
        if (s.length() == 1) return YES;
        int[] frequencies = new int[26];
        for (char character: s.toCharArray()) {
            final int index = character - FIRST_LETTER;
            frequencies[index]++;
        }
        int sum = 0;
        for (int index = 0; index < frequencies.length; index++) {
            frequencies[index] = frequencies[index] % 2;
            sum += frequencies[index];
        }
        final boolean canCreatePalindrome = sum == 0 || sum == 1;
        return canCreatePalindrome ? YES: NO;
    }

    private static final String OUTPUT_PATH = "/output.txt";

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(OUTPUT_PATH));

        String s = scanner.nextLine();

        String result = gameOfThrones(s);

        bufferedWriter.write(result);
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}