package hexlet.code;

import hexlet.code.parsers.ParserFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.HashSet;
import java.util.List;

public final class Differ {

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    private Differ() {
    }

    public static String generate(String filepath1, String filepath2) throws Exception {
        return generate(filepath1, filepath2, "stylish");
    }

    public static String generate(String filepath1, String filepath2, String format) throws Exception {
        Map<String, Object> map1 = readAndParse(filepath1);
        Map<String, Object> map2 = readAndParse(filepath2);

        Map<String, DiffEntry> diff = buildDiff(map1, map2);

        return formatOutput(diff, format);
    }

    private static Map<String, Object> readAndParse(String filepath) throws Exception {
        String content = Files.readString(Paths.get(filepath));
        String extension = getFileExtension(filepath);

        var parser = ParserFactory.getParser(extension);
        return parser.parse(content);
    }

    private static String getFileExtension(String filepath) {
        int lastDotIndex = filepath.lastIndexOf('.');
        if (lastDotIndex > 0) {
            return filepath.substring(lastDotIndex + 1);
        }
        return "";
    }

    private static Map<String, DiffEntry> buildDiff(Map<String, Object> map1,
                                                    Map<String, Object> map2) {
        Map<String, DiffEntry> diff = new TreeMap<>();
        Set<String> allKeys = new HashSet<>();
        allKeys.addAll(map1.keySet());
        allKeys.addAll(map2.keySet());

        for (String key : allKeys) {
            if (!map1.containsKey(key)) {
                diff.put(key, new DiffEntry(DiffStatus.ADDED, null, map2.get(key)));
            } else if (!map2.containsKey(key)) {
                diff.put(key, new DiffEntry(DiffStatus.REMOVED, map1.get(key), null));
            } else if (map1.get(key) == null && map2.get(key) == null) {
                diff.put(key, new DiffEntry(DiffStatus.UNCHANGED, null, null));
            } else if (map1.get(key) == null || map2.get(key) == null) {
                diff.put(key, new DiffEntry(DiffStatus.CHANGED, map1.get(key), map2.get(key)));
            } else if (map1.get(key).equals(map2.get(key))) {
                diff.put(key, new DiffEntry(DiffStatus.UNCHANGED, map1.get(key), map2.get(key)));
            } else {
                diff.put(key, new DiffEntry(DiffStatus.CHANGED, map1.get(key), map2.get(key)));
            }
        }

        return diff;
    }

    private static String formatOutput(Map<String, DiffEntry> diff, String format) throws Exception {
        switch (format.toLowerCase()) {
            case "stylish":
                return formatStylish(diff);
            case "plain":
                return formatPlain(diff);
            case "json":
                return formatJson(diff);
            default:
                throw new Exception("Unsupported format: " + format
                        + ". Supported formats: stylish, plain, json");
        }
    }

    private static String formatStylish(Map<String, DiffEntry> diff) {
        StringBuilder result = new StringBuilder("{\n");

        for (Map.Entry<String, DiffEntry> entry : diff.entrySet()) {
            String key = entry.getKey();
            DiffEntry diffEntry = entry.getValue();

            switch (diffEntry.getStatus()) {
                case ADDED:
                    result.append("  + ").append(key).append(": ")
                            .append(formatValue(diffEntry.getNewValue()))
                            .append("\n");
                    break;
                case REMOVED:
                    result.append("  - ").append(key).append(": ")
                            .append(formatValue(diffEntry.getOldValue()))
                            .append("\n");
                    break;
                case CHANGED:
                    result.append("  - ").append(key).append(": ")
                            .append(formatValue(diffEntry.getOldValue()))
                            .append("\n");
                    result.append("  + ").append(key).append(": ")
                            .append(formatValue(diffEntry.getNewValue()))
                            .append("\n");
                    break;
                case UNCHANGED:
                    result.append("    ").append(key).append(": ")
                            .append(formatValue(diffEntry.getOldValue()))
                            .append("\n");
                    break;
                default:
                    break;
            }
        }

        result.append("}");
        return result.toString();
    }

    private static String formatValue(Object value) {
        if (value == null) {
            return "null";
        }
        if (value instanceof String) {
            return value.toString();
        }
        if (value instanceof Boolean) {
            return value.toString();
        }
        if (value instanceof Number) {
            return value.toString();
        }
        if (value instanceof List) {
            return formatList((List<?>) value);
        }
        if (value instanceof Map) {
            return formatMap((Map<?, ?>) value);
        }
        return value.toString();
    }

    private static String formatList(List<?> list) {
        StringBuilder result = new StringBuilder("[");
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) {
                result.append(", ");
            }
            result.append(list.get(i));
        }
        result.append("]");
        return result.toString();
    }

    private static String formatMap(Map<?, ?> map) {
        StringBuilder result = new StringBuilder("{");
        int count = 0;
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            if (count > 0) {
                result.append(", ");
            }
            result.append(entry.getKey()).append("=").append(entry.getValue());
            count++;
        }
        result.append("}");
        return result.toString();
    }

    private static String formatPlain(Map<String, DiffEntry> diff) {
        StringBuilder result = new StringBuilder();

        for (Map.Entry<String, DiffEntry> entry : diff.entrySet()) {
            String key = entry.getKey();
            DiffEntry diffEntry = entry.getValue();

            switch (diffEntry.getStatus()) {
                case ADDED:
                    result.append("Property '").append(key)
                            .append("' was added with value: ")
                            .append(formatPlainValue(diffEntry.getNewValue()))
                            .append("\n");
                    break;
                case REMOVED:
                    result.append("Property '").append(key)
                            .append("' was removed\n");
                    break;
                case CHANGED:
                    result.append("Property '").append(key)
                            .append("' was updated. From ")
                            .append(formatPlainValue(diffEntry.getOldValue()))
                            .append(" to ")
                            .append(formatPlainValue(diffEntry.getNewValue()))
                            .append("\n");
                    break;
                default:
                    break;
            }
        }

        return result.toString().trim();
    }

    private static String formatPlainValue(Object value) {
        if (value == null) {
            return "null";
        }
        if (value instanceof String) {
            return "'" + value + "'";
        }
        if (value instanceof Map || value instanceof List) {
            return "[complex value]";
        }
        return value.toString();
    }

    private static String formatJson(Map<String, DiffEntry> diff) throws Exception {
        Map<String, Object> jsonOutput = new LinkedHashMap<>();

        for (Map.Entry<String, DiffEntry> entry : diff.entrySet()) {
            String key = entry.getKey();
            DiffEntry diffEntry = entry.getValue();

            Map<String, Object> diffData = new LinkedHashMap<>();
            diffData.put("status", diffEntry.getStatus().toString().toLowerCase());
            if (diffEntry.getOldValue() != null) {
                diffData.put("oldValue", diffEntry.getOldValue());
            }
            if (diffEntry.getNewValue() != null) {
                diffData.put("newValue", diffEntry.getNewValue());
            }

            jsonOutput.put(key, diffData);
        }

        return JSON_MAPPER.writerWithDefaultPrettyPrinter()
                .writeValueAsString(jsonOutput);
    }
}
