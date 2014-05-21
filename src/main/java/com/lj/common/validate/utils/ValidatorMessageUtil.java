package com.lj.common.validate.utils;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.lj.common.message.Msg;
import com.lj.enums.MessageEnum;

public class ValidatorMessageUtil {

	/**
	 * @param validator
	 *            验证器
	 * @param obj
	 *            验证对象
	 * @param groupClazzes
	 *            验证组
	 * @return
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 * 
	 *             根据group验证对象
	 */
	public static synchronized <T> Msg getValidateErrorMessage(Validator validator, T obj, Class<?>[] groupClazzes) throws IllegalArgumentException,
			SecurityException, IllegalAccessException, NoSuchFieldException {
		return getValidateErrorMessage(validator, obj, null, groupClazzes);
	}

	/**
	 * @param validator
	 *            验证器
	 * @param obj
	 *            验证对象
	 * @param groupClazzes
	 *            验证组
	 * @param porp
	 *            验证指定的参数
	 * @return
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 * 
	 *             根据group验证特定属性
	 */
	public static synchronized <T> Msg getValidateErrorMessage(Validator validator, T obj, String porp, Class<?>[] groupClazzes)
			throws IllegalArgumentException, SecurityException, IllegalAccessException, NoSuchFieldException {
		Set<ConstraintViolation<T>> sets = null;
		if (null != porp) {
			sets = validator.validateProperty(obj, porp, groupClazzes);
		} else {
			sets = validator.validate(obj, groupClazzes);
		}
		if (!sets.isEmpty()) {
			ConstraintViolation<T> constraintViolation = sets.iterator().next();
			String message = constraintViolation.getMessage();
			MessageEnum error = (MessageEnum) MessageEnum.class.getField(message).get(null);
			return getErrorMessage(error);
		}
		return null;
	}

	private static Msg getErrorMessage(MessageEnum error) {
		return new Msg(error.getErrorCode(), error.getErrorCode() == 100, error.getErrorMessage());
	}
}
