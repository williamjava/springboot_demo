package com.jhzz.demo.biz.util;

import java.util.HashMap;
import java.util.Map;

import com.jhzz.demo.biz.filter.Page;

/**
 * 工具类，根据原始的page对象构造对数据库查询有实际意义的对象
 *
 */
public class PageConverter {
	public static Map<String, Integer> buildOffsetAndLimitMapByPage(Page page) {
		Map<String, Integer> map = new HashMap<>();

		map.put("offset", (page.getPageNo() - 1) * page.getPageSize());
		map.put("limit", page.getPageSize());

		return map;
	}

}
