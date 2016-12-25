package com.jhzz.demo.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 设置系统默认访问页面
 * @author wuhoujian
 */
@Controller
@RequestMapping("/user")
public class UserController {

	@RequestMapping("/login")
	public String login(ModelMap map) {
		return "index";
	}
}
