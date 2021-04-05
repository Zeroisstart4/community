package com.nowcoder.community.util;

import java.util.List;
import java.util.Map;
import java.util.Set;

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
    // 验证码
    private static final String PREFIX_KAPTCHA = "kaptcha";
    // 入场券
    private static final String PREFIX_TICKET = "ticket";
    // 登录用户
    private static final String PREFIX_USER = "user";

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

    // 登录验证码
    public static String getKaptchaKey(String owner) {
        return PREFIX_KAPTCHA + SPLIT + owner;
    }

    // 登录的凭证
    public static String getTicketKey(String ticket) {
        return PREFIX_TICKET + SPLIT + ticket;
    }

    // 用户
    public static String getUserKey(int userId) {
        return PREFIX_USER + SPLIT + userId;
    }


}