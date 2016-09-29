package com.project.cs.dto;

/**
 * Created by dell on 2016/9/26.
 */
public class JSONBeanWithData extends JSONBean {
    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
