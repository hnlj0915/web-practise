package com.lj.common.utils.object;

public class StrUtils {

	/**
	 * @description 首字母大写
	 * @param str
	 * @return
	 */
	public static String firstUpper(String str) {
		if (str.length() < 1)
			return str;
		String first = str.substring(0, 1);
		return upperCase(first) + str.substring(1, str.length());
	}

	/**
	 * @description 首字母小写
	 * @param str
	 * @return
	 */
	public static String firstLower(String str) {
		if (str.length() < 1)
			return str;
		String first = str.substring(0, 1);
		return lowerCase(first) + str.substring(1, str.length());
	}

	/**
	 * @description 全部转化为大写
	 * @param str
	 * @return
	 */
	public static String upperCase(String str) {
		if (str == null) {
			return null;
		}
		return str.toUpperCase();
	}

	/**
	 * @description 全部转化为小写
	 * @param str
	 * @return
	 */
	public static String lowerCase(String str) {
		if (str == null) {
			return null;
		}
		return str.toLowerCase();
	}
}
