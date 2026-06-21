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

    public static Parser getParser(String extension) throws Exception {
        Parser parser = PARSERS.get(extension.toLowerCase());
        if (parser == null) {
            throw new Exception(
                    "Unsupported file format: '" + extension
                            + "'. Supported formats: json, yml, yaml"
            );
        }
        return parser;
    }
}
