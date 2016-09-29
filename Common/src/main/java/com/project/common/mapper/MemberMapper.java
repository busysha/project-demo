package com.project.common.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.project.common.entity.mybatis.Member;

public interface MemberMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Member record);

    int insertSelective(Member record);

    Member selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Member record);

    int updateByPrimaryKey(Member record);

    Member selectMemberByMobile(@Param("mobile") String mobile);

    void updateByVersion(@Param("member") Member member);

    Member selectMemberByOpenId(@Param("openId") String openId,@Param("channel") String channel);
    
    List<Member> selectMemberByParams(@Param("params") Map<String, Object> params, @Param("offset") int offset, @Param("limit") int limit);

    int countByParams(@Param("params") Map<String, Object> params);
    
    List<Member> selectMemberByType(@Param("type") String type);
    
    Member selectMemberByName(@Param("name") String name);
}