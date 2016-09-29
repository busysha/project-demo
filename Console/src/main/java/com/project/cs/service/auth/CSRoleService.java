package com.project.cs.service.auth;

import java.util.List;

import com.project.common.entity.mybatis.CSRole;
import com.project.cs.dto.ResultDto;
import com.project.cs.dto.bean.auth.RoleBean;
import com.project.cs.dto.search.auth.RoleSearchDto;

public interface CSRoleService {
	
	
	
	ResultDto<RoleBean> searchRole(RoleSearchDto searchDto);
	
	void addRole(RoleBean role,String currentName);
	
	List<CSRole> getAllRoles();
	
	CSRole findById(int roleId);
	
	RoleBean findBeanById(int roleId);
	
	CSRole updateRolePrivilege(int roleId,String privilegeIds,String currentName);
	

}
