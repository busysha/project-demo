package com.project.cs.common.aspect;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.project.common.util.MessageUtil;
import com.project.cs.common.Constants;
import com.project.cs.common.annotations.CheckToken;
import com.project.cs.service.RedisService;

@Component
@Aspect
public class ControllerTokenAspect {

	private final static Logger logger = Logger.getLogger(ControllerTokenAspect.class);
	@Autowired
	private RedisService redisService;
	
	
	@Pointcut("execution(* com.project.cs.controller..*(..))")
	public void aspect(){	}
	
	@Before("aspect()")
	public void before(JoinPoint joinPoint){
	}
	
	@After("aspect()")
	public void after(JoinPoint joinPoint){
	}
	
	@Around("aspect()")
	public Object around(ProceedingJoinPoint pjp)throws Throwable{
		MethodSignature methodSignature = (MethodSignature)pjp.getSignature();
		
		Class<?>[] parameterTypes = methodSignature.getMethod().getParameterTypes();
		
		Method method = pjp.getTarget().getClass().getMethod(pjp.getSignature().getName(), parameterTypes);
		if(method.isAnnotationPresent(CheckToken.class)){
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			String token=request.getParameter("token");
			
			if(!checkToken(token)){
				ModelAndView mav = new ModelAndView();
				CheckToken checkToken= method.getAnnotation(CheckToken.class);
				mav.setViewName(checkToken.viewName());
				request.setAttribute(Constants.Request.KEY_INFO_MESSAGE, MessageUtil.buildMessage(Constants.Message.SYSTEM_DOUBLE_SUBMIT));
				return mav;
			}
			
			
		}

		
		return pjp.proceed();
		
	}
	
	
	/**
	 * submit check token
	 * @param token
	 * @return
	 */
	protected boolean checkToken(String token){
		if(StringUtils.isEmpty(token)){
			return false;
		}
		Object tk=redisService.get(token);
		if(tk!=null){
			redisService.delete(token);
			return true;
		}
		return false;
	}
	
	@AfterReturning("aspect()")
	public void afterReturn(JoinPoint joinPoint){
	}
	
	@AfterThrowing(pointcut="aspect()", throwing="ex")
	public void afterThrow(JoinPoint joinPoint, Exception ex){
	}
}
