package com.project.cs.common;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.project.cs.dto.bean.auth.ModuleBean;
import com.project.cs.dto.bean.auth.UserBean;

public class SessionInfo implements Serializable {

	private static final long serialVersionUID = -2240554228460062191L;
	
	private UserBean user;
	private  int currentMenu;
	private int currentParentMenu;
	
	private Set<Integer> privilege;
	private List<ModuleBean> modules;

	public UserBean getUser() {
		return user;
	}

	public void setUser(UserBean user) {
		this.user = user;
	}

	public Set<Integer> getPrivilege() {
		return privilege;
	}

	public void setPrivilege(Set<Integer> privilege) {
		this.privilege = privilege;
	}


	public int getCurrentMenu() {
		return currentMenu;
	}

	public void setCurrentMenu(int currentMenu) {
		this.currentMenu = currentMenu;
	}

	public int getCurrentParentMenu() {
		return currentParentMenu;
	}

	public void setCurrentParentMenu(int currentParentMenu) {
		this.currentParentMenu = currentParentMenu;
	}

	public List<ModuleBean> getModules() {
		return modules;
	}

	public void setModules(List<ModuleBean> modules) {
		this.modules = modules;
	}

	
	
	
}
