package com.project.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.project.common.annotation.ConsoleUse;
import com.project.common.entity.mybatis.Config;

public interface ConfigMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Config record);

    int insertSelective(Config record);

    Config selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Config record);

    int updateByPrimaryKey(Config record);

    Config selectConfigValueByKey(@Param("key") String key);

    @ConsoleUse
    List<Config> selectByIsCanChange();
}