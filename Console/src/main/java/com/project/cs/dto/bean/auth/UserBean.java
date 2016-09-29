package com.project.cs.dto.bean.auth;

import java.io.Serializable;
import java.util.List;

public class UserBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3819964932614302848L;
	
	private int id;
	
	private String loginName;
	
	private String status;
	
	private String realName;
	
	private String mobile;
	
	private String email;
	
	private String time;
	
	private String password;
	
	
	private String roIds;
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public String getRoIds() {
		return roIds;
	}

	public void setRoIds(String roIds) {
		this.roIds = roIds;
	}

	
	
}
