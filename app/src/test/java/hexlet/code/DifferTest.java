package hexlet.code;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Paths;

class DifferTest {
    private static String file1Json;
    private static String file2Json;
    private static String file3Json;
    private static String file1Yaml;
    private static String file2Yaml;

    @BeforeAll
    static void setUp() throws Exception {
        file1Json = Paths.get("src/test/resources/file1.json")
                .toAbsolutePath().toString();
        file2Json = Paths.get("src/test/resources/file2.json")
                .toAbsolutePath().toString();
        file3Json = Paths.get("src/test/resources/file3.json")
                .toAbsolutePath().toString();
        file1Yaml = Paths.get("src/test/resources/file1.yml")
                .toAbsolutePath().toString();
        file2Yaml = Paths.get("src/test/resources/file2.yml")
                .toAbsolutePath().toString();
    }

    // Тесты для JSON файлов
    @Test
    @DisplayName("Should return empty diff for identical JSON files")
    void testCompareIdenticalJsonFiles() throws Exception {
        String result = Differ.generate(file1Json, file2Json);
        assertThat(result).isNotNull();
        assertThat(result).contains("host");
        assertThat(result).contains("timeout");
        assertThat(result).doesNotContain("+");
        assertThat(result).doesNotContain("-");
    }

    @Test
    @DisplayName("Should show differences between different JSON files")
    void testCompareDifferentJsonFiles() throws Exception {
        String result = Differ.generate(file1Json, file3Json);
        assertThat(result).isNotNull();
        assertThat(result).contains("host");
        assertThat(result).contains("127.0.0.1");
    }

    @Test
    @DisplayName("Should format output in stylish format for JSON")
    void testStylishFormatJson() throws Exception {
        String result = Differ.generate(file1Json, file3Json, "stylish");
        assertThat(result).isNotNull();
        assertThat(result).startsWith("{");
        assertThat(result).endsWith("}");
        assertThat(result).contains("- host: localhost");
        assertThat(result).contains("+ host: 127.0.0.1");
    }

    @Test
    @DisplayName("Should format output in plain format for JSON")
    void testPlainFormatJson() throws Exception {
        String result = Differ.generate(file1Json, file3Json, "plain");
        assertThat(result).isNotNull();
        assertThat(result).contains("Property");
    }

    @Test
    @DisplayName("Should format output in JSON format for JSON")
    void testJsonFormatJson() throws Exception {
        String result = Differ.generate(file1Json, file3Json, "json");
        assertThat(result).isNotNull();
        assertThat(result).startsWith("{");
        assertThat(result).contains("status");
        assertThat(result).contains("changed");
    }

    // Тесты для YAML файлов
    @Test
    @DisplayName("Should compare YAML files and show differences")
    void testYamlComparison() throws Exception {
        String result = Differ.generate(file1Yaml, file2Yaml);
        assertThat(result).isNotNull();

        // Проверяем наличие ожидаемых элементов в выводе
        assertThat(result).contains("- follow: false");
        assertThat(result).contains("host: hexlet.io");
        assertThat(result).contains("- proxy: 123.234.53.22");
        assertThat(result).contains("- timeout: 50");
        assertThat(result).contains("+ timeout: 20");
        assertThat(result).contains("+ verbose: true");
    }

    @Test
    @DisplayName("Should format output in stylish format for YAML")
    void testYamlStylishFormat() throws Exception {
        String result = Differ.generate(file1Yaml, file2Yaml, "stylish");
        assertThat(result).isNotNull();
        assertThat(result).startsWith("{");
        assertThat(result).endsWith("}");
    }

    @Test
    @DisplayName("Should format output in plain format for YAML")
    void testYamlPlainFormat() throws Exception {
        String result = Differ.generate(file1Yaml, file2Yaml, "plain");
        assertThat(result).isNotNull();
        assertThat(result).contains("Property 'timeout' was updated");
    }

    @Test
    @DisplayName("Should format output in JSON format for YAML")
    void testYamlJsonFormat() throws Exception {
        String result = Differ.generate(file1Yaml, file2Yaml, "json");
        assertThat(result).isNotNull();
        assertThat(result).startsWith("{");
        assertThat(result).contains("status");
    }

    // Тесты для смешанных форматов
    @Test
    @DisplayName("Should compare JSON and YAML files")
    void testMixedFormatsJsonAndYaml() throws Exception {
        String result = Differ.generate(file1Json, file2Yaml);
        assertThat(result).isNotNull();
        assertThat(result).startsWith("{");
        assertThat(result).endsWith("}");
    }

    @Test
    @DisplayName("Should compare YAML and JSON files")
    void testMixedFormatsYamlAndJson() throws Exception {
        String result = Differ.generate(file1Yaml, file2Json);
        assertThat(result).isNotNull();
        assertThat(result).startsWith("{");
        assertThat(result).endsWith("}");
    }

    // Тесты на ошибки
    @Test
    @DisplayName("Should throw exception for invalid format")
    void testInvalidFormat() {
        assertThrows(Exception.class, () -> {
            Differ.generate(file1Json, file3Json, "invalid");
        });
    }

    @Test
    @DisplayName("Should throw exception for unsupported file format")
    void testInvalidFileFormat() {
        assertThrows(Exception.class, () -> {
            Differ.generate("test.txt", file1Json);
        });
    }
}
