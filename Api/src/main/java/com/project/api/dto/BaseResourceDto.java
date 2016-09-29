package com.project.api.dto;

import com.project.common.dto.BaseDto;

/**
 * Created by dell on 2016/9/26.
 */
public class BaseResourceDto extends BaseDto {
    private String orgShareUrl;
    private String orgShareSubTitle;
    private String orgShareImg;
    private String actShareUrl;
    private String actShareImg;
    private String actShareSubTitle;
    private String aboutMe;


    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getActShareSubTitle() {
        return actShareSubTitle;
    }

    public void setActShareSubTitle(String actShareSubTitle) {
        this.actShareSubTitle = actShareSubTitle;
    }

    public String getOrgShareUrl() {
        return orgShareUrl;
    }

    public void setOrgShareUrl(String orgShareUrl) {
        this.orgShareUrl = orgShareUrl;
    }

    public String getOrgShareSubTitle() {
        return orgShareSubTitle;
    }

    public void setOrgShareSubTitle(String orgShareSubTitle) {
        this.orgShareSubTitle = orgShareSubTitle;
    }

    public String getOrgShareImg() {
        return orgShareImg;
    }

    public void setOrgShareImg(String orgShareImg) {
        this.orgShareImg = orgShareImg;
    }

    public String getActShareUrl() {
        return actShareUrl;
    }

    public void setActShareUrl(String actShareUrl) {
        this.actShareUrl = actShareUrl;
    }

    public String getActShareImg() {
        return actShareImg;
    }

    public void setActShareImg(String actShareImg) {
        this.actShareImg = actShareImg;
    }
}

