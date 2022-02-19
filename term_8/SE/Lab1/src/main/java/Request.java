public class Request {
    private final Engine engine;
    private final String queryText;

    public Request(Engine engine, String queryText) {
        this.engine = engine;
        this.queryText = queryText;
    }

    public Engine getSearchEngine() {
        return engine;
    }

    public String getQueryText() {
        return queryText;
    }
}