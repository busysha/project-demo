package com.project.cs.dto;

import com.project.common.annotation.ColumnMap;


public class BaseQueryBean {
    private int pageNo;
    
    @ColumnMap("ignore")
    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }
}
