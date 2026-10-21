package hexlet.code.parsers;

import java.util.HashMap;
import java.util.Map;

public final class ParserFactory {
    private static final Map<String, Parser> PARSERS = new HashMap<>();

    static {
        PARSERS.put("json", new JsonParser());
        PARSERS.put("yml", new YamlParser());
        PARSERS.put("yaml", new YamlParser());
    }

    private ParserFactory() {
    }

    public static Parser getParser(String format) throws Exception {
        if (format == null || format.isBlank()) {
            throw new Exception(
                    "Cannot determine input format. Supported formats: json, yml, yaml"
            );
        }
        Parser parser = PARSERS.get(format.toLowerCase());
        if (parser == null) {
            throw new Exception(
                    "Unsupported format: '" + format
                            + "'. Supported formats: json, yml, yaml"
            );
        }
        return parser;
    }
}
