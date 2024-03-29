package com.nowcoder.community.util;




import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * MD5 加密与 Json 转 String 工具类
 */
public class CommunityUtil {

    // 生成随机字符串
    public static String generateUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    // MD5加密
    public static String md5(String key) {
        // 若加密对象为空
        if(StringUtils.isBlank(key)) {
            return null;
        }
        // md5 加密
        return DigestUtils.md5DigestAsHex(key.getBytes());
    }

    // 将 JSON 转为字符串
    public static String getJSONString(int code, String msg, Map<String,Object> map) {

        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put("msg", msg);
        if(map != null){
            for (String key : map.keySet()) {
                json.put(key, map.get(key));
            }
        }

        return json.toJSONString();

    }

    public static String getJSONString(int code, String msg) {
        return getJSONString(code, msg, null);
    }

    public static String getJSONString(int code) {
        return getJSONString(code, null, null);
    }

}

