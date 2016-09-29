package com.project.common.common;


import java.util.ArrayList;
import java.util.List;

public interface Constants {
	
	interface RequestCode{
		

	}
	
	interface RequestHeader{
		
        String KEY_SIGN 	= "sign";
        String KEY_DEVICE 	= "device";
        String KEY_DEVICEID = "deviceId";
        String KEY_VERSION 	= "version";
        String KEY_TOKEN  	= "token";
		
	}
	
	interface MessageKeys {

        String SUCCESS_CODE = "msg.success";

        String FORBIDDEN = "msg.forbidden";
		
        String USER_NO_LOGIN = "msg.user.no.login";

        String USER_LOGIN_ANOTHER = "msg.user.login.another";

        String SYSTEM_ERROR = "msg.sys.error";

        String NETWORK_ERROR= "msg.network.error";

        String SIGN_ERROR = "msg.sign.error";

        String USER_NAME_EXISTS = "msg.user.name.exists";

        String USER_ACCOUNT_PASSWORD_ERROR = "msg.user.account.password.error";

        String PARAMS_ERROR = "msg.params.error";

        String AUTH_CODE_EXISTS = "msg.auth.code.exists";

        String AUTH_CODE_GET_SUCCESS = "msg.auth.code.get.success";

        String FAIL_CODE = "msg.fail";

        String MOBILE_EXISTS = "msg.mobile.exists";

        String AUTH_CODE_ERROR = "msg.auth.code.error";

        String USER_NOT_EXISTS = "msg.user.not.exists";

        String USER_REGISTER_SUCCESS = "msg.user.register.success";

        String USER_LOGIN_SUCCESS = "msg.user.login.success";

        String USER_EXISTS = "msg.user.exists";

        String CHANGE_PASSWORD_SUCCESS = "msg.change.password.success";
        
        String APP_NO_NEED_UPDATE = "msg.app.no.need.update";

        //举报
        String REPORT_SUCCESS = "msg.report.success";

    }

	/**
	 * redis 缓存头
	 */
	interface RedisKeys{
		//Ticket key前缀
        String TICKET_PREFIX = "ticket_";

        String MEMBER_INFO_PREFIX = "member_info_";

        String REGION = "region";
        //城市json
        String REGION_CITY = "city";
        //用户经验值集合
        String MEMBER_EXP_SET = "member_exp_set";
        //用户action集合
        String MEMBER_ACTION_SET = "member_action_set";
        //用户经验值集合

    }
   
	interface Sign{
		//跳过签名
        String SKIP_VALIDATE_SIGN ="skip.validate.sign";

		//签名参数sign
        String SIGN_PARAM_SIGN = "sign";
		
		//签名参数timestamp
        String SIGN_PARAM_TIMESTAMP="timestamp";
		
		//签名时间差(毫秒)
        String SIGN_TIMEOUT_MS = "120000";
		
	}

	interface ConfigKeys{
		//config redis 头
		String APP_KEY = "config.app.key";

		String APP_ID  = "config.app.id";

	}

	/**
	 * 状态常量
	 */
	interface Status {
		String STATUS_ACTIVE = "A";

		String STATUS_INACTIVE = "I";

		String STATUS_PENDING = "P";
		
        String STATUS_YES = "Y";

        String STATUS_NO  = "N";

        String BLANK = "";

        String APPROVE_TYPE_A = "A";

        String APPROVE_TYPE_R = "R";

        String ACT_STATUS_END  = "E";
	}

	/**
	 * 系统默认常量
	 */
	interface System{

		String DEFAULT_SYSTEM_UPDATER = "config.default.system.updater";

		String DEFAULT_NICKNAME = "config.default.nickname";

		String DEFAULT_AVATAR_IMG = "config.default.avatar.img";

		String DEFAULT_CHANNEL_FROM_APP = "app";

		String DEFAULT_CHANNEL_FROM_QQ = "qq";

		String DEFAULT_CHANNEL_FROM_WECHAT = "wechat";

		String DEFAULT_CHANNEL_FROM_WEIBO = "weibo";

		String DEFAULT_MEMBER_TYPE_NORMAL = "N";

		String DEFAULT_MEMBER_TYPE_AUTH = "M";

		String DEFAULT_MEMBER_TYPE_ORG = "O";

		String DEFAULT_MEMBER_GENDER_MALE = "M";

		String DEFAULT_MEMBER_GENDER_FEMALE = "F";

		String DEFAULT_MEMBER_AGE = "config.default.age";

        String DEFAULT_SMS_EXPIRE = "config.sms.expire";

        String DEFAULT_VALIDATE_LENGTH = "config.validate.length";

        String DEFAULT_HOME_PAGE_SIZE = "config.default.pagesize";

        String DEFAULT_HOME_VIDEO_PAGESIZE= "config.default.video.pagesize";

        String DEFAULT_AUTH_URL ="config.auth.url";

        String DEFAULT_ORG_AUTH_URL ="config.org.auth.url";

		String DEFAULT_REGISTER_CONTENT = "config.sms.register.content";

        String DEFAULT_GET_PASSWORD_CONTENT = "config.sms.password.content";

        String DEFAULT_REGION_UPDATE = "config.region.update";

        String ACTION_TYPE_LOVE = "L";

        String ACTION_TYPE_TIME_BANK = "TB";

        String ACTION_TYPE_EXP = "EXP";
        
        String COUNTRY = "全国";

        String DEFAULT_ABOUT_ME = "config.about.me";


	}

    interface GoodsAction{
    	String DAY_LOGIN = "day_login";
        //获取量-1为无限制获取
        int MAGIC_NUMBER = -1;
    	String UPDATE_INFO = "update_info";
    }

    interface NotificationType{
    }
    enum AuthUrl{
        MEMBER_AUTH(System.DEFAULT_MEMBER_TYPE_AUTH,System.DEFAULT_AUTH_URL),
        ORG_AUTH(System.DEFAULT_MEMBER_TYPE_ORG,System.DEFAULT_AUTH_URL),
        ;

        private String type;
        private String url;

        AuthUrl(String type,String url) {
            this.type = type;
            this.url = url;
        }

        public static String getAuthUrl(String type){
            for (AuthUrl authUrl : AuthUrl.values()) {
                if(authUrl.getType().equals(type)){
                    return authUrl.getUrl();
                }
            }
            return Status.BLANK;
        }

        public String getType() {
            return type;
        }

        public String getUrl() {
            return url;
        }
    }

	//一天的时间戳
    long ONE_DAY_TIMESTAMP = 86400000;

    String LOG_PREFIX = "============>[BizLog] ";

	/**
	 * logger 头
	 */
	String LOGGER_HEAD = "=====";


    /**
     * authCode action 权限
     */

    List<String> AUTH_ACTION_LIST = new ArrayList<String>(){
        {
            add(AUTH_ACTION_REGISTER);
            add(AUTH_ACTION_FORGET_PASSWORD);
        }
    };

    /**
     * authCode action
     */
	String AUTH_ACTION_REGISTER = "register";

    String AUTH_ACTION_FORGET_PASSWORD = "forgetPassword";


}
