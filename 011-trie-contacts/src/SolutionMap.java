import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 *  Tries
 *
 *  https://www.hackerrank.com/challenges/contacts/problem
 *
 *  ---> This is not the Solution!!!!
 *          Because this uses a Map not a Try.
 *          Check --> Solution.java
 *
 */
public class SolutionMap {

    private static final Integer ZERO = 0;
    private static final Integer ONE = 1;
    public static final String ADD = "add";

    /*
     * Complete the contacts function below.
     */
    static int[] contacts(String[][] queries) {
        /*
         * Write your code here.
         */
        int[] numberOfContacts = new int[queries.length];
        int index = 0;

        Map<String, Integer> frequency = new HashMap<>();

        for (int i = 0; i < queries.length; i++) {
            String contact = queries[i][1];
            if (ADD.equals(queries[i][0])) {
                frequency = add(contact, frequency);
            } else { //find
                numberOfContacts[index] = frequency.getOrDefault(contact, ZERO);
                index++;
            }
        }

        return Arrays.copyOfRange(numberOfContacts, 0, index);
    }

    private static Map<String, Integer> add(String contact, Map<String, Integer> frequency) {
        for (int j=1; j <= contact.length(); j++) {
            String prefix = contact.substring(0, j);
            frequency.merge(prefix, ONE, Integer::sum);
        }
        return frequency;
    }

    private static final String OUTPUT_PATH = "/Users/victorcardona/Documents/code/hackerrank/011-trie-contacts/output.txt";

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(OUTPUT_PATH));

        String fileName = "/Users/victorcardona/Documents/code/hackerrank/011-trie-contacts/input1.txt";

        List<String> lines = Files.readAllLines(Paths.get(fileName));
        String[][] queries = new String[lines.size() - 1][2];
        lines.remove(0);

        int index = 0;
        for (String line: lines) {
            String[] split = line.split(" ");
            queries[index][0] = split[0];
            queries[index][1] = split[1];
            index++;
        }

        int[] result = contacts(queries);

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
