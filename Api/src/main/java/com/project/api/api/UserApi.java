package com.project.api.api;


import com.wordnik.swagger.annotations.ApiParam;
import com.project.api.common.help.ValidateCategory;
import com.project.common.dto.MemberCache;
import com.project.api.service.*;
import com.project.api.util.RequestUtil;
import com.project.common.util.ToolUtil;
import com.project.common.entity.mybatis.Member;
import com.project.common.entity.mybatis.MemberDevice;
import com.project.common.util.MessageUtil;
import com.project.common.util.XlmEncryption;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.project.common.common.Constants;
import com.project.api.common.MediaTypes;
import com.project.api.common.enums.BusinessTypeEnum;
import com.project.api.aspect.performance.SupportPerformanceLog;
import com.project.api.response.Response;
import com.project.api.response.ResponseFactory;
import com.project.api.service.ConfigService;
import com.project.api.service.MemberDeviceService;
import com.project.api.service.MemberService;
import com.project.api.service.SystemService;

/**
 * Created by dell on 2016/9/6.
 */
@RestController
@Api(value = "授权接口" , consumes="application/json")
@RequestMapping(value = "/v1/auth")
public class UserApi extends BaseApi{

    private static final Logger logger = Logger.getLogger(UserApi.class);

    @Autowired
    private SystemService systemService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberDeviceService memberDeviceService;

    @Autowired
    private ConfigService configService;

    @Autowired
    private AuthorizeService authorizeService;

    @Autowired
    private MemberAccountHistService memberAccountHistService;

    @Autowired
    private OtherService otherService;


    @ApiOperation(value = "用户-注册", response = Response.class,notes = "" )
    @SupportPerformanceLog
    @RequestMapping(value = "/register",method = RequestMethod.POST,produces = MediaTypes.JSON_UTF_8)
    public Response register(
            @ApiParam(value = "手机号") @RequestParam String mobile,
            @ApiParam(value = "密码需要md5") @RequestParam String password,
            @ApiParam(value = "验证码") @RequestParam String authCode,
            @ApiParam(value = "昵称") @RequestParam String nickName,
            HttpServletRequest request
    ){
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("mobile",mobile);
        map.put("password",password);
        map.put("authCode",authCode);
        map.put("nickName",nickName);
        if(!validateMandatoryMap(map) || !ToolUtil.isPhoneNum(mobile) || nickName.length() > 10){
            return ResponseFactory.createResponse(BusinessTypeEnum.PARAMS_ERROR.getCode(),BusinessTypeEnum.PARAMS_ERROR.getDescription());
        }
        //验证码处已经验证手机是否注册,所以这里不需要验证了
        if(!systemService.validateAuthCode(mobile,Constants.AUTH_ACTION_REGISTER,authCode)) return ResponseFactory.createResponse(BusinessTypeEnum.AUTH_CODE_ERROR.getCode(),BusinessTypeEnum.AUTH_CODE_ERROR.getDescription());

        //生成新用户
        long now      = System.currentTimeMillis();
        Member member = new Member();

        buildMemberAndAccount(mobile,nickName,XlmEncryption.MD5(password),configService.getConfigVal(Constants.System.DEFAULT_AVATAR_IMG),Constants.System.DEFAULT_CHANNEL_FROM_APP,null,Constants.System.DEFAULT_MEMBER_GENDER_FEMALE,now,member,true);

        systemService.deleteAuthCode(mobile,Constants.AUTH_ACTION_REGISTER,authCode);

        MemberCache memberCache = handleLoginAndRegister(member,now,request);

        return ResponseFactory.createResponse(BusinessTypeEnum.SUCCESS.getCode(),MessageUtil.buildMessage(Constants.MessageKeys.USER_REGISTER_SUCCESS),memberCache);
    }


