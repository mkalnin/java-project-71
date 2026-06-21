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
    @DisplayName("Should format added property with string value")
    void testFormatAddedProperty() throws Exception {
        diff.put("key2", new DiffEntry(DiffStatus.ADDED, null, "value2"));

        String result = formatter.format(diff);

        assertThat(result).isEqualTo("Property 'key2' was added with value: 'value2'");
    }

    @Test
    @DisplayName("Should format added property with complex value")
    void testFormatAddedPropertyComplexValue() throws Exception {
        List<String> complexValue = List.of("a", "b", "c");
        diff.put("obj1", new DiffEntry(DiffStatus.ADDED, null, complexValue));

        String result = formatter.format(diff);

        assertThat(result).isEqualTo("Property 'obj1' was added with value: [complex value]");
    }

    @Test
    @DisplayName("Should format removed property")
    void testFormatRemovedProperty() throws Exception {
        diff.put("key1", new DiffEntry(DiffStatus.REMOVED, "value1", null));

        String result = formatter.format(diff);

        assertThat(result).isEqualTo("Property 'key1' was removed");
    }

    @Test
    @DisplayName("Should format updated property")
    void testFormatUpdatedProperty() throws Exception {
        diff.put("setting1", new DiffEntry(DiffStatus.CHANGED, "Some value", "Another value"));

        String result = formatter.format(diff);

        assertThat(result).isEqualTo("Property 'setting1' was updated. From 'Some value' to 'Another value'");
    }

    @Test
    @DisplayName("Should format updated property with complex values")
    void testFormatUpdatedPropertyComplexValues() throws Exception {
        List<Integer> oldValue = List.of(2, 3, 4, 5);
        List<Integer> newValue = List.of(22, 33, 44, 55);
        diff.put("numbers2", new DiffEntry(DiffStatus.CHANGED, oldValue, newValue));

        String result = formatter.format(diff);

        assertThat(result).isEqualTo(
                "Property 'numbers2' was updated. From [complex value] to [complex value]");
    }

    @Test
    @DisplayName("Should not include unchanged properties")
    void testDoesNotIncludeUnchangedProperties() throws Exception {
        diff.put("chars1", new DiffEntry(DiffStatus.UNCHANGED, List.of("a", "b", "c"), List.of("a", "b", "c")));

        String result = formatter.format(diff);

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Should format multiple properties")
    void testFormatMultipleProperties() throws Exception {
        diff.put("key2", new DiffEntry(DiffStatus.ADDED, null, "value2"));
        diff.put("key1", new DiffEntry(DiffStatus.REMOVED, "value1", null));
        diff.put("setting3", new DiffEntry(DiffStatus.CHANGED, true, "none"));

        String result = formatter.format(diff);

        assertThat(result).contains("Property 'key2' was added with value: 'value2'");
        assertThat(result).contains("Property 'key1' was removed");
        assertThat(result).contains("Property 'setting3' was updated. From true to 'none'");
        assertThat(result).doesNotContain("chars1");
    }

    @Test
    @DisplayName("Should format null values")
    void testFormatNullValues() throws Exception {
        diff.put("id", new DiffEntry(DiffStatus.CHANGED, 45, null));

        String result = formatter.format(diff);

        assertThat(result).isEqualTo("Property 'id' was updated. From 45 to null");
    }

    @Test
    @DisplayName("Should support plain format")
    void testSupportsPlainFormat() {
        assertThat(formatter.supports("plain")).isTrue();
        assertThat(formatter.supports("PLAIN")).isTrue();
    }

    @Test
    @DisplayName("Should not support other formats")
    void testDoesNotSupportOtherFormats() {
        assertThat(formatter.supports("stylish")).isFalse();
        assertThat(formatter.supports("json")).isFalse();
    }
}

