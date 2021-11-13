package api;

import com.google.gson.JsonObject;
import request.URLBuilder;
import request.URLReader;
import response.ResponseParser;
import utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

public class Client {
    private final URLReader urlReader;
    private final ResponseParser responseParser;

    public Client(URLReader urlReader, ResponseParser responseParser) {
        this.urlReader = urlReader;
        this.responseParser = responseParser;
    }

    public List<NewsFeed> getNewsFeeds(String hashTagText, long timeInterval) {
        List<NewsFeed> newsFeeds = new ArrayList<>();
        URLBuilder urlBuilder = new URLBuilder()
                .setHashTagText(hashTagText)
                .setStartTime(TimeUtils.getPastTimeInSeconds(timeInterval))
                .setEndTime(TimeUtils.getCurrentTimeInSeconds());

        while (true) {
            String response = urlReader.readAsText(urlBuilder.build());
            JsonObject responseJson = responseParser.parseResponse(response);
            newsFeeds.addAll(responseParser.parseNewsFeeds(responseJson));

            String startFrom = responseParser.getStartFrom(responseJson);
            if (startFrom.isEmpty())
                break;
            urlBuilder.setStartFrom(startFrom);
        }
        return newsFeeds;
    }

}
