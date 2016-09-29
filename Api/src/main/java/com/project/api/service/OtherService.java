package com.project.api.service;

import java.util.Map;

import com.project.api.common.PaginationResult;
import com.project.api.dto.MemberAttendActivityDto;
import com.project.api.dto.TreeDto;
import com.project.common.entity.mybatis.AppUpdate;
import com.project.common.entity.mybatis.MemberDevice;

public interface OtherService {


	/**
	 * 获取树的信息
	 * @param memberId /
	 * @return
     */
	TreeDto selectTreeInfo(int memberId);

    /**
     * 反馈
     * @param /
     */
    void insertFeedBack(String memberName,String contact,int memberId,String content);

	/**
	 * 获取deviceHist
	 */
	MemberDevice selectDeviceByMemberId(int memberId);

	/**
	 * 推送
	 * @param memberId
	 * @return
     */
	void pushNotificationByRid(int memberId,String alert,String title,Map<String,String> map);

	/**
	 * 获取更新信息
	 */
	AppUpdate selectUpdateInfoByPlatform(String platform);

}
