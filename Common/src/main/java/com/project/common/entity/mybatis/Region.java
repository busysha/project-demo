package com.project.common.entity.mybatis;

public class Region {
    private int id;

    private int parent;

    private String regionName;

    private String pingName;

    private int regionType;

    private String status;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName == null ? null : regionName.trim();
    }

    public String getPingName() {
        return pingName;
    }

    public void setPingName(String pingName) {
        this.pingName = pingName == null ? null : pingName.trim();
    }

    public int getRegionType() {
        return regionType;
    }

    public void setRegionType(int regionType) {
        this.regionType = regionType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

}