package com.project.api.common;

/**
 * Created by dell on 2016/9/20.
 */
public class PaginationResultExtra<T> extends PaginationResult<T> {
    private Object extra;

    public Object getExtra() {
        return extra;
    }

    public void setExtra(Object extra) {
        this.extra = extra;
    }
}
