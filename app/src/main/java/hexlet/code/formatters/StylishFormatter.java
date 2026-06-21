package hexlet.code.formatters;

import hexlet.code.DiffEntry;
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
                        .append(ValueFormatter.formatStylishValue(diffEntry.getNewValue()))
                        .append("\n");
                break;
            case REMOVED:
                result.append(REMOVED_PREFIX).append(key).append(": ")
                        .append(ValueFormatter.formatStylishValue(diffEntry.getOldValue()))
                        .append("\n");
                break;
            case CHANGED:
                result.append(REMOVED_PREFIX).append(key).append(": ")
                        .append(ValueFormatter.formatStylishValue(diffEntry.getOldValue()))
                        .append("\n");
                result.append(ADDED_PREFIX).append(key).append(": ")
                        .append(ValueFormatter.formatStylishValue(diffEntry.getNewValue()))
                        .append("\n");
                break;
            case UNCHANGED:
                result.append(UNCHANGED_PREFIX).append(key).append(": ")
                        .append(ValueFormatter.formatStylishValue(diffEntry.getOldValue()))
                        .append("\n");
                break;
            default:
                break;
        }
    }

    @Override
    protected String getFormatName() {
        return "stylish";
    }
}
