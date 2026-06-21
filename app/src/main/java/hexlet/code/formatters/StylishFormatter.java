package hexlet.code.formatters;

import hexlet.code.DiffEntry;
import java.util.List;
import java.util.Map;

public class StylishFormatter implements Formatter {

    private static final String ADDED_PREFIX = "  + ";
    private static final String REMOVED_PREFIX = "  - ";
    private static final String UNCHANGED_PREFIX = "    ";

    @Override
    public String format(Map<String, DiffEntry> diff) throws Exception {
        StringBuilder result = new StringBuilder("{\n");

        for (Map.Entry<String, DiffEntry> entry : diff.entrySet()) {
            String key = entry.getKey();
            DiffEntry diffEntry = entry.getValue();

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
                    break;
            }
        }

        result.append("}");
        return result.toString();
    }

    private String formatValue(Object value) {
        if (value == null) {
            return "null";
        }
        if (value instanceof String) {
            return value.toString();
        }
        if (value instanceof Boolean || value instanceof Number) {
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

    private String formatList(List<?> list) {
        StringBuilder result = new StringBuilder("[");
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) {
                result.append(", ");
            }
            Object item = list.get(i);
            if (item instanceof String) {
                result.append(item.toString());
            } else {
                result.append(item);
            }
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
    public boolean supports(String format) {
        return "stylish".equalsIgnoreCase(format);
    }
}
