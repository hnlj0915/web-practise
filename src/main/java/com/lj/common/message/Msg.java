package com.lj.common.message;

import java.io.Serializable;

public class Msg implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 返回状态码
	private int code = -1;
	// 返回状态
	private boolean ok;
	// 返回信息
	private Object data;

	public Msg() {
		super();
	}

	public Msg(boolean ok, Object data) {
		super();
		this.ok = ok;
		this.data = data;
	}

	public Msg(int code, Object data) {
		super();
		this.code = code;
		this.data = data;
	}

	public Msg(int code, boolean ok, Object data) {
		super();
		this.code = code;
		this.ok = ok;
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
