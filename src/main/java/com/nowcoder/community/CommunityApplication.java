package com.nowcoder.community;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// 生成一个 SpringBootApplication 对象，并加入 Spring 容器
@SpringBootApplication
public class CommunityApplication {
	// 通过反射的方式生成 CommunityApplication 对象
	public static void main(String[] args) {
		SpringApplication.run(CommunityApplication.class, args);
	}

}
