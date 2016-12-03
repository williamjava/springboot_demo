package com.jhzz.demo.dal.mapper;

import com.jhzz.demo.dal.model.PatternDetail;
import com.jhzz.demo.dal.model.PatternDetailExample;
import java.util.List;

public interface PatternDetailMapper {
    int countByExample(PatternDetailExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PatternDetail record);

    int insertSelective(PatternDetail record);

    List<PatternDetail> selectByExample(PatternDetailExample example);

    PatternDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PatternDetail record);

    int updateByPrimaryKey(PatternDetail record);
}