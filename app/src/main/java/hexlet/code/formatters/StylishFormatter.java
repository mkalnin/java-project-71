package hexlet.code.formatters;

import hexlet.code.DiffEntry;
import java.util.List;
import java.util.Map;

public class StylishFormatter extends AbstractFormatter {

    private static final String ADDED_PREFIX = "  + ";
    private static final String REMOVED_PREFIX = "  - ";
    private static final String UNCHANGED_PREFIX = "    ";

    @Override
    public String format(Map<String, DiffEntry> diff) throws Exception {
        StringBuilder result = new StringBuilder("{\n");

        for (Map.Entry<String, DiffEntry> entry : diff.entrySet()) {
            String key = entry.getKey();
            DiffEntry diffEntry = entry.getValue();
            appendDiffEntry(result, key, diffEntry);
        }

        result.append("}");
        return result.toString();
    }

    private void appendDiffEntry(StringBuilder result, String key, DiffEntry diffEntry) {
        switch (diffEntry.getStatus()) {
            case ADDED:
                result.append(ADDED_PREFIX).append(key).append(": ")
                        .append(formatValue(diffEntry.getNewValue()))
                        .append("\n");
                break;
            case REMOVED:
                result.append(REMOVED_PREFIX).append(key).append(": ")
                        .append(formatValue(diffEntry.getOldValue()))
                        .append("\n");
                break;
            case CHANGED:
                result.append(REMOVED_PREFIX).append(key).append(": ")
                        .append(formatValue(diffEntry.getOldValue()))
                        .append("\n");
                result.append(ADDED_PREFIX).append(key).append(": ")
                        .append(formatValue(diffEntry.getNewValue()))
                        .append("\n");
                break;
            case UNCHANGED:
                result.append(UNCHANGED_PREFIX).append(key).append(": ")
                        .append(formatValue(diffEntry.getOldValue()))
                        .append("\n");
                break;
            default:
                throw new RuntimeException("Unknown status: " + diffEntry.getStatus());
        }
    }

    private String formatValue(Object value) {
        if (value == null) {
            return "null";
        }
        if (value instanceof List) {
            return formatList((List<?>) value);
        }
        if (value instanceof Map) {
            return formatMap((Map<?, ?>) value);
        }
        return value.toString();
    }

    private String formatList(List<?> list) {
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

    private String formatMap(Map<?, ?> map) {
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

    @Override
    protected String getFormatName() {
        return "stylish";
    }
}
