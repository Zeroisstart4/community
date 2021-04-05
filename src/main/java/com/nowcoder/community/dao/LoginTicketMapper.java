package com.nowcoder.community.dao;

import com.nowcoder.community.entity.LoginTicket;
import org.apache.ibatis.annotations.*;


@Mapper
@Deprecated
public interface LoginTicketMapper {

    // 插入凭证
    @Insert({
            "insert into login_ticket(user_id,ticket,status,expired) ",
            "values(#{userId},#{ticket},#{status},#{expired})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "id") // 自动生成 id 主键
    int insertLoginTicket(LoginTicket loginTicket);

    // 查询凭证
    @Select({
            "select id , user_id, ticket, status, expired ",
            "from login_ticket where ticket = #{ticket}"
    })
    LoginTicket selectByTicket(String ticket);

    // 修改状态
    @Update({
            "update login_ticket set status = #{status} where ticket = #{ticket}"
    })
    int updateStatus(String ticket, int status);
}
