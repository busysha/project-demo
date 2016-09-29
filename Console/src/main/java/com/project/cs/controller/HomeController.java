package com.project.cs.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController extends BaseController{
	
	@RequestMapping("/")
	public ModelAndView goHomePage(){
		ModelAndView mav = new ModelAndView("index");
		mav.addObject("date", new Date());
		return mav;
	}
	
	@RequestMapping(value="/record" , method=RequestMethod.POST)
	@ResponseBody
	public String recordModuleId(HttpServletRequest request){
		int moduleId=getIntParam(request,"moduleId",0);
		int selectedPMenu=getIntParam(request,"pmoduleId",0);
		setCurrentMenu(request, moduleId, selectedPMenu);
		return "{\"code\":1}";
	}
	
}
