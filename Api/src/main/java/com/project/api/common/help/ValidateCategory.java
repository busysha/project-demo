package com.project.api.common.help;

import com.project.common.common.Constants;
import com.project.api.common.help.category.ValidateTencent;
import com.project.api.common.help.category.ValidateWechat;
import com.project.api.common.help.category.ValidateWeibo;

/**
 * Created by dell on 2016/9/8.
 */
public class ValidateCategory {

    private ValidateCategory(){}

    public static boolean validateFactory(String from,String token,String openId){
        if(Constants.System.DEFAULT_CHANNEL_FROM_QQ.equals(from)) {
            return true;//new ValidateTencent().runValidate(token,openId);
        }else if(Constants.System.DEFAULT_CHANNEL_FROM_WEIBO.equals(from)){
            return new ValidateWeibo().runValidate(token,openId);
        }else if(Constants.System.DEFAULT_CHANNEL_FROM_WECHAT.equals(from)){
            return new ValidateWechat().runValidate(token,openId);
        }
        return false;
    }
}
