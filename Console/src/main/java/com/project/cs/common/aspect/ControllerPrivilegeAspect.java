package com.project.cs.common.aspect;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.project.common.util.MessageUtil;
import com.project.cs.common.Constants;
import com.project.cs.common.SessionInfo;
import com.project.cs.common.annotations.CheckPrivilege;

@Component
@Aspect
public class ControllerPrivilegeAspect {

	private final static Logger logger = Logger.getLogger(ControllerPrivilegeAspect.class);
	
	@Pointcut("execution(* com.project.cs.controller..*(..))")
	public void aspect(){	
		
		logger.info("aspect");
	}
	
	@Before("aspect()")
	public void before(JoinPoint joinPoint){
		logger.info("before");
	}
	
	@After("aspect()")
	public void after(JoinPoint joinPoint){
		logger.info("after");
	}
	
	@Around("aspect()")
	public Object around(ProceedingJoinPoint pjp)throws Throwable{
		logger.info("around");
		MethodSignature methodSignature = (MethodSignature)pjp.getSignature();
		
		Class<?>[] parameterTypes = methodSignature.getMethod().getParameterTypes();
		
		Method method = pjp.getTarget().getClass().getMethod(pjp.getSignature().getName(), parameterTypes);
		if(method.isAnnotationPresent(CheckPrivilege.class)){
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			if(!checkPrivilege(request)){
				ModelAndView mav = new ModelAndView();
				CheckPrivilege an= method.getAnnotation(CheckPrivilege.class);
				SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(Constants.Session.KEY_SESSION_INFO);
				if (sessionInfo == null) {
					request.setAttribute(Constants.Request.KEY_INFO_MESSAGE, MessageUtil.buildMessage(Constants.Message.SYSTEM_DOUBLE_SUBMIT));
					return mav;
				}
				if(!sessionInfo.getPrivilege().contains(an.privilege().getId())){
					mav.setViewName("/no_privilege");
					return mav;
				}
			}
			
		}

		
		return pjp.proceed();
		
	}
	
	
	/**
	 * submit check token
	 * @param token
	 * @return
	 */
	protected boolean checkPrivilege(HttpServletRequest request){
		return false;
	}
	
	@AfterReturning("aspect()")
	public void afterReturn(JoinPoint joinPoint){
		logger.info("afterReturn");
	}
	
	@AfterThrowing(pointcut="aspect()", throwing="ex")
	public void afterThrow(JoinPoint joinPoint, Exception ex){
		logger.info("afterThrow");
	}
}
