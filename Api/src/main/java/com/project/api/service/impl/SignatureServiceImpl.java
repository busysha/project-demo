package com.project.api.service.impl;

import com.project.common.common.Constants;
import com.project.api.service.ConfigService;
import com.project.api.service.SignatureService;
import com.project.api.util.PropertiesUtil;
import com.project.api.util.SignatureUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SignatureServiceImpl implements SignatureService {
	private static Logger logger = Logger.getLogger(SignatureServiceImpl.class);

	@Autowired
	private ConfigService configService;

	@Override
	public boolean isPassSignature(String sign, Map<String, String> params) {
		if(Boolean.parseBoolean(PropertiesUtil.getValue(Constants.Sign.SKIP_VALIDATE_SIGN))){
			return true;
		}
		try{
			if(StringUtils.isBlank(sign)){
				logger.info(" sign param is not valid ");
				return false;
			}
			
			if(StringUtils.isBlank(params.get(Constants.Sign.SIGN_PARAM_TIMESTAMP))){
				logger.info(" sign timestamp is not valid ");
				return false;
			}
			
			long requestTimestamp = Long.parseLong(params.get(Constants.Sign.SIGN_PARAM_TIMESTAMP));
			if(!SignatureUtil.checkRequestTimeout(requestTimestamp)){
				logger.info(" sign time is not valid ");
				return false;
			}
			
			String appId = configService.getConfigVal(Constants.ConfigKeys.APP_ID);
			if(StringUtils.isBlank(appId)){
				logger.info(" sign appId is not valid ");
				return false;
			}

			
			String signature = SignatureUtil.generateSignature(SignatureUtil.buildKVString(params), configService.getConfigVal(Constants.ConfigKeys.APP_KEY));
			logger.info("sign : " + signature);
			if(signature.equals(sign)){
				return true;
			}
			
		}catch(Exception e){
			logger.error(" sign error " , e);
		}
		return false;
	}

}
