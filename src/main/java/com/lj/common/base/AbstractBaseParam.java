package com.lj.common.base;

import java.io.Serializable;

/**
 * @Title: AbstractBaseParam.java
 * @Package com.b5m.test.model
 * @Description: TODO(用一句话描述该文件做什么)
 * @author jia.liu
 * @date 2014-5-16 上午10:43:56
 * @version V1.0
 */
public abstract class AbstractBaseParam implements Serializable {

	private static final long serialVersionUID = 3515851494756372606L;

	private String signature;
	private String jsonpCallback;

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getJsonpCallback() {
		return jsonpCallback;
	}

	public void setJsonpCallback(String jsonpCallback) {
		this.jsonpCallback = jsonpCallback;
	}

}
