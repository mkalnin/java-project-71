package hexlet.code;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

class DiffBuilderTest {

    @Test
    @DisplayName("Should detect added key")
    void testAddedKey() {
        Map<String, Object> map1 = new LinkedHashMap<>();
        Map<String, Object> map2 = new LinkedHashMap<>();
        map2.put("key1", "value1");

        Map<String, DiffEntry> diff = DiffBuilder.buildDiff(map1, map2);

        assertThat(diff).containsKey("key1");
        assertThat(diff.get("key1").getStatus()).isEqualTo(DiffStatus.ADDED);
        assertThat(diff.get("key1").getOldValue()).isNull();
        assertThat(diff.get("key1").getNewValue()).isEqualTo("value1");
    }

    @Test
    @DisplayName("Should detect removed key")
    void testRemovedKey() {
        Map<String, Object> map1 = new LinkedHashMap<>();
        map1.put("key1", "value1");
        Map<String, Object> map2 = new LinkedHashMap<>();

        Map<String, DiffEntry> diff = DiffBuilder.buildDiff(map1, map2);

        assertThat(diff).containsKey("key1");
        assertThat(diff.get("key1").getStatus()).isEqualTo(DiffStatus.REMOVED);
        assertThat(diff.get("key1").getOldValue()).isEqualTo("value1");
        assertThat(diff.get("key1").getNewValue()).isNull();
    }

    @Test
    @DisplayName("Should detect unchanged key")
    void testUnchangedKey() {
        Map<String, Object> map1 = new LinkedHashMap<>();
        map1.put("key1", "value1");
        Map<String, Object> map2 = new LinkedHashMap<>();
        map2.put("key1", "value1");

        Map<String, DiffEntry> diff = DiffBuilder.buildDiff(map1, map2);

        assertThat(diff).containsKey("key1");
        assertThat(diff.get("key1").getStatus()).isEqualTo(DiffStatus.UNCHANGED);
        assertThat(diff.get("key1").getOldValue()).isEqualTo("value1");
        assertThat(diff.get("key1").getNewValue()).isEqualTo("value1");
    }

    @Test
    @DisplayName("Should detect changed key")
    void testChangedKey() {
        Map<String, Object> map1 = new LinkedHashMap<>();
        map1.put("key1", "oldValue");
        Map<String, Object> map2 = new LinkedHashMap<>();
        map2.put("key1", "newValue");

        Map<String, DiffEntry> diff = DiffBuilder.buildDiff(map1, map2);

        assertThat(diff).containsKey("key1");
        assertThat(diff.get("key1").getStatus()).isEqualTo(DiffStatus.CHANGED);
        assertThat(diff.get("key1").getOldValue()).isEqualTo("oldValue");
        assertThat(diff.get("key1").getNewValue()).isEqualTo("newValue");
    }

    @Test
    @DisplayName("Should handle null values")
    void testNullValues() {
        Map<String, Object> map1 = new LinkedHashMap<>();
        map1.put("key1", null);
        Map<String, Object> map2 = new LinkedHashMap<>();
        map2.put("key1", null);

        Map<String, DiffEntry> diff = DiffBuilder.buildDiff(map1, map2);

        assertThat(diff).containsKey("key1");
        assertThat(diff.get("key1").getStatus()).isEqualTo(DiffStatus.UNCHANGED);
    }

    @Test
    @DisplayName("Should handle complex values")
    void testComplexValues() {
        Map<String, Object> map1 = new LinkedHashMap<>();
        map1.put("list", List.of(1, 2, 3));
        Map<String, Object> map2 = new LinkedHashMap<>();
        map2.put("list", List.of(1, 2, 3));

        Map<String, DiffEntry> diff = DiffBuilder.buildDiff(map1, map2);

        assertThat(diff).containsKey("list");
        assertThat(diff.get("list").getStatus()).isEqualTo(DiffStatus.UNCHANGED);
    }

    @Test
    @DisplayName("Should sort keys alphabetically")
    void testSortKeys() {
        Map<String, Object> map1 = new LinkedHashMap<>();
        map1.put("c", "value1");
        map1.put("a", "value2");
        Map<String, Object> map2 = new LinkedHashMap<>();
        map2.put("b", "value3");
        map2.put("a", "value2");

        Map<String, DiffEntry> diff = DiffBuilder.buildDiff(map1, map2);

        // Проверяем, что ключи отсортированы
        String[] keys = diff.keySet().toArray(new String[0]);
        assertThat(keys).isSorted();
    }
}
