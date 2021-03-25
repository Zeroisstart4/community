package com.nowcoder.community.entity;

import java.util.Date;

/**
 * 用户登录凭证
 * @author zhou
 */
public class LoginTicket {
    // 用户 id
    private int id;
    // 用户名
    private int userId;
    // 入场票（登录凭证）
    private String ticket;
    // 用户状态
    private int status;
    // 登录到期期限
    private Date expired;

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

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getExpired() {
        return expired;
    }

    public void setExpired(Date expired) {
        this.expired = expired;
    }

    @Override
    public String toString() {
        return "LoginTicket{" +
                "id=" + id +
                ", userId=" + userId +
                ", ticket='" + ticket + '\'' +
                ", status=" + status +
                ", expired=" + expired +
                '}';
    }
}
