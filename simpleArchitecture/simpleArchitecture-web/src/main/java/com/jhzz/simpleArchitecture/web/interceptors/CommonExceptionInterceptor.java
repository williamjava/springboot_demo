package com.jhzz.simpleArchitecture.web.interceptors;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author sunjian 2016/11/30
 * @desc 统一异常处理 如果异常没有进行主动的捕获，那么最终这个类会进行处理
 *
 */
@Component
public class CommonExceptionInterceptor implements HandlerExceptionResolver {
	private static final Logger logger = LoggerFactory.getLogger(CommonExceptionInterceptor.class);

	/**
	 * 具体实现处理逻辑
	 */
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		
		logger.error("服务器发生异常，异常产生的位置为:{}",
				handlerMethod.getBeanType().getName() + "." + handlerMethod.getMethod().getName(), ex);

		//response.setCharacterEncoding("UTF-8");
		response.setHeader("Cache-Control", "no-cache, must-revalidate");
		response.setStatus(500);

		try {
			response.getWriter().write(ex.getMessage());
		}

		catch (IOException e) {

		}

		return new ModelAndView("error");
	}

}
