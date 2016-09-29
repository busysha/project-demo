package com.project.common.mapper;

import com.project.common.entity.mybatis.MemberAccount;
import org.apache.ibatis.annotations.Param;

public interface MemberAccountMapper {
    int deleteByPrimaryKey(int id);

    int insert(MemberAccount record);

    int insertSelective(MemberAccount record);

    MemberAccount selectByPrimaryKey(int id);

    int updateByPrimaryKeySelective(MemberAccount record);

    int updateByPrimaryKey(MemberAccount record);

    MemberAccount selectMemberAccount(int memberId);

    void updateByVersion(@Param("memberAccount") MemberAccount memberAccount);
}