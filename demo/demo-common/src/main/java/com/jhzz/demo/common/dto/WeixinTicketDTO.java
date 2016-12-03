package com.jhzz.demo.common.dto;

import java.io.Serializable;

/**
 * 
 * @author sunjian 2016/12/01
 * 封装微信接口返回的ticket信息
 *
 */

@SuppressWarnings("serial")
public class WeixinTicketDTO implements Serializable {
	private Integer errcode;
	private String errmsg;
	private String ticket;
	private Integer expires_in;

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

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public Integer getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(Integer expires_in) {
		this.expires_in = expires_in;
	}

}
