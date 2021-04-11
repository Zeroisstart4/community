package com.nowcoder.community.dao;

import com.nowcoder.community.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    // 通过 id 查用户
    User selectById(int id);

    // 通过 username 查用户
    User selectByName(String username);

    // 通过 email 查用户
    User selectByEmail(String email);

    // 将 user 对象存入数据库
    int insertUser(User user);

    // 通过 id，status 更新用户状态
    int updateStatus(int id, int status);

    // 通过 id，headerUrl 更新用户头像
    int updateHeader(int id, String headerUrl);

    // 通过 id，password 更新用户密码
    int updatePassword(int id, String password);

}
