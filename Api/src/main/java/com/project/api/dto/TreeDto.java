package com.project.api.dto;


import com.project.common.dto.BaseDto;

/**
 * Created by dell on 2016/9/13.
 */
public class TreeDto extends BaseDto {
    private int love;

    private int nowLvExp;

    private int nextLvExp;

    private int level;

    private String expName;

    private String expImgUrl;

    private int remainExp;

    private int timeBank;

    public int getTimeBank() {
        return timeBank;
    }

    public void setTimeBank(int timeBank) {
        this.timeBank = timeBank;
    }

    public int getRemainExp() {
        return remainExp;
    }

    public void setRemainExp(int remainExp) {
        this.remainExp = remainExp;
    }

    public int getLove() {
        return love;
    }

    public void setLove(int love) {
        this.love = love;
    }

    public int getNowLvExp() {
        return nowLvExp;
    }

    public void setNowLvExp(int nowLvExp) {
        this.nowLvExp = nowLvExp;
    }

    public int getNextLvExp() {
        return nextLvExp;
    }

    public void setNextLvExp(int nextLvExp) {
        this.nextLvExp = nextLvExp;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getExpName() {
        return expName;
    }

    public void setExpName(String expName) {
        this.expName = expName;
    }

    public String getExpImgUrl() {
        return expImgUrl;
    }

    public void setExpImgUrl(String expImgUrl) {
        this.expImgUrl = expImgUrl;
    }
}
