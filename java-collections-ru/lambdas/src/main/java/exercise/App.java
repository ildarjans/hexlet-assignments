package exercise;

import java.util.Arrays;

// BEGIN
class App {
    public static void main(String[] args) {}

    public static String[] duplicateValues(String[] items) {
        return Arrays.stream(items)
                .flatMap((item) -> Arrays.stream(new String[] { item, item }))
                .toArray(String[]::new);
    }
    public static String[][] enlargeArrayImage(String[][] arr) {
        String[][] horizontallyStretched = Arrays.stream(arr)
                .map(App::duplicateValues)
                .toArray(String[][]::new);

        return Arrays.stream(horizontallyStretched)
                .flatMap((item) -> Arrays.stream(new String[][] { item, item }))
                .toArray(String[][]::new);

    }
}

// END
