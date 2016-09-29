package com.project.api.aspect.performance;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

public class PerformanceAcpect {
	
	private final static Logger logger = Logger.getLogger(PerformanceAcpect.class);
	
	public Object around(ProceedingJoinPoint pjp)throws Throwable{
		logger.info("PerformanceAcpect around " + pjp);
		MethodSignature methodSignature = (MethodSignature)pjp.getSignature();
		Class<?>[] parameterTypes = methodSignature.getMethod().getParameterTypes();
		Method method = pjp.getTarget().getClass().getMethod(pjp.getSignature().getName(), parameterTypes);
		if(method.isAnnotationPresent(SupportPerformanceLog.class)){
			StringBuilder activityName = new StringBuilder();
			activityName.append(pjp.getTarget().getClass().getName());
			activityName.append("[").append(method.getName()).append("]");
			long start=System.currentTimeMillis();
			Object val = pjp.proceed();
			long end = System.currentTimeMillis();
			PerformanceLogger.logActivity(activityName.toString(), PerformanceLogger.ACT_TYPE_ACTION, (end-start));
			return val;
		}else{
			return pjp.proceed();
		}
	}
	
}
