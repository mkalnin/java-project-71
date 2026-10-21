package hexlet.code.parsers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class ParserFactoryTest {

    @Test
    @DisplayName("Should return JsonParser for 'json' format")
    void testGetJsonParser() throws Exception {
        Parser parser = ParserFactory.getParser("json");
        assertNotNull(parser);
        assertInstanceOf(JsonParser.class, parser);
        assertTrue(parser.supports("json"));
        assertFalse(parser.supports("yml"));
        assertFalse(parser.supports("yaml"));
    }

    @Test
    @DisplayName("Should return YamlParser for 'yml' and 'yaml' formats")
    void testGetYamlParser() throws Exception {
        Parser parser = ParserFactory.getParser("yml");
        assertNotNull(parser);
        assertInstanceOf(YamlParser.class, parser);
        assertTrue(parser.supports("yml"));
        assertTrue(parser.supports("yaml"));

        parser = ParserFactory.getParser("yaml");
        assertNotNull(parser);
        assertInstanceOf(YamlParser.class, parser);
        assertTrue(parser.supports("yml"));
        assertTrue(parser.supports("yaml"));
    }

    @Test
    @DisplayName("Should throw exception for unsupported format")
    void testUnsupportedFormat() {
        Exception exception = assertThrows(Exception.class, () -> {
            ParserFactory.getParser("xml");
        });

        assertTrue(exception.getMessage().contains("Unsupported format"));
        assertTrue(exception.getMessage().contains("xml"));
    }

    @Test
    @DisplayName("Should throw exception when format is empty")
    void testEmptyFormat() {
        Exception exception = assertThrows(Exception.class, () -> {
            ParserFactory.getParser("");
        });

        assertTrue(exception.getMessage().contains("Cannot determine input format"));
    }

    @Test
    @DisplayName("Should be case insensitive")
    void testCaseInsensitivity() throws Exception {
        Parser parser = ParserFactory.getParser("JSON");
        assertNotNull(parser);
        assertInstanceOf(JsonParser.class, parser);

        parser = ParserFactory.getParser("YML");
        assertNotNull(parser);
        assertInstanceOf(YamlParser.class, parser);

        parser = ParserFactory.getParser("YAML");
        assertNotNull(parser);
        assertInstanceOf(YamlParser.class, parser);
    }

    @Test
    @DisplayName("YamlParser should support both yml and yaml formats")
    void testYamlParserSupport() throws Exception {
        Parser parser = ParserFactory.getParser("yml");
        assertTrue(parser.supports("yml"));
        assertTrue(parser.supports("yaml"));
        assertFalse(parser.supports("json"));
    }
}