    @ApiOperation(value = "用户-第三方注册", response = Response.class,notes = "")
    @SupportPerformanceLog
    @RequestMapping(value = "/thirdregister",method = RequestMethod.POST,produces = MediaTypes.JSON_UTF_8)
    public Response thirdRegister(
            @ApiParam(value = "昵称") @RequestParam String nickName,
            @ApiParam(value = "头像",required = false) @RequestParam(required = false) String avatar,
            @ApiParam(value = "openId") @RequestParam String openId,
            @ApiParam(value = "渠道<wechat,qq,weibo>") @RequestParam String from,
            @ApiParam(value = "性别<F/女,M/男>",required = false) @RequestParam(required = false) String sex,
            @ApiParam(value = "第三方access token") @RequestParam String token,
            HttpServletRequest request
    ){
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("nickName",nickName);
        map.put("openId",openId);
        map.put("token",token);
        map.put("from",from);

        if(!validateMandatoryMap(map) || nickName.length() > 10){
            return ResponseFactory.createResponse(BusinessTypeEnum.PARAMS_ERROR.getCode(),BusinessTypeEnum.PARAMS_ERROR.getDescription());
        }

        Member memberInfo = memberService.selectMemberByOpenId(openId, from);

        if(memberInfo != null)return ResponseFactory.createResponse(BusinessTypeEnum.USER_EXISTS.getCode(),BusinessTypeEnum.USER_EXISTS.getDescription());

        if(!ValidateCategory.validateFactory(from,token,openId))return ResponseFactory.createResponse(BusinessTypeEnum.PARAMS_ERROR.getCode(),BusinessTypeEnum.PARAMS_ERROR.getDescription());

        //生成新用户
        long now = System.currentTimeMillis();
        Member member = new Member();

        try {
            buildMemberAndAccount(null,nickName,XlmEncryption.MD5(XlmEncryption.MD5(openId)),avatar,from,openId,sex,now,member,true);
        }catch (DuplicateKeyException e){
            logger.error(Constants.LOGGER_HEAD + e);
            return ResponseFactory.createResponse(BusinessTypeEnum.USER_EXISTS.getCode(),BusinessTypeEnum.USER_EXISTS.getDescription());
        }catch (Exception e){
            logger.error(Constants.LOGGER_HEAD + e);
            return ResponseFactory.createResponse(BusinessTypeEnum.SYSTEM_ERROR.getCode(),BusinessTypeEnum.SYSTEM_ERROR.getDescription());
        }

        MemberCache memberCache = handleLoginAndRegister(member,now,request);
        return ResponseFactory.createResponse(BusinessTypeEnum.SUCCESS.getCode(), MessageUtil.buildMessage(Constants.MessageKeys.USER_REGISTER_SUCCESS),memberCache);
    }


    @ApiOperation(value = "用户-登录", response = Response.class,notes = "")
    @SupportPerformanceLog
    @RequestMapping(value = "/login",method = RequestMethod.POST,produces = MediaTypes.JSON_UTF_8)
    public Response login(
            @ApiParam(value = "手机号") @RequestParam String mobile,
            @ApiParam(value = "密码需要MD5") @RequestParam String password,
            HttpServletRequest request
    ) {
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("mobile",mobile);
        map.put("password",password);
        if(!validateMandatoryMap(map)){
            return ResponseFactory.createResponse(BusinessTypeEnum.PARAMS_ERROR.getCode(),BusinessTypeEnum.PARAMS_ERROR.getDescription());
        }
        Member memberInfo = memberService.selectMemberByMobile(mobile);
        if(memberInfo == null)return ResponseFactory.createResponse(BusinessTypeEnum.USER_NOT_EXISTS.getCode(),BusinessTypeEnum.USER_NOT_EXISTS.getDescription());

        if(!memberInfo.getPassword().equals(XlmEncryption.MD5(password)))return ResponseFactory.createResponse(BusinessTypeEnum.USER_ACCOUNT_PASSWORD_ERROR.getCode(),BusinessTypeEnum.USER_ACCOUNT_PASSWORD_ERROR.getDescription());

        long now = System.currentTimeMillis();
        MemberCache memberCache = handleLoginAndRegister(memberInfo, now, request);

        return ResponseFactory.createResponse(BusinessTypeEnum.SUCCESS.getCode(), MessageUtil.buildMessage(Constants.MessageKeys.USER_LOGIN_SUCCESS),memberCache);
    }

    @ApiOperation(value = "用户-第三方登录", response = Response.class,notes = "")
    @SupportPerformanceLog
    @RequestMapping(value = "/thirdlogin",method = RequestMethod.POST,produces = MediaTypes.JSON_UTF_8)
    public Response thirdLogin(
            @ApiParam(value = "openId") @RequestParam String openId,
            @ApiParam(value = "渠道<wechat,qq,weibo>") @RequestParam String from,
            HttpServletRequest request
    ) {
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("openId",openId);
        map.put("from",from);
        if(!validateMandatoryMap(map)){
            return ResponseFactory.createResponse(BusinessTypeEnum.PARAMS_ERROR.getCode(),BusinessTypeEnum.PARAMS_ERROR.getDescription());
        }

        Member memberInfo = memberService.selectMemberByOpenId(openId,from);
        if(memberInfo == null)return ResponseFactory.createResponse(BusinessTypeEnum.USER_NOT_EXISTS.getCode(),BusinessTypeEnum.USER_NOT_EXISTS.getDescription());

        long now     = System.currentTimeMillis();
        MemberCache memberCache = handleLoginAndRegister(memberInfo, now, request);

        return ResponseFactory.createResponse(BusinessTypeEnum.SUCCESS.getCode(), MessageUtil.buildMessage(Constants.MessageKeys.USER_LOGIN_SUCCESS),memberCache);
    }

