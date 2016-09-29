package com.project.api.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.project.api.dto.BaseResourceDto;
import com.project.api.service.*;
import com.project.api.util.PropertiesUtil;
import com.project.common.entity.mybatis.AppUpdate;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.project.api.aspect.performance.SupportPerformanceLog;
import com.project.api.common.MediaTypes;
import com.project.api.common.enums.BusinessTypeEnum;
import com.project.api.dto.RegionDto;
import com.project.api.response.Response;
import com.project.api.response.ResponseFactory;
import com.project.api.util.RequestUtil;
import com.project.common.common.Constants;
import com.project.common.entity.mybatis.Member;
import com.project.common.util.MessageUtil;
import com.project.common.util.ToolUtil;


/**
 * Created by dell on 2016/9/7.
 */
@Api(value = "系统接口" , consumes="application/json")
@RestController
@RequestMapping(value = "/v1/system")
public class SystemApi extends BaseApi{
    private static final Logger logger = Logger.getLogger(UserApi.class);

    @Autowired
    private SystemService systemService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private ConfigService configService;

    @Autowired
    private RegionService regionService;

    @Autowired
    private OtherService otherService;

    @Autowired
    private RedisService redisService;


    @ApiOperation(value = "系统-发送验证码", response = Response.class,notes = "")
    @SupportPerformanceLog
    @RequestMapping(value = "/getauthcode",method = RequestMethod.POST,produces = MediaTypes.JSON_UTF_8)
    public Response getAuthCode(@ApiParam(value = "手机号")@RequestParam String mobile, @ApiParam(value = "获取验证码类型<注册/register,取回密码/forgetPassword,参加活动确认/attendActivity>",required = true)@RequestParam String authType) {
        if(!ToolUtil.validateAuthCodeAction(authType) || !ToolUtil.isPhoneNum(mobile))return ResponseFactory.createResponse(BusinessTypeEnum.PARAMS_ERROR.getCode(),BusinessTypeEnum.PARAMS_ERROR.getDescription());
        //注册动作验证是否已经注册过
        if(authType.equals(Constants.AUTH_ACTION_REGISTER)){
            Member memberInfo = memberService.selectMemberByMobile(mobile);
            if(memberInfo != null)return ResponseFactory.createResponse(BusinessTypeEnum.MOBILE_EXISTS.getCode(),BusinessTypeEnum.MOBILE_EXISTS.getDescription());
        }else if(authType.equals(Constants.AUTH_ACTION_FORGET_PASSWORD)){
            //忘记密码验证是否有这个用户
            Member memberInfo = memberService.selectMemberByMobile(mobile);
            if(memberInfo == null)return ResponseFactory.createResponse(BusinessTypeEnum.USER_NOT_EXISTS.getCode(),BusinessTypeEnum.USER_NOT_EXISTS.getDescription());
        }

        String authCode = systemService.getAuthCode(mobile, authType);
        if(StringUtils.isNotBlank(authCode)){
            return ResponseFactory.createResponse(BusinessTypeEnum.AUTH_CODE_EXISTS.getCode(),BusinessTypeEnum.AUTH_CODE_EXISTS.getDescription());
        }

        authCode = systemService.generateAuthCode(Integer.parseInt(configService.getConfigVal(Constants.System.DEFAULT_VALIDATE_LENGTH)));
        boolean flag = systemService.putAuthCode(mobile,authType,authCode,Long.parseLong(configService.getConfigVal(Constants.System.DEFAULT_SMS_EXPIRE)));

        if(flag){
            return ResponseFactory.createResponse(BusinessTypeEnum.SUCCESS.getCode(),MessageUtil.buildMessage(Constants.MessageKeys.AUTH_CODE_GET_SUCCESS));
        }else{
            return ResponseFactory.createResponse(BusinessTypeEnum.SYSTEM_ERROR.getCode(),BusinessTypeEnum.SYSTEM_ERROR.getDescription());
        }
    }


    @ApiOperation(value = "系统-地区列表", response = Response.class,notes = "")
    @SupportPerformanceLog
    @RequestMapping(value = "/regionlist",method = RequestMethod.GET,produces = MediaTypes.JSON_UTF_8)
    public Response regionList() {

        List<RegionDto> list = regionService.selectRegionSortByFirstLetter();

        return ResponseFactory.createResponse(BusinessTypeEnum.SUCCESS.getCode(),BusinessTypeEnum.SUCCESS.getDescription(),list);
    }

