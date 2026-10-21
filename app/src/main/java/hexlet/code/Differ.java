package hexlet.code;

import hexlet.code.formatters.FormatterFactory;
import hexlet.code.parsers.ParserFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public final class Differ {

    private Differ() {
    }

    public static String generate(String filepath1, String filepath2) throws Exception {
        return generate(filepath1, filepath2, "stylish");
    }

    public static String generate(String filepath1, String filepath2, String outputFormat) throws Exception {
        Map<String, Object> map1 = readAndParse(filepath1);
        Map<String, Object> map2 = readAndParse(filepath2);

        Map<String, DiffEntry> diff = DiffBuilder.buildDiff(map1, map2);

        return FormatterFactory.getFormatter(outputFormat).format(diff);
    }

    private static Map<String, Object> readAndParse(String filepath) throws Exception {
        String inputFormat = getInputFormat(filepath);
        var parser = ParserFactory.getParser(inputFormat);

        String content = Files.readString(Paths.get(filepath));
        return parser.parse(content);
    }

    private static String getInputFormat(String filepath) {
        String name = Path.of(filepath).getFileName().toString();
        int lastDotIndex = name.lastIndexOf('.');
        if (lastDotIndex > 0) {
            return name.substring(lastDotIndex + 1);
        }
        return "";
    }
}
