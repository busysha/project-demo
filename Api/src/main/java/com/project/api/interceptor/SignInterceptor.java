package com.project.api.interceptor;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.common.common.Constants;
import com.project.api.common.MediaTypes;
import com.project.api.common.enums.BusinessTypeEnum;
import com.project.api.response.Response;
import com.project.api.response.ResponseFactory;
import com.project.api.service.SignatureService;
import com.project.api.util.RequestUtil;


class SignInterceptor extends BaseInterceptor{

	@Autowired
	private SignatureService signatureService;
	
	private static final Logger logger = Logger.getLogger(SignInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		RequestUtil.printRequest(request);
		
		long start = System.currentTimeMillis();
		String sign = RequestUtil.getHeaderValue(Constants.Sign.SIGN_PARAM_SIGN, request);
		
		Map<String,String[]> parameters = request.getParameterMap();
		Set<Entry<String, String[]>> entries =  parameters.entrySet();
		Map<String,String> params = new HashMap<String,String>();
		for (Entry<String, String[]> entry : entries) {
			params.put(entry.getKey(), entry.getValue()[0]);
		}
		if(!signatureService.isPassSignature(sign, params)){
			logger.info(" sign is not pass param sign : " + sign);
			response.setContentType(MediaTypes.JSON_UTF_8);
			
			Response res = ResponseFactory.createResponse(BusinessTypeEnum.SIGN_ERROR.getCode(),BusinessTypeEnum.SIGN_ERROR.getDescription());
			ObjectMapper mapper = new ObjectMapper();
			response.getOutputStream().write(mapper.writeValueAsString(res).getBytes());
			logger.info(" sign cost fail time " + (System.currentTimeMillis() - start));
			return false;
		}
		logger.info(" sign cost pass time " + (System.currentTimeMillis() - start));
		return super.preHandle(request,response,handler);
	}

}
