package com.project.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.project.common.entity.mybatis.Region;

public interface RegionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Region record);

    int insertSelective(Region record);

    Region selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Region record);

    int updateByPrimaryKey(Region record);

    List<Region> selectRegionSortByFirstLetter();

    List<Region> selectRegionByName(@Param("cityName") String cityName);
    
    List<Region> selectCityByProvinceId(@Param("provinceId") Integer provinceId);
}