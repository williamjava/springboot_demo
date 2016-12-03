package com.jhzz.demo.common.dto;

import java.io.Serializable;


/**
 * 
 * @author sunjian 2016/12/01
 * 封装微信接口返回的token信息
 *
 */
@SuppressWarnings("serial")
public class WeixinTokenDTO implements Serializable {
	private String access_token;
	private Integer expires_in;

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public Integer getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(Integer expires_in) {
		this.expires_in = expires_in;
	}

}
