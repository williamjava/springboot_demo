package com.jhzz.simpleArchitecture.biz.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.jhzz.simpleArchitecture.biz.StudentService;
import com.jhzz.simpleArchitecture.dal.dataobject.Student;
import com.jhzz.simpleArchitecture.dal.mapper.StudentMapper;

/**
 * 
 * @author sunjian 2016/11/30
 * 
 *         学生实现类
 *
 */
@Service
public class StudentServiceImpl implements StudentService {
	private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

	/**
	 * @Value 自动注入配置文件中解析得到的属性值
	 */
	@Value("${version}")
	private String version;

	@Value("${language}")
	private String language;

	@Autowired
	private StudentMapper studentMapper;

	@Override
	public List<Student> getList() {
		logger.info("开始执行学生列表查询");

		return studentMapper.selectList();
	}

	/**
	 * @Scheduled 配合Application的@EnableScheduling注解就完全可以实现一个定时器的功能了。
	 *            这种方式无须其它的xml配置，使用非常灵活
	 * 
	 *            cron指定执行频率
	 */

	@Scheduled(cron = "0 0/1 * * * ?")
	public void printSchduled() {
		for (int i = 0; i < 3; i++) {
			System.out.println("The language we use to develop is : " + language);

			System.out.println("Now the version of the language is : " + version);

		}

	}

}
