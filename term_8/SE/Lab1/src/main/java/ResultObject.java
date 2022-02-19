public class ResultObject {
    private final String url;
    private final String title;

    public ResultObject(String url, String title) {
        this.url = url;
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "{\n\t\tURL : \"" + url + "\",\n\t\ttitle : \"" + title + "\"\n\t}";
    }
}