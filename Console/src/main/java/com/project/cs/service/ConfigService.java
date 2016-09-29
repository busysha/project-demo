package com.project.cs.service;

import com.project.common.entity.mybatis.Config;

import java.util.List;

/**
 * Created by dell on 2016/9/26.
 */
public interface ConfigService {
	
	String getConfigVal(String key);
	
	int getIntegerVal(String key,  int defaultVal);
	
    List<Config> selectAllConfig();

    void updateConfig(Config config);

    Config selectById(int id);
}
