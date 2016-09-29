package com.project.cs.service;

import java.util.Map;

import com.project.common.entity.mybatis.MemberDevice;

public interface OtherService {

    MemberDevice selectDeviceByMemberId(int memberId);
    
    /**
	 * 推送
	 * @param memberId
	 * @return
     */
	void pushNotificationByRid(int memberId,String alert,String title,Map<String,String> map);
}
