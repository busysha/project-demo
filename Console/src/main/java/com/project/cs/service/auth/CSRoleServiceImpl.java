package com.project.cs.service.auth;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.common.entity.mybatis.CSRole;
import com.project.common.mapper.CSRoleMapper;
import com.project.common.util.DateUtil;
import com.project.cs.dto.ResultDto;
import com.project.cs.dto.bean.auth.RoleBean;
import com.project.cs.dto.search.auth.RoleSearchDto;

@Service
public class CSRoleServiceImpl implements CSRoleService {

	@Autowired
	private CSRoleMapper roleDao;
	

	@Override
	public ResultDto<RoleBean> searchRole(RoleSearchDto searchDto) {
		ResultDto<RoleBean> result=new ResultDto<RoleBean>();
		result.setCurrentPageNo(searchDto.getPageNo());
		result.setPageSize(searchDto.getPageSize());
		result.setTotalRecords((int)roleDao.countBySearch(searchDto.getName()));
		List<RoleBean> list=new ArrayList<RoleBean>();
		if(result.getTotalRecords()>0){
			List<CSRole> roles=roleDao.selectBySearch(searchDto.getName(), (searchDto.getPageNo()-1)*searchDto.getPageSize(), searchDto.getPageSize());
			for(CSRole role:roles)
				list.add(entityToBean(role));
		}
		result.setResult(list);
		return result;
	}
	

	private RoleBean entityToBean(CSRole role) {
		RoleBean bean=new RoleBean();
		bean.setRoleId(role.getRoleId());
		bean.setRoleName(role.getRoleName());
		String[] arr=role.getPrivilege().split(",");
		List<Integer> privileges=new ArrayList<Integer>();
		for(String src:arr){
			if(StringUtils.isNotBlank(src))
				privileges.add(Integer.valueOf(src));
		}
		bean.setPrivileges(privileges);
		bean.setTime(DateUtil.longToDateAll(role.getCreateTime()));
		return bean;
	}


	@Override
	public void addRole(RoleBean role, String currentName) {
		CSRole entity=new CSRole();
		entity.setRoleName(role.getRoleName());
		entity.setPrivilege(role.getPrivilegeSrc());
		entity.setCreator(currentName);
		entity.setCreateTime(System.currentTimeMillis());
		entity.setUpdater(currentName);
		entity.setUpdateTime(System.currentTimeMillis());
		roleDao.insert(entity);
	}


	@Override
	public List<CSRole> getAllRoles() {
		return roleDao.getAllRoles();
	}


	@Override
	public CSRole findById(int roleId) {
		return roleDao.selectByPrimaryKey(roleId);
	}


	@Override
	public RoleBean findBeanById(int roleId) {
		CSRole role=findById(roleId);
		if(role!=null)
			return entityToBean(role);
		return null;
	}


	@Override
	public CSRole updateRolePrivilege(int roleId, String privilegeIds, String currentName) {
		CSRole role=findById(roleId);
		if(role==null)
			return null;
		role.setPrivilege(privilegeIds);
		role.setUpdateTime(System.currentTimeMillis());
		role.setUpdater(currentName);
		roleDao.updateByPrimaryKey(role);
		return role;
	}


	
}
