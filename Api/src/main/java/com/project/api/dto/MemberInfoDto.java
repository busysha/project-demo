package com.project.api.dto;

import com.project.common.dto.BaseDto;

/**
 * Created by dell on 2016/9/13.
 */
public class MemberInfoDto extends BaseDto {
    private String approveStatusM;
    private String approveStatusO;

    public String getApproveStatusM() {
        return approveStatusM;
    }

    public void setApproveStatusM(String approveStatusM) {
        this.approveStatusM = approveStatusM;
    }

    public String getApproveStatusO() {
        return approveStatusO;
    }

    public void setApproveStatusO(String approveStatusO) {
        this.approveStatusO = approveStatusO;
    }
}
