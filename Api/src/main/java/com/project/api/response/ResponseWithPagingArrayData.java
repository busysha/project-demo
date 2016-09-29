package com.project.api.response;

import java.util.List;

import com.project.api.response.data.ResponsePagingData;

public class ResponseWithPagingArrayData extends Response {
	private static final long serialVersionUID = 7954493356423189343L;
	private ResponsePagingData data;
	
	public ResponseWithPagingArrayData(int code, String message, int currentPage, int pageSize, int totalNum, List<?> data){
		super(code, message);
		this.code = code;
		this.message = message;
		this.data = new ResponsePagingData(currentPage, pageSize, totalNum, data);
	}

	public ResponseWithPagingArrayData(int code, String message, int currentPage, int pageSize, int totalNum, List<?> data,Object extra){
		super(code, message);
		this.code = code;
		this.message = message;
		this.data = new ResponsePagingData(currentPage, pageSize, totalNum, data,extra);
	}

	public ResponsePagingData getData() {
		return data;
	}

	public void setData(ResponsePagingData data) {
		this.data = data;
	}

}
