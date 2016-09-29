package com.project.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.api.service.SmsCodeService;
import com.project.common.entity.mybatis.SmsCode;
import com.project.common.mapper.SmsCodeMapper;

/**
 * Created by dell on 2016/9/7.
 */
@Service
public class SmsCodeServiceImpl implements SmsCodeService {
    @Autowired
    private SmsCodeMapper smsCodeMapper;

    @Override
    public void insert(SmsCode smsCode) {
        smsCodeMapper.insert(smsCode);
    }
}
