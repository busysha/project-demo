package com.project.cs.dto.search.auth;

import com.project.cs.dto.search.BaseSearchDto;

public class UserSearchDto extends BaseSearchDto {

	private static final long serialVersionUID = 1233366526062282392L;
	
	private String loginName;
	
	private String status;
	
	private String phone;

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
	
}
