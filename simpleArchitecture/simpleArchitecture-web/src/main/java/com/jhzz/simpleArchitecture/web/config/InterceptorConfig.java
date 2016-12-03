package com.jhzz.simpleArchitecture.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.jhzz.simpleArchitecture.web.interceptors.AuthInterceptor;
import com.jhzz.simpleArchitecture.web.interceptors.MethodInterceptor;

/**
 * 
 * @author sunjian
 * @date 2016/11/30
 * @desc 拦截器配置类
 * 
 *       添加@Configuration注解，将自定义的拦截器添加到拦截器的队列中
 *       声明当前类是一个配置类，相当于一个Spring配置的xml文件，这是Spring4.x推荐的配置方式
 *       也就是所谓java-based风格的配置方式
 *
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {
	// 注册自定义的拦截器类
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 第一个拦截器
		registry.addInterceptor(new MethodInterceptor()).addPathPatterns("/**");

		// 第二个拦截器
		registry.addInterceptor(new AuthInterceptor()).addPathPatterns("/**");

		// 这个一定要保留，否则拦截器无效
		super.addInterceptors(registry);
	}

}
