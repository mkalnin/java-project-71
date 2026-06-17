package hexlet.code;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Paths;

public class DifferTest {
    private static String file1Json;
    private static String file2Json;
    private static String file3Json;

    @BeforeAll
    static void setUp() throws Exception {
        file1Json = Paths.get("src/test/resources/file1.json").toAbsolutePath().toString();
        file2Json = Paths.get("src/test/resources/file2.json").toAbsolutePath().toString();
        file3Json = Paths.get("src/test/resources/file3.json").toAbsolutePath().toString();
    }

    @Test
    void testCompareIdenticalFiles() throws Exception {
        String result = Differ.generate(file1Json, file2Json);
        assertNotNull(result);
        assertTrue(result.contains("host"));
        assertTrue(result.contains("timeout"));
    }

    @Test
    void testCompareDifferentFiles() throws Exception {
        String result = Differ.generate(file1Json, file3Json);
        assertNotNull(result);
        assertTrue(result.contains("-") || result.contains("+"));
    }

    @Test
    void testStylishFormat() throws Exception {
        String result = Differ.generate(file1Json, file3Json, "stylish");
        assertNotNull(result);
        assertTrue(result.startsWith("{"));
        assertTrue(result.endsWith("}"));
    }

    @Test
    void testPlainFormat() throws Exception {
        String result = Differ.generate(file1Json, file3Json, "plain");
        assertNotNull(result);
        assertTrue(result.contains("was added") || result.contains("was removed"));
    }

    @Test
    void testJsonFormat() throws Exception {
        String result = Differ.generate(file1Json, file3Json, "json");
        assertNotNull(result);
        assertTrue(result.startsWith("{"));
        assertTrue(result.contains("status"));
    }

    @Test
    void testInvalidFormat() {
        assertThrows(Exception.class, () -> {
            Differ.generate(file1Json, file3Json, "invalid");
        });
    }
}
