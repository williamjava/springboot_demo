package com.jhzz.demo.dal.mapper;

import com.jhzz.demo.dal.model.Customer;
import com.jhzz.demo.dal.model.CustomerExample;
import java.util.List;

public interface CustomerMapper {
    int countByExample(CustomerExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Customer record);

    int insertSelective(Customer record);

    List<Customer> selectByExample(CustomerExample example);

    Customer selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Customer record);

    int updateByPrimaryKey(Customer record);
}