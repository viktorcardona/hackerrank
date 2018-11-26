import java.io.*;
import java.util.*;

/**
 *      Sherlock and the Valid String
 *
 *      https://www.hackerrank.com/challenges/sherlock-and-valid-string/problem
 *
 *
 */
public class Solution {

    private static final String YES = "YES";
    private static final String NO = "NO";
    private static final char FIRST_LETTER = 'a';

    // Complete the isValid function below.
    static String isValid(String s) {
        int[] frequencies = new int[26];
        for (char character: s.toCharArray()) frequencies[character - FIRST_LETTER]++;
        frequencies = Arrays.stream(frequencies).filter(v -> v != 0).toArray();
        final int targetValue = frequencies[0];
        final boolean allCharsSameFrequency = Arrays.stream(frequencies).allMatch(v -> v == targetValue);
        if (allCharsSameFrequency) return YES;
        int[] frequenciesDistinct = Arrays.stream(frequencies).distinct().toArray();
        if (frequenciesDistinct.length != 2) return NO;
        final boolean isValueFreq1 = freqIs1(frequencies, frequenciesDistinct[0]) || freqIs1(frequencies, frequenciesDistinct[1]);
        if (isValueFreq1 && Math.abs(frequenciesDistinct[0] - frequenciesDistinct[1]) == 1) return YES;
        if (frequenciesDistinct[0] == 1 && freqIs1(frequencies, frequenciesDistinct[0])) return YES;
        if (frequenciesDistinct[1] == 1 && freqIs1(frequencies, frequenciesDistinct[1])) return YES;
        return NO;
    }

    private static boolean freqIs1(int[] frequencies, int value) {
        return Arrays.stream(frequencies).filter(v -> v == value).count() == 1l;
    }

    private static final String OUTPUT_PATH = "/output.txt";

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(OUTPUT_PATH));

        String s = scanner.nextLine();

        String result = isValid(s);

        bufferedWriter.write(result);
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}