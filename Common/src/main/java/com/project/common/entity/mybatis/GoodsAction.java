package com.project.common.entity.mybatis;

public class GoodsAction {
    private int id;

    private String actionName;

    private String goodType;

    private String limitTime;

    private int limitTotal;

    private int exp;

    private long createTime;

    private String creator;

    private long updateTime;

    private String updater;

    private int version;

    private int getNum;

    private String isTask;

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsTask() {
        return isTask;
    }

    public void setIsTask(String isTask) {
        this.isTask = isTask;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName == null ? null : actionName.trim();
    }

    public String getGoodType() {
        return goodType;
    }

    public void setGoodType(String goodType) {
        this.goodType = goodType == null ? null : goodType.trim();
    }

    public String getLimitTime() {
        return limitTime;
    }

    public void setLimitTime(String limitTime) {
        this.limitTime = limitTime;
    }

    public int getLimitTotal() {
        return limitTotal;
    }

    public void setLimitTotal(int limitTotal) {
        this.limitTotal = limitTotal;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater == null ? null : updater.trim();
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getGetNum() {
        return getNum;
    }

    public void setGetNum(int getNum) {
        this.getNum = getNum;
    }

}