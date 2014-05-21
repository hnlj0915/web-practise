package com.lj.common.param;

import com.lj.common.base.AbstractBaseParam;

/**
 * @Title: QueryParam.java
 * @Package com.b5m.test.model
 * @Description: TODO(spirng validate)
 * @author jia.liu
 * @date 2014-5-16 上午10:42:27
 * @version V1.0
 */
public class QueryParam extends AbstractBaseParam {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9006142264519659510L;

	private String userId;

	private String[] params;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String[] getParams() {
		return params;
	}

	public void setParams(String[] params) {
		this.params = params;
	}

}
