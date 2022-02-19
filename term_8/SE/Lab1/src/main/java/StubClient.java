import java.util.ArrayList;
import java.util.List;

public class StubClient implements Client {

    private final long responseDelay;

    public StubClient(long responseDelay) {
        this.responseDelay = responseDelay;
    }

    @Override
    public Result searchQuery(Request request) {
        List<ResultObject> resultObjects = new ArrayList<>();
        int SEARCH_RESULT_BATCH_SIZE = 10;
        for (int i = 0; i < SEARCH_RESULT_BATCH_SIZE; i++) {
            resultObjects.add(new ResultObject(
                    generateUrl(i, request.getQueryText()),
                    generateTitle(i, request.getQueryText())
            ));
        }
        try {
            Thread.sleep(responseDelay);
            return new Result(request.getSearchEngine(), resultObjects);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return new Result(request.getSearchEngine(), resultObjects);
        }
    }

    private String generateUrl(int i, String queryText) {
        return "https://superwebsite9000/" + queryText + "/" + i;
    }

    private String generateTitle(int i, String queryText) {
        return "Ultra unique title namba " + i + " named \'" + queryText + "\' ";
    }
}