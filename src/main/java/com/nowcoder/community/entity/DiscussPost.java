package com.nowcoder.community.entity;

import java.util.Date;

/**
 * 封装帖子的内容
 * @author zhou
 */
public class DiscussPost {

    // 帖子 id
    private int id;
    // 用户 id
    private int userId;
    // 帖子标题
    private String title;
    // 帖子内容
    private String content;
    // 帖子类型，表示是否置顶
    private int type;
    // 帖子状态，用于表示是否被屏蔽
    private int status;
    // 帖子创建时间
    private Date createTime;
    // 帖子的回帖数
    private int commentCount;
    // 帖子的点赞数
    private double score;

    // 相应的 get/set 方法
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String context) {
        this.content = context;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "DiscussPost{" +
                "id=" + id +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", type=" + type +
                ", status=" + status +
                ", createTime=" + createTime +
                ", commentCount=" + commentCount +
                ", score=" + score +
                '}';
    }
}
