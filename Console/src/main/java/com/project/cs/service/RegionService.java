package com.project.cs.service;

import java.util.List;

import com.project.common.entity.mybatis.Region;

/** 
* 类说明 
* @author chitu
* @version 创建时间：2016年9月21日 下午1:42:49 
*/
public interface RegionService {
	List<Region> getProvince();

	List<Region> getCityByProvinceId(int provinceId);
	
	Region selectById(int id);
}
