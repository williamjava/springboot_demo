package com.jhzz.demo.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 设置系统默认访问页面
 * @author wuhoujian
 */
@Controller
public class DefaultController {

	@RequestMapping("/")
	public String index(ModelMap map) {
		return "index";
	}
}
