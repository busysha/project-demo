package com.project.cs.dto.search;

import java.io.Serializable;

/**
 * Data transfer object
 * @author
 *
 */
public class BaseSearchDto implements Serializable {
	
	protected int pageNo=1;
	protected int pageSize=10;
	public static final int NO_PAGINATION=-1;
	
	
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	
}
