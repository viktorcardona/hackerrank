import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.stream.IntStream;

/**
 *
 * Anagram Difference
 *
 * It should return the minimum number of characters to change to make the words anagrams, or -1 if it's not possible.
 *
 * https://www.hackerrank.com/challenges/anagram/problem
 *
 */
public class Solution2 {

    // Complete the anagram function below.
    static int anagram(String s) {
        if (s == null) return -1;
        if (s.length() % 2 != 0) return -1;
        String a = s.substring(0, s.length() / 2);
        String b = s.substring(s.length() / 2, s.length());
        if (a.equals(b)) return 0;
        int[] letters = new int[26];
        for (char character: a.toCharArray()) {
            letters[character - 'a']++;
        }
        for (char character: b.toCharArray()) {
            letters[character - 'a']--;
        }
        return IntStream.of(letters).filter(v -> v >= 0).sum();
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
