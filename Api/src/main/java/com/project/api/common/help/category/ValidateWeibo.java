package com.project.api.common.help.category;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.api.common.help.ValidateRun;
import com.project.common.util.HttpClientUtil;
import com.project.common.util.ObjectMapperSingle;

import java.io.IOException;

/**
 * Created by dell on 2016/9/8.
 */
public class ValidateWeibo implements ValidateRun{

    @Override
    public boolean runValidate(String token,String openId) {
        String result = HttpClientUtil.get("https://api.weibo.com/2/users/show.json?access_token=" + token + "&uid=" + openId);
        ObjectMapper objectMapper = ObjectMapperSingle.getInstance();
        try {
            JsonNode obj = objectMapper.readTree(result);
            return null == obj.get("error_code");
        } catch (IOException e) {

        }
        return false;
    }
}
