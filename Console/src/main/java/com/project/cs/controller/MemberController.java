package com.project.cs.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.project.common.entity.mybatis.Member;
import com.project.cs.common.Constants;
import com.project.cs.common.PrivilegeEnum;
import com.project.cs.common.annotations.CheckPrivilege;
import com.project.cs.dto.MemberQueryBean;
import com.project.cs.dto.ResultDto;
import com.project.cs.service.MemberService;

@Controller
@RequestMapping("/member")
public class MemberController extends BaseController{
	
	
	@Autowired
	private MemberService memberService;
	
	@RequestMapping(value = "/list")
	@CheckPrivilege(privilege=PrivilegeEnum.MEMBER_LIST)
	public ModelAndView listMembers(
			@ModelAttribute MemberQueryBean memberQueryBean,
			HttpServletRequest request){
		ModelAndView mav = new ModelAndView("member/memberList");
		int pageNo = memberQueryBean.getPageNo();
		if(pageNo <= 0) pageNo = 1;
		
		//if the status is null , retrieve all status 
		ResultDto<Member> results = memberService.retrieveMembersByStatus(memberQueryBean, pageNo, Constants.System.DEFAULT_PAGE_SIZE);
		
		mav.addObject("memberQueryBean" , memberQueryBean);
		mav.addObject("members", results);
		return mav;
	}
	
	@RequestMapping(value = "/status")
	@CheckPrivilege(privilege=PrivilegeEnum.MEMBER_LIST)
	public ModelAndView updateMemberStatus(
			@RequestParam(required = true) int mid,
			@RequestParam(required = true) String action ,
			HttpServletRequest request){
		ModelAndView mav = new ModelAndView("forward:/member/list");
		String status = null;
		if("active".equals(action)){
			status = Constants.Status.STATUS_ACTIVE;
		}else{
			status = Constants.Status.STATUS_INACTIVE;
		}
		
		memberService.updateMemberStatus(mid, status);
		
		setMessage(request, Constants.Message.SYSTEM_SUCCESS);
		
		return mav;
	}
	//TODO 修改用户密码
	
}
