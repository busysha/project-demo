package com.project.common.mapper;

import com.project.common.entity.mybatis.MemberAccountHist;

public interface MemberAccountHistMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MemberAccountHist record);

    int insertSelective(MemberAccountHist record);

    MemberAccountHist selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MemberAccountHist record);

    int updateByPrimaryKey(MemberAccountHist record);
}