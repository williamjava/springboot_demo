package com.jhzz.demo.common.dto;

import java.io.Serializable;

/**
 * 
 * @author sunjian 2016/12/01
 * 封装微信接口返回的错误信息
 *
 */

@SuppressWarnings("serial")
public class ErrorDTO implements Serializable {
	private Integer errcode;
	private String errmsg;

	public Integer getErrcode() {
		return errcode;
	}

	public void setErrcode(Integer errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

}
