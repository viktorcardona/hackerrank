import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * Anagram Difference
 *
 * It should return the minimum number of characters to change to make the words anagrams, or -1 if it's not possible.
 *
 * https://www.hackerrank.com/challenges/anagram/problem
 *
 */
public class Solution {

    // Complete the anagram function below.
    static int anagram(String s) {
        if (s == null) return -1;
        if (s.length() % 2 != 0) return -1;
        String a = s.substring(0, s.length() / 2);
        String b = s.substring(s.length() / 2, s.length());
        if (a.equals(b)) return 0;
        Map<Character, Integer> aFreq = frequency(a);
        Map<Character, Integer> bFreq = frequency(b);
        int countChanges = 0;
        for (Character key: aFreq.keySet()) {
            int freqA = aFreq.get(key);
            int freqB = bFreq.getOrDefault(key, 0);
            countChanges += ((freqA - freqB) < 0) ? 0: (freqA - freqB);
        }
        return countChanges;
    }

    private static Map<Character,Integer> frequency(String s) {
        Map<Character, Integer> frequency = new HashMap<>();
        for (char character: s.toCharArray()) {
            frequency.merge(character, 1, Integer::sum);
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

            int result = anagram(s);

            bufferedWriter.write(String.valueOf(result));
            bufferedWriter.newLine();
        }

        bufferedWriter.close();

        scanner.close();
    }

}
