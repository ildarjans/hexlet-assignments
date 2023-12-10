package exercise;

import java.util.HashMap;
import java.util.Map;

// BEGIN
class App {
    public static void main(String[] args) {
        System.out.println("getWordCount method");
    }

    public static Map getWordCount(String str) {
        Map<String, Integer> dict = new HashMap<>();

        if (str.equals("")) {
            return dict;
        }

        String[] words = str.split(" ");

        for (String word: words) {
            dict.putIfAbsent(word, 0);
            dict.computeIfPresent(word, (k, v) -> v + 1);
        }

        return dict;
    }

    public static String toString(Map<String, Integer> map) {
        if (map.size() == 0) {
            return "{}";
        }

        StringBuilder result = new StringBuilder("{\n");

        for (Map.Entry<String, Integer> entry: map.entrySet()) {
            result.append("  ");
            result.append(entry.getKey());
            result.append(": ");
            result.append(entry.getValue());
            result.append("\n");
        }

        result.append("}");

        return result.toString();
    }
}
//END
