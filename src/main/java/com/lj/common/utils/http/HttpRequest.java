package com.lj.common.utils.http;

import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;

public class HttpRequest {

	public static Logger logger = Logger.getLogger(HttpRequest.class);

	public static String postRequest(String url, String data) {
		return postRequest(url, data, "application/json");
	}

	public static String postRequest(String url, String data, String encode) {
		String result = null;
		HttpClient httpClient = HttpClientFactory.getHttpClient();
		PostMethod method = new PostMethod(url);
		logger.info("----" + url + "---" + data);
		try {
			method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
			method.setRequestEntity(new StringRequestEntity(data, encode, "UTF-8"));
			int statusCode = httpClient.executeMethod(method);
			if (statusCode == HttpStatus.SC_OK) {
				result = method.getResponseBodyAsString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String getRequest(String url, Map<String, String> params) {
		String response = null;
		HttpClient httpClient = HttpClientFactory.getHttpClient();

		// append parameters
		StringBuilder sb = new StringBuilder(url);
		if (params != null) {
			int i = 0;
			for (Map.Entry<String, String> entry : params.entrySet()) {
				if (i == 0) {
					sb.append("?").append(entry.getKey()).append("=").append(entry.getValue());
				} else {
					sb.append("&").append(entry.getKey()).append("=").append(entry.getValue());
				}
				i++;
			}
		}

		GetMethod method = new GetMethod(sb.toString());
		try {
			method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");

			int statusCode = httpClient.executeMethod(method);
			if (statusCode == HttpStatus.SC_OK) {
				response = method.getResponseBodyAsString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
}
