package com.project.api.service.impl;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.common.common.Constants;
import com.project.api.dto.RegionDto;
import com.project.api.service.RedisService;
import com.project.api.service.RegionService;
import com.project.common.util.ObjectMapperSingle;
import com.project.common.util.ToolUtil;
import com.project.common.entity.mybatis.Region;
import com.project.common.mapper.RegionMapper;

@Service
public class RegionServiceImpl implements RegionService {
    private static final Logger logger = Logger.getLogger(RegionServiceImpl.class);

	@Autowired
	private RegionMapper regionDao;
	
	@Autowired
	private RedisService redisService;
	
	@Override
	public Region retrieveRegion(int id) {
		
		//retrieve from cache
		String serializableStr = redisService.hashSetGet(Constants.RedisKeys.REGION, Constants.Status.BLANK + id);
		
		Region region = (Region)redisService.deSerializeObject(serializableStr, Region.class);
		
		if(region == null){
			region = regionDao.selectByPrimaryKey(id);
			
			serializableStr = redisService.serializeObject(region);
			
			redisService.hashSetPut(Constants.RedisKeys.REGION, Constants.Status.BLANK + id, serializableStr);
		}
		
		return region;
		
	}

	@Override
	public List<RegionDto> selectRegionSortByFirstLetter() {

        ObjectMapper objectMapper = ObjectMapperSingle.getInstance();
        List<RegionDto> res = null;
        String resJson = redisService.get(Constants.RedisKeys.REGION_CITY);
        if(StringUtils.isBlank(resJson)){
            try {
                res = ToolUtil.listEntityToDto(regionDao.selectRegionSortByFirstLetter(), RegionDto.class);
                RegionDto regionDto = new RegionDto();
                regionDto.setId(0);
                regionDto.setParent(0);
                regionDto.setPingName("#");
                regionDto.setRegionName(Constants.System.COUNTRY);
                res.add(0,regionDto);
                String cityJson = objectMapper.writeValueAsString(res);
                redisService.put(Constants.RedisKeys.REGION_CITY,cityJson);
                return res;
            } catch (JsonProcessingException e) {
                logger.error(Constants.LOGGER_HEAD + " parse error");
            }
        }
        try {
            res = objectMapper.readValue(resJson, new TypeReference<List<RegionDto>>(){});
        } catch (IOException e) {
            logger.error(Constants.LOGGER_HEAD + " parse error");
        }
        return res;
	}

	@Override
	public void updateBySelect(Region region) {
		regionDao.updateByPrimaryKeySelective(region);
	}

    @Override
    public List<RegionDto> selectRegionByName(String cityName) {
        return ToolUtil.listEntityToDto(regionDao.selectRegionByName(cityName), RegionDto.class);
    }


}
