package com.project.common.mapper;

import org.apache.ibatis.annotations.Param;

import com.project.common.entity.mybatis.AppUpdate;

public interface AppUpdateMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AppUpdate record);

    int insertSelective(AppUpdate record);

    AppUpdate selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AppUpdate record);

    int updateByPrimaryKeyWithBLOBs(AppUpdate record);

    int updateByPrimaryKey(AppUpdate record);

    AppUpdate selectUpdateInfoByPlatform(@Param("platform") String platform);
}