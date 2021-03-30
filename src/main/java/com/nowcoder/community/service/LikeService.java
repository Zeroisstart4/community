package com.nowcoder.community.service;

import com.nowcoder.community.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 点赞
 * @author zhou
 * @create 2021-3-29 22:08
 */

@Service
public class LikeService {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 点赞
     * @param userId 用户 id
     * @param entityType 实体类类型
     * @param entityId 实体类 id
     */
    public void like(int userId, int entityType, int entityId) {

        // 查询实体类的 key
        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
        // 查询 entityLikeKey 是否存在与 redis 中
        boolean isMember = redisTemplate.opsForSet().isMember(entityLikeKey, userId);
        // 若存在 redis 中，再次点击时，移除该 entityLikeKey，（对应着点赞后再次点赞则取消点赞）
        if(isMember) {
            // 移除该 entityLikeKey
            redisTemplate.opsForSet().remove(entityLikeKey, userId);
        }
        else {
            redisTemplate.opsForSet().add(entityLikeKey, userId);
        }
    }

    /**
     * 查询某实体点赞的数量
     * @param entityType
     * @param entityId
     * @return
     */
    public long findEntityLikeCount(int entityType, int entityId) {
        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
        return redisTemplate.opsForSet().size(entityLikeKey);
    }


    /**
     * 查询某人对某实体的点赞状态
     * @param userId
     * @param entityType
     * @param entityId
     * @return
     */
    public int findEntityLikeStatus(int userId, int entityType, int entityId) {
        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
        return redisTemplate.opsForSet().isMember(entityLikeKey, userId) ? 1 : 0;
    }
}
