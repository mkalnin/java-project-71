package hexlet.code;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class DifferTest {
    private static final Path RESOURCES = Paths.get("src/test/resources");
    private static final Path EXPECTED = RESOURCES.resolve("expected");

    private static String file1Json;
    private static String file2Json;
    private static String file3Json;
    private static String file4Json;
    private static String file5Json;
    private static String file1Yaml;
    private static String file2Yaml;

    @BeforeAll
    static void setUp() {
        file1Json = absolute("file1.json");
        file2Json = absolute("file2.json");
        file3Json = absolute("file3.json");
        file4Json = absolute("file4.json");
        file5Json = absolute("file5.json");
        file1Yaml = absolute("file1.yml");
        file2Yaml = absolute("file2.yml");
    }

    private static String absolute(String name) {
        return RESOURCES.resolve(name).toAbsolutePath().toString();
    }

    private static String readExpected(String name) throws Exception {
        return Files.readString(EXPECTED.resolve(name)).stripTrailing();
    }

    @Test
    @DisplayName("YAML input, json output")
    void testYamlToJson() throws Exception {
        String actual = Differ.generate(file1Yaml, file2Yaml, "json");
        String expected = readExpected("diff_yml_json.txt");

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("YAML input, stylish output")
    void testYamlToStylish() throws Exception {
        String actual = Differ.generate(file1Yaml, file2Yaml, "stylish");
        String expected = readExpected("diff_yml_stylish.txt");

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("YAML input, plain output")
    void testYamlToPlain() throws Exception {
        String actual = Differ.generate(file1Yaml, file2Yaml, "plain");
        String expected = readExpected("diff_yml_plain.txt");

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("YAML input, default output (stylish)")
    void testYamlToDefault() throws Exception {
        String actual = Differ.generate(file1Yaml, file2Yaml);
        String expected = readExpected("diff_yml_default.txt");

        assertThat(actual).isEqualTo(expected);
        assertThat(actual).isEqualTo(readExpected("diff_yml_stylish.txt"));
    }

    @Test
    @DisplayName("JSON input, json output")
    void testJsonToJson() throws Exception {
        String actual = Differ.generate(file4Json, file5Json, "json");
        String expected = readExpected("diff_json_json.txt");

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("JSON input, stylish output")
    void testJsonToStylish() throws Exception {
        String actual = Differ.generate(file4Json, file5Json, "stylish");
        String expected = readExpected("diff_json_stylish.txt");

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("JSON input, plain output")
    void testJsonToPlain() throws Exception {
        String actual = Differ.generate(file4Json, file5Json, "plain");
        String expected = readExpected("diff_json_plain.txt");

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("JSON input, default output (stylish)")
    void testJsonToDefault() throws Exception {
        String actual = Differ.generate(file4Json, file5Json);
        String expected = readExpected("diff_json_default.txt");

        assertThat(actual).isEqualTo(expected);
        assertThat(actual).isEqualTo(readExpected("diff_json_stylish.txt"));
    }

    @Test
    @DisplayName("Should return empty diff for identical files")
    void testIdenticalFiles() throws Exception {
        String actual = Differ.generate(file1Json, file2Json, "plain");

        assertThat(actual).isEmpty();
    }

    @Test
    @DisplayName("Should compare JSON and YAML files")
    void testMixedFormats() throws Exception {
        String actual = Differ.generate(file1Json, file2Yaml, "stylish");
        String expected = """
                {
                  - debug: true
                  - host: localhost
                  + host: hexlet.io
                  - port: 8080
                  - timeout: 5000
                  + timeout: 20
                  + verbose: true
                }""";

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Should detect input format when directory name contains a dot")
    void testFormatDetectionWithDotsInPath() throws Exception {
        Path dir = Files.createTempDirectory("gendiff.v1");
        try {
            Path first = Files.copy(RESOURCES.resolve("file1.yml"), dir.resolve("first.yml"));
            Path second = Files.copy(RESOURCES.resolve("file2.yml"), dir.resolve("second.yml"));

            String actual = Differ.generate(first.toString(), second.toString(), "plain");

            assertThat(actual).isEqualTo(readExpected("diff_yml_plain.txt"));
        } finally {
            Files.deleteIfExists(dir.resolve("first.yml"));
            Files.deleteIfExists(dir.resolve("second.yml"));
            Files.deleteIfExists(dir);
        }
    }

    @Test
    @DisplayName("Should throw exception for unknown output format")
    void testInvalidFormat() {
        assertThrows(Exception.class, () -> Differ.generate(file1Json, file3Json, "invalid"));
    }

    @Test
    @DisplayName("Should throw exception for unsupported input format")
    void testInvalidInputFormat() {
        assertThrows(Exception.class, () -> Differ.generate("test.txt", file1Json));
    }
}
