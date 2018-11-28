import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 *  Sherlock and Anagrams
 *
 *  https://www.hackerrank.com/challenges/sherlock-and-anagrams/problem
 *
 */
public class Solution {
    private static final int ALPHABET_SIZE = 26;
    private static final char FIRST_CHAR = 'a';

    // Complete the sherlockAndAnagrams function below.
    static int sherlockAndAnagrams(String s) {
        if (s == null || s.length() == 1) return 0;
        List<String> allSubstrings = allSubstrings(s);
        int countAnagrams = 0;
        for (int i = 0; i < allSubstrings.size(); i++) {
            String first = allSubstrings.get(i);
            for (int j = i + 1; j < allSubstrings.size(); j++) {
                String second = allSubstrings.get(j);
                if (areAnagrams(first, second)) {
                    countAnagrams++;
                }
            }
        }
        return countAnagrams;
    }

    private static List<String> allSubstrings(String s) {
        List<String> substrings = new ArrayList<>();
        for (int i=0; i < s.length(); i++) {
            for (int j= i + 1; j <= s.length(); j++) {
                substrings.add(s.substring(i , j));
            }
        }
        return substrings;
    }

    static boolean areAnagrams(String s1, String s2) {
        if (s1.length() != s2.length()) return false;
        int[] frequencyS1 = frequency(s1);
        int[] frequencyS2 = frequency(s2);
        for (int i = 0; i < ALPHABET_SIZE; i++) {
            if (frequencyS1[i] != frequencyS2[i]) {
                return false;
            }
        }
        return true;
    }


    private static int[] frequency(String s1) {
        int[] frequency = new int[ALPHABET_SIZE];
        for (char character : s1.toCharArray()) {
            frequency[character - FIRST_CHAR]++;
        }
        return frequency;
    }

    private static final String OUTPUT_PATH = "/output.txt";
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(OUTPUT_PATH));

        int q = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int qItr = 0; qItr < q; qItr++) {
            String s = scanner.nextLine();

            int result = sherlockAndAnagrams(s);

            bufferedWriter.write(String.valueOf(result));
            bufferedWriter.newLine();
        }

        bufferedWriter.close();

        scanner.close();
    }
}
