package com.project.cs.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.project.cs.dto.bean.auth.ModuleBean;

/**
 *  模块管理
 */
public enum ModuleEnum {
	
	
	
	/**
	 * 系统设置
	 */
	SYSTEM_SET("系统设置",null,PrivilegeEnum.SYSTEM_STE,null,"icon-cog"),
	MEMBER_MGR("用户管理","member/list",PrivilegeEnum.MEMBER_MGR,ModuleEnum.SYSTEM_SET,""),
	USER_MGR("后台用户管理","user/list",PrivilegeEnum.USER_LIST,ModuleEnum.SYSTEM_SET,""),
	ROLE_MGR("角色管理","role/list",PrivilegeEnum.ROLE_MGR,ModuleEnum.SYSTEM_SET,""),
    BASE_RESOURCE("app基础数据管理","system/base",PrivilegeEnum.BASE_RESOURCE,ModuleEnum.SYSTEM_SET,""),

	;
	

	private String name;

	private String url;
	
	private PrivilegeEnum privilegeEnum;
	
	private ModuleEnum parentModule;
	
	private String icon;
	
	

	private ModuleEnum(String name, String url,PrivilegeEnum privilegeEnum,ModuleEnum parentModule,String icon) {
		this.name = name;
		this.url = url;
		this.privilegeEnum=privilegeEnum;
		this.parentModule=parentModule;
		this.icon=icon;
	}



	public String getUrl() {
		return url;
	}



	public void setUrl(String url) {
		this.url = url;
	}



	public PrivilegeEnum getPrivilegeEnum() {
		return privilegeEnum;
	}



	public void setPrivilegeEnum(PrivilegeEnum privilegeEnum) {
		this.privilegeEnum = privilegeEnum;
	}



	public ModuleEnum getParentModule() {
		return parentModule;
	}



	public void setParentModule(ModuleEnum parentModule) {
		this.parentModule = parentModule;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}
	
	
	
	public String getIcon() {
		return icon;
	}



	public void setIcon(String icon) {
		this.icon = icon;
	}



	public static  int _id=1;
	public static List<ModuleBean>  getAllModules(Set<Integer> privilegeSet){
		List<ModuleBean> list=new ArrayList<ModuleBean>();
		for(ModuleEnum module:ModuleEnum.values()){
			//int flag=privilegeNum&module.getPrivilegeEnum().getId();
			if(module.getParentModule()==null&&privilegeSet.contains(module.getPrivilegeEnum().getId())){
				ModuleBean bean=new ModuleBean();
				bean.setId(_id);
				bean.setName(module.getName());
				bean.setUrl(module.getUrl());
				bean.setIcon(module.getIcon());
				if(null!=module.getPrivilegeEnum()){
					bean.setPrivilegeId(module.getPrivilegeEnum().getId());
				}else{
					bean.setPrivilegeId(1);
				}
				bean.setChilds(getChilds(module,privilegeSet));
				list.add(bean);
				_id++;
			}
		}
		return list;
	}
	
	public static List<ModuleBean> getChilds(ModuleEnum moduleEnum,Set<Integer> privilegeSet){
		List<ModuleBean> list=new ArrayList<ModuleBean>();
		for(ModuleEnum module:ModuleEnum.values()){
			if(moduleEnum==module.getParentModule()&&privilegeSet.contains(module.getPrivilegeEnum().getId())){
				ModuleBean bean=new ModuleBean();
				bean.setId(_id);
				bean.setName(module.getName());
				bean.setUrl(module.getUrl());
				bean.setIcon(module.getIcon());
				if(null!=module.getPrivilegeEnum()){
					bean.setPrivilegeId(module.getPrivilegeEnum().getId());
				}else{
					bean.setPrivilegeId(1);
				}
				list.add(bean);
				_id++;
			}
		}
		return list;
	}
	



}
