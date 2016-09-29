package com.project.api.common;

import java.util.List;

public class PaginationResult<T> {
	
	private int totalNum;
	private List<T> result;
	public int getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}
	public List<T> getResult() {
		return result;
	}
	public void setResult(List<T> result) {
		this.result = result;
	}
	
	

}
