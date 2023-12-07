package exercise;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

// BEGIN
class App {
    public static void main(String[] args) {
        App.scrabble("rkqodlw", "woRld");
    }

    public static List<Character> getCharArrayList(String string) {
        List<Character> chars = new ArrayList<>();
        for (char c: string.toCharArray()) {
            chars.add(c);
        }

        return chars;
    }

    public static boolean scrabble(String chars, String word) {
        List<Character> arr = App.getCharArrayList(chars);
        boolean contains = true;
        String lowerCaseWord = word.toLowerCase();
        int i = 0;

        while (contains && i < lowerCaseWord.length()) {
            char letter = lowerCaseWord.charAt(i);

            int index = arr.indexOf(letter);
            if (index > -1) {
                arr.remove(index);
            } else {
                contains = false;
            }
            i++;
        }

        return contains;
    }
}
//END
