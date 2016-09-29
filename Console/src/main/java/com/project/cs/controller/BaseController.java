package com.project.cs.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.project.common.util.MessageUtil;
import com.project.cs.common.Constants;
import com.project.cs.common.SessionInfo;

public class BaseController implements Constants.Session {
	
	protected SessionInfo getSessionInfo(HttpServletRequest request) {
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(KEY_SESSION_INFO);

		if (sessionInfo == null) {
			sessionInfo = new SessionInfo();
		}

		request.getSession().setAttribute(KEY_SESSION_INFO, sessionInfo);
		return sessionInfo;
	}
	
	protected void setMessage(HttpServletRequest request , String messageKey){
		String message = MessageUtil.buildMessage(messageKey);
		request.setAttribute(Constants.Request.KEY_INFO_MESSAGE, message);
	}
	
	protected void setErrorMessage(HttpServletRequest request , String message){
		request.setAttribute(Constants.Request.KEY_INFO_MESSAGE, message);
	}
	protected void setCurrentMenu(HttpServletRequest request, int menuId,int parentMenuId){
		  getSessionInfo(request).setCurrentMenu(menuId);
		  getSessionInfo(request).setCurrentParentMenu(parentMenuId);
	}

	protected String getCurrentName(HttpServletRequest request){
		return getSessionInfo(request).getUser().getLoginName();
	}
	
	protected int getIntParam(HttpServletRequest request, String paramName, int defaultVal){
		try{
			if(StringUtils.isNotBlank(paramName))
				return Integer.valueOf(request.getParameter(paramName)).intValue();
		}catch(Exception e){
			
		}
		return defaultVal;
	}
}
