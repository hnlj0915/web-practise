package com.lj.enums;

/**
 * @Title: MessageEnum.java
 * @Package com.lj.enums
 * @Description: TODO(用一句话描述该文件做什么)
 * @author jia.liu
 * @date 2014-5-21 下午3:54:09
 * @version V1.0
 */
public enum MessageEnum {

	SUCESS(100, "SUCCESS"), QUERY_PARAM_ERROR(101, "QUERY_PARAM_ERROR");

	private Integer errorCode;

	private String errorMessage;

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	private MessageEnum(Integer errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
}