    @ApiOperation(value = "系统-搜索地区", response = Response.class,notes = "")
    @SupportPerformanceLog
    @RequestMapping(value = "/regionsearch",method = RequestMethod.GET,produces = MediaTypes.JSON_UTF_8)
    public Response regionSearch(
            @ApiParam(value = "城市名") @RequestParam String cityName
    ) {
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("cityName",cityName);
        if(!validateMandatoryMap(map)){
            return ResponseFactory.createResponse(BusinessTypeEnum.PARAMS_ERROR.getCode(),BusinessTypeEnum.PARAMS_ERROR.getDescription());
        }

        List<RegionDto> list = regionService.selectRegionByName(cityName);

        return ResponseFactory.createResponse(BusinessTypeEnum.SUCCESS.getCode(),BusinessTypeEnum.SUCCESS.getDescription(),list);
    }

    @ApiOperation(value = "系统-地区更新开关", response = Response.class,notes = "")
    @SupportPerformanceLog
    @RequestMapping(value = "/isregionupdate",method = RequestMethod.POST,produces = MediaTypes.JSON_UTF_8)
    public Response isRegionUpdate() {
        Map<String,String> map = new HashMap<String,String>();
        map.put("isNeedUpdate",configService.getConfigVal(Constants.System.DEFAULT_REGION_UPDATE));
        return ResponseFactory.createResponse(BusinessTypeEnum.SUCCESS.getCode(),BusinessTypeEnum.SUCCESS.getDescription(),map);
    }

    @ApiOperation(value = "系统-反馈", response = Response.class,notes = "")
    @SupportPerformanceLog
    @RequestMapping(value = "/feedback",method = RequestMethod.POST,produces = MediaTypes.JSON_UTF_8)
    public Response feedBack(
            @ApiParam(value = "称呼",required = false) @RequestParam(required = false,defaultValue = "") String memberName,
            @ApiParam(value = "联系电话",required = false) @RequestParam(required = false,defaultValue = "") String mobile,
            @ApiParam(value = "内容") @RequestParam String content,
            HttpServletRequest request
    ) {
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("content",content);
        if(!validateMandatoryMap(map)){
            return ResponseFactory.createResponse(BusinessTypeEnum.PARAMS_ERROR.getCode(),BusinessTypeEnum.PARAMS_ERROR.getDescription());
        }
        int memberId = RequestUtil.getMemberIdFromHeader(request);
        otherService.insertFeedBack(memberName,mobile,memberId,content);
        
        return ResponseFactory.createResponse(BusinessTypeEnum.SUCCESS.getCode(),BusinessTypeEnum.SUCCESS.getDescription());
    }

    @ApiOperation(value = "系统-更新", response = Response.class,notes = "")
    @SupportPerformanceLog
    @RequestMapping(value = "/appupdate",method = RequestMethod.GET,produces = MediaTypes.JSON_UTF_8)
    public Response appUpdate(
            HttpServletRequest request
    ) {
        String noFormatVersion = RequestUtil.getHeaderValue("version",request);
        String platform = RequestUtil.getHeaderValue("device",request);

        if(!"ios".equals(platform) && !"android".equals(platform))return ResponseFactory.createResponse(BusinessTypeEnum.PARAMS_ERROR.getCode(),BusinessTypeEnum.PARAMS_ERROR.getDescription());
        double version = Double.parseDouble(noFormatVersion.replaceFirst("\\.",""));
        AppUpdate updateInfo = otherService.selectUpdateInfoByPlatform(platform);
        double nowVersion = Double.parseDouble(updateInfo.getNowVersion().replaceFirst("\\.",""));

        if(version < nowVersion){
            return ResponseFactory.createResponse(BusinessTypeEnum.SUCCESS.getCode(),BusinessTypeEnum.SUCCESS.getDescription(),updateInfo);
        }

        return ResponseFactory.createResponse(BusinessTypeEnum.APP_NO_NEED_UPDATE.getCode(),BusinessTypeEnum.APP_NO_NEED_UPDATE.getDescription());
    }

    @ApiOperation(value = "系统-app端基础数据" , response = Response.class,notes = "")
    @SupportPerformanceLog
    @RequestMapping(value = "/baseresource",method = RequestMethod.GET,produces = MediaTypes.JSON_UTF_8)
    public Response baseResource() {
        BaseResourceDto baseResourceDto = new BaseResourceDto();
        baseResourceDto.setAboutMe(configService.getConfigVal(Constants.System.DEFAULT_ABOUT_ME));
        return ResponseFactory.createResponse(BusinessTypeEnum.SUCCESS.getCode(), BusinessTypeEnum.SUCCESS.getDescription(),baseResourceDto);
    }

}
