package com.jhzz.simpleArchitecture.web.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.jhzz.simpleArchitecture.web.annotation.AdminPermission;

/**
 * 
 * @author sunjian
 * @desc 自定义拦截器，控制访问权限的拦截器 这里设置一个规则，被AdminPermission自定义注解标注的方法将不允许访问，其它的都可以被访问
 * 
 */

public class AuthInterceptor extends HandlerInterceptorAdapter {
	private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.info("The URL is: {}", request.getRequestURL());

		HandlerMethod handlerMethod = (HandlerMethod) handler;
		AdminPermission permission = handlerMethod.getMethodAnnotation(AdminPermission.class);

		// 说明某个方法被AdminPermission标注了，那么此方法不允许访问
		if (permission != null) {
			throw new RuntimeException("你没有管理员的权限，无法访问");

			// return false;
		}

		return true;
	}

}
