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

class PlainFormatterTest {

    private PlainFormatter formatter;
    private Map<String, DiffEntry> diff;

    @BeforeEach
    void setUp() {
        formatter = new PlainFormatter();
        diff = new LinkedHashMap<>();
    }

    @Test
    @DisplayName("Should format added property")
    void testFormatAddedProperty() throws Exception {
        diff.put("key1", new DiffEntry(DiffStatus.ADDED, null, "value1"));

        String result = formatter.format(diff);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo("Property 'key1' was added with value: 'value1'");
    }

    @Test
    @DisplayName("Should format added property with complex value")
    void testFormatAddedPropertyComplexValue() throws Exception {
        List<String> complexValue = List.of("a", "b", "c");
        diff.put("key1", new DiffEntry(DiffStatus.ADDED, null, complexValue));

        String result = formatter.format(diff);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo("Property 'key1' was added with value: [complex value]");
    }

    @Test
    @DisplayName("Should format removed property")
    void testFormatRemovedProperty() throws Exception {
        diff.put("key1", new DiffEntry(DiffStatus.REMOVED, "value1", null));

        String result = formatter.format(diff);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo("Property 'key1' was removed");
    }

    @Test
    @DisplayName("Should format changed property")
    void testFormatChangedProperty() throws Exception {
        diff.put("key1", new DiffEntry(DiffStatus.CHANGED, "oldValue", "newValue"));

        String result = formatter.format(diff);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo("Property 'key1' was updated. From 'oldValue' to 'newValue'");
    }

    @Test
    @DisplayName("Should not include unchanged properties")
    void testDoesNotIncludeUnchangedProperties() throws Exception {
        diff.put("key1", new DiffEntry(DiffStatus.UNCHANGED, "value1", "value1"));

        String result = formatter.format(diff);

        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
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
        assertThat(result).contains("Property 'key1' was added with value: 'value1'");
        assertThat(result).contains("Property 'key2' was removed");
        assertThat(result).contains("Property 'key4' was updated. From 'old' to 'new'");
        assertThat(result).doesNotContain("key3");
    }

    @Test
    @DisplayName("Should format null value")
    void testFormatNullValue() throws Exception {
        diff.put("key1", new DiffEntry(DiffStatus.ADDED, null, null));

        String result = formatter.format(diff);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo("Property 'key1' was added with value: null");
    }

    @Test
    @DisplayName("Should format boolean values without quotes")
    void testFormatBooleanValues() throws Exception {
        diff.put("key1", new DiffEntry(DiffStatus.CHANGED, true, false));

        String result = formatter.format(diff);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo("Property 'key1' was updated. From true to false");
    }

    @Test
    @DisplayName("Should format numeric values without quotes")
    void testFormatNumericValues() throws Exception {
        diff.put("key1", new DiffEntry(DiffStatus.CHANGED, 100, 200));

        String result = formatter.format(diff);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo("Property 'key1' was updated. From 100 to 200");
    }

    @Test
    @DisplayName("Should format complex values as [complex value]")
    void testFormatComplexValues() throws Exception {
        Map<String, Object> complexValue = Map.of("key", "value");
        diff.put("key1", new DiffEntry(DiffStatus.CHANGED, complexValue, complexValue));

        String result = formatter.format(diff);

        assertThat(result).isNotNull();
        assertThat(result).contains("[complex value]");
    }

    @Test
    @DisplayName("Should support plain format")
    void testSupportsPlainFormat() {
        assertThat(formatter.supports("plain")).isTrue();
        assertThat(formatter.supports("PLAIN")).isTrue();
        assertThat(formatter.supports("Plain")).isTrue();
    }

    @Test
    @DisplayName("Should not support other formats")
    void testDoesNotSupportOtherFormats() {
        assertThat(formatter.supports("stylish")).isFalse();
        assertThat(formatter.supports("json")).isFalse();
        assertThat(formatter.supports("xml")).isFalse();
    }

    @Test
    @DisplayName("Should handle empty diff")
    void testEmptyDiff() throws Exception {
        String result = formatter.format(diff);

        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
    }
}
