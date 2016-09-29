package com.project.api.service;


import com.project.common.entity.mybatis.MemberDevice;

/**
 * Created by dell on 2016/9/7.
 */
public interface MemberDeviceService {
    void insert(MemberDevice memberDevice);

    MemberDevice selectByMemberId(int memberId);
}
