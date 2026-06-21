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
    private static String file4Json;
    private static String file5Json;
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
        file4Json = Paths.get("src/test/resources/file4.json")
                .toAbsolutePath().toString();
        file5Json = Paths.get("src/test/resources/file5.json")
                .toAbsolutePath().toString();
        file1Yaml = Paths.get("src/test/resources/file1.yml")
                .toAbsolutePath().toString();
        file2Yaml = Paths.get("src/test/resources/file2.yml")
                .toAbsolutePath().toString();
    }

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
    @DisplayName("Should show differences between different flat JSON files")
    void testCompareDifferentJsonFiles() throws Exception {
        String result = Differ.generate(file1Json, file3Json);
        assertThat(result).isNotNull();
        assertThat(result).contains("host");
        assertThat(result).contains("127.0.0.1");
    }

    @Test
    @DisplayName("Should compare nested JSON files in stylish format")
    void testNestedJsonComparison() throws Exception {
        String result = Differ.generate(file4Json, file5Json);

        // Выводим результат для отладки
        System.out.println("=== NESTED JSON RESULT ===");
        System.out.println(result);
        System.out.println("=== END ===");

        assertThat(result).isNotNull();
        assertThat(result).startsWith("{");
        assertThat(result).endsWith("}");

        // Проверяем основные ключи
        assertThat(result).contains("chars1");
        assertThat(result).contains("chars2");
        assertThat(result).contains("checked");
        assertThat(result).contains("default");
        assertThat(result).contains("id");
        assertThat(result).contains("key1");
        assertThat(result).contains("key2");
        assertThat(result).contains("numbers1");
        assertThat(result).contains("numbers2");
        assertThat(result).contains("numbers3");
        assertThat(result).contains("numbers4");
        assertThat(result).contains("obj1");
        assertThat(result).contains("setting1");
        assertThat(result).contains("setting2");
        assertThat(result).contains("setting3");

        // Проверяем наличие маркеров изменений
        assertThat(result).contains("  + ");
        assertThat(result).contains("  - ");
    }

    @Test
    @DisplayName("Should format nested JSON in plain format")
    void testNestedJsonPlainFormat() throws Exception {
        String result = Differ.generate(file4Json, file5Json, "plain");
        assertThat(result).isNotNull();
        assertThat(result).contains("Property");
        assertThat(result).contains("was updated");
        assertThat(result).contains("was added");
        assertThat(result).contains("was removed");
    }

    @Test
    @DisplayName("Should format nested JSON in JSON format")
    void testNestedJsonJsonFormat() throws Exception {
        String result = Differ.generate(file4Json, file5Json, "json");
        assertThat(result).isNotNull();
        assertThat(result).startsWith("{");
        assertThat(result).contains("status");
        assertThat(result).contains("changed");
        assertThat(result).contains("added");
    }

    @Test
    @DisplayName("Should format output in stylish format for JSON")
    void testStylishFormatJson() throws Exception {
        String result = Differ.generate(file1Json, file3Json, "stylish");
        assertThat(result).isNotNull();
        assertThat(result).startsWith("{");
        assertThat(result).endsWith("}");
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
    }

    @Test
    @DisplayName("Should compare YAML files and show differences")
    void testYamlComparison() throws Exception {
        String result = Differ.generate(file1Yaml, file2Yaml);
        assertThat(result).isNotNull();
        assertThat(result).contains("follow");
        assertThat(result).contains("host");
        assertThat(result).contains("proxy");
        assertThat(result).contains("timeout");
        assertThat(result).contains("verbose");
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
        assertThat(result).contains("Property");
    }

    @Test
    @DisplayName("Should format output in JSON format for YAML")
    void testYamlJsonFormat() throws Exception {
        String result = Differ.generate(file1Yaml, file2Yaml, "json");
        assertThat(result).isNotNull();
        assertThat(result).startsWith("{");
        assertThat(result).contains("status");
    }

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

    @Test
    @DisplayName("Should format nested JSON in plain format correctly")
    void testNestedJsonPlainFormatDetailed() throws Exception {
        String result = Differ.generate(file4Json, file5Json, "plain");

        assertThat(result).isNotNull();

        // Проверяем конкретные строки вывода
        assertThat(result).contains("Property 'chars2' was updated. From [complex value] to false");
        assertThat(result).contains("Property 'checked' was updated. From false to true");
        assertThat(result).contains("Property 'default' was updated. From null to [complex value]");
        assertThat(result).contains("Property 'id' was updated. From 45 to null");
        assertThat(result).contains("Property 'key1' was removed");
        assertThat(result).contains("Property 'key2' was added with value: 'value2'");
        assertThat(result).contains("Property 'numbers2' was updated. From [complex value] to [complex value]");
        assertThat(result).contains("Property 'numbers3' was removed");
        assertThat(result).contains("Property 'numbers4' was added with value: [complex value]");
        assertThat(result).contains("Property 'obj1' was added with value: [complex value]");
        assertThat(result).contains("Property 'setting1' was updated. From 'Some value' to 'Another value'");
        assertThat(result).contains("Property 'setting2' was updated. From 200 to 300");
        assertThat(result).contains("Property 'setting3' was updated. From true to 'none'");

        // Проверяем, что нет лишних свойств
        String[] lines = result.split("\n");
        assertThat(lines).hasSize(13); // Должно быть 13 строк с изменениями
    }
}
