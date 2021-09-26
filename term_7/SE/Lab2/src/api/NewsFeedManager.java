package api;

import utils.TimeUtils;

import java.util.List;

public class NewsFeedManager {
    private final Client client;

    public NewsFeedManager(Client client) {
        this.client = client;
    }

    public int[] getHashTagOccurrence(String hashTagText, int timeInterval) {
        if (timeInterval < 0 || timeInterval > 24) {
            throw new IllegalArgumentException("time interval should be from 0 to 24");
        }

        long currentTime = TimeUtils.getCurrentTimeInSeconds();
        List<NewsFeed> newsFeeds = client.getNewsFeeds(hashTagText, timeInterval);

        int[] result = new int[timeInterval];
        for (NewsFeed newsFeed : newsFeeds) {
            int index = (int) ((currentTime - newsFeed.getDate().getTime()) / TimeUtils.HOUR);
            result[index]++;
        }
        return result;
    }

}