package com.project.api.api;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.api.aspect.performance.SupportPerformanceLog;
import com.project.api.common.MediaTypes;
import com.project.api.common.enums.BusinessTypeEnum;
import com.project.api.dto.TreeDto;
import com.project.api.response.Response;
import com.project.api.response.ResponseFactory;
import com.project.api.service.ConfigService;
import com.project.api.service.MemberService;
import com.project.api.service.OtherService;
import com.project.api.util.RequestUtil;
import com.project.common.common.Constants;
import com.project.common.dto.MemberCache;
import com.project.common.entity.mybatis.Member;
import com.project.common.util.MessageUtil;
import com.project.common.util.QiNiuUtil;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;


/**
 * Created by dell on 2016/9/12.
 */
@RestController
@Api(value = "用户接口" , consumes="application/json")
@RequestMapping(value = "/v1/member")
public class MemberApi extends BaseApi{
    private static final Logger logger = Logger.getLogger(UserApi.class);

    @Autowired
    private MemberService memberService;

    @Autowired
    private ConfigService configService;

    @Autowired
    private OtherService otherService;


    @ApiOperation(value = "用户-修改资料", response = Response.class,notes = "" )
    @SupportPerformanceLog
    @RequestMapping(value = "/update",method = RequestMethod.POST,produces = MediaTypes.JSON_UTF_8)
    public Response update(
            @ApiParam(value = "昵称",required = false) @RequestParam(required = false) String nickName,
            @ApiParam(value = "头像",required = false) @RequestParam(required = false) String avatar,
            @ApiParam(value = "性别<F/女,M/男>",required = false) @RequestParam(required = false) String sex,
            @ApiParam(value = "年龄",required = false) @RequestParam(required = false) String age,
            HttpServletRequest request
    ){
        int memberId = RequestUtil.getMemberIdFromHeader(request);
        Member memberInfo = memberService.selectMemberById(memberId);

        if(null == memberInfo)return ResponseFactory.createResponse(BusinessTypeEnum.USER_NOT_EXISTS.getCode(),BusinessTypeEnum.USER_NOT_EXISTS.getDescription());
        MemberCache memberCache = memberService.getMemberCache(memberId);

        if(StringUtils.isNotBlank(nickName)){
            memberInfo.setNickname(nickName);
            memberCache.setNickName(nickName);
        }

        if (StringUtils.isNotBlank(avatar)){
            memberInfo.setAvatarImg(avatar);
            memberCache.setAvatar(avatar);

        }
        int flag = 0 ;
        if(StringUtils.isNotBlank(sex) && (sex.equals(Constants.System.DEFAULT_MEMBER_GENDER_FEMALE) || sex.equals(Constants.System.DEFAULT_MEMBER_GENDER_MALE))){
            memberInfo.setGender(sex);
            memberCache.setGender(sex);
            flag++;
        }

        if (StringUtils.isNotBlank(age)){
            try{
                int old = Integer.parseInt(age);
                memberInfo.setAge(old);
                memberCache.setAge(old);
                flag++;
            }catch (Exception e){
                logger.info(Constants.LOGGER_HEAD + "age parse fail");
                return ResponseFactory.createResponse(BusinessTypeEnum.PARAMS_ERROR.getCode(),BusinessTypeEnum.PARAMS_ERROR.getDescription());
            }
        }

        memberCache = memberService.updateMemberAndCache(memberCache,memberInfo,flag);

        return ResponseFactory.createResponse(BusinessTypeEnum.SUCCESS.getCode(),BusinessTypeEnum.SUCCESS.getDescription(),memberCache);
    }

    @ApiOperation(value = "用户-树信息", response = Response.class,notes = "" )
    @SupportPerformanceLog
    @RequestMapping(value = "/tree",method = RequestMethod.GET,produces = MediaTypes.JSON_UTF_8)
    public Response tree(
            HttpServletRequest request
    ){
        int memberId = RequestUtil.getMemberIdFromHeader(request);
        TreeDto res = otherService.selectTreeInfo(memberId);
        return ResponseFactory.createResponse(BusinessTypeEnum.SUCCESS.getCode(),BusinessTypeEnum.SUCCESS.getDescription(),res);
    }



    @ApiOperation(value = "用户-登出", response = Response.class,notes = "" )
    @SupportPerformanceLog
    @RequestMapping(value = "/logout",method = RequestMethod.POST,produces = MediaTypes.JSON_UTF_8)
    public Response logOut(
            HttpServletRequest request
    ){
        int memberId = RequestUtil.getMemberIdFromHeader(request);
        memberService.memberLogOut(memberId);
        return ResponseFactory.createDefaultSuccessResponse();
    }

    @ApiOperation(value = "用户-上传token", response = Response.class,notes = "" )
    @SupportPerformanceLog
    @RequestMapping(value = "/sevenoxtoken",method = RequestMethod.POST,produces = MediaTypes.JSON_UTF_8)
    public Response severnOxToken(){
        Map<String,String> map = new HashMap<String,String>();
        map.put("token", QiNiuUtil.getToken());
        map.put("fileName", UUID.randomUUID().toString().replaceAll("-",""));
        map.put("url",QiNiuUtil.getUrl());
        return ResponseFactory.createResponse(BusinessTypeEnum.SUCCESS.getCode(),BusinessTypeEnum.SUCCESS.getDescription(),map);
    }

    @ApiOperation(value = "用户-举报", response = Response.class,notes = "" )
    @SupportPerformanceLog
    @RequestMapping(value = "/report",method = RequestMethod.GET,produces = MediaTypes.JSON_UTF_8)
    public Response tree(
            @ApiParam(value = "关联id") @RequestParam String referenceId,
            @ApiParam(value = "类型<评论comment>") @RequestParam String referenceType,
            HttpServletRequest request
    ){

        return ResponseFactory.createResponse(BusinessTypeEnum.SUCCESS.getCode(),MessageUtil.buildMessage(Constants.MessageKeys.REPORT_SUCCESS));
    }
}
