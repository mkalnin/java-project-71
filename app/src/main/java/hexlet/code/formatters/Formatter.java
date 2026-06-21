package hexlet.code.formatters;

import java.util.Map;
import hexlet.code.DiffEntry;

public interface Formatter {
    String format(Map<String, DiffEntry> diff) throws Exception;
    boolean supports(String format);
}
