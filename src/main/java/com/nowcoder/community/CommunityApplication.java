package com.nowcoder.community;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

// 生成一个 SpringBootApplication 对象，并加入 Spring 容器
@SpringBootApplication
public class CommunityApplication {

	@PostConstruct
	public void init() {
		// 解决netty启动冲突问题
		// see Netty4Utils.setAvailableProcessors()
		System.setProperty("es.set.netty.runtime.available.processors", "false");
	}
	// 通过反射的方式生成 CommunityApplication 对象
	public static void main(String[] args) {
		SpringApplication.run(CommunityApplication.class, args);
	}

}
