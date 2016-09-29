package com.project.api.common.enums;


import com.project.common.common.Constants;
import com.project.common.util.MessageUtil;

public enum BusinessTypeEnum {
    //成功
    SUCCESS(1000, MessageUtil.buildMessage(Constants.MessageKeys.SUCCESS_CODE)),

    //系统错误
    SYSTEM_ERROR(1001,MessageUtil.buildMessage(Constants.MessageKeys.SYSTEM_ERROR)),

    //网络错误
    NETWORK_ERROR(1002,MessageUtil.buildMessage(Constants.MessageKeys.NETWORK_ERROR)),

    //参数错误
    PARAMS_ERROR(1003,MessageUtil.buildMessage(Constants.MessageKeys.PARAMS_ERROR)),

    //签名错误
    SIGN_ERROR(1004,MessageUtil.buildMessage(Constants.MessageKeys.SIGN_ERROR)),

    //用户名密码错误
    USER_ACCOUNT_PASSWORD_ERROR(1005,MessageUtil.buildMessage(Constants.MessageKeys.USER_ACCOUNT_PASSWORD_ERROR)),

    //用户已存在
    USER_EXISTS(1006,MessageUtil.buildMessage(Constants.MessageKeys.USER_EXISTS)),

    //用户未登录
    USER_NO_LOGIN(1007,MessageUtil.buildMessage(Constants.MessageKeys.USER_NO_LOGIN)),

    //电话已存在
    MOBILE_EXISTS(1008,MessageUtil.buildMessage(Constants.MessageKeys.MOBILE_EXISTS)),

    //用户不存在
    USER_NOT_EXISTS(1009,MessageUtil.buildMessage(Constants.MessageKeys.USER_NOT_EXISTS)),

    FORBIDDEN(1010, MessageUtil.buildMessage(Constants.MessageKeys.FORBIDDEN)),

    //单点登录
    USER_LOGIN_ANOTHER(1011,MessageUtil.buildMessage(Constants.MessageKeys.USER_LOGIN_ANOTHER)),
    AUTH_CODE_EXISTS(1012,MessageUtil.buildMessage(Constants.MessageKeys.AUTH_CODE_EXISTS)),
    AUTH_CODE_ERROR(1013,MessageUtil.buildMessage(Constants.MessageKeys.AUTH_CODE_ERROR)),
    APP_NO_NEED_UPDATE(1023,MessageUtil.buildMessage(Constants.MessageKeys.APP_NO_NEED_UPDATE)),
    ;


    private int code;

    private String description;

    private BusinessTypeEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static String getDescription(int code) {
        for (BusinessTypeEnum type : BusinessTypeEnum.values()) {
            if (code == type.getCode()) {
                return type.getDescription();
            }
        }
        return "";
    }

}
