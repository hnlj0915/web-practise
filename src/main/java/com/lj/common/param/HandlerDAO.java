package com.lj.common.param;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.lj.common.utils.object.StrUtils;

/**
 * hql，sql的查询字段，返回成数组
 * 
 * @author jia.liu
 * 
 */
public class HandlerDAO {

	public static String[] parseHqlAliases(String hql) {
		if (hql.toLowerCase().indexOf("select") < 0) {
			return new String[0];
		}
		String[] selFields = getSelFields(hql);
		return selFields;
	}

	public static String[] parseSqlAliases(String sql) {
		if (sql.toLowerCase().indexOf("select") < 0) {
			return new String[0];
		}
		String[] selFields = getSelFields(sql);
		return dealWithSqlFields(selFields);
	}

	private static String[] dealWithSqlFields(String[] selFields) {
		for (int index = 0; index < selFields.length; index++) {
			String selField = selFields[index];
			if (selField.indexOf("_") > 0) {
				selFields[index] = consist(selField);
			}
		}
		return selFields;
	}

	private static String consist(String selField) {
		String[] ls = StringUtils.split(selField, "_");
		if (ls.length == 2) {
			return StrUtils.lowerCase(ls[0]) + StrUtils.firstUpper(ls[1]);
		}
		if (ls.length == 3) {
			return StrUtils.lowerCase(ls[0]) + StrUtils.firstUpper(ls[1]) + StrUtils.firstUpper(ls[2]);
		}
		if (ls.length == 4) {
			return StrUtils.lowerCase(ls[0]) + StrUtils.firstUpper(ls[1]) + StrUtils.firstUpper(ls[2]) + StrUtils.firstUpper(ls[3])
					+ StrUtils.firstUpper(ls[2]);
		}
		if (ls.length == 5) {
			return StrUtils.lowerCase(ls[0]) + StrUtils.firstUpper(ls[1]) + StrUtils.firstUpper(ls[2]) + StrUtils.firstUpper(ls[3])
					+ StrUtils.firstUpper(ls[4]);
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < ls.length; i++) {
			if (i == 0) {
				sb.append(StrUtils.lowerCase(ls[i]));
			} else {
				sb.append(StrUtils.firstUpper(ls[i]));
			}
		}
		return sb.toString();
	}

	private static String[] getSelFields(String ql) {
		String lowerql = ql.toLowerCase().trim();
		ql = ql.trim();
		int fromIndex = lowerql.indexOf("from");
		String fieldsStr = ql.substring("select".length(), fromIndex).trim();
		if (fieldsStr.indexOf(",") > 0) {
			String[] fieldArray = StringUtils.split(fieldsStr, ",");
			List<String> fieldList = new ArrayList<String>(fieldArray.length);
			for (String fieldStr : fieldArray) {
				fieldList.add(getSelField(fieldStr));
			}
			return removePrefix(fieldList.toArray(new String[] {}));
		}
		return removePrefix(new String[] { getSelField(fieldsStr) });
	}

	private static String[] removePrefix(String[] selFields) {
		for (int index = 0; index < selFields.length; index++) {
			String selField = selFields[index];
			if (selField.indexOf(".") > 0) {
				selFields[index] = StringUtils.split(selField, ".")[1];
			}
		}
		return selFields;
	}

	public static String getSelField(String fieldStr) {
		fieldStr = fieldStr.trim();
		if (fieldStr.indexOf(" ") > 0) {
			String fieldArray[] = StringUtils.split(fieldStr, " ");
			return fieldArray[fieldArray.length - 1].trim();
		}
		if (fieldStr.indexOf("as") > 0) {
			String fieldArray[] = StringUtils.split(fieldStr, "as");
			return fieldArray[1].trim();
		}
		return fieldStr;
	}

	public static String[] getTeAliases(String ql) {
		List<String> teList = null;
		String lowerql = ql.toLowerCase();
		int fromIndex = lowerql.indexOf("from") + 4;
		int whereIndex = lowerql.indexOf("where");
		whereIndex = whereIndex > 0 ? whereIndex : ql.length();
		String te = ql.substring(fromIndex, whereIndex).trim();
		if (te.indexOf(",") < 0 && te.toLowerCase().indexOf("as") < 0 && te.indexOf(" ") < 0)
			return new String[] { te };
		if (te.indexOf(",") > 0) {
			String tes[] = StringUtils.split(te, ",");
			teList = new ArrayList<String>(tes.length);
			for (String t : tes) {
				teList.add(getTeAlias(t));
			}
			return teList.toArray(new String[] {});
		}
		return new String[] { getTeAlias(te) };
	}

	private static String getTeAlias(String te) {
		te = te.trim();
		if (te.indexOf(" ") > 0) {
			String[] tearray = StringUtils.split(te, " ");
			return tearray[tearray.length - 1];
		}
		if (te.indexOf("as") > 0) {
			String[] tearray = StringUtils.split(te, "as");
			return tearray[tearray.length - 1].trim();
		}
		return te;
	}

}
