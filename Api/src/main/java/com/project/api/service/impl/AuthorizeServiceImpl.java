package com.project.api.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.common.common.Constants;
import com.project.api.service.AuthorizeService;
import com.project.api.service.RedisService;

@Service
public class AuthorizeServiceImpl implements AuthorizeService {
	@Autowired
	private RedisService redisService;
	
	@Override
	public boolean checkUserTicket(String userId, String ticket) {
		if(StringUtils.isEmpty(userId) || StringUtils.isEmpty(ticket)){
			return false;
		}
		
		//get from redis
		String key = Constants.RedisKeys.TICKET_PREFIX + userId;
		
		String _t = redisService.get(key);
		
		if(ticket.equals(_t)){
			
			updateUserTicket(key, _t);
			return true;
		}else{
			return false;
		}
	}

	@Override
	public void updateUserTicket(String ticketKey, String ticket) {
		redisService.put(ticketKey, ticket ,Constants.ONE_DAY_TIMESTAMP * 5);
	}

}
