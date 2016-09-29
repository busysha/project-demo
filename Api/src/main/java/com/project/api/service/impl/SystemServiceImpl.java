package com.project.api.service.impl;

import com.project.common.common.Constants;
import com.project.api.service.ConfigService;
import com.project.api.service.RedisService;
import com.project.api.service.SmsCodeService;
import com.project.api.service.SystemService;
import com.project.common.util.ToolUtil;
import com.project.common.entity.mybatis.SmsCode;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

/**
 * Created by dell on 2016/9/7.
 */
@Service
public class SystemServiceImpl implements SystemService {
    @Autowired
    private RedisService redisService;

    @Autowired
    private SmsCodeService smsCodeService;

    @Autowired
    private ConfigService configService;

    @Override
    public String generateAuthCode(int codeLength) {
        return ToolUtil.randomSet(codeLength);
    }

    @Override
    public boolean validateAuthCode(String mobile,String authType,String authCode) {
        String smsCode = redisService.get(mobile + authType);
        return !StringUtils.isBlank(smsCode) && smsCode.equals(authCode);
    }

    @Override
    public String getAuthCode(String mobile, String authType) {
        return redisService.get(mobile + authType);
    }

    @Override
    public boolean putAuthCode(String mobile, String authType,String authCode ,long expireTime) {

        try {
            byte[] noConvert= smsCodeFormat(authCode,authType).getBytes("GB2312");
            String content = new String(noConvert,"GB2312");
            boolean response = ToolUtil.sendSms(authCode, content, mobile);
            if(response){
                redisService.put(mobile + authType,authCode,expireTime);
                long now = System.currentTimeMillis();
                SmsCode smsCode = new SmsCode();
                smsCode.setCreateTime(now);
                smsCode.setUpdateTime(now);
                smsCode.setType(authType);
                smsCode.setPhone(mobile);
                smsCode.setCode(authCode);
                smsCode.setStatus(Constants.Status.STATUS_ACTIVE);
                smsCodeService.insert(smsCode);
                return true;
            }else{
                return false;
            }
        } catch (UnsupportedEncodingException e) {
            return false;
        }
    }

    @Override
    public void deleteAuthCode(String mobile, String authType, String authCode) {
        redisService.delete(mobile + authType);

        long now = System.currentTimeMillis();
        SmsCode smsCode = new SmsCode();
        smsCode.setCreateTime(now);
        smsCode.setUpdateTime(now);
        smsCode.setType(authType);
        smsCode.setPhone(mobile);
        smsCode.setCode(authCode);
        smsCode.setStatus(Constants.Status.STATUS_INACTIVE);
        smsCodeService.insert(smsCode);
    }

    //==============private function========================//
    private String smsCodeFormat(String formatString,String authType){
        String smsContent = null;
        if(Constants.AUTH_ACTION_REGISTER.equals(authType)){
            smsContent = String.format(configService.getConfigVal(Constants.System.DEFAULT_REGISTER_CONTENT),formatString);
        }else if(Constants.AUTH_ACTION_FORGET_PASSWORD.equals(authType)){
            smsContent = String.format(configService.getConfigVal(Constants.System.DEFAULT_GET_PASSWORD_CONTENT),formatString);
        }
        return smsContent;
    }
}
