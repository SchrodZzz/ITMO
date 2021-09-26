package response;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import api.NewsFeed;

import java.util.ArrayList;
import java.util.List;

public class ResponseParser {

    public JsonObject parseResponse(String response) {
        System.out.println(response);
        return new Gson().fromJson(response, JsonObject.class).getAsJsonObject("response");
    }

    public List<NewsFeed> parseNewsFeeds(JsonObject responseJson) {
        JsonArray items = responseJson.getAsJsonArray("items");
        List<NewsFeed> newsFeeds = new ArrayList<>();
        items.forEach(jsonElement -> newsFeeds.add(parseNewsFeed(jsonElement.getAsJsonObject())));

        return newsFeeds;
    }

    public String getStartFrom(JsonObject responseJson) {
        JsonElement nextFromNode = responseJson.get("next_from");
        if (nextFromNode == null) {
            return "";
        }
        return nextFromNode.getAsString();
    }

    private NewsFeed parseNewsFeed(JsonObject item) {
        return new NewsFeed(
                item.get("id").getAsLong(),
                item.get("text").getAsString(),
                item.get("date").getAsLong());
    }

}
