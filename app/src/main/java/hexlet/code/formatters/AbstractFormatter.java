package hexlet.code.formatters;

import hexlet.code.DiffEntry;
import java.util.Map;

public abstract class AbstractFormatter implements Formatter {

    @Override
    public abstract String format(Map<String, DiffEntry> diff) throws Exception;

    @Override
    public boolean supports(String format) {
        return getFormatName().equalsIgnoreCase(format);
    }

    protected abstract String getFormatName();
}
