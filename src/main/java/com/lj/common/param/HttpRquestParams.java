package com.lj.common.param;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class HttpRquestParams {

	public static <T> T requestParams(HttpServletRequest request, Class<T> type, String[] reqFields) {
		Map<String, Object> map = new HashMap<String, Object>();
		Enumeration<?> paraName = request.getParameterNames();
		while (paraName.hasMoreElements()) {
			String str = (String) paraName.nextElement();
			if (str != null) {
				if (reqFields != null) {
					for (int i = 0; i < reqFields.length; i++) {
						String field = reqFields[i];
						if (field != null && str.equals(field)) {
							String value = request.getParameter(str);

							map.put(field, value);
						}
					}
				}
			}
		}
		try {
			return convertMap(type, map);
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private static <T> T convertMap(Class<T> type, Map<String, Object> map) throws IntrospectionException, IllegalAccessException,
			InstantiationException, InvocationTargetException {
		BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性
		T obj = type.newInstance(); // 创建 JavaBean 对象

		// 给 JavaBean 对象的属性赋值
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();

			if (map.containsKey(propertyName)) {
				// 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
				Object value = map.get(propertyName);

				Object[] args = new Object[1];
				args[0] = value;

				descriptor.getWriteMethod().invoke(obj, args);
			}
		}
		return obj;
	}
}
