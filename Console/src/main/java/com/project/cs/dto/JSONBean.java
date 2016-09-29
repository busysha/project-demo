package com.project.cs.dto;

import com.project.common.common.ConsoleConstants;

public class JSONBean {
	
	private String code = ConsoleConstants.MessageKey.SUCCESS.getCode();
	private String message = ConsoleConstants.MessageKey.SUCCESS.getMessage();
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
