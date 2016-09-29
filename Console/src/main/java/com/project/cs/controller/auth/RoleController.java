package com.project.cs.controller.auth;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.project.common.entity.mybatis.CSRole;
import com.project.cs.common.Constants;
import com.project.cs.common.PrivilegeEnum;
import com.project.cs.common.annotations.CheckPrivilege;
import com.project.cs.controller.BaseController;
import com.project.cs.dto.ResultDto;
import com.project.cs.dto.bean.auth.RoleBean;
import com.project.cs.dto.search.auth.RoleSearchDto;
import com.project.cs.service.auth.CSRoleService;

@Controller
@RequestMapping("/role")
public class RoleController extends BaseController{
	
	private static final Logger logger=Logger.getLogger(RoleController.class);
	
	@Autowired
	private CSRoleService csRoleService;
	
	/**
	 * 列表
	 */
	@RequestMapping(value = "/list")
	@CheckPrivilege(privilege=PrivilegeEnum.ROLE_LIST)
	public ModelAndView list(
			@ModelAttribute RoleSearchDto roleSearchDto,
			HttpServletRequest request){
		ModelAndView mav = new ModelAndView("auth/role_list");
		if(roleSearchDto==null)
			roleSearchDto=new RoleSearchDto();
		if(roleSearchDto.getPageSize()<1)
			roleSearchDto.setPageSize(Constants.System.DEFAULT_PAGE_SIZE);
		if(roleSearchDto.getPageNo()<1)
			roleSearchDto.setPageNo(1);
		ResultDto<RoleBean> results = csRoleService.searchRole(roleSearchDto);
		mav.addObject("roleSearchDto" , roleSearchDto);
		mav.addObject("roles", results);
		return mav;
	}
	
	/**
	 * 去新增页面
	 */
	@RequestMapping(value = "/goadd")
	@CheckPrivilege(privilege=PrivilegeEnum.ROLE_ADD)
	public ModelAndView goadd(){
		ModelAndView mav = new ModelAndView("auth/role_add");
		mav.addObject("role", new RoleBean());
		mav.addObject("privilegeNodes",getPrivilegeJson(null));
		return mav;
	}
	
	/**
	 * 转换
	 * @param privileges
	 * @param privilegeIds
	 * @return
	 */
	private String getPrivilegeJson(List<Integer> havePrivileges){
		try{
			if(havePrivileges==null)
				havePrivileges=new ArrayList<Integer>();
			List<PrivilegeEnum> privileges=PrivilegeEnum.getParentParentEnums(null);
			
			StringBuilder builder=new StringBuilder();
			builder.append("[");
			int size=privileges.size();
			int temp=0;
			for(PrivilegeEnum privilege:privileges){
				builder.append("{");
				builder.append("id:").append(privilege.getId());
				PrivilegeEnum p=privilege.getParentPrivilege();
				if(p!=null){
					builder.append(",pId:").append(p.getId());
				}else{
					builder.append(",pId:").append(-1);
				}
				
				builder.append(",name:'").append(privilege.getName()).append("'");
				builder.append(",open:false");
				if(havePrivileges.contains(privilege.getId())){
					builder.append(", checked:true");
				}
				if(temp<size-1){
					builder.append("},");
				}else{
					builder.append("}");
				}
				temp++;
			}
			builder.append("]");
			logger.info("notejson:"+builder);
			return builder.toString();
		}catch(Exception e){
			logger.error("throw a error at privigelist get parnet to ztree json:",e);
		}
		return "";
	}
	
	/**
	 * 新增
	 */
	@RequestMapping(value = "/add")
	@CheckPrivilege(privilege=PrivilegeEnum.ROLE_ADD)
	public ModelAndView add(@ModelAttribute RoleBean role,
			HttpServletRequest request){
		ModelAndView mav = new ModelAndView("auth/role_add");
		String checkMessage=checkRole(1, role);
		if(StringUtils.isNotBlank(checkMessage)){
			setErrorMessage(request, checkMessage);
			return mav;
		}
		csRoleService.addRole(role, getCurrentName(request));
		mav.setViewName("redirect:list");
		return mav;
	}
	
	/**
	 * go权限设置
	 */
	@RequestMapping(value = "/goprivilegeset")
	@CheckPrivilege(privilege=PrivilegeEnum.ROLE_SET_PRIVILEGE)
	public ModelAndView goprivilegeset(HttpServletRequest request){
		int roleId = getIntParam(request, "roleId", -1);
		ModelAndView mav = new ModelAndView("auth/role_privilege_set");
		RoleBean roleBean=csRoleService.findBeanById(roleId);
		if(roleBean!=null){
			mav.addObject("roleBean",roleBean );
			mav.addObject("privilegeNodes",getPrivilegeJson(roleBean.getPrivileges()));
		}else{
			
		}
		return mav;
	}
	
	/**
	 * 权限设置
	 */
	@RequestMapping(value = "/privilegeset")
	@CheckPrivilege(privilege=PrivilegeEnum.ROLE_SET_PRIVILEGE)
	public ModelAndView privilegeset(@ModelAttribute RoleBean roleBean,HttpServletRequest request){
		ModelAndView mav = new ModelAndView("auth/role_privilege_set");
		CSRole role=csRoleService.updateRolePrivilege(roleBean.getRoleId(),roleBean.getPrivilegeSrc(),getCurrentName(request));
		if(role==null){
			setErrorMessage(request, "对象不存在");
			return mav;
		}
		mav.setViewName("redirect:list");
		return mav;
	}
	
	/**
	 * 验证
	 * @param flag   1 新增
	 * @param userBean
	 * @return
	 */
	private String checkRole(int flag,RoleBean role){
		if(role==null){
			return "参数错误";
		}
		if(flag==1){
		}
		
		if(StringUtils.isBlank(role.getRoleName())){
			return "名称不能为空";
		}
		return null;
	}
}
