package com.project.common.mapper;

import com.project.common.entity.mybatis.MemberExtra;

public interface MemberExtraMapper {
    int insert(MemberExtra record);

    int insertSelective(MemberExtra record);
}