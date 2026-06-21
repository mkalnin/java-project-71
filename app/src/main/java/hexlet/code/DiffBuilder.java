package hexlet.code;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.HashSet;

public final class DiffBuilder {

    private DiffBuilder() {
    }

    public static Map<String, DiffEntry> buildDiff(Map<String, Object> map1,
                                                   Map<String, Object> map2) {
        Map<String, DiffEntry> diff = new TreeMap<>();
        Set<String> allKeys = new HashSet<>();
        allKeys.addAll(map1.keySet());
        allKeys.addAll(map2.keySet());

        for (String key : allKeys) {
            if (!map1.containsKey(key)) {
                diff.put(key, new DiffEntry(DiffStatus.ADDED, null, map2.get(key)));
            } else if (!map2.containsKey(key)) {
                diff.put(key, new DiffEntry(DiffStatus.REMOVED, map1.get(key), null));
            } else if (map1.get(key) == null && map2.get(key) == null) {
                diff.put(key, new DiffEntry(DiffStatus.UNCHANGED, null, null));
            } else if (map1.get(key) == null || map2.get(key) == null) {
                diff.put(key, new DiffEntry(DiffStatus.CHANGED, map1.get(key), map2.get(key)));
            } else if (isEqual(map1.get(key), map2.get(key))) {
                diff.put(key, new DiffEntry(DiffStatus.UNCHANGED, map1.get(key), map2.get(key)));
            } else {
                diff.put(key, new DiffEntry(DiffStatus.CHANGED, map1.get(key), map2.get(key)));
            }
        }

        return diff;
    }

    private static boolean isEqual(Object value1, Object value2) {
        if (value1 == null || value2 == null) {
            return false;
        }
        return value1.equals(value2);
    }
}
