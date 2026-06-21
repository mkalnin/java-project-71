package hexlet.code.formatters;

import hexlet.code.DiffEntry;
import java.util.List;
import java.util.Map;

public class PlainFormatter implements Formatter {

    @Override
    public String format(Map<String, DiffEntry> diff) throws Exception {
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

    private String formatPlainValue(Object value) {
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

    @Override
    public boolean supports(String format) {
        return "plain".equalsIgnoreCase(format);
    }
}
