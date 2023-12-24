package exercise;

import java.util.Comparator;
import java.util.Map;
import java.util.List;
import java.time.LocalDate;
import java.util.stream.Collectors;

// BEGIN
class Sorter {
    public static List<String> takeOldestMans(List<Map<String, String>> peoples) {
        return peoples.stream()
                .filter(man -> man.getOrDefault("gender", "female") == "male")
                .sorted((m1, m2) -> {
                    LocalDate date1 = LocalDate.parse(m1.get("birthday"));
                    LocalDate date2 = LocalDate.parse(m2.get("birthday"));

                    return date1.compareTo(date2);
                })
                .map(man -> man.get("name"))
                .collect(Collectors.toList());
    }
}
// END
