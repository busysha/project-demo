package com.project.api.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.common.common.Constants;
import com.project.api.common.enums.BusinessTypeEnum;
import com.project.common.dto.MemberCache;
import com.project.api.response.Response;
import com.project.api.response.ResponseFactory;
import com.project.api.service.MemberAccountHistService;
import com.project.api.service.RedisService;
import com.project.common.util.ObjectMapperSingle;
import com.project.api.util.RequestUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class BaseInterceptor extends HandlerInterceptorAdapter {

	private static final Logger  logger = Logger.getLogger(BaseInterceptor.class);

	@Autowired
	private RedisService redisService;

	@Autowired
	private MemberAccountHistService memberAccountHistService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		String token = RequestUtil.getHeaderValue(Constants.RequestHeader.KEY_TOKEN, request);

		if(StringUtils.isNotBlank(token)){
			String[] splitStrings = token.split(":");

			String userId = splitStrings.length > 0 ? splitStrings[0] : null ;
			String ticket = splitStrings.length > 1 ? splitStrings[1] : null ;

			if(StringUtils.isNotBlank(userId)){
				MemberCache memberCache = redisService.getMemberCache(Constants.RedisKeys.MEMBER_INFO_PREFIX + userId);
				ObjectMapper mapper = ObjectMapperSingle.getInstance();
				if(null == memberCache || Constants.Status.STATUS_INACTIVE.equals(memberCache.getStatus())){
					Response res = ResponseFactory.createResponse(BusinessTypeEnum.USER_NOT_EXISTS.getCode(),BusinessTypeEnum.USER_NOT_EXISTS.getDescription());
					response.getOutputStream().write(mapper.writeValueAsString(res).getBytes());
					return false;
				}
				request.setAttribute("userId",userId);
				request.setAttribute("memberCache",memberCache);
				request.setAttribute("ticket",ticket);
				//登录获取热力值
				//1.redis 查询是否获取过了
				//2.redis 热力值增加
				//3.redis exp 增加
				//4.持久化数据入库
				memberAccountHistService.insertMemberAccountAndRedis(Integer.parseInt(userId),Constants.GoodsAction.DAY_LOGIN);
			}
		}
		return super.preHandle(request, response, handler);
	}
}
