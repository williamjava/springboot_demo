package com.jhzz.simpleArchitecture.web;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jhzz.simpleArchitecture.biz.StudentService;
import com.jhzz.simpleArchitecture.dal.dataobject.Student;

/**
 * 
 * @author sunjian
 * @date 2016/11/30
 * @desc 测试类，下面这两个注解一般必须同时出现，它们的作用是测试某个方法的时候启动整个应用，目的在于对于复杂的需要使用其它服务的测试方法，有时候这么做是必须的
 *       如果不写的话很有可能出现空指针异常等情况
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class StudentServiceTest {
	@Autowired
	private StudentService studentService;

	/**
	 * 测试某一个方法的标准写法，对于这种依赖其它服务的测试，两个注解必须同时加上
	 */

	@Test
	public void testGetStudentList() {
		List<Student> studentList = studentService.getList();

		System.out.println(studentList.size());
	}

	/**
	 * 这种简单的测试即使测试类不加任何注解也没有问题
	 */
	@Test
	public void testSimple() {
		System.out.println("this is a simple test , I dont need any annotation");
	}

}
