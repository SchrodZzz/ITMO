import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Result {
    private final Map<Engine, List<ResultObject>> searchEngineResults;

    public Result() {
        searchEngineResults = new HashMap<>();
    }

    public Result(Engine engine, List<ResultObject> resultObjects) {
        this();
        searchEngineResults.put(engine, resultObjects);
    }

    public void mergeSearchResults(Result result) {
        searchEngineResults.putAll(result.getSearchEngineResults());
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[\n");
        for (Engine engine : searchEngineResults.keySet()) {
            StringBuilder items = new StringBuilder();
            for (ResultObject item : searchEngineResults.get(engine)) {
                items.append("    ").append(item.toString()).append("\n");
            }
            result.append("{\n")
                    .append("  provided-by : \"").append(engine).append("\",\n")
                    .append("  objects : [\n")
                    .append(items.toString())
                    .append("  ]\n")
                    .append("}\n");
        }
        return result.append("]").toString();
    }

    public Map<Engine, List<ResultObject>> getSearchEngineResults() {
        return searchEngineResults;
    }
}