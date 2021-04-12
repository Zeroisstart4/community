package com.nowcoder.community.dao;

import com.nowcoder.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

// Spring 整合 myBatis
@Mapper
public interface DiscussPostMapper {

    // 查找帖子
    List<DiscussPost> selectDiscussPosts(int userId, int offset, int limit, int orderMode);

    // @Param注解用于给参数取别名,
    // 如果只有一个参数,并且在<if>里使用,则必须加别名.

    // 查找帖子数量
    int selectDiscussPostRows(@Param("userId") int userId);

    // 添加帖子
    int insertDiscussPost(DiscussPost discussPost);

    // 通过 id 查询帖子
    DiscussPost selectDiscussPostById(int id);

    // 更新帖子数量
    int updateCommentCount(int id, int commentCount);

    // 更新帖子类型
    int updateType(int id, int type);

    // 更新帖子状态
    int updateStatus(int id, int status);

    // 更新帖子分数
    int updateScore(int id, double score);

}
