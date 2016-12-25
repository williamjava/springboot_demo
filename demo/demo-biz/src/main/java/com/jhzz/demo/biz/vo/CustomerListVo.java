package com.jhzz.demo.biz.vo;

import java.util.List;

import com.jhzz.demo.dal.model.Customer;

import lombok.Data;

@Data
public class CustomerListVo {
	private List<Customer> customers;
	private Integer totalNum;
}
