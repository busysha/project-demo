package com.project.common.mapper;

import com.project.common.entity.mybatis.LevelExpMap;

public interface LevelExpMapMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LevelExpMap record);

    int insertSelective(LevelExpMap record);

    LevelExpMap selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LevelExpMap record);

    int updateByPrimaryKey(LevelExpMap record);

    LevelExpMap selectLvInfoByLv(int lv);
}