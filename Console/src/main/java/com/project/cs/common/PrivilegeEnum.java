package com.project.cs.common;

import java.util.ArrayList;
import java.util.List;

/**
 *  权限管理
 * @author tianmao
 *
 */
public enum PrivilegeEnum {
	
	
	//-=-=-=-=-=-=-=-=-=-其他管理-=-=-=-=-=-=-=-=-=-=-
	YQB(2000,"其他",null),
	
	MEMBER_MGR(2020,"用户管理",PrivilegeEnum.YQB),
	MEMBER_LIST(2021,"用户列表",PrivilegeEnum.MEMBER_MGR),



	
	//-=-=-=-=-=-=-=-=-=-系统设置-=-=-=-=-=-=-=-=-=-=-
	SYSTEM_STE(1000,"系统设置",null),
	BASE_RESOURCE(1001,"基础数据",PrivilegeEnum.SYSTEM_STE),
	/**
	 * CSUser
	 */
	USER_MGR(1010,"用户管理",PrivilegeEnum.SYSTEM_STE),
	USER_LIST(1011,"用户列表",PrivilegeEnum.USER_MGR),
	USER_ADD(1012,"用户新增",PrivilegeEnum.USER_MGR),
	USER_ROLE_SET(1013,"用户权限设置",PrivilegeEnum.USER_MGR),
	/**
	 * CSRole
	 */
	ROLE_MGR(1020,"角色管理",PrivilegeEnum.SYSTEM_STE),
	ROLE_LIST(1021,"角色列表",PrivilegeEnum.ROLE_MGR),
	ROLE_ADD(1022,"角色新增",PrivilegeEnum.ROLE_MGR),
	ROLE_SET_PRIVILEGE(1023,"权限设置",PrivilegeEnum.ROLE_MGR),
	;
	

	private int id;
	

	private String name;
	
	private PrivilegeEnum parentPrivilege;
	

	private PrivilegeEnum(int id ,String name, PrivilegeEnum parentPrivilege) {
		this.id = id;
		this.name=name;
		this.parentPrivilege=parentPrivilege;
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public PrivilegeEnum getParentPrivilege() {
		return parentPrivilege;
	}


	public void setParentPrivilege(PrivilegeEnum parentPrivilege) {
		this.parentPrivilege = parentPrivilege;
	}
	
	/**
	 * @param parentEnum
	 */
	public static List<PrivilegeEnum> getParentParentEnums(PrivilegeEnum parentEnum){
		List<PrivilegeEnum> enums=new ArrayList<PrivilegeEnum>();
		for(PrivilegeEnum privilegeEnum:PrivilegeEnum.values()){
			
			if(parentEnum!=null&&parentEnum.parentPrivilege==parentEnum)
				enums.add(privilegeEnum);
			
			if(parentEnum==null)
				enums.add(privilegeEnum);
			
		}
		return enums;
	}
}
