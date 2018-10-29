import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

/**
 *  Balanced Brackets
 *  https://www.hackerrank.com/challenges/balanced-brackets/problem
 *
 *      A bracket is considered to be any one of the following characters: (, ), {, }, [, or ]
 */
public class Solution {

    private static final Map<Character, Character> bracketOpenClose = initOpenCloseBrackets();
    private static final String NO = "NO";
    private static final String YES = "YES";

    private static Map<Character,Character> initOpenCloseBrackets() {
        Map<Character, Character> map = new HashMap<>();
        map.put(toCharacter("("),toCharacter(")"));
        map.put(toCharacter("{"),toCharacter("}"));
        map.put(toCharacter("["),toCharacter("]"));
        return map;
    }

    // Complete the isBalanced function below.
    static String isBalanced(String s) {

        Stack<Character> stack = new Stack<>();

        for (Character bracket: s.toCharArray()) {
            boolean isOpenBracket = bracketOpenClose.containsKey(bracket);
            if (isOpenBracket) {
                stack.push(bracket);
            } else {
                boolean isMatchedClosedBracket = isMatchedClosedBracket(bracket, stack);
                if (!isMatchedClosedBracket) {
                    return NO;
                }
            }
        }

        return stack.isEmpty() ? YES : NO;
    }

    private static boolean isMatchedClosedBracket(Character bracket, Stack<Character> stack) {
        if (stack.isEmpty()) {
            return false;
        }
        Character lastOpenBracket = stack.pop();
        Character closeBracket = bracketOpenClose.get(lastOpenBracket);
        return bracket.equals(closeBracket);
    }

    private static Character toCharacter(String letter) {
        return Character.valueOf(letter.charAt(0));
    }

    private static final String OUTPUT_PATH = "/output.txt";
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(OUTPUT_PATH));

        int t = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int tItr = 0; tItr < t; tItr++) {
            String s = scanner.nextLine();

            String result = isBalanced(s);

            bufferedWriter.write(result);
            bufferedWriter.newLine();
        }

        bufferedWriter.close();

        scanner.close();
    }
}