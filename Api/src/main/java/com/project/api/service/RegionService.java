package com.project.api.service;


import com.project.api.dto.RegionDto;

import java.util.List;

import com.project.common.entity.mybatis.Region;

public interface RegionService {
	Region retrieveRegion(int id);

	List<RegionDto> selectRegionSortByFirstLetter();

	void updateBySelect(Region region);

	List<RegionDto> selectRegionByName(String cityName);
}
