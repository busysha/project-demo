package com.project.cs.dto.bean.auth;

import java.io.Serializable;
import java.util.List;

public class RoleBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3819964932614302848L;
	
	private int roleId;
	
	private String roleName;
	
	private List<Integer> privileges;
	
	private String time;
	
	private String privilegeSrc;
	
	


	
	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}


	public List<Integer> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(List<Integer> privileges) {
		this.privileges = privileges;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getPrivilegeSrc() {
		return privilegeSrc;
	}

	public void setPrivilegeSrc(String privilegeSrc) {
		this.privilegeSrc = privilegeSrc;
	}

	
	
	
}
