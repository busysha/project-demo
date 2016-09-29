package com.project.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.project.common.entity.mybatis.CSUser;

public interface CSUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CSUser record);

    int insertSelective(CSUser record);

    CSUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CSUser record);

    int updateByPrimaryKey(CSUser record);
    
    CSUser selectByLoginName(@Param("loginName") String loginName);
    
    List<CSUser> selectBySerach(@Param("loginName") String loginName,@Param("status") String status,@Param("phone") String phone,@Param("pageBegin") int pageBegin,@Param("pageSize") int pageSize);
    
    long countBySerach(@Param("loginName") String loginName,@Param("status") String status,@Param("phone") String phone);
}