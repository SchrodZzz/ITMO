package api;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import utils.TimeUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class NewsFeedManagerTest {
    @Mock
    private Client client;

    private NewsFeedManager manager;

    @BeforeAll
    public void prepare() {
        MockitoAnnotations.initMocks(this);
        manager = new NewsFeedManager(client);
    }

    @Test
    public void testNegativeTimeInterval() {
        assertThrows(IllegalArgumentException.class, () -> manager.getHashTagOccurrence("messi", -1));
    }

    @Test
    public void testBigTimeInterval() {
        assertThrows(IllegalArgumentException.class, () -> manager.getHashTagOccurrence("messi", 25));
    }

    @Test
    public void testEmpty() {
        when(client.getNewsFeeds("messi", 0))
                .thenReturn(Collections.emptyList());

        int[] occurrence = manager.getHashTagOccurrence("messi", 0);
        assertEquals(0, occurrence.length);
    }

    @Test
    public void testNoPosts() {
        when(client.getNewsFeeds("messi", 24))
                .thenReturn(Collections.emptyList());

        int[] occurrence = manager.getHashTagOccurrence("messi", 24);
        int[] expected = new int[24];
        Arrays.fill(expected, 0);

        assertArrayEquals(expected, occurrence);
    }

    @Test
    public void testWithPosts() {
        List<NewsFeed> newsFeeds = Arrays.asList(
                new NewsFeed(
                        0,
                        "some content",
                        TimeUtils.getCurrentTimeInSeconds() - 10
                ),
                new NewsFeed(
                        1,
                        "another content",
                        TimeUtils.getPastTimeInSeconds(1) - 10
                )
        );

        when(client.getNewsFeeds("messi", 2))
                .thenReturn(newsFeeds);

        int[] occurrence = manager.getHashTagOccurrence("messi", 2);
        int[] expected = new int[2];
        Arrays.fill(expected, 1);

        assertArrayEquals(expected, occurrence);
    }

}
