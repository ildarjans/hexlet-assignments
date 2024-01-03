package exercise;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

// BEGIN
class App {
    public static void main(String[] args) {}

    public static Map<String, String> genDiff(Map<String, Object> map1, Map<String, Object> map2) {
        Map<String, String> result = new LinkedHashMap<>();

        for (Map.Entry<String, Object> entry: map1.entrySet()) {
            result.put(entry.getKey(), "deleted");
        }

        for (Map.Entry<String, Object> entry: map2.entrySet()) {
            result.compute(entry.getKey(), (k, v) -> {
                if (v == null) {
                    return "added";
                }

                if (map1.get(k).equals(entry.getValue())) {
                    return "unchanged";
                }

                return "changed";
            });
        }

        return result;
    }
}
//END
