package com.project.api.common.exception;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.api.common.MediaTypes;
import com.project.api.common.enums.BusinessTypeEnum;
import com.project.api.response.Response;
import com.project.api.response.ResponseFactory;

public class ExceptionHandler  extends AbstractHandlerExceptionResolver{
	
	private static Logger logger = Logger.getLogger(ExceptionHandler.class);

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		logger.error("system error", ex);
		response.setContentType(MediaTypes.JSON_UTF_8);
		Response msg = ResponseFactory.createResponse(BusinessTypeEnum.SYSTEM_ERROR.getCode(),BusinessTypeEnum.SYSTEM_ERROR.getDescription());
		ObjectMapper mapper = new ObjectMapper();
		try {
			response.getOutputStream().write(mapper.writeValueAsString(msg).getBytes());
		} catch (JsonProcessingException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		}
		return new ModelAndView();
	}

}
