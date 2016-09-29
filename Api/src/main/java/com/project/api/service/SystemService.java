package com.project.api.service;

/**
 * Created by dell on 2016/9/7.
 */
public interface SystemService {
    /**
     * generate sms code
     * @param codeLength code length
     * @return
     */
    String generateAuthCode(int codeLength);

    /**
     * validate sms code
     * @param mobile /
     * @param authType /
     * @param authCode /
     * @return boolean
     */
    boolean validateAuthCode(String mobile,String authType,String authCode);

    /**
     * get redis sms code
     * @param mobile /
     * @param authType /
     * @return
     */
    String getAuthCode(String mobile,String authType);

    /**
     * put sms code in redis
     * @param mobile /
     * @param authType /
     * @param authCode /
     * @param expireTime /
     */
    boolean putAuthCode(String mobile,String authType,String authCode,long expireTime);

    /**
     * delete sms code if validate pass
     * @param mobile /
     * @param authType /
     */
    void deleteAuthCode(String mobile,String authType,String authCode);

}
