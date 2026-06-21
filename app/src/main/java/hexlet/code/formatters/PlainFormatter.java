package hexlet.code.formatters;

import hexlet.code.DiffEntry;
import java.util.Map;

public class PlainFormatter extends AbstractFormatter {

    @Override
    public String format(Map<String, DiffEntry> diff) throws Exception {
        StringBuilder result = new StringBuilder();

        for (Map.Entry<String, DiffEntry> entry : diff.entrySet()) {
            String key = entry.getKey();
            DiffEntry diffEntry = entry.getValue();
            appendDiffEntry(result, key, diffEntry);
        }

        return result.toString().trim();
    }

    private void appendDiffEntry(StringBuilder result, String key, DiffEntry diffEntry) {
        switch (diffEntry.getStatus()) {
            case ADDED:
                result.append("Property '").append(key)
                        .append("' was added with value: ")
                        .append(ValueFormatter.formatPlainValue(diffEntry.getNewValue()))
                        .append("\n");
                break;
            case REMOVED:
                result.append("Property '").append(key)
                        .append("' was removed\n");
                break;
            case CHANGED:
                result.append("Property '").append(key)
                        .append("' was updated. From ")
                        .append(ValueFormatter.formatPlainValue(diffEntry.getOldValue()))
                        .append(" to ")
                        .append(ValueFormatter.formatPlainValue(diffEntry.getNewValue()))
                        .append("\n");
                break;
            default:
                break;
        }
    }

    @Override
    protected String getFormatName() {
        return "plain";
    }
}
