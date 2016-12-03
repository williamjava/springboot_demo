package com.jhzz.demo.common.util;

import java.io.Serializable;

/**
 * 
 * @author sunjian
 * 
 * @date 2016/11/29
 *
 * @param <T>  数据
 *            以json格式返回客户端请求的数据
 */

@SuppressWarnings("serial")
public class Result<T> implements Serializable {
	private T data;
	private int status;
	private String msg;

	public static <T> Result<T> generateSuccess(T t) {
		Result<T> result = new Result<T>();
		result.setData(t);
		result.setStatus(0);
		result.setMsg("success");

		return result;
	}

	public static <T> Result<T> generateFail(T t) {
		Result<T> result = new Result<T>();
		result.setData(t);
		result.setStatus(1);
		result.setMsg("fail");

		return result;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
