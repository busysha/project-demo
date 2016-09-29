package com.project.api.response.data;


import java.io.Serializable;
import java.util.List;

import com.project.common.util.ToolUtil;

public class ResponsePagingData implements Serializable {
	private static final long serialVersionUID = -4730159496256440005L;
	private List<?> list;
	private int pageSize;
	private int currentPage;
	private int totalNum;
	private Object extra;

	public ResponsePagingData(int currentPage, int pageSize, int totalNum, List<?> array){
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.totalNum = ToolUtil.pageConvert(totalNum,pageSize);
		this.list = array;
	}

	public ResponsePagingData(int currentPage, int pageSize, int totalNum, List<?> array,Object extra){
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.totalNum = ToolUtil.pageConvert(totalNum,pageSize);
		this.list = array;
		this.extra = extra;
	}

	public List<?> getList() {
		return list;
	}
	
	public void setList(List<?> list) {
		this.list = list;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public Object getExtra() {
		return extra;
	}

	public void setExtra(Object extra) {
		this.extra = extra;
	}
}
