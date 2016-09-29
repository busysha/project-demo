package com.project.cs.controller;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.common.common.ConsoleConstants;
import com.project.common.entity.mybatis.Config;
import com.project.common.util.ObjectMapperSingle;
import com.project.cs.dto.JSONBean;
import com.project.cs.service.ConfigService;
import com.project.cs.service.RegionService;

/**
 * Created by dell on 2016/9/26.
 */
@Controller
@RequestMapping("/system")
public class SystemController extends BaseController{

    @Autowired
    private ConfigService configService;
    
    @Autowired
    private RegionService regionService;

    @RequestMapping(value = "/base",method = RequestMethod.GET)
    public ModelAndView list(){
        ModelAndView modelAndView = new ModelAndView("system/baseresource");

        List<Config> list = configService.selectAllConfig();

        modelAndView.addObject("res",list);
        return modelAndView;
    }


    //========return json=======================================//
    @RequestMapping(value = "/info",method = RequestMethod.POST)
    @ResponseBody
    public Config info(@RequestParam("id") String id){
        JSONBean jsonBean = new JSONBean();
        jsonBean.setCode(ConsoleConstants.MessageKey.SUCCESS.getCode());
        jsonBean.setMessage(ConsoleConstants.MessageKey.SUCCESS.getMessage());
        return configService.selectById(Integer.parseInt(id));
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public JSONBean update(@RequestParam("source") String source, HttpServletRequest request){
        JSONBean jsonBean = new JSONBean();
        jsonBean.setCode(ConsoleConstants.MessageKey.SUCCESS.getCode());
        jsonBean.setMessage(ConsoleConstants.MessageKey.SUCCESS.getMessage());

        ObjectMapper objectMapper = ObjectMapperSingle.getInstance();
        try {
            Config result = objectMapper.readValue(source, Config.class);
            result.setUpdateTime(System.currentTimeMillis());
            result.setUpdater(getSessionInfo(request).getUser().getLoginName());
            configService.updateConfig(result);
        } catch (IOException e) {

        }
        return jsonBean;
    }

    /**
	 * 获取省
	 */
	@RequestMapping(value = "/getAllProvice",method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getAllProvice() {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("provinces",regionService.getCityByProvinceId(0));
		return map;
	}
	
	/**
	 * 获取市
	 */
	@RequestMapping(value = "/getCityByProvinceId",method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getCityByProvinceId(int provinceId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("citys",regionService.getCityByProvinceId(provinceId));
		return map;
	}
	

}
