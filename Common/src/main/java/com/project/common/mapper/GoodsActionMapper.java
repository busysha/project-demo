package com.project.common.mapper;

import com.project.common.entity.mybatis.GoodsAction;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodsActionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GoodsAction record);

    int insertSelective(GoodsAction record);

    GoodsAction selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GoodsAction record);

    int updateByPrimaryKey(GoodsAction record);

    List<GoodsAction> getActionByActionType(@Param("actionType") String actionType,@Param("status") String status);
}