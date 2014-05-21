package com.lj.common.utils.json;

import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;

/**
 * jsonpCallbak写出方法
 **/

public class JsonpCallbackView {
	public static ModelAndView Render(Object obj, String jsonpCallback, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			StringBuffer jsonp = new StringBuffer();
			if (StringUtils.isBlank(jsonpCallback)) {
				jsonp.append(JsonUtil.Object2JsonStr(obj));
				response.setContentType("application/json");
			} else {
				jsonp.append(jsonpCallback + "(" + JsonUtil.Object2JsonStr(obj) + ")");
				response.setContentType("application/javascript");
			}
			response.setCharacterEncoding("utf-8");
			Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
			Matcher m = p.matcher(jsonp.toString());
			StringBuffer res = new StringBuffer();
			while (m.find()) {
				m.appendReplacement(res, "\\" + toUnicode(m.group()));
			}
			m.appendTail(res);

			out = response.getWriter();
			out.write(res.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != out)
				out.close();
		}

		return null;
	}

	private static String toUnicode(String str) {
		char[] arChar = str.toCharArray();
		int iValue = 0;
		String uStr = "";
		for (int i = 0; i < arChar.length; i++) {
			iValue = (int) str.charAt(i);
			if (iValue <= 256) {
				uStr += "\\" + Integer.toHexString(iValue);
			} else {
				uStr += "\\u" + Integer.toHexString(iValue);
			}
		}
		return uStr;
	}
}
