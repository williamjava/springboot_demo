package com.jhzz.demo.biz.filter;

import lombok.Data;

@Data
public class Page {
	private Integer pageNo;//分页参数-当前页面
	private Integer pageSize;//分页参数-每页条数
}
