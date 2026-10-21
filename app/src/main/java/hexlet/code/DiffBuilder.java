package hexlet.code;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public final class DiffBuilder {

    private DiffBuilder() {
    }

    public static Map<String, DiffEntry> buildDiff(Map<String, Object> map1,
                                                   Map<String, Object> map2) {
        Map<String, DiffEntry> diff = new TreeMap<>();
        Set<String> allKeys = new TreeSet<>(map1.keySet());
        allKeys.addAll(map2.keySet());

        for (String key : allKeys) {
            Object value1 = map1.get(key);
            Object value2 = map2.get(key);

            if (!map1.containsKey(key)) {
                diff.put(key, new DiffEntry(DiffStatus.ADDED, null, value2));
            } else if (!map2.containsKey(key)) {
                diff.put(key, new DiffEntry(DiffStatus.REMOVED, value1, null));
            } else if (Objects.equals(value1, value2)) {
                diff.put(key, new DiffEntry(DiffStatus.UNCHANGED, value1, value2));
            } else {
                diff.put(key, new DiffEntry(DiffStatus.CHANGED, value1, value2));
            }
        }

        return diff;
    }
}
