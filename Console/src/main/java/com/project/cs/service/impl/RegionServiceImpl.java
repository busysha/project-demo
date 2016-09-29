package com.project.cs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.common.entity.mybatis.Region;
import com.project.common.mapper.RegionMapper;
import com.project.cs.service.RegionService;

/** 
* 类说明 
* @author chitu
* @version 创建时间：2016年9月28日 上午9:31:02 
*/
@Service
public class RegionServiceImpl implements RegionService{

	@Autowired
	RegionMapper regionDao;
	
	@Override
	public List<Region> getProvince() {
		return regionDao.selectRegionSortByFirstLetter();
	}

	@Override
	public List<Region> getCityByProvinceId(int provinceId) {
		return regionDao.selectCityByProvinceId(provinceId);
	}

	@Override
	public Region selectById(int id) {
		return regionDao.selectByPrimaryKey(id);
	}

}
