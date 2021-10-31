package com.nowcoder.community.config;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

// kaptcha 配置类
@Configuration
public class KaptchaConfig {

    @Bean
    public Producer kaptchaProducer() {
        // 用于储存 kaptcha 属性
        Properties properties = new Properties();
        // 设置图片宽度
        properties.setProperty("kaptcha.image.width", "100");
        // 设置图片高度
        properties.setProperty("kaptcha.image.height", "40");
        // 设置图片字体大小
        properties.setProperty("kaptcha.textproducer.font.size", "32");
        // 设置图片字体颜色
        properties.setProperty("kaptcha.textproducer.font.color", "0,0,0");
        // 设置图片验证码随机字符范围
        properties.setProperty("kaptcha.textproducer.char.string", "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYAZ");
        // 设置图片验证码随机字符长度
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        // 设置图片干扰类型
        properties.setProperty("kaptcha.noise.impl", "com.google.code.kaptcha.impl.NoNoise");
        // 生成默认 kaptcha 对象
        DefaultKaptcha kaptcha = new DefaultKaptcha();

        Config config = new Config(properties);
        kaptcha.setConfig(config);

        return kaptcha;
    }
}
