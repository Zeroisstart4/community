package com.nowcoder.community.util;

/**
 * @author zhou
 * @create 2021-3-29 22:04
 */


public class RedisKeyUtil {

    // 分隔符
    private static final String SPLIT = ":";
    // 实体类赞前缀
    private static String PREFIX_ENTITY_LIKE = "like:entity";
    //用户赞前缀
    private static final String PREFIX_USER_LIKE = "like:user";
    // 我关注的人
    private static final String PREFIX_FOLLOWEE = "followee";
    // 关注我的人
    private static final String PREFIX_FOLLOWER = "follower";

    // 某个实体的赞
    // like:entity:entityType:entityId -> set(userId)
    public static String getEntityLikeKey(int entityType, int entityId) {

        return PREFIX_ENTITY_LIKE + SPLIT + entityType + SPLIT + entityId;
    }

    // 某个用户的赞
    // like:user:userId -> int
    public static String getUserLikeKey(int userId) {

        return PREFIX_USER_LIKE + SPLIT + userId;
    }

    // 某个用户关注的实体
    // followee:userId:entityType -> zset(entityId,now)
    public static String getFolloweeKey(int userId, int entityType) {

        return PREFIX_FOLLOWEE + SPLIT + userId + SPLIT + entityType;
    }

    // 某个实体拥有的粉丝
    // follower:entityType:entityId -> zset(userId,now)
    public static String getFollowerKey(int entityType, int entityId) {

        return PREFIX_FOLLOWER + SPLIT + entityType + SPLIT + entityId;
    }
}
