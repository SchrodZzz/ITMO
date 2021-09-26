package response;

import api.NewsFeed;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;
import utils.TestWithResources;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ResponseParserTest extends TestWithResources {
    private final ResponseParser parser = new ResponseParser();

    @Test
    public void testExample() throws Exception {
        List<NewsFeed> parsed = parseNewsFeedsFromFile("regular");

        assertEquals(parsed.size(), 2);
        assertTrue(parsed.contains(new NewsFeed(
                2916,
                "VIDEO | #Juve, #Dybala scatena i tifosi: esultanza polemica verso la tribuna Le ipotesi\nhttps://www.90min.com/it/posts/6481957-video-juve-dybala-scatena-i-tifosi-esultanza-polemica-verso-la-tribuna-le-ipotesi/partners/43892  #calcio #football  #JuveLokomotiv #ChampionsLeague",
                1571833353
        )));
        assertTrue(parsed.contains(new NewsFeed(
                40009,
                "#Dybala@fbomen \n#Juventus",
                1571832010
        )));
    }

    @Test
    public void testEmpty() throws Exception {
        List<NewsFeed> parsed = parseNewsFeedsFromFile("empty");
        assertTrue(parsed.isEmpty());
    }

    @Test
    public void testEmptyNextFrom() throws Exception {
        assertTrue(parseNextFrom("regular").isBlank());
    }

    @Test
    public void testNextFrom() throws Exception {
        assertEquals(parseNextFrom("next_from"), "30/384063108_1701");
    }

    private JsonObject parseResponseFromFile(String fileName) throws Exception {
        String fileExtension = ".json";
        String response = readFromResources(fileName, fileExtension);
        return parser.parseResponse(response);
    }

    private String parseNextFrom(String fileName) throws Exception {
        JsonObject responseJson = parseResponseFromFile(fileName);
        return parser.getStartFrom(responseJson);
    }

    private List<NewsFeed> parseNewsFeedsFromFile(String fileName) throws Exception {
        JsonObject responseJson = parseResponseFromFile(fileName);
        return parser.parseNewsFeeds(responseJson);
    }

}
