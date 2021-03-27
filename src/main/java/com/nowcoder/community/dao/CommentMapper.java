package com.nowcoder.community.dao;

import com.nowcoder.community.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author zhou
 * @create 2021-3-26 22:10
 */
@Mapper
public interface CommentMapper {

    // 查询回复类型（评论回复？ 帖子回复？视频回复？）
    List<Comment> selectCommentsByEntity(int entityType, int entityId, int offset, int limit);

    // 查询回复数
    int selectCountByEntity(int entityType, int entityId);

    // 添加回复
    int insertComment(Comment comment);
}
