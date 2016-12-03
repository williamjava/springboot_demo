package com.jhzz.simpleArchitecture.biz;

import java.util.List;

import com.jhzz.simpleArchitecture.dal.dataobject.Student;

/**
 * 
 * @author sunjian 2016/11/30
 * 
 *
 */
public interface StudentService {
	/**
	 * 获取所有的学生
	 * 
	 * @return
	 */
	List<Student> getList();

}
