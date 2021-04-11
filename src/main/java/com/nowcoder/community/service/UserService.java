package com.nowcoder.community.service;

import com.nowcoder.community.dao.LoginTicketMapper;
import com.nowcoder.community.dao.UserMapper;
import com.nowcoder.community.entity.LoginTicket;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.util.CommunityConstant;
import com.nowcoder.community.util.CommunityUtil;
import com.nowcoder.community.util.MailClient;
import com.nowcoder.community.util.RedisKeyUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class UserService implements CommunityConstant {

    // 用户映射
    @Autowired
    private UserMapper userMapper;

    // 邮件客户端
    @Autowired
    private MailClient mailClient;

    // 模板引擎
    @Autowired
    private TemplateEngine templateEngine;

    // redis 模板
    @Autowired
    private RedisTemplate redisTemplate;

    // 主机域名
    @Value("${community.path.domain}")
    private String domain;

    // 项目访问前缀
    @Value("${server.servlet.context-path}")
    private String contextPath;

/*    @Autowired
    private LoginTicketMapper loginTicketMapper;*/

    /**
     * 查询用户方法
     * @param id 用户 id
     * @return
     */
    public User findUserById(int id) {
        //return userMapper.selectById(id);
        User user = getCache(id);
        if (user == null) {
            user = initCache(id);
        }
        return user;
    }

    /**
     * 注册方法
     * @param user 用户对象
     * @return
     */
    public Map<String, Object> register(User user) {
        // 返回的 map 对象
        Map<String, Object> map = new HashMap<>();
        // 空值处理
        if(user == null) {
            throw new IllegalArgumentException("参数不能为空");
        }
        // 账号检测
        if(StringUtils.isBlank(user.getUsername())) {
            map.put("usernameMsg", "账号不能为空");
            return map;
        }
        // 密码检测
        if(StringUtils.isBlank(user.getPassword())) {
            map.put("passwordMsg", "密码不能为空");
            return map;
        }
        // 邮箱检测
        if(StringUtils.isBlank(user.getEmail())) {
            map.put("emailMsg", "邮箱不能为空");
            return map;
        }
        // 账号验证
        User u = userMapper.selectByName(user.getUsername());
        if(u != null) {
            map.put("usernameMsg", "该账号已存在");
            return map;
        }

        u = userMapper.selectByEmail(user.getEmail());

        // 账号邮箱
        if(u != null) {
            map.put("emailMsg", "该邮箱已被注册");
            return map;
        }

        // 注册用户
        // 生成随机字符串
        user.setSalt(CommunityUtil.generateUUID().substring(0, 5));
        // md5 加密密码
        user.setPassword(CommunityUtil.md5(user.getPassword() + user.getSalt()));
        // 普通用户
        user.setType(0);
        // 未激活状态
        user.setStatus(0);
        // 激活码
        user.setActivationCode(CommunityUtil.generateUUID());
        // 生成随机头像
        user.setHeaderUrl(String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)));
        // 用户创建时间
        user.setCreateTime(new Date());
        // 添加用户
        userMapper.insertUser(user);


        // 发送激活邮件功能
        // 上下文对象
        Context context = new Context();
        // 添加属性
        context.setVariable("email", user.getEmail());
        // 设置访问路径 = 主机域名 + 访问前缀 + 激活页面所在页面 + 用户 id + / + 用户激活码
        String url = domain + contextPath + "/activation/" + user.getId() + "/" +user.getActivationCode();
        // 添加访问的 url 属性
        context.setVariable("url", url);
        // 创建页面内容
        String content = templateEngine.process("/mail/activation", context);
        // 发送 HTML 邮件
        mailClient.sendMail(user.getEmail(), "激活账号", content);

        return map;
    }

    /**
     * 激活方法
     * @param userId 用户 id
     * @param code 激活码
     * @return
     */
    public int activation(int userId, String code) {
        User user = userMapper.selectById(userId);
        if(user.getStatus() == 1) {
            return ACTIVATION_REPEAT;
        }
        else if(user.getActivationCode().equals(code)) {
            userMapper.updateStatus(userId, 1);
            return ACTIVATION_SUCCESS;
        }
        else {
            return ACTIVATION_FAILURE;
        }
    }

    /**
     * 生成登录凭证
     * @param username
     * @param password
     * @param expiredSeconds
     * @return
     */
    public Map<String, Object> login(String username, String password, int expiredSeconds) {
        // 创建 map
        Map<String, Object> map = new HashMap<>();

        // 空值处理
        if(StringUtils.isBlank(username)) {
            map.put("usernameMsg", "账号不能为空");
            return map;
        }

        if(StringUtils.isBlank(password)) {
            map.put("passwordMsg", "密码不能为空");
            return map;
        }


        User user = userMapper.selectByName(username);
        // 验证账号是否存在
        if(user == null) {
            map.put("usernameMsg", "该账号不存在");
            return map;
        }
        // 验证账号是否激活
        if(user.getStatus() == 0) {
            map.put("usernameMsg", "该账号未激活");
            return map;
        }

        // 验证密码
        password = CommunityUtil.md5(password + user.getSalt());
        if(!user.getPassword().equals(password)) {
            map.put("passwordMsg", "密码不正确");
            return map;
        }

        // 生成登录凭证
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(user.getId());
        loginTicket.setTicket(CommunityUtil.generateUUID());
        loginTicket.setStatus(0);
        loginTicket.setExpired(new Date(System.currentTimeMillis() + expiredSeconds * 1000));
        //loginTicketMapper.insertLoginTicket(loginTicket);

        String redisKey = RedisKeyUtil.getTicketKey(loginTicket.getTicket());
        redisTemplate.opsForValue().set(redisKey, loginTicket);

        map.put("ticket", loginTicket.getTicket());
        return map;
    }

    /**
     * 退出登录
     * @param ticket
     */
    public void logout(String ticket) {
        //loginTicketMapper.updateStatus(ticket, 1);
        String redisKey = RedisKeyUtil.getTicketKey(ticket);
        LoginTicket loginTicket = (LoginTicket) redisTemplate.opsForValue().get(redisKey);
        loginTicket.setStatus(1);
        redisTemplate.opsForValue().set(redisKey, loginTicket);
    }

    /**
     * 查找用户凭证
     * @param ticket
     * @return
     */
    public LoginTicket findLoginTicket(String ticket) {
        //return loginTicketMapper.selectByTicket(ticket);
        String redisKey = RedisKeyUtil.getTicketKey(ticket);
        return (LoginTicket) redisTemplate.opsForValue().get(redisKey);
    }

    /**
     * 更新头像路径
     * @param userId
     * @param headerUrl
     * @return
     */
    public int updateHeader(int userId, String headerUrl) {
        //return userMapper.updateHeader(userId, headerUrl);
        int rows = userMapper.updateHeader(userId, headerUrl);
        clearCache(userId);
        return rows;
    }

    /**
     * 通过用户名查询用户
     */
    public User findUserByName(String username) {
        return userMapper.selectByName(username);
    }


    // 1.优先从缓存中取值
    private User getCache(int userId) {
        String redisKey = RedisKeyUtil.getUserKey(userId);
        return (User) redisTemplate.opsForValue().get(redisKey);
    }

    // 2.取不到时初始化缓存数据
    private User initCache(int userId) {
        User user = userMapper.selectById(userId);
        String redisKey = RedisKeyUtil.getUserKey(userId);
        redisTemplate.opsForValue().set(redisKey, user, 3600, TimeUnit.SECONDS);
        return user;
    }

    // 3.数据变更时清除缓存数据
    private void clearCache(int userId) {
        String redisKey = RedisKeyUtil.getUserKey(userId);
        redisTemplate.delete(redisKey);
    }

    /**
     * 获取权限
     * @param userId
     * @return
     */
    public Collection<? extends GrantedAuthority> getAuthorities(int userId) {
        // 通过用户 id 查询用户
        User user = this.findUserById(userId);

        // 用于储存用户权限
        List<GrantedAuthority> list = new ArrayList<>();
        list.add(new GrantedAuthority() {
            // 通过用户类型授予相应的权限
            @Override
            public String getAuthority() {
                switch (user.getType()) {
                    case 1:
                        return AUTHORITY_ADMIN;
                    case 2:
                        return AUTHORITY_MODERATOR;
                    default:
                        return AUTHORITY_USER;
                }
            }
        });
        return list;
    }
}
