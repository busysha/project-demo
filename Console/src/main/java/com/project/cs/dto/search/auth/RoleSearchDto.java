package com.project.cs.dto.search.auth;

import com.project.cs.dto.search.BaseSearchDto;

public class RoleSearchDto extends BaseSearchDto {

	private static final long serialVersionUID = 1233366526062282392L;
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
