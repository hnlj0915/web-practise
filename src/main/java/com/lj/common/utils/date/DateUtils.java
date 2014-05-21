package com.lj.common.utils.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class DateUtils {
	/**
	 * @param o
	 * @return
	 * @throws ParseException
	 */
	public static Date toDate(Object o) throws ParseException {
		if (o == null)
			return null;
		if (o instanceof Date)
			return (Date) o;
		String dateStr = o.toString();
		if (StringUtils.isBlank(dateStr))
			return null;// yyyyMMdd
		String dateFormat = "yyyy-MM-dd";
		if (dateStr.length() == 8)
			dateFormat = "yyyyMMdd";
		if (dateStr.length() == 10)
			dateFormat = "yyyy-MM-dd";
		if (dateStr.length() == 19 && dateStr.indexOf("T") < 0)
			dateFormat = "yyyy-MM-dd HH:mm:ss";
		if (dateStr.length() == 23)
			dateFormat = "yyyy-MM-dd HH:mm:ss.SSS";
		if (dateStr.indexOf("T") >= 0)
			dateFormat = "yyyy-MM-dd'T'hh:mm:ss";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
		return simpleDateFormat.parse(dateStr);
	}
}
