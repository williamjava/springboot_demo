package com.jhzz.demo.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 
 * 注意：配置yml文件时，冒号后面必须空一格再写值，否则报错
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.jhzz")
@ConfigurationProperties("classpath:application.yml")
@EnableScheduling
public class Application extends SpringBootServletInitializer {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}
}
