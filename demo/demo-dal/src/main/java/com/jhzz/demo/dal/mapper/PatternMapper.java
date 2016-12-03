package com.jhzz.demo.dal.mapper;

import com.jhzz.demo.dal.model.Pattern;
import com.jhzz.demo.dal.model.PatternExample;
import java.util.List;

public interface PatternMapper {
    int countByExample(PatternExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Pattern record);

    int insertSelective(Pattern record);

    List<Pattern> selectByExample(PatternExample example);

    Pattern selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Pattern record);

    int updateByPrimaryKey(Pattern record);
}