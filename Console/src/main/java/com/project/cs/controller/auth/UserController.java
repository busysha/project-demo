package com.project.cs.controller.auth;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.project.common.common.ConsoleConstants;
import com.project.common.entity.mybatis.CSRole;
import com.project.common.entity.mybatis.CSUser;
import com.project.cs.common.Constants;
import com.project.cs.common.PrivilegeEnum;
import com.project.cs.common.annotations.CheckPrivilege;
import com.project.cs.controller.BaseController;
import com.project.cs.dto.JSONBean;
import com.project.cs.dto.ResultDto;
import com.project.cs.dto.bean.auth.UserBean;
import com.project.cs.dto.search.auth.UserSearchDto;
import com.project.cs.service.CSUserService;
import com.project.cs.service.auth.CSRoleService;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController{
	
	private static final Logger logger=Logger.getLogger(UserController.class);
	
	@Autowired
	private CSUserService csUserService;
	
	@Autowired
	private CSRoleService roleService;
	
	/**
	 * 列表
	 */
	@RequestMapping(value = "/list")
	@CheckPrivilege(privilege=PrivilegeEnum.USER_LIST)
	public ModelAndView listMembers(
			@ModelAttribute UserSearchDto userSearchDto,
			HttpServletRequest request){
		ModelAndView mav = new ModelAndView("auth/user_list");
		if(userSearchDto==null)
			userSearchDto=new UserSearchDto();
		if(userSearchDto.getPageSize()<1)
			userSearchDto.setPageSize(Constants.System.DEFAULT_PAGE_SIZE);
		if(userSearchDto.getPageNo()<1)
			userSearchDto.setPageNo(1);
		ResultDto<UserBean> results = csUserService.searchUser(userSearchDto);
		mav.addObject("userSearchDto" , userSearchDto);
		mav.addObject("users", results);
		return mav;
	}
	
	/**
	 * 去新增页面
	 */
	@RequestMapping(value = "/goadd")
	@CheckPrivilege(privilege=PrivilegeEnum.USER_ADD)
	public ModelAndView goadd(){
		ModelAndView mav = new ModelAndView("auth/user_add");
		mav.addObject("userBean", new UserBean());
		//mav.addObject("roleNotes" , getRoleJson(roleService.getAllRoles(),new HashSet()));
		return mav;
	}
	
	/**
	 * 转换
	 */
	private String getRoleJson(List<CSRole> roles,Set set){
		try{
			if(roles==null||roles.isEmpty())
				return "[]";
			StringBuilder builder=new StringBuilder();
			builder.append("[");
			int size=roles.size();
			int temp=0;
			for(CSRole role:roles){
				builder.append("{");
				builder.append("id:").append(role.getRoleId());
				builder.append(",pId:").append(0);
				builder.append(",name:'").append(role.getRoleName()).append("'");
				builder.append(",open:true");
				if(set.contains(role.getRoleId())){
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
			logger.info("json:"+builder);
			return builder.toString();
		}catch(Exception e){
			logger.error("throw a error at user get rolelist  to ztree json:",e);
		}
		return "";
	}
	
	/**
	 * 新增
	 */
	@RequestMapping(value = "/add")
	@CheckPrivilege(privilege=PrivilegeEnum.USER_ADD)
	public ModelAndView add(@ModelAttribute UserBean userBean,
			HttpServletRequest request){
		ModelAndView mav = new ModelAndView("auth/user_add");
		String checkMessage=checkUser(1, userBean);
		if(StringUtils.isNotBlank(checkMessage)){
			setErrorMessage(request, checkMessage);
			return mav;
		}
		csUserService.addUser(userBean, getCurrentName(request));
		mav.setViewName("redirect:list");
		return mav;
	}
	
	/**
	 * 验证
	 * @param flag   1 新增
	 * @param userBean
	 * @return
	 */
	private String checkUser(int flag,UserBean userBean){
		if(userBean==null){
			return "参数错误";
		}
		if(flag==1){
			if(StringUtils.isBlank(userBean.getLoginName())){
				return "用户名不符合";
			}
			if(null!=csUserService.retrieveUserByLoginName(userBean.getLoginName())){
				return "用户名已经存在";
			}
			
			if(StringUtils.isBlank(userBean.getPassword())){
				return "密码为空";
			}
		}
		
		if(StringUtils.isBlank(userBean.getRealName())){
			return "真实姓名不能为空";
		}
		
		if(StringUtils.isBlank(userBean.getMobile())){
			return "手机号码不能为空";
		}
		
		return null;
	}
	
	
	/**
	 * 去新增页面
	 */
	@RequestMapping(value = "/goroleset")
	@CheckPrivilege(privilege=PrivilegeEnum.USER_ROLE_SET)
	public ModelAndView goroleset(HttpServletRequest request){
		int userId = getIntParam(request, "userId", -1);
		ModelAndView mav = new ModelAndView("auth/user_role_set");
		UserBean user=csUserService.findById(userId);
		if(user!=null){
			mav.addObject("userBean" ,user);
			String[] arr=user.getRoIds().split(",");
			Set<Integer> set=new HashSet<Integer>();
			for(String rId:arr){
				if(StringUtils.isNotBlank(rId))
					set.add(Integer.valueOf(rId));
			}
			mav.addObject("roleNotes" , getRoleJson(roleService.getAllRoles(),set));
		}else{
			mav.addObject("userBean" ,new UserBean());
			mav.addObject("roleNotes" , "");
		}
		return mav;
	}
	
	/**
	 * 去新增页面
	 */
	@RequestMapping(value = "/roleset")
	@CheckPrivilege(privilege=PrivilegeEnum.USER_ROLE_SET)
	public ModelAndView roleset(@ModelAttribute UserBean userBean,HttpServletRequest request){
		ModelAndView mav = new ModelAndView("auth/user_role_set");
		CSUser user=csUserService.updateRole(userBean.getId(),userBean.getRoIds(),getCurrentName(request));
		if(user==null){
			setErrorMessage(request, "对象不存在");
			return mav;
		}
		mav.setViewName("redirect:list");
		return mav;
	}
	
	 @RequestMapping(value = "/active")
	    @ResponseBody
	    public JSONBean active(@RequestParam(value = "id") String id,HttpServletRequest request){
	        JSONBean jsonBean = new JSONBean();
	        int userId = Integer.parseInt(id);
	        CSUser user = csUserService.updateStatus(userId, Constants.Status.STATUS_ACTIVE, getCurrentName(request));
	        jsonBean.setCode(ConsoleConstants.MessageKey.SUCCESS.getCode());
	        jsonBean.setMessage(ConsoleConstants.MessageKey.SUCCESS.getMessage());
	        return jsonBean;
	    }

	    @RequestMapping(value = "/inactive")
	    @ResponseBody
	    public JSONBean inActive(@RequestParam(value = "id") String id,HttpServletRequest request){
	        JSONBean jsonBean = new JSONBean();
	        int orgId = Integer.parseInt(id);
	        int userId = Integer.parseInt(id);
	        CSUser user = csUserService.updateStatus(userId, Constants.Status.STATUS_INACTIVE, getCurrentName(request));
	        return jsonBean;
	    }
}
