package com.jhzz.simpleArchitecture.dal.mapper;

import com.jhzz.simpleArchitecture.dal.dataobject.Student;
import com.jhzz.simpleArchitecture.dal.dataobject.StudentExample;
import java.util.List;

public interface StudentMapper {
    int countByExample(StudentExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Student record);

    int insertSelective(Student record);

    List<Student> selectByExample(StudentExample example);
    
    List<Student> selectList();

    Student selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Student record);

    int updateByPrimaryKey(Student record);
}