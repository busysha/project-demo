package com.project.common.mapper;

import com.project.common.entity.mybatis.SmsCode;

public interface SmsCodeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SmsCode record);

    int insertSelective(SmsCode record);

    SmsCode selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SmsCode record);

    int updateByPrimaryKey(SmsCode record);
}