package com.lj.common.utils.object;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import com.lj.common.utils.date.DateUtils;
import com.lj.log.Loggers;

public class ObjUtils {

	private static Loggers logger = new Loggers(ObjUtils.class.getName());

	public ObjUtils() {
		logger = new Loggers(this.getClass().getName());
	}

	/**
	 * @description 判断 对象 o 是否是 cls 类型
	 * @param o
	 * @param cls
	 * @return
	 */
	public static boolean isClass(Object o, Class<?> cls) {
		return isSuperClass(o.getClass(), cls);
	}

	public static boolean isSuperClass(Class<?> cls, Class<?> superCls) {
		if (superCls.equals(Object.class) || cls == superCls)
			return true;
		if (superCls.isInterface()) {
			Class<?>[] clss = cls.getInterfaces();
			for (Class<?> c : clss)
				if (c.equals(superCls))
					return true;
		}
		Class<?> pcls = cls.getSuperclass();
		while (!pcls.equals(Object.class)) {
			if (pcls.equals(superCls))
				return true;
			pcls = pcls.getSuperclass();
		}
		return false;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void setValue(Object o, String name, Object value) {
		if (value == null)
			return;
		if (o instanceof Map) {
			((Map) o).put(name, value);
			return;
		}
		if (haveReadMethod(o.getClass(), name)) {
			Method method = writeMethod(o.getClass(), name);
			try {
				Class<?> type = method.getParameterTypes()[0];
				if (isClass(value, type)) {
					method.invoke(o, value);
				} else {
					setValue(method, o, value, type);
				}
			} catch (IllegalArgumentException e) {
				throw new RuntimeException(e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			} catch (InvocationTargetException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public static boolean haveReadMethod(Class<?> cls, String name) {
		Method[] methods = cls.getDeclaredMethods();
		for (Method method : methods) {
			if (method.getName().equalsIgnoreCase("get" + name) || method.getName().equalsIgnoreCase("is" + name)) {
				return true;
			}
		}
		return false;
	}

	public static Method writeMethod(Class<?> cls, String prop) throws RuntimeException {
		try {
			PropertyDescriptor propertyDescriptor = new PropertyDescriptor(prop, cls);
			return propertyDescriptor.getWriteMethod();
		} catch (IntrospectionException e) {
			throw new RuntimeException(e);
		}
	}

	private static Class<?> getGenericClass(Class<?> clazz, int index) {
		Type genType = clazz.getGenericSuperclass();
		if (genType instanceof ParameterizedType) {
			Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
			if ((params != null) && (params.length >= (index - 1))) {
				return (Class<?>) params[index];
			}
		}// end if.
		return null;
	}

	public static Class<?> getEntityClass(Class<?> clazz) {
		return getGenericClass(clazz, 0);
	}

	/**
	 * 获取对象实例
	 * 
	 * @return
	 */
	public static Object getEntityObject(Class<?> clazz) {
		Class<?> zz = getEntityClass(clazz);
		Object obj = null;
		try {
			obj = zz.newInstance();
		} catch (InstantiationException ie) {
			logger.logError("获取EntityObject时实例化claszz异常!");
		} catch (IllegalAccessException iae) {
			logger.logError("非法访问异常!");
		}
		return obj;
	}

	protected static void setValue(Method method, Object o, Object value, Class<?> paramType) {
		try {
			if (Long.class.equals(paramType) || long.class.equals(paramType)) {
				method.invoke(o, Long.valueOf(value.toString()));
			} else if (Integer.class.equals(paramType) || int.class.equals(paramType)) {
				method.invoke(o, Integer.valueOf(value.toString()));
			} else if (Float.class.equals(paramType) || float.class.equals(paramType)) {
				method.invoke(o, Float.valueOf(value.toString()));
			} else if (Date.class.equals(paramType) || Timestamp.class.equals(paramType) || Time.class.equals(paramType)) {
				method.invoke(o, DateUtils.toDate(value));
			} else if (Boolean.class.equals(paramType) || boolean.class.equals(paramType)) {
				Integer v = Integer.valueOf(value.toString());
				if (v == 1) {
					method.invoke(o, true);
				} else {
					method.invoke(o, false);
				}
			} else {
				method.invoke(o, value.toString());
			}
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
}
