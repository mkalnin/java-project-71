package hexlet.code.formatters;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FormatterFactoryTest {

    @Test
    @DisplayName("Should return StylishFormatter for 'stylish' format")
    void testGetStylishFormatter() throws Exception {
        Formatter formatter = FormatterFactory.getFormatter("stylish");
        assertThat(formatter).isNotNull();
        assertThat(formatter).isInstanceOf(StylishFormatter.class);
    }

    @Test
    @DisplayName("Should return PlainFormatter for 'plain' format")
    void testGetPlainFormatter() throws Exception {
        Formatter formatter = FormatterFactory.getFormatter("plain");
        assertThat(formatter).isNotNull();
        assertThat(formatter).isInstanceOf(PlainFormatter.class);
    }

    @Test
    @DisplayName("Should return JsonFormatter for 'json' format")
    void testGetJsonFormatter() throws Exception {
        Formatter formatter = FormatterFactory.getFormatter("json");
        assertThat(formatter).isNotNull();
        assertThat(formatter).isInstanceOf(JsonFormatter.class);
    }

    @Test
    @DisplayName("Should be case insensitive")
    void testCaseInsensitivity() throws Exception {
        assertThat(FormatterFactory.getFormatter("STYLISH"))
                .isInstanceOf(StylishFormatter.class);
        assertThat(FormatterFactory.getFormatter("Plain"))
                .isInstanceOf(PlainFormatter.class);
        assertThat(FormatterFactory.getFormatter("JSON"))
                .isInstanceOf(JsonFormatter.class);
    }

    @Test
    @DisplayName("Should throw exception for unsupported format")
    void testUnsupportedFormat() {
        Exception exception = assertThrows(Exception.class, () -> {
            FormatterFactory.getFormatter("xml");
        });

        assertThat(exception.getMessage()).contains("Unsupported format");
        assertThat(exception.getMessage()).contains("xml");
    }
}
