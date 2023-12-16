package exercise;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Map.Entry;

// BEGIN
class App {
    public static void main(String[] args) {}

    public static <T extends Map<String, String>> List<T> findWhere(List<T> arr, T find) {
        List<T> result = new ArrayList<>();

        for (var book: arr) {
            boolean match = true;
            for (Map.Entry<String, String> entry: find.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (!book.containsKey(key) || !book.get(key).equals(value)) {
                    match = false;
                    break;
                }
            }

            if (match) {
                result.add(book);
            }
        }

        return result;
    }

}
//END
