package com.nowcoder.community.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author zhou
 * @create 2021-4-12 14:58
 */

// 线程池配置类
@Configuration
@EnableScheduling
@EnableAsync
public class ThreadPoolConfig {
}
