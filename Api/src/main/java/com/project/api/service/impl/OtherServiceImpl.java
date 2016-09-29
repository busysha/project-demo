package com.project.api.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.project.api.common.PaginationResult;
import com.project.api.common.thread.PushThread;
import com.project.api.dto.MemberAttendActivityDto;
import com.project.common.dto.MemberCache;
import com.project.api.dto.TreeDto;
import com.project.api.service.*;
import com.project.common.dto.PushDto;
import com.project.common.entity.mybatis.*;
import com.project.common.mapper.*;
import com.project.common.util.ThreadUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.common.common.Constants;

@Service
public class OtherServiceImpl implements OtherService {

	@Autowired
	private MemberService memberService;

	@Autowired
	private RedisService redisService;

	@Autowired
	private LevelExpMapMapper levelExpMapMapper;

    @Autowired
    private MemberAccountService memberAccountService;

    @Autowired
    private FeedBackMapper feedBackMapper;

	@Autowired
	private MemberDeviceService memberDeviceService;

	@Autowired
	private AppUpdateMapper appUpdateMapper;

	@Autowired
	private ConfigService configService;


	@Override
	public TreeDto selectTreeInfo(int memberId) {
        MemberAccount memberAccountInfo = memberAccountService.selectMemberAccount(memberId);
        LevelExpMap lvInfo = levelExpMapMapper.selectLvInfoByLv(memberAccountInfo.getLevel());
        TreeDto res = new TreeDto();
        res.setExpImgUrl(lvInfo.getExpImgUrl());
        res.setLevel(lvInfo.getLevel());
        res.setExpName(lvInfo.getExpName());
        res.setNowLvExp(memberAccountInfo.getExp());
        res.setNextLvExp(lvInfo.getExp());
        res.setRemainExp(lvInfo.getExp() - memberAccountInfo.getExp());
        res.setLove(memberAccountInfo.getLove());
        res.setTimeBank(memberAccountInfo.getTimebank());
        return res;
	}

    @Override
    public void insertFeedBack(String memberName,String contact,int memberId,String content) {
        FeedBack feedBack = new FeedBack();
        feedBack.setContactName(memberName);
        feedBack.setContactPhone(contact);
        feedBack.setContent(content);
        feedBack.setCreateTime(System.currentTimeMillis());
        feedBack.setVersion(0);
        feedBack.setCreator("system");
        feedBack.setMemberId(memberId);
        feedBackMapper.insert(feedBack);
    }

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

	@Override
	public AppUpdate selectUpdateInfoByPlatform(String platform) {
		return appUpdateMapper.selectUpdateInfoByPlatform(platform);
	}

}
