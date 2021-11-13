package request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class URLReaderTest {
    private static final String ACCESS_TOKEN = System.getenv("vk-api-service-token");

    private final URLReader reader = new URLReader();

    @Test
    public void testReader() {
        String url = "https://api.vk.com/method/newsfeed.search?q=%23messi&start_time=1572184510&access_token=" + ACCESS_TOKEN + "&v=5.131";
        String response = reader.readAsText(url);
        assertFalse(response.isBlank());
    }
}
