package com.project.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperSingle {
    private static ObjectMapper objectMapper = new ObjectMapper();
    private ObjectMapperSingle(){}
    public static ObjectMapper getInstance(){
        return objectMapper;
    }
}
