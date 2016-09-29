package com.project.api.util;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.project.common.dto.MemberCache;
import com.project.common.common.Constants;
import com.project.common.util.ToolUtil;

public class RequestUtil {
	
	private static final Logger logger = Logger.getLogger(RequestUtil.class);
	
	public static String getHeaderValue(String key, HttpServletRequest request){
		Enumeration<String> headerKeys = request.getHeaderNames();
		while (headerKeys.hasMoreElements()) {
			String hk = (String) headerKeys.nextElement();
			if(key.equals(hk)){
				return request.getHeader(hk);
			}
		}
		return "";
	}

	public static int getMemberIdFromHeader(HttpServletRequest request){
		String uid = request.getAttribute("userId") == null ? "0" : request.getAttribute("userId").toString();
		return StringUtils.isNotBlank(uid) ? ToolUtil.parseStringToInteger(uid) : 0;
	}

	public static MemberCache getMemberCacheFromRequest(HttpServletRequest request){
		return (MemberCache) request.getAttribute("memberCache");
	}

	public static String getTicketFromHeader(HttpServletRequest request){
		
		String token = getHeaderValue(Constants.RequestHeader.KEY_TOKEN, request);
		
		String[] splitStrings = token.split(":");
		
		return splitStrings.length>1 ? splitStrings[1] : null ;

	}
	
	
	public static void printRequest(HttpServletRequest request){
		Enumeration<String> headerKeys = request.getHeaderNames();
		Enumeration<String> paramKeys = request.getParameterNames();
		
		StringBuilder sb = new StringBuilder();
		sb.append("=====> Request URL: ").append(request.getRequestURI()).append("\n");
		sb.append("============================ Headers ============================ \n");
		while (headerKeys.hasMoreElements()) {
			String key = (String) headerKeys.nextElement();
			sb.append("[").append(key).append("] : ").append(request.getHeader(key)).append("\n");
		}
		sb.append("============================ Params ============================ \n");
		while (paramKeys.hasMoreElements()) {
			String key = (String) paramKeys.nextElement();
			sb.append("[").append(key).append("] : ").append(request.getParameter(key)).append("\n");
		}
		sb.append("<=====");
		logger.info(sb.toString());
		
	}
	
	public static String prettyRequestParams(HttpServletRequest request){
		Enumeration<String> headerKeys = request.getHeaderNames();
		Enumeration<String> paramKeys = request.getParameterNames();
		
		StringBuilder sb = new StringBuilder();
		sb.append("=====> Request URL: ").append(request.getRequestURI()).append("\n");
		sb.append("============================ Headers ============================ \n");
		while (headerKeys.hasMoreElements()) {
			String key = (String) headerKeys.nextElement();
			sb.append("[").append(key).append("] : ").append(request.getHeader(key)).append("\n");
		}
		sb.append("============================ Params ============================ \n");
		while (paramKeys.hasMoreElements()) {
			String key = (String) paramKeys.nextElement();
			sb.append("[").append(key).append("] : ").append(request.getParameter(key)).append("\n");
		}
		sb.append("<=====");
		return sb.toString();
		
	}


}
