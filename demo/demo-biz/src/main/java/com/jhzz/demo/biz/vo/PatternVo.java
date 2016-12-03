/**
 * Project Name:demo-biz
 * File Name:PatternVo.java
 * Package Name:com.jhzz.demo.biz.vo
 * Date:2016年12月1日下午4:14:35
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.jhzz.demo.biz.vo;

import com.jhzz.demo.dal.model.Pattern;

/**
 * ClassName:PatternVo <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016年12月1日 下午4:14:35 <br/>
 * @author   wuhoujian
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class PatternVo extends Pattern{
	private Object[] pics;

	public Object[] getPics() {
		return pics;
	}

	public void setPics(Object[] pics) {
		this.pics = pics;
	}

}

