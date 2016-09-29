package com.project.cs.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.project.cs.common.Constants;
import com.project.cs.common.SessionInfo;

@Repository
public class CheckUserSessionInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(Constants.Session.KEY_SESSION_INFO);
		if (sessionInfo == null || sessionInfo.getUser() == null) {
			response.sendRedirect(request.getContextPath() + "/auth/login");
			return false;
		}
		return true;
	}

}
