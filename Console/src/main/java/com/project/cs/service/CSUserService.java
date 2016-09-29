package com.project.cs.service;

import com.project.common.entity.mybatis.CSUser;
import com.project.cs.dto.ResultDto;
import com.project.cs.dto.bean.auth.UserBean;
import com.project.cs.dto.search.auth.UserSearchDto;

public interface CSUserService {
	
	
	CSUser retrieveUserByLoginName(String loginName);
	
	UserBean findById(int id);
	
	ResultDto<UserBean> searchUser(UserSearchDto searchDto);
	
	void addUser(UserBean userBean,String currentName);
	
	UserBean entityToBean(CSUser user); 
	
	CSUser updateRole(int userId,String roleIds,String currentName);
	
	CSUser updateStatus(int userId,String status,String currentName);
	
	

}
