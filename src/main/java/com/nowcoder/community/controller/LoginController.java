package com.nowcoder.community.controller;


import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.UserService;
import com.nowcoder.community.util.CommunityConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

// 登录页控制器
@Controller
public class LoginController implements CommunityConstant {

    // 自动注入
    @Autowired
    private UserService userService;

    /**
     *  register 页面跳转
     */

    // 获取映射路径，请求方式
    @RequestMapping(path = "/register", method = RequestMethod.GET)
    public String getRegisterPage() {
        return "/site/register";
    }

    /**
     *  login 页面跳转
     */
    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String getLoginPage() {
        return "/site/login";
    }

    /**
     * 处理注册请求， 浏览器向服务器请求数据（Post 方式）
     * @param model
     * @param user
     * @return
     */
    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public String register(Model model, User user) {
        // 通过 userService 的 register 方法获取 map 对象
        Map<String, Object> map = userService.register(user);

        // 若 map 为空或者 map 没有内容
        if(map == null || map.isEmpty()) {
            // 添加信息
            model.addAttribute("msg", "注册成功,我们已经向您的邮箱发送了一封激活邮件,请尽快激活!");
            // 添加 target 目标跳转路径
            model.addAttribute("target", "/index");
            return "/site/operate-result";
        }
        else {
            // 添加信息
            model.addAttribute("usernameMsg", map.get("usernameMsg"));
            model.addAttribute("passwordMsg", map.get("passwordMsg"));
            model.addAttribute("emailMsg", map.get("emailMsg"));
            return "/site/register";
        }

    }

    /**
     * 激活账号方法
     * @param model
     * @param userId
     * @param code
     * @return
     */
    @RequestMapping(path = "/activation/{userId}/{code}", method = RequestMethod.GET)
    public String activation(Model model, @PathVariable("userId") int userId, @PathVariable("code") String code) {
        // 获取激活码状态
        int result = userService.activation(userId, code);

        if(result == ACTIVATION_SUCCESS) {
            // 添加信息
            model.addAttribute("msg", "激活成功,您的账号已经可以正常使用了!");
            // 添加 target 目标跳转路径
            model.addAttribute("target", "/login");
        }
        else if(result == ACTIVATION_REPEAT) {
            model.addAttribute("msg", "无效操作,该账号已经激活过了!");
            model.addAttribute("target", "/index");
        }
        else {
            model.addAttribute("msg", "激活失败,您提供的激活码不正确!");
            model.addAttribute("target", "/index");
        }
        return "/site/operate-result";
    }
}
