package com.project.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.project.common.entity.mybatis.CSRole;

public interface CSRoleMapper {
    int deleteByPrimaryKey(Integer roleId);

    int insert(CSRole record);

    int insertSelective(CSRole record);

    CSRole selectByPrimaryKey(Integer roleId);

    int updateByPrimaryKeySelective(CSRole record);

    int updateByPrimaryKey(CSRole record);
    
    List<CSRole> selectBySearch(@Param("name")String name,@Param("pageBegin")int pageBegin,@Param("pageSize")int pageSize);
    
    long countBySearch(@Param("name") String name);
    
    
    List<CSRole> getAllRoles();
    
}