package com.nowcoder.community.util;

/**
 * @author zhou
 * @create 2021-3-29 22:04
 */


public class RedisKeyUtil {

    // 分隔符
    private static final String SPLIT = ":";

    // 实体类前缀
    private static String PREFIX_ENTITY_LIKE = "like:entity";

    // 某个实体的赞
    public static String getEntityLikeKey(int entityType, int entityId) {

        return PREFIX_ENTITY_LIKE + SPLIT + entityType + SPLIT + entityId;
    }
}
