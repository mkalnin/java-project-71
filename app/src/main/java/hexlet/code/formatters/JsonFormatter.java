package hexlet.code.formatters;

import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.DiffEntry;
import java.util.LinkedHashMap;
import java.util.Map;

public class JsonFormatter extends AbstractFormatter {

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    @Override
    public String format(Map<String, DiffEntry> diff) throws Exception {
        Map<String, Object> jsonOutput = new LinkedHashMap<>();

        for (Map.Entry<String, DiffEntry> entry : diff.entrySet()) {
            String key = entry.getKey();
            DiffEntry diffEntry = entry.getValue();

            Map<String, Object> diffData = new LinkedHashMap<>();
            diffData.put("status", diffEntry.getStatus().toString().toLowerCase());
            if (diffEntry.getOldValue() != null) {
                diffData.put("oldValue", diffEntry.getOldValue());
            }
            if (diffEntry.getNewValue() != null) {
                diffData.put("newValue", diffEntry.getNewValue());
            }

            jsonOutput.put(key, diffData);
        }

        return JSON_MAPPER.writerWithDefaultPrettyPrinter()
                .writeValueAsString(jsonOutput);
    }

    @Override
    protected String getFormatName() {
        return "json";
    }
}
