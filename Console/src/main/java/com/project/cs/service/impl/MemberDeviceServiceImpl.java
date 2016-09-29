package com.project.cs.service.impl;

import com.project.common.entity.mybatis.MemberDevice;
import com.project.common.mapper.MemberDeviceMapper;
import com.project.cs.service.MemberDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by dell on 2016/9/7.
 */
@Service
public class MemberDeviceServiceImpl implements MemberDeviceService {
    @Autowired
    private MemberDeviceMapper memberDeviceMapper;

    @Override
    public void insert(MemberDevice memberDevice){
        memberDeviceMapper.insert(memberDevice);
    }

    @Override
    public MemberDevice selectByMemberId(int memberId){
        return memberDeviceMapper.selectByMemberId(memberId);
    }
}

