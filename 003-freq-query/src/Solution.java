import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.joining;

/**
 * https://www.hackerrank.com/challenges/frequency-queries/problem
 */
public class Solution {

    // Complete the freqQuery function below.
    static List<Integer> freqQuery(int[][] queries) {
        List<Integer> out = new ArrayList<>();
        Map<Integer, Integer> mapValFreq = new HashMap<>();
        Map<Integer, Integer> mapFreqOcc = new HashMap<>();
        for (int[] query: queries) {
            int operation = query[0];
            int value = query[1];
            out = apply(operation, value, out, mapValFreq, mapFreqOcc);
        }
        return out;
    }

    private static List<Integer> apply(final int operation, final int value, List<Integer> out, Map<Integer, Integer> mapValFreq, Map<Integer, Integer> mapFreqOcc) {

        final Integer ZERO = 0;
        final Integer ONE = 1;

        switch (operation) {
            case 1:
                mapFreqOcc.compute(mapValFreq.get(value), (k, v) -> (v == null || ZERO.equals(v)) ? ZERO : v - ONE );
                mapValFreq.compute(value,                 (k, v) -> (v == null || ZERO.equals(v)) ? ONE : v + ONE);
                mapFreqOcc.compute(mapValFreq.get(value), (k, v) -> (v == null || ZERO.equals(v)) ? ONE : v + ONE );
                break;
            case 2:
                mapFreqOcc.compute(mapValFreq.get(value), (k, v) -> (v == null || ZERO.equals(v)) ? ZERO : v - ONE );
                mapValFreq.compute(value,                 (k, v) -> (v == null || ZERO.equals(v)) ? ZERO : v - ONE);
                mapFreqOcc.compute(mapValFreq.get(value), (k, v) -> (v == null || ZERO.equals(v)) ? ONE : v + ONE );
                break;
            case 3:
                out.add(mapFreqOcc.getOrDefault(value, ZERO) >= ONE ? ONE : ZERO);
                break;
        }
        return out;
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(System.in))) {

            int q = Integer.parseInt(bufferedReader.readLine().trim());
            int[][] queries = new int[q][2];

            for (int i = 0; i < q; i++) {
                String[] query = bufferedReader.readLine().split(" ");
                queries[i][0] = Integer.parseInt(query[0]);
                queries[i][1] = Integer.parseInt(query[1]);
            }

            List<Integer> ans = freqQuery(queries);

            try (BufferedWriter bufferedWriter = new BufferedWriter(
                    new FileWriter(System.getenv("OUTPUT_PATH")))) {

                bufferedWriter.write(ans.stream().map(Object::toString)
                        .collect(joining("\n")) + "\n");
            }
        }
    }
}