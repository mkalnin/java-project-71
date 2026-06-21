package hexlet.code.parsers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;

public class JsonParser implements Parser {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Map<String, Object> parse(String content) throws Exception {
        return mapper.readValue(content, new TypeReference<Map<String, Object>>() { });
    }

    @Override
    public boolean supports(String extension) {
        return "json".equalsIgnoreCase(extension);
    }
}
