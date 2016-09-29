package com.project.api.service.impl;

import com.project.common.common.Constants;
import com.project.api.service.MemberAccountHistService;
import com.project.api.service.MemberAccountService;
import com.project.api.service.RedisService;
import com.project.common.util.DateUtil;
import com.project.common.util.ToolUtil;
import com.project.common.entity.mybatis.GoodsAction;
import com.project.common.entity.mybatis.MemberAccount;
import com.project.common.entity.mybatis.MemberAccountHist;
import com.project.common.mapper.GoodsActionMapper;
import com.project.common.mapper.MemberAccountHistMapper;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by dell on 2016/9/13.
 */
@Service
public class MemberAccountHistServiceImpl implements MemberAccountHistService {
    private static final Logger logger = Logger.getLogger(MemberAccountHistServiceImpl.class);

    @Autowired
    private GoodsActionMapper goodsActionMapper;

    @Autowired
    private MemberAccountHistMapper memberAccountHistMapper;

    @Autowired
    private RedisService redisService;

    @Autowired
    private MemberAccountService memberAccountService;

    @Override
    public void insertMemberAccountAndRedis(int memberId, String actionType) {
        List<GoodsAction> actions = goodsActionMapper.getActionByActionType(actionType, Constants.Status.STATUS_ACTIVE);
        long now = System.currentTimeMillis();
        for (GoodsAction action : actions) {
            int putNum = 0;
            String timeQuery = buildTimeQuery(action.getLimitTime(),now);
            String queryKey  = action.getActionName() + "_" + action.getGoodType() + "_" + memberId + "_" + timeQuery;
            //取值判断限制
            String res = redisService.hashSetGet(Constants.RedisKeys.MEMBER_ACTION_SET, queryKey);
            if(StringUtils.isNotBlank(res)){
                int nowNum = Integer.parseInt(res);
                putNum = nowNum + action.getGetNum();
                if(action.getLimitTotal() == Constants.GoodsAction.MAGIC_NUMBER || putNum <= action.getLimitTotal()){
                    categoryInsert(action,memberId,now,queryKey,putNum);
                }
            }else{
                putNum += action.getGetNum();
                categoryInsert(action,memberId,now,queryKey,putNum);
            }
        }
        logger.info(Constants.LOGGER_HEAD + "运行货币获取系统");
    }

    //==============private function=========================//

    // TODO: 2016/9/13 需要队列来保证并发的问题
    private void categoryInsert(GoodsAction action,int memberId,long now,String queryKey,int putNum){
        String uid = memberId + Constants.Status.BLANK;
        insertMemberAccountHist(action,memberId,now);
        MemberAccount memberAccount = memberAccountService.selectMemberAccount(memberId);
        //增加经验值
        int totalExp = increaseExp(uid,action.getExp());
        int lv = ToolUtil.convertLvByExp(totalExp);
        memberAccount.setLevel(lv);
        memberAccount.setExp(totalExp); 
        if(action.getGoodType().equals(Constants.System.ACTION_TYPE_TIME_BANK)){
            memberAccount.setTimebank(memberAccount.getTimebank() + action.getGetNum());
        }
        //更新数据库
        memberAccountService.updateBySelect(memberAccount);
        //汇总数据存放
        redisService.hashSetPut(Constants.RedisKeys.MEMBER_ACTION_SET,queryKey,putNum + Constants.Status.BLANK);
    }

    private void insertMemberAccountHist(GoodsAction action,int memberId,long now){
        //数据库入库
        //对应goods
        MemberAccountHist memberAccountHist = new MemberAccountHist();
        memberAccountHist.setMemberId(memberId);
        memberAccountHist.setVersion(0);
        memberAccountHist.setCreateTime(now);
        memberAccountHist.setCreator(Constants.System.DEFAULT_SYSTEM_UPDATER);
        memberAccountHist.setActionType(action.getActionName());
        memberAccountHist.setType(action.getGoodType());
        memberAccountHist.setNum(action.getGetNum());
        memberAccountHistMapper.insert(memberAccountHist);
        //经验值
        memberAccountHist.setType(Constants.System.ACTION_TYPE_EXP);
        memberAccountHist.setNum(action.getExp());
        memberAccountHistMapper.insert(memberAccountHist);
    }

    private int increaseExp(String memberId,int exp){
        return (int) redisService.hashSetIncr(Constants.RedisKeys.MEMBER_EXP_SET, memberId, exp);
    }

    private String buildTimeQuery(String type,long now){
        String timeQuery = "";
        if(type.equals("day")){
            timeQuery = DateUtil.longToDate(now);
        }else if(type.equals("week")){
            // TODO: 2016/9/13
        }else if(type.equals("once")){
            timeQuery = "once";
        }
        return timeQuery;
    }
}
