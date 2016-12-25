package com.jhzz.demo.biz;

import com.jhzz.demo.biz.filter.Page;
import com.jhzz.demo.biz.vo.CustomerListVo;

public interface CustomerService {
	CustomerListVo queryCustomerList(Page page);
}