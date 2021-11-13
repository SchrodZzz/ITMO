package api;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import response.ResponseParser;
import request.URLReader;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ClientTest {
    @Mock
    private URLReader urlReader;

    @Mock
    private ResponseParser responseParser;

    private Client client;

    @BeforeAll
    public void prepare() {
        MockitoAnnotations.initMocks(this);

        client = new Client(urlReader, responseParser);
    }

    private void mocks(String url, String response, JsonObject responseJson, List<NewsFeed> newsFeeds, String startFrom) {
        when(urlReader.readAsText(url))
                .thenReturn(response);
        when(responseParser.parseResponse(response))
                .thenReturn(responseJson);
        when(responseParser.parseNewsFeeds(responseJson))
                .thenReturn(newsFeeds);
        when(responseParser.getStartFrom(responseJson))
                .thenReturn(startFrom);
    }

    private void mocks(List<NewsFeed> newsFeeds) {
        mocks(anyString(), "response", new JsonObject(), newsFeeds, "");
    }

    private void mocks(String url, List<NewsFeed> newsFeeds, String startFrom, int mockNumber) {
        JsonObject responseJson = new JsonObject();
        responseJson.addProperty("id", mockNumber);
        mocks(url, Integer.toString(mockNumber), responseJson, newsFeeds, startFrom);
    }

    @Test
    public void testZeroTimeInterval() {
        mocks(Collections.emptyList());

        List<NewsFeed> newsFeeds = client.getNewsFeeds("hashTag", 0);
        assertTrue(newsFeeds.isEmpty());
    }

    @Test
    public void testOneJson() {
        List<NewsFeed> expectedNewsFeeds = Arrays.asList(
                new NewsFeed(
                        1708,
                        "Luar biasa king leo, kami salut, bangga dan bahagia, sukses selalu , from indonesia",
                        1572449412
                ),
                new NewsFeed(
                        1706,
                        "#Repost @foxsportsbrasil with [club87821973|@make_repost]",
                        1572449409
                )
        );

        mocks(expectedNewsFeeds);

        List<NewsFeed> newsFeeds = client.getNewsFeeds("messi", 24);
        assertEquals(newsFeeds, expectedNewsFeeds);
    }

    @Test
    public void testTwoJsons() {
        NewsFeed firstNewsFeed = new NewsFeed(
                1708,
                "Luar biasa king leo, kami salut, bangga dan bahagia, sukses selalu , from indonesia",
                1572449412
        );
        NewsFeed secondNewsFeed = new NewsFeed(
                1706,
                "#Repost @foxsportsbrasil with [club87821973|@make_repost]",
                1572449409
        );

        List<NewsFeed> expectedNewsFeeds = Arrays.asList(firstNewsFeed, secondNewsFeed);

        mocks(
                not(contains("&start_from=")),
                Collections.singletonList(firstNewsFeed),
                "30/-184783437_1583",
                1
        );
        mocks(
                contains("&start_from=30/-184783437_1583"),
                Collections.singletonList(secondNewsFeed),
                "",
                2
        );

        List<NewsFeed> newsFeeds = client.getNewsFeeds("messi", 24);
        assertEquals(newsFeeds, expectedNewsFeeds);
    }

}
