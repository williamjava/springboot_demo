package com.jhzz.demo.dal.mapper;

import java.util.List;
import java.util.Map;

import com.jhzz.demo.dal.model.Customer;

public interface CustomerMapperExt extends CustomerMapper{
    List<Customer> selectByPage(Map<String, Integer> params);
    Integer selectCountByPage(Map<String, Integer> params);
}