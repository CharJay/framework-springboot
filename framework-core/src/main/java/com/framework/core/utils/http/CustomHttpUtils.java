package com.framework.core.utils.http;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

public class CustomHttpUtils {

	public static String urlEncode(String value, String encoding) {
		if (value == null) {
			return "";
		}

		try {
			return URLEncoder.encode(value, encoding);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException("Failed to encode url", e);
		}
	}

	public static String urlDecode(String value, String encoding) {
		if (StringUtils.isEmpty(value)) {
			return value;
		}

		try {
			return URLDecoder.decode(value, encoding);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException("Failed to decode url", e);
		}
	}

	public static String paramToQueryString(Map<String, String> params, String charset) {

		if (params == null || params.isEmpty()) {
			return null;
		}

		StringBuilder paramString = new StringBuilder();
		boolean first = true;
		for (Entry<String, String> p : params.entrySet()) {
			String key = p.getKey();
			String value = p.getValue();

			if (!first) {
				paramString.append("&");
			}

			paramString.append(urlEncode(key, charset)).append("=");
			if (value != null) {
				paramString.append(urlEncode(value, charset));
			}

			first = false;
		}

		return paramString.toString();
	}
	
	public static Map<String, String> parseUrlParams(String url){
		Map<String, String> params = new HashMap<>();
		int idx = url.indexOf('?');
		if(idx != -1 && idx < url.length()-1){
			int idx2 = url.lastIndexOf('#');
			String ps = "";
			if(idx2 == -1)
				ps = url.substring(idx+1);
			else 
				ps = url.substring(idx+1, idx2);
			String[] sps = StringUtils.split(ps, "&");
			for(String sp : sps){
				String[] ss = StringUtils.split(sp, "=", 2);
				params.put(ss[0], ss.length>=2 ? ss[1] : "");
			}
		}
		return params;
	}

	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Real-IP");
		if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}
		ip = request.getHeader("X-Forwarded-For");
		if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个IP值，第一个为真实IP。
			int index = ip.indexOf(',');
			if (index != -1) {
				ip = ip.substring(0, index);
			}
		} else {
			ip = request.getRemoteAddr();
		}
		if ("0:0:0:0:0:0:0:1".equals(ip)) {
			ip = "127.0.0.1";
		}
		return ip;
	}
	
	public static String printRequest(HttpServletRequest request) {
		StringBuilder sb = new StringBuilder(64);
		sb.append("URI > ").append(request.getMethod()).append(" ").append(request.getRequestURI()).append("\n");
		sb.append("remote addr > ").append(request.getRemoteAddr()).append("\n");
		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String hname = headerNames.nextElement();
			sb.append("header > ").append(hname).append(":").append(request.getHeader(hname)).append("\n");
		}
		Enumeration<String> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String pname = parameterNames.nextElement();
			sb.append("param > ").append(pname).append("=").append(Arrays.toString(request.getParameterValues(pname))).append("\n");
		}
		return sb.toString();
	}

}
