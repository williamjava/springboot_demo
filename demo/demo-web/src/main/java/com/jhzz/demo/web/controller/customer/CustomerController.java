package com.jhzz.demo.web.controller.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jhzz.demo.biz.CustomerService;
import com.jhzz.demo.biz.filter.Page;
import com.jhzz.demo.biz.vo.CustomerListVo;
import com.jhzz.demo.common.util.Result;

/**
 * 设置系统默认访问页面
 * @author wuhoujian
 */
@Controller
@RequestMapping("/customer")
public class CustomerController {
	@Autowired
	private CustomerService customerService;

	@RequestMapping("/list")
	@ResponseBody
	public Result<CustomerListVo> list(Page page) {
		return Result.generateSuccess(customerService.queryCustomerList(page));
	}
}
