package com.project.cs.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.common.common.Constants;
import com.project.common.entity.mybatis.Config;
import com.project.common.mapper.ConfigMapper;
import com.project.cs.service.ConfigService;
import com.project.cs.service.RedisService;

/**
 * Created by dell on 2016/9/26.
 */
@Service
public class ConfigServiceImpl implements ConfigService {
	private static Logger logger = Logger.getLogger(ConfigServiceImpl.class);
    @Autowired
    private RedisService redisService;

    @Autowired
    private ConfigMapper configMapper;


    @Override
    public List<Config> selectAllConfig() {
        return configMapper.selectByIsCanChange();
    }

    @Override
    public void updateConfig(Config config) {
        configMapper.updateByPrimaryKeySelective(config);
        redisService.hashSetPut(Constants.ConfigKeys.APP_KEY,config.getKey(),config.getValue());
    }

    @Override
    public Config selectById(int id) {
        return configMapper.selectByPrimaryKey(id);
    }

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
}
