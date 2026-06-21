package hexlet.code.formatters;

import hexlet.code.DiffEntry;
import hexlet.code.DiffStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.LinkedHashMap;
import java.util.Map;

class JsonFormatterTest {

    private JsonFormatter formatter;
    private Map<String, DiffEntry> diff;

    @BeforeEach
    void setUp() {
        formatter = new JsonFormatter();
        diff = new LinkedHashMap<>();
    }

    @Test
    @DisplayName("Should format added property to JSON")
    void testFormatAddedProperty() throws Exception {
        diff.put("key1", new DiffEntry(DiffStatus.ADDED, null, "value1"));

        String result = formatter.format(diff);

        assertThat(result).isNotNull();
        assertThat(result).startsWith("{");
        assertThat(result).endsWith("}");
        assertThat(result).contains("\"status\" : \"added\"");
        assertThat(result).contains("\"newValue\" : \"value1\"");
        assertThat(result).doesNotContain("oldValue");
    }

    @Test
    @DisplayName("Should format removed property to JSON")
    void testFormatRemovedProperty() throws Exception {
        diff.put("key1", new DiffEntry(DiffStatus.REMOVED, "value1", null));

        String result = formatter.format(diff);

        assertThat(result).isNotNull();
        assertThat(result).contains("\"status\" : \"removed\"");
        assertThat(result).contains("\"oldValue\" : \"value1\"");
        assertThat(result).doesNotContain("newValue");
    }

    @Test
    @DisplayName("Should format unchanged property to JSON")
    void testFormatUnchangedProperty() throws Exception {
        diff.put("key1", new DiffEntry(DiffStatus.UNCHANGED, "value1", "value1"));

        String result = formatter.format(diff);

        assertThat(result).isNotNull();
        assertThat(result).contains("\"status\" : \"unchanged\"");
        assertThat(result).contains("\"oldValue\" : \"value1\"");
        assertThat(result).contains("\"newValue\" : \"value1\"");
    }

    @Test
    @DisplayName("Should format changed property to JSON")
    void testFormatChangedProperty() throws Exception {
        diff.put("key1", new DiffEntry(DiffStatus.CHANGED, "oldValue", "newValue"));

        String result = formatter.format(diff);

        assertThat(result).isNotNull();
        assertThat(result).contains("\"status\" : \"changed\"");
        assertThat(result).contains("\"oldValue\" : \"oldValue\"");
        assertThat(result).contains("\"newValue\" : \"newValue\"");
    }

    @Test
    @DisplayName("Should format multiple properties to JSON")
    void testFormatMultipleProperties() throws Exception {
        diff.put("key1", new DiffEntry(DiffStatus.ADDED, null, "value1"));
        diff.put("key2", new DiffEntry(DiffStatus.REMOVED, "value2", null));

        String result = formatter.format(diff);

        assertThat(result).isNotNull();
        assertThat(result).contains("key1");
        assertThat(result).contains("key2");
        assertThat(result).contains("\"status\" : \"added\"");
        assertThat(result).contains("\"status\" : \"removed\"");
    }

    @Test
    @DisplayName("Should handle null values in JSON")
    void testFormatNullValues() throws Exception {
        diff.put("key1", new DiffEntry(DiffStatus.CHANGED, null, "value1"));

        String result = formatter.format(diff);

        assertThat(result).isNotNull();
        assertThat(result).contains("\"status\" : \"changed\"");
        assertThat(result).contains("\"newValue\" : \"value1\"");
        assertThat(result).doesNotContain("oldValue");
    }

    @Test
    @DisplayName("Should format boolean values in JSON")
    void testFormatBooleanValues() throws Exception {
        diff.put("key1", new DiffEntry(DiffStatus.CHANGED, true, false));

        String result = formatter.format(diff);

        assertThat(result).isNotNull();
        assertThat(result).contains("\"oldValue\" : true");
        assertThat(result).contains("\"newValue\" : false");
    }

    @Test
    @DisplayName("Should format numeric values in JSON")
    void testFormatNumericValues() throws Exception {
        diff.put("key1", new DiffEntry(DiffStatus.CHANGED, 100, 200));

        String result = formatter.format(diff);

        assertThat(result).isNotNull();
        assertThat(result).contains("\"oldValue\" : 100");
        assertThat(result).contains("\"newValue\" : 200");
    }

    @Test
    @DisplayName("Should support json format")
    void testSupportsJsonFormat() {
        assertThat(formatter.supports("json")).isTrue();
        assertThat(formatter.supports("JSON")).isTrue();
        assertThat(formatter.supports("Json")).isTrue();
    }

    @Test
    @DisplayName("Should not support other formats")
    void testDoesNotSupportOtherFormats() {
        assertThat(formatter.supports("stylish")).isFalse();
        assertThat(formatter.supports("plain")).isFalse();
        assertThat(formatter.supports("xml")).isFalse();
    }

    @Test
    @DisplayName("Should handle empty diff")
    void testEmptyDiff() throws Exception {
        String result = formatter.format(diff);

        assertThat(result).isNotNull();
        assertThat(result).startsWith("{");
        assertThat(result).endsWith("}");
        assertThat(result.trim()).isEqualTo("{ }");
    }

    @Test
    @DisplayName("Should produce valid JSON")
    void testProducesValidJson() throws Exception {
        diff.put("key1", new DiffEntry(DiffStatus.CHANGED, "old", "new"));

        String result = formatter.format(diff);

        // Проверяем, что результат является валидным JSON
        assertThat(result).isNotNull();
        assertThat(result).startsWith("{");
        assertThat(result).endsWith("}");
        assertThat(result).contains("\"key1\"");
        assertThat(result).contains("\"status\"");
        assertThat(result).contains("\"oldValue\"");
        assertThat(result).contains("\"newValue\"");
    }
}
