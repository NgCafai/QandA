package com.ngcafai.QandA.Util;

/**
 * Generate a specific key for certain data
 */
public class RedisKeyUtil {
    private static final String SPILT_SYMBOL = ":";

    public static String generateLikeKey(int entityType, int entityId) {
        return "LIKE" + SPILT_SYMBOL + entityType + SPILT_SYMBOL + entityId;
    }

    public static String generateDislikeKey(int entityType, int entityId) {
        return "DISLIKE" + SPILT_SYMBOL + entityType + SPILT_SYMBOL + entityId;
    }
}
