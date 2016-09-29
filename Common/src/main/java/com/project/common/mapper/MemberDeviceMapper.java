package com.project.common.mapper;

import com.project.common.entity.mybatis.MemberDevice;

public interface MemberDeviceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MemberDevice record);

    int insertSelective(MemberDevice record);

    MemberDevice selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MemberDevice record);

    int updateByPrimaryKey(MemberDevice record);

    MemberDevice selectByMemberId(int memberId);
}