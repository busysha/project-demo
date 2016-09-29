package com.project.api.aspect.performance;

import org.apache.log4j.Logger;

public class PerformanceLogger {

	private static Logger logger = Logger.getLogger(PerformanceLogger.class);
	public static final String ACT_TYPE_ACTION = "ACTION";

	public static void logActivity(String activityName, String activityType, long cost) {
		StringBuilder sb = new StringBuilder();
		sb.append("ActName:").append(activityName);
		sb.append(",ActType:").append(activityType);
		sb.append(",Thread:").append(Thread.currentThread().getId());
		sb.append(",Cost:").append(cost).append("ms");
		logger.info(sb.toString());
	}

}
