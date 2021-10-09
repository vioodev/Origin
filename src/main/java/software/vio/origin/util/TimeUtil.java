package software.vio.origin.util;

public final class TimeUtil {

    /**
     * Get a pretty-printed time from milliseconds
     *
     * @param millis Time in milliseconds
     * @return Pretty-printed time, such as '3 hours'
     */

    public static String getPrettyTime(long millis) {
        if (millis == Long.MAX_VALUE) return "Permanent";

        long seconds = millis / 1000L;
        long minutes = seconds / 60L;
        long hours = minutes / 60L;
        long days = hours / 24L;
        long weeks = days / 7L;
        long months = weeks / 4L;
        long years = months / 12L;

        if (years > 0) return years + " year" + (years > 1 ? "s" : "");
        if (months > 0) return months + " month" + (months > 1 ? "s" : "");
        if (weeks > 0) return weeks + " week" + (weeks > 1 ? "s" : "");
        if (days > 0) return days + " day" + (days > 1 ? "s" : "");
        if (hours > 0) return hours + " hour" + (hours > 1 ? "s" : "");
        if (minutes > 0) return minutes + " minute" + (minutes > 1 ? "s" : "");
        if (seconds > 0) return seconds + " second" + (seconds > 1 ? "s" : "");

        return "Less than a second";
    }

    /**
     * Get a pretty-printed time difference
     *
     * @param firstMillis First unix time
     * @param otherMillis Second unix time
     * @return Pretty printed time
     */

    public static String getPrettyTimeDiff(long firstMillis, long otherMillis) {
        return (firstMillis < otherMillis ? getPrettyTime(otherMillis - firstMillis) : getPrettyTime(firstMillis - otherMillis));
    }
}
