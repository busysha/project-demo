package com.project.cs.controller;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.project.common.entity.mybatis.CSUser;
import com.project.common.util.XlmEncryption;
import com.project.cs.common.Constants;
import com.project.cs.common.ModuleEnum;
import com.project.cs.common.annotations.CheckToken;
import com.project.cs.dto.LoginBean;
import com.project.cs.dto.bean.auth.RoleBean;
import com.project.cs.dto.bean.auth.UserBean;
import com.project.cs.service.CSUserService;
import com.project.cs.service.auth.CSRoleService;

@Controller
@RequestMapping("/auth")
public class AuthController extends BaseController{
	
	
	@Autowired
	private CSUserService csUserService;
	
	@Autowired
	private CSRoleService csRoleService;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView goLoginPage(HttpServletRequest request){
		ModelAndView mav = new ModelAndView("login");
		mav.addObject("loginBean", new LoginBean());
		return mav;
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request){
		ModelAndView mav = new ModelAndView("forward:/auth/login");
		getSessionInfo(request).setUser(null);
		return mav;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@CheckToken(viewName = "login" )
	public ModelAndView login(
			@ModelAttribute LoginBean bean,
			HttpServletRequest request
  			){
		ModelAndView mav = new ModelAndView();
		mav.addObject("loginBean", bean);
		if(StringUtils.isEmpty(bean.getLoginName()) || StringUtils.isEmpty(bean.getPassword())){
			mav.setViewName("login");
			setMessage(request, Constants.Message.LOGIN_NAME_PASSWORD_INCORRECT);
			return mav;
		}else{
			
			CSUser user = csUserService.retrieveUserByLoginName(bean.getLoginName());
			if(user == null){
				mav.setViewName("login");
				setMessage(request, Constants.Message.LOGIN_NAME_PASSWORD_INCORRECT);
				return mav;
			}
			
			if( !Constants.Status.STATUS_ACTIVE.equals(user.getStatus())){
				mav.setViewName("login");
				setMessage(request, Constants.Message.LOGIN_NAME_PASSWORD_INCORRECT);
				return mav;
			}
			
			if( !XlmEncryption.MD5(bean.getPassword()).equals(user.getPassword())){
				mav.setViewName("login");
				setMessage(request, Constants.Message.LOGIN_NAME_PASSWORD_INCORRECT);
				return mav;
			}else{
				UserBean userBean=csUserService.entityToBean(user);
				String[] arr=userBean.getRoIds().split(",");
				Set<Integer> set=new HashSet<Integer>();
				for(String roleId:arr){
					if(StringUtils.isNotBlank(roleId)){
						RoleBean role=csRoleService.findBeanById(Integer.valueOf(roleId));
						if(role!=null){
							for(Integer privilege:role.getPrivileges()){
								set.add(privilege);
							}
						}
					}
				}
				getSessionInfo(request).setUser(userBean);
				getSessionInfo(request).setPrivilege(set);
				getSessionInfo(request).setModules(ModuleEnum.getAllModules(set));
				mav.setViewName("redirect:/");
			}
		}
		
		return mav;
	}
	
}
