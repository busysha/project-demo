package com.project.api.api;

import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;


public class BaseApi {
	
	public static final Logger logger = Logger.getLogger(BaseApi.class);

	protected boolean validateMandatoryMap(Map<String, String> params){
		boolean valid = true;
		Set<String> keys =  params.keySet();
		for (String string : keys) {
			if(StringUtils.isEmpty(params.get(string))){
				logger.info(string + " is invalid");
				valid = false;
				break;
			}
		}
		return valid;
	}
}
