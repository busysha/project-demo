package com.project.cs.dto;

import java.io.Serializable;
import java.util.List;

import com.project.common.entity.mybatis.Member;

public class ResultDto<T> implements Serializable{
	
	private static final long serialVersionUID = 6960556217842404384L;
	private List<T> result;
	private int totalRecords;
	private int currentPageNo;
	private int pageSize;
	
	public List<T> getResult() {
		return result;
	}
	public void setResult(List<T> result) {
		this.result = result;
	}
	public int getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
	public int getCurrentPageNo() {
		return currentPageNo;
	}
	public void setCurrentPageNo(int currentPageNo) {
		this.currentPageNo = currentPageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	

}
