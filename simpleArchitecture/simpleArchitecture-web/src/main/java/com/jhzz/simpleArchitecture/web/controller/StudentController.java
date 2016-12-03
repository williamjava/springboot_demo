package com.jhzz.simpleArchitecture.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jhzz.simpleArchitecture.biz.StudentService;
import com.jhzz.simpleArchitecture.dal.dataobject.Student;
import com.jhzz.simpleArchitecture.dal.dataobject.StudentExample;
import com.jhzz.simpleArchitecture.web.annotation.AdminPermission;

/**
 * 
 * @author sunjian 2016/11/29
 * 
 *         基本的控制器类
 *
 */
@RequestMapping("/stu")
@Controller
public class StudentController {
	private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

	@Autowired
	private StudentService studentService;

	@RequestMapping("stuList")
	public String index(ModelMap map) {
		StudentExample example = new StudentExample();
		StudentExample.Criteria criteria = example.or();
		criteria.andIdGreaterThan(0L);

		List<Student> stuList = studentService.getList();
		map.put("stuList", stuList);

		return "stuList";
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping("hello")
	@ResponseBody
	public String greeting(@RequestParam("name") String name) {
		logger.info("方法入参为name:{}", name);

		return "Hello " + name;
	}

	/**
	 * 用来测试自定义注解和拦截器是否发挥了作用
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping("testAnno")
	@ResponseBody
	@AdminPermission
	public String adminOnly(@RequestParam("name") String name) {
		logger.info("方法入参为name:{}", name);

		return "你是管理员， " + name;
	}

	/**
	 * 用来测试自定义注解和拦截器是否发挥了作用
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping("throw")
	@ResponseBody
	public String throwEx() {
		throw new RuntimeException("纯属为了抛异常");
	}

}
