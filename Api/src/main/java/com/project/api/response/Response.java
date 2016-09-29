package com.project.api.response;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class Response implements Serializable{
	private static final long serialVersionUID = 1821064254290399528L;
	private static final Logger logger = Logger.getLogger(Response.class);
	protected int code;
	protected String message;
	
	public Response(int code, String message){
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		logger.info("response code : " + code);
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	

}
