package com.nowcoder.community.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * 获取 cookie 小工具
 * @author zhou
 * @create 2021-3-25 15:37
 */
public class CookieUtil {

    /**
     * 获取 request 域中的所有 cookie ，查找是否有所需的 cookie，并返回相应的结果
     * @param request
     * @param name
     * @return
     */
    public static String getValue(HttpServletRequest request, String name) {

        // 判断 request 与 name 是否满足要求
        if(request == null || name == null) {
            throw new IllegalArgumentException("参数为空!");
        }
        // 获取 request 域中的所有 cookie
        Cookie[] cookies = request.getCookies();
        // 判断 cookies 是否满足要求
        if(cookies != null && cookies.length != 0) {
            // 遍历数组，查找 cookie
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals(name)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