    @ApiOperation(value = "用户-取回密码", response = Response.class,notes = "")
    @SupportPerformanceLog
    @RequestMapping(value = "/forgetpassword",method = RequestMethod.POST,produces = MediaTypes.JSON_UTF_8)
    public Response forgetPassword(
            @ApiParam(value = "手机") @RequestParam String mobile,
            @ApiParam(value = "验证码") @RequestParam String authCode,
            @ApiParam(value = "新密码") @RequestParam String passwordNew
    ) {
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("mobile",mobile);
        map.put("autCode",authCode);
        map.put("passwordNew",passwordNew);
        if(!validateMandatoryMap(map)){
            return ResponseFactory.createResponse(BusinessTypeEnum.PARAMS_ERROR.getCode(),BusinessTypeEnum.PARAMS_ERROR.getDescription());
        }

        if(!systemService.validateAuthCode(mobile,Constants.AUTH_ACTION_FORGET_PASSWORD,authCode))return ResponseFactory.createResponse(BusinessTypeEnum.AUTH_CODE_ERROR.getCode(),BusinessTypeEnum.AUTH_CODE_ERROR.getDescription());
        
        Member memberInfo = memberService.selectMemberByMobile(mobile);
        if(memberInfo == null)return ResponseFactory.createResponse(BusinessTypeEnum.USER_NOT_EXISTS.getCode(),BusinessTypeEnum.USER_NOT_EXISTS.getDescription());

        long now = System.currentTimeMillis();
        memberInfo.setPassword(XlmEncryption.MD5(passwordNew));
        memberInfo.setUpdateTime(now);
        memberInfo.setUpdater(Constants.System.DEFAULT_SYSTEM_UPDATER);
        memberService.updateBySelect(memberInfo);

        systemService.deleteAuthCode(mobile,Constants.AUTH_ACTION_FORGET_PASSWORD,authCode);
        return ResponseFactory.createResponse(BusinessTypeEnum.SUCCESS.getCode(), MessageUtil.buildMessage(Constants.MessageKeys.CHANGE_PASSWORD_SUCCESS));
    }


    //================private function==========================//
    private void buildDeviceLog(long now,HttpServletRequest request,int memberId){
        MemberDevice memberDevice = new MemberDevice();
        memberDevice.setVersion(0);
        memberDevice.setCreateTime(now);
        memberDevice.setCreator(configService.getConfigVal(Constants.System.DEFAULT_SYSTEM_UPDATER));
        memberDevice.setDeviceId(RequestUtil.getHeaderValue("deviceid",request));
        memberDevice.setDeviceType(RequestUtil.getHeaderValue("device",request));
        memberDevice.setMemberId(memberId);
        memberDeviceService.insert(memberDevice);
    }

    private MemberCache buildMemberCache(Member member,String ticket){

        MemberCache memberCache;
        memberCache= memberService.getMemberCache(member.getId());
        if(memberCache == null){
            //生成用户缓存
            memberCache = new MemberCache();
            memberCache.setChannel(member.getChannel());
            memberCache.setMemberId(member.getId());
            memberCache.setAge(member.getAge());
            memberCache.setAvatar(member.getAvatarImg());
            memberCache.setGender(member.getGender());
            memberCache.setNickName(member.getNickname());
            memberCache.setMemberType(member.getType());
            memberCache.setMobile(member.getMobile());
            memberCache.setStatus(member.getStatus());
        }
        memberCache.setToken(member.getId() + ":" + ticket);
        memberService.putMemberCache(memberCache);
        memberAccountHistService.insertMemberAccountAndRedis(member.getId(),Constants.GoodsAction.DAY_LOGIN);
        return memberCache;
    }

    private int buildMemberAndAccount(String mobile,String nickName,String password,String avatar,String from,String openId,String sex,long now,Member member,boolean isThird){
        member.setStatus(Constants.Status.STATUS_ACTIVE);
        member.setCreateTime(now);
        member.setCreator(configService.getConfigVal(Constants.System.DEFAULT_SYSTEM_UPDATER));
        member.setVersion(0);
        member.setNickname(nickName);
        member.setAvatarImg(StringUtils.isBlank(avatar) ? configService.getConfigVal(Constants.System.DEFAULT_AVATAR_IMG) : avatar);
        member.setType(Constants.System.DEFAULT_MEMBER_TYPE_NORMAL);
        member.setChannel(from);
        member.setChannelId(openId);
        member.setAge(Integer.parseInt(configService.getConfigVal(Constants.System.DEFAULT_MEMBER_AGE)));
        member.setGender(StringUtils.isBlank(sex) ? Constants.System.DEFAULT_MEMBER_GENDER_MALE : sex);
        member.setMobile(mobile);
        member.setPassword(password);
        return memberService.insert(member,isThird);
    }

    private MemberCache handleLoginAndRegister(Member member,long now,HttpServletRequest request){
        String token = ToolUtil.buildToken();
        //更新token
        authorizeService.updateUserTicket(Constants.RedisKeys.TICKET_PREFIX + member.getId(),token);
        //生成登录记录
        buildDeviceLog(now,request,member.getId());
        //生成cache
        return buildMemberCache(member, token);
    }







}
