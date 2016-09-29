package com.project.api.dto;

import com.project.common.dto.BaseDto;

/**
 * Created by dell on 2016/9/12.
 */
public class RegionDto extends BaseDto {
    private int id;

    private String regionName;

    private int parent;

    private String pingName;


    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getPingName() {
        return pingName;
    }

    public void setPingName(String pingName) {
        this.pingName = pingName;
    }
}
