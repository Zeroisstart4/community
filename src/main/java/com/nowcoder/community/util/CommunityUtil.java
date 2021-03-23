package com.nowcoder.community.util;




import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.util.UUID;

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
}
