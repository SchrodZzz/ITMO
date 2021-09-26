package utils;

import java.util.Date;

public class TimeUtils {
    public static long HOUR = 3600;
    private static long MILLISECONDS = 1000;

    public static long getCurrentTimeInSeconds() {
        return getCurrentTime() / MILLISECONDS;
    }

    public static long getPastTimeInSeconds(long hoursBefore) {
        return getPastTime(hoursBefore) / MILLISECONDS;
    }

    public static long getCurrentTime() {
        return new Date().getTime();
    }

    public static long getPastTime(long hoursBefore) {
        return getCurrentTime() - hoursBefore * HOUR * MILLISECONDS;
    }
}
