package com.project.api.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project.common.dto.MemberCache;
import com.project.common.util.ObjectMapperSingle;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.common.common.Constants;
import com.project.api.common.MediaTypes;
import com.project.api.common.enums.BusinessTypeEnum;
import com.project.api.response.Response;
import com.project.api.response.ResponseFactory;
import com.project.api.service.AuthorizeService;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


public class AuthorizationInterceptor extends HandlerInterceptorAdapter {
	private static Logger logger = Logger.getLogger(AuthorizationInterceptor.class);

	@Autowired
	private AuthorizeService authService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		//printRequest(request);
        String userId = request.getAttribute("userId") == null ? null : request.getAttribute("userId").toString();
        String ticket = request.getAttribute("ticket") == null ? null : request.getAttribute("ticket").toString();
		ObjectMapper mapper = ObjectMapperSingle.getInstance();
		//验证ticket
		if( StringUtils.isEmpty(userId) || StringUtils.isEmpty(ticket) || !authService.checkUserTicket(userId, ticket)){
			//用户登录无效
			logger.info(Constants.LOG_PREFIX+"用户ticket无效 :" + userId + ":" + ticket);
			response.setContentType(MediaTypes.JSON_UTF_8);
			Response res = ResponseFactory.createResponse(BusinessTypeEnum.USER_NO_LOGIN.getCode(),BusinessTypeEnum.USER_NO_LOGIN.getDescription());
			response.getOutputStream().write(mapper.writeValueAsString(res).getBytes());
			return false;
		}
		MemberCache memberCache = (MemberCache) request.getAttribute("memberCache");
		if(!(userId + ":" + ticket).equals(memberCache.getToken())){
			Response res = ResponseFactory.createResponse(BusinessTypeEnum.USER_LOGIN_ANOTHER.getCode(),BusinessTypeEnum.USER_LOGIN_ANOTHER.getDescription());
			response.getOutputStream().write(mapper.writeValueAsString(res).getBytes());
			return false;
		}
        return true;
	}


}
