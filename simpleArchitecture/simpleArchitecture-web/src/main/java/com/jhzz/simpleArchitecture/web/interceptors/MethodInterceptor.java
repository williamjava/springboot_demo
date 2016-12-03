package com.jhzz.simpleArchitecture.web.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 
 * @author sunjian
 * @desc 自定义拦截器，这是一个示例，在每个拦截的方法执行之前打印当前访问的请求路径
 * 
 */

public class MethodInterceptor extends HandlerInterceptorAdapter {
	private static final Logger logger = LoggerFactory.getLogger(MethodInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.info("===========preHandle=============");
		logger.info("The URL is: {}", request.getRequestURL());

		return true;
	}

}
