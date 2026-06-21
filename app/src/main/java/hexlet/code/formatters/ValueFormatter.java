package hexlet.code.formatters;

import java.util.List;
import java.util.Map;

public final class ValueFormatter {

    private ValueFormatter() {
    }

    public static String formatStylishValue(Object value) {
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

    public static String formatPlainValue(Object value) {
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

    private static String formatList(List<?> list) {
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
}
