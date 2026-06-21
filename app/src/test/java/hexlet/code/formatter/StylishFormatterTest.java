package hexlet.code.formatters;

import hexlet.code.DiffEntry;
import hexlet.code.DiffStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

class StylishFormatterTest {

    private StylishFormatter formatter;
    private Map<String, DiffEntry> diff;

    @BeforeEach
    void setUp() {
        formatter = new StylishFormatter();
        diff = new LinkedHashMap<>();
    }

    @Test
    @DisplayName("Should format added property")
    void testFormatAddedProperty() throws Exception {
        diff.put("key1", new DiffEntry(DiffStatus.ADDED, null, "value1"));

        String result = formatter.format(diff);

        assertThat(result).isNotNull();
        assertThat(result).startsWith("{");
        assertThat(result).endsWith("}");
        assertThat(result).contains("+ key1: value1");
    }

    @Test
    @DisplayName("Should format removed property")
    void testFormatRemovedProperty() throws Exception {
        diff.put("key1", new DiffEntry(DiffStatus.REMOVED, "value1", null));

        String result = formatter.format(diff);

        assertThat(result).isNotNull();
        assertThat(result).contains("- key1: value1");
    }

    @Test
    @DisplayName("Should format unchanged property")
    void testFormatUnchangedProperty() throws Exception {
        diff.put("key1", new DiffEntry(DiffStatus.UNCHANGED, "value1", "value1"));

        String result = formatter.format(diff);

        assertThat(result).isNotNull();
        assertThat(result).contains("    key1: value1");
    }

    @Test
    @DisplayName("Should format changed property")
    void testFormatChangedProperty() throws Exception {
        diff.put("key1", new DiffEntry(DiffStatus.CHANGED, "oldValue", "newValue"));

        String result = formatter.format(diff);

        assertThat(result).isNotNull();
        assertThat(result).contains("- key1: oldValue");
        assertThat(result).contains("+ key1: newValue");
    }

    @Test
    @DisplayName("Should format multiple properties")
    void testFormatMultipleProperties() throws Exception {
        diff.put("key1", new DiffEntry(DiffStatus.ADDED, null, "value1"));
        diff.put("key2", new DiffEntry(DiffStatus.REMOVED, "value2", null));
        diff.put("key3", new DiffEntry(DiffStatus.UNCHANGED, "value3", "value3"));
        diff.put("key4", new DiffEntry(DiffStatus.CHANGED, "old", "new"));

        String result = formatter.format(diff);

        assertThat(result).isNotNull();
        assertThat(result).contains("+ key1: value1");
        assertThat(result).contains("- key2: value2");
        assertThat(result).contains("    key3: value3");
        assertThat(result).contains("- key4: old");
        assertThat(result).contains("+ key4: new");
    }

    @Test
    @DisplayName("Should format null value")
    void testFormatNullValue() throws Exception {
        diff.put("key1", new DiffEntry(DiffStatus.ADDED, null, null));

        String result = formatter.format(diff);

        assertThat(result).isNotNull();
        assertThat(result).contains("+ key1: null");
    }

    @Test
    @DisplayName("Should format boolean values")
    void testFormatBooleanValues() throws Exception {
        diff.put("key1", new DiffEntry(DiffStatus.CHANGED, true, false));

        String result = formatter.format(diff);

        assertThat(result).isNotNull();
        assertThat(result).contains("- key1: true");
        assertThat(result).contains("+ key1: false");
    }

    @Test
    @DisplayName("Should format numeric values")
    void testFormatNumericValues() throws Exception {
        diff.put("key1", new DiffEntry(DiffStatus.CHANGED, 100, 200));

        String result = formatter.format(diff);

        assertThat(result).isNotNull();
        assertThat(result).contains("- key1: 100");
        assertThat(result).contains("+ key1: 200");
    }

    @Test
    @DisplayName("Should format list values")
    void testFormatListValues() throws Exception {
        List<String> oldList = List.of("a", "b", "c");
        List<Integer> newList = List.of(1, 2, 3);

        diff.put("key1", new DiffEntry(DiffStatus.CHANGED, oldList, newList));

        String result = formatter.format(diff);

        assertThat(result).isNotNull();
        assertThat(result).contains("- key1: [a, b, c]");
        assertThat(result).contains("+ key1: [1, 2, 3]");
    }

    @Test
    @DisplayName("Should format map values")
    void testFormatMapValues() throws Exception {
        Map<String, Object> mapValue = new LinkedHashMap<>();
        mapValue.put("nestedKey", "value");
        mapValue.put("isNested", true);

        diff.put("key1", new DiffEntry(DiffStatus.ADDED, null, mapValue));

        String result = formatter.format(diff);

        assertThat(result).isNotNull();
        assertThat(result).contains("+ key1: {nestedKey=value, isNested=true}");
    }

    @Test
    @DisplayName("Should support stylish format")
    void testSupportsStylishFormat() {
        assertThat(formatter.supports("stylish")).isTrue();
        assertThat(formatter.supports("STYLISH")).isTrue();
        assertThat(formatter.supports("Stylish")).isTrue();
    }

    @Test
    @DisplayName("Should not support other formats")
    void testDoesNotSupportOtherFormats() {
        assertThat(formatter.supports("plain")).isFalse();
        assertThat(formatter.supports("json")).isFalse();
        assertThat(formatter.supports("xml")).isFalse();
    }

    @Test
    @DisplayName("Should handle empty diff")
    void testEmptyDiff() throws Exception {
        String result = formatter.format(diff);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo("{\n}");
    }
}
