package com.project.common.util;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.utils.Base64;
import com.project.common.common.Constants;
import com.project.common.dto.PushDto;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dell on 2016/9/14.
 */
public class JPushUtil {

    private static final String masterKey = "878c9ec8ef71c836513fb4bc";
    private static final String appKey = "d049f110e7921c32e32185b8";
    private static final Logger logger = Logger.getLogger(JPushUtil.class);

    private JPushUtil(){

    }

    private static class JPushUtilInner{
        private static JPushClient jPushClient = new JPushClient(masterKey,appKey);
    }


    public static JPushClient getClient(){
        return JPushUtilInner.jPushClient;
    }

    private static String buildAuthKey(){
        String authKey = appKey + ":" + masterKey;
        return "Basic " +  String.valueOf(Base64.encode(authKey.getBytes()));
    }

    public static PushResult sendNotification(PushDto pushDto){
        try {
            if("android".equals(pushDto.getDevice())){
                PushResult res = JPushUtil.getClient().sendAndroidNotificationWithRegistrationID(pushDto.getTitle(), pushDto.getAlert(), pushDto.getExtra(), pushDto.getDeviceId());
                logger.info(Constants.LOGGER_HEAD + " res  " + res.toString() + " push content " + pushDto.getAlert() + " " + pushDto.getExtra().toString());
                return res;
            }else{
                PushResult res  = JPushUtil.getClient().sendIosNotificationWithRegistrationID(pushDto.getAlert(),pushDto.getExtra(),pushDto.getDeviceId());
                logger.info(Constants.LOGGER_HEAD + " res  " + res.toString() + " push content " + pushDto.getAlert() + " " + pushDto.getExtra().toString());
                return res;
            }
        } catch (APIConnectionException | APIRequestException e) {
            logger.error(Constants.LOGGER_HEAD + " push error " + e);
        }
        return null;
    }

    public static void main(String[] args) {
        Map<String,String> map = new HashMap<String,String>(){
            {
                put("from","jpush");
            }
        };
        PushDto pushDto = new PushDto();
        pushDto.setExtra(map);
        pushDto.setTitle("test");
        pushDto.setAlert("test");
        pushDto.setDeviceId("18071adc0305289a0c3");
        pushDto.setDevice("android");
        PushResult res = sendNotification(pushDto);
        System.out.println(res.toString() + JPushUtil.getClient());
        PushResult res2 = sendNotification(pushDto);
        System.out.println(res2.toString() + JPushUtil.getClient());
    }
}