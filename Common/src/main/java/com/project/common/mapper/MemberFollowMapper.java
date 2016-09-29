package com.project.common.mapper;

import com.project.common.entity.mybatis.MemberFollow;

public interface MemberFollowMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MemberFollow record);

    int insertSelective(MemberFollow record);

    MemberFollow selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MemberFollow record);

    int updateByPrimaryKey(MemberFollow record);
}