package hexlet.code.formatters;

import java.util.HashMap;
import java.util.Map;

public final class FormatterFactory {
    private static final Map<String, Formatter> FORMATTERS = new HashMap<>();

    static {
        FORMATTERS.put("stylish", new StylishFormatter());
        FORMATTERS.put("plain", new PlainFormatter());
        FORMATTERS.put("json", new JsonFormatter());
    }

    private FormatterFactory() {
    }

    public static Formatter getFormatter(String format) throws Exception {
        Formatter formatter = FORMATTERS.get(format.toLowerCase());
        if (formatter == null) {
            throw new Exception(
                    "Unsupported format: '" + format
                            + "'. Supported formats: stylish, plain, json"
            );
        }
        return formatter;
    }
}
