package com.project.api.response;

import java.util.List;

import com.project.api.response.data.ResponseData;

public class ResponseWithArrayData extends Response {
	private static final long serialVersionUID = 7954493356423189343L;
	private ResponseData data;
	
	public ResponseWithArrayData(int code, String message, List<?> data){
		super(code, message);
		this.code = code;
		this.message = message;
		this.data = new ResponseData(data);
	}

	public ResponseData getData() {
		return data;
	}

	public void setData(ResponseData data) {
		this.data = data;
	}


}
