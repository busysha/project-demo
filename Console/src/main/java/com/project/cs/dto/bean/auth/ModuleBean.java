package com.project.cs.dto.bean.auth;

import java.io.Serializable;
import java.util.List;

public class ModuleBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3819964932614302848L;
	
	private int id;
	
	private String name;
	
	private int privilegeId;
	
	private String url;
	
	private List<ModuleBean> childs;
	
	private String icon;

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

	public int getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(int privilegeId) {
		this.privilegeId = privilegeId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<ModuleBean> getChilds() {
		return childs;
	}

	public void setChilds(List<ModuleBean> childs) {
		this.childs = childs;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	
}
