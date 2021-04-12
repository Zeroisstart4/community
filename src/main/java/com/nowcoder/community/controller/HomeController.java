package com.nowcoder.community.controller;

import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.entity.Page;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.DiscussPostService;
import com.nowcoder.community.service.LikeService;
import com.nowcoder.community.service.UserService;
import com.nowcoder.community.util.CommunityConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 主页控制器
@Controller
public class HomeController implements CommunityConstant{
    // 自动注入
    @Autowired
    private DiscussPostService discussPostService;

    // 自动注入
    @Autowired
    private UserService userService;

    @Autowired
    private LikeService likeService;

    // 获取映射路径，请求方式
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    // 获取页面页数
    public String getIndexPage(Model model, Page page,
                               @RequestParam(name = "orderMode", defaultValue = "0") int orderMode) {

        // 方法调用前,SpringMVC 会自动实例化 Model 和 Page,并将 Page 注入 Model.
        // 所以,在 thymeleaf 中可以直接访问 Page 对象中的数据.

        // 设置页面的帖子数量
        page.setRows(discussPostService.findDiscussPostRows(0));
        // 设置帖子的查询路径(用于复用分页链接)
        page.setPath("/index?orderMode=" + orderMode);

        // 查找帖子数，并将其封装入 list 集合
        List<DiscussPost> list = discussPostService.findDiscussPosts(0, page.getOffset(), page.getLimit(), orderMode);
        // 用于封装用户名与帖子
        List<Map<String, Object>> discussPosts = new ArrayList<>();
        // 判断帖子数不为 0
        if(list != null) {
            // 遍历帖子
            for (DiscussPost post : list) {
                // 创建一个 map 对象
                Map<String, Object> map = new HashMap<>();
                // 将 post 对象添加入 map
                map.put("post", post);
                // 通过 userId 查找 User 对象
                User user = userService.findUserById(post.getUserId());
                // 将 user 对象添加入 map
                map.put("user", user);

                long likeCount = likeService.findEntityLikeCount(ENTITY_TYPE_POST, post.getId());
                map.put("likeCount", likeCount);

                // 将 map 对象添加入 discussPosts 数组
                discussPosts.add(map);
            }
        }
        // 将 discussPosts 存入 model
        model.addAttribute("discussPosts",discussPosts);
        model.addAttribute("orderMode", orderMode);

        // 返回 index 页面
        return "/index";
    }

    @RequestMapping(path = "/error", method = RequestMethod.GET)
    public String getErrorPage() {
        return "/error/500";
    }

    @RequestMapping(path = "/denied", method = RequestMethod.GET)
    public String getDeniedPage() {
        return "/error/404";
    }
}
