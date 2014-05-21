package com.lj.common.base;

import java.lang.reflect.Field;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.lj.common.param.QueryParam;

/**
 * @Title: ParamValidator.java
 * @Package com.b5m.test.base
 * @Description: TODO(自定义spring validate)
 * @author jia.liu
 * @date 2014-5-16 下午2:10:57
 * @version V1.0
 */
@Component
public class ParamValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return QueryParam.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		QueryParam param = (QueryParam) target;
		Field[] fs = (Field[]) ArrayUtils.addAll(target.getClass().getDeclaredFields(), param.getClass().getSuperclass().getDeclaredFields());
		for (Field field : fs) {
			field.setAccessible(true);
			for (String str : param.getParams()) {
				if (field.getName().equals(str)) {
					ValidationUtils.rejectIfEmpty(errors, str, str + ".required.error");
				}
			}
		}
	}
}
