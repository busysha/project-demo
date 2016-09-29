package com.project.api.response.data;

import java.io.Serializable;
import java.util.List;

public class ResponseData implements Serializable {
	private static final long serialVersionUID = -4730159496256440005L;
	private List<?> list;
	
	public ResponseData(List<?> array){
		this.list = array;
	}
	
	public List<?> getList() {
		return list;
	}
	
	public void setList(List<?> list) {
		this.list = list;
	}
	
	
}
