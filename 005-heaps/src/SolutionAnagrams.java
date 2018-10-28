public class SolutionAnagrams {

    public static void main(String[] args) {
        String a = "anagramm";
        String b = "marganaa";

        boolean isAnagram = isAnagram(a, b);

        System.out.println("isAnagram: " + isAnagram);

    }

    static boolean isAnagram(String a, String b) {
        // Complete the function

        java.util.Map<Character, Integer> frequencyA = frequency(a);
        java.util.Map<Character, Integer> frequencyB = frequency(b);

        if (frequencyA.size() != frequencyB.size()) {
            return false;
        }

        for (Character character: frequencyA.keySet()) {
            if (frequencyA.get(character).intValue() != frequencyB.getOrDefault(character, -1)) {
                return false;
            }
        }

        return true;
    }

    private static java.util.Map<Character,Integer> frequency(String a) {
        java.util.Map<Character, Integer> frequency = new java.util.HashMap<>();
        for (char character : a.toLowerCase().toCharArray()) {
            frequency.compute(character, (k, v) -> v == null ? 1 : v + 1);
        }
        return frequency;
    }

}
