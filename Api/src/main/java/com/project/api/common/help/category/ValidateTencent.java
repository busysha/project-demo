package com.project.api.common.help.category;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.common.util.HttpClientUtil;
import com.project.common.util.ObjectMapperSingle;

import java.io.IOException;

/**
 * Created by dell on 2016/9/8.
 */
public class ValidateTencent{

    public boolean runValidate(String token,String openId) {
        String result = HttpClientUtil.get("https://graph.qq.com/user/get_simple_userinfo?access_token=" + token +"&oauth_consumer_key=1104356777&openid=" + openId);
        ObjectMapper objectMapper = ObjectMapperSingle.getInstance();
        try {
            JsonNode obj = objectMapper.readTree(result);
            return 0 == obj.get("ret").asInt();
        } catch (IOException e) {

        }
        return false;
    }
}
