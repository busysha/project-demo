package com.project.api.service;

import com.project.common.dto.MemberCache;

public interface ConfigService {
	
	String getConfigVal(String key);

	int getIntegerVal(String key,  int defaultVal);

	String getAuthUrl(MemberCache memberCache);
}
