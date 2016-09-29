package com.project.api.service.impl;


import com.project.common.dto.MemberCache;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.common.common.Constants;
import com.project.api.service.ConfigService;
import com.project.api.service.RedisService;
import com.project.common.entity.mybatis.Config;
import com.project.common.mapper.ConfigMapper;

@Service
public class ConfigServiceImpl implements ConfigService {
    private static Logger logger = Logger.getLogger(ConfigServiceImpl.class);

    @Autowired
    private RedisService redisService;

    @Autowired
    private ConfigMapper configMapper;


    @Override
    public String getConfigVal(String key) {
        logger.debug("get config  key: " + key);

        //1. get config from redis

        String values = redisService.hashSetGet(Constants.ConfigKeys.APP_KEY,key);

        if(StringUtils.isNotBlank(values)){
            return values;
        }

        Config config = configMapper.selectConfigValueByKey(key);
        if(config==null) return Constants.Status.BLANK;
        redisService.hashSetPut(Constants.ConfigKeys.APP_KEY,key,config.getValue());

        return config.getValue();
    }


	@Override
	public int getIntegerVal(String key, int defaultVal) {
		String val = getConfigVal(key);
		try{
			return Integer.parseInt(val);
		}catch(Exception e){
			return defaultVal;
		}
	}

    @Override
    public String getAuthUrl(MemberCache memberCache) {
        if(memberCache == null)return Constants.Status.BLANK;
        return getConfigVal(Constants.AuthUrl.getAuthUrl(memberCache.getMemberType()));
    }
}