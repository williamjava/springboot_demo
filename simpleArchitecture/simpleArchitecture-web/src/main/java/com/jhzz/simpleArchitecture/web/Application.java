package com.jhzz.simpleArchitecture.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * springboot应用启动类，注意下面的注解
 * @author sunjian
 * 
 * @date   2016/11/30
 *
 * @desc 项目启动类，注意下面的四个注解。
 *       SpringBootApplication：用于启动应用上下文，没有这个注解应用无法启动。应用自动集成tomcat，可以打开项目的maven依赖查看，里面引入了tomcat相关的jar包 
 *       
 *       ComponentScan：指定应用上下文需要扫描哪些包，下面的配置说明扫描所有以com.jhzz开头的包。比起传统的在xml里面的componentscan配置，这个简便很多
 *       
 *       
 *       EnableScheduling：指定应用具有执行定时任务的能力。在相应的类的任意方法上面加上@Scheduled注解，那么该方法就相当于定时器，无须其它任何配置。
 *       
 *       ConfigurationProperties：指定配置文件的位置，应用上下文会自动加载解析该文件。可以在这个注解后面加上多个配置。
 *       
 *       注意：配置yml文件时，冒号后面必须空一格再写值，否则报错
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
