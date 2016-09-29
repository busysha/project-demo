package com.project.api.response;

public class ResponseWithObjectData extends Response {
	private static final long serialVersionUID = 7954493356423189343L;
	private Object data;
	
	public ResponseWithObjectData(int code, String message, Object data){
		super(code, message);
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object obj) {
		this.data = data;
	}
	
}
