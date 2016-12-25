package com.jhzz.demo.biz.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jhzz.demo.biz.CustomerService;
import com.jhzz.demo.biz.filter.Page;
import com.jhzz.demo.biz.util.PageConverter;
import com.jhzz.demo.biz.vo.CustomerListVo;
import com.jhzz.demo.dal.mapper.CustomerMapperExt;

@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private CustomerMapperExt customerMapperExt;
	
	/**
	 * 查询客户信息列表
	 */
	@Override
	public CustomerListVo queryCustomerList(Page page) {
		Map<String, Integer> params = null;
		if (page != null && page.getPageNo() != null && page.getPageSize() != null) {
			params = PageConverter.buildOffsetAndLimitMapByPage(page);
		}
		
		CustomerListVo listVo = new CustomerListVo();
		listVo.setCustomers(customerMapperExt.selectByPage(params));
		listVo.setTotalNum(customerMapperExt.selectCountByPage(params));
		
		return listVo;
	}

}
