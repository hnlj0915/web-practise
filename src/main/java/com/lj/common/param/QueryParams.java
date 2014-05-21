package com.lj.common.param;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.lj.common.validate.group.CheckQueryParamGroup;

/**
 * hibernate 对象属性校验
 * 
 * @author lscm
 * 
 */
public class QueryParams {

	// private Loggers logger = new Loggers(QueryParams.class.getName());

	@NotBlank(groups = CheckQueryParamGroup.class, message = "QUERY_PARAM_ERROR")
	@Length(max = 255, groups = CheckQueryParamGroup.class, message = "QUERY_PARAM_ERROR")
	@Pattern(regexp = ".*_.*", groups = CheckQueryParamGroup.class, message = "QUERY_PARAM_ERROR")
	private String queryParam;

	private String jsonpCallback;

	public String getQueryParam() {
		return queryParam;
	}

	public void setQueryParam(String queryParam) {
		this.queryParam = queryParam;
	}

	public String getJsonpCallback() {
		return jsonpCallback;
	}

	public void setJsonpCallback(String jsonpCallback) {
		this.jsonpCallback = jsonpCallback;
	}

}
