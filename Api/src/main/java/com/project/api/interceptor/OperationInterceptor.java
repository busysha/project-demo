package com.project.api.interceptor;

import java.util.Enumeration;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


public class OperationInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = Logger.getLogger(OperationInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String requestUrl = request.getRequestURI().substring(request.getContextPath().length());
		String requestIp = getOutAddress(request);
		logger.info("--- request api ip : " + requestIp + " --- request api url : " + requestUrl);
		Map<String, String[]> paramters = request.getParameterMap();
		Set<Entry<String, String[]>> entrys = paramters.entrySet();

		Enumeration<String> headernames = request.getHeaderNames();
		while (headernames.hasMoreElements()) {
			String headerKey = (String) headernames.nextElement();
			String headerValue = request.getHeader(headerKey);
			logger.info(" --- request api header key : " + headerKey);
			logger.info(" --- request api header value : " + headerValue);
		}
		for (Entry<String, String[]> entry : entrys) {
			logger.info(" --- request api param key : " + entry.getKey());
			logger.info(" --- request api param value : " + entry.getValue()[0]);
		}

		return super.preHandle(request, response, handler);
	}

	private String getOutAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	
}
