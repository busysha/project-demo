package com.project.cs.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.common.dto.PushDto;
import com.project.common.entity.mybatis.MemberDevice;
import com.project.common.util.ThreadUtil;
import com.project.cs.job.PushThread;
import com.project.cs.service.MemberDeviceService;
import com.project.cs.service.OtherService;
import com.project.cs.service.RedisService;


@Service
public class OtherServiceImpl implements OtherService {

	@Autowired
	protected RedisService redisService;
	
	@Autowired
	private MemberDeviceService memberDeviceService;

	@Override
	public MemberDevice selectDeviceByMemberId(int memberId) {
		return memberDeviceService.selectByMemberId(memberId);
	}
	
	@Override
	public void pushNotificationByRid(int memberId, String alert, String title, Map<String, String> map) {
		MemberDevice hist = selectDeviceByMemberId(memberId);
		if (hist != null) {
			PushDto pushDto = new PushDto();
			pushDto.setDevice(hist.getDeviceType());
			pushDto.setDeviceId(hist.getDeviceId());
			pushDto.setTitle(title);
			pushDto.setAlert(alert);
			pushDto.setExtra(map);
			ThreadUtil.getPoolInstance().execute(new PushThread(pushDto));
		}
	}

}
