package com.project.cs.exception;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerExceptionResolverComposite;

public class GlobalExceptionHandler extends HandlerExceptionResolverComposite {

	private static Logger logger = Logger.getLogger(GlobalExceptionHandler.class);
	
	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {

		logger.error("System error found: "+ex.getMessage(), ex);
		
		ModelAndView mav=new ModelAndView("500");
		
		return mav;
	}

	
	
}
