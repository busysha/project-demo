package com.project.api.service.impl;

import com.project.api.common.PaginationResult;
import com.project.api.dto.RankDto;
import com.project.api.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.common.common.Constants;
import com.project.common.dto.MemberCache;
import com.project.common.entity.mybatis.Member;
import com.project.common.entity.mybatis.MemberAccount;
import com.project.common.mapper.MemberMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by dell on 2016/9/7.
 */
@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private RedisService redisService;

    @Autowired
    private ConfigService configService;

    @Autowired
    private MemberAccountService memberAccountService;

    @Autowired
    private MemberAccountHistService memberAccountHistService;

    @Override
    public Member selectMemberByMobile(String mobile) {
        return memberMapper.selectMemberByMobile(mobile);
    }


    @Transactional
    @Override
    public int insert(Member member,boolean isThird) {
        memberMapper.insert(member);
        int memberId = member.getId();

        if(!isThird){
            member.setNickname(configService.getConfigVal(Constants.System.DEFAULT_NICKNAME) + memberId);
            updateBySelect(member);
        }
        memberAccountService.insert(generateMemberAccount(member));

        redisService.hashSetPut(Constants.RedisKeys.MEMBER_EXP_SET,memberId + Constants.Status.BLANK,"0");
        return memberId;
    }

    @Override
    public int insert(Member member) {
        return memberMapper.insert(member);
    }

    @Override
    public void updateBySelect(Member member) {
        memberMapper.updateByVersion(member);
    }

    @Override
    public void putMemberCache(MemberCache cache) {
        redisService.putMemberCache(Constants.RedisKeys.MEMBER_INFO_PREFIX + cache.getMemberId(),cache);
    }

    @Override
    public MemberCache getMemberCache(int memberId) {
        return redisService.getMemberCache(Constants.RedisKeys.MEMBER_INFO_PREFIX + memberId);
    }

    @Override
    public Member selectMemberByOpenId(String openId,String channel) {
        return memberMapper.selectMemberByOpenId(openId,channel);
    }

    @Override
    public Member selectMemberById(int id) {
        return memberMapper.selectByPrimaryKey(id);
    }

    @Override
    public PaginationResult<RankDto> getMemberWarmHeartRank(String key, long begin, long end) {
        Set<RedisZSetCommands.Tuple> list = redisService.getRankList(key, begin, end);
        int totalNum = (int) redisService.countRank(key);
        List<RankDto> data = new ArrayList<RankDto>();
        PaginationResult<RankDto> res = new PaginationResult<RankDto>();
        int rank = 1;
        for (RedisZSetCommands.Tuple tuple : list) {
            int memberId = Integer.parseInt(new String(tuple.getValue()));
            MemberCache memberInfo = getMemberCache(memberId);
            if(memberInfo == null) continue;
            RankDto rankDto = new RankDto();
            rankDto.setId(memberId);
            rankDto.setRank(rank);
            rankDto.setSex(memberInfo.getGender());
            rankDto.setAvatar(memberInfo.getAvatar());
            rankDto.setNickName(memberInfo.getNickName());
            rankDto.setLove(tuple.getScore().intValue());
            rankDto.setAuthUrl(configService.getAuthUrl(memberInfo));
            data.add(rankDto);
            rank++;
        }
        res.setTotalNum(totalNum);
        res.setResult(data);
        return res;
    }

    @Override
    public MemberCache updateMemberAndCache(MemberCache memberCache, Member memberInfo,int flag) {
        updateBySelect(memberInfo);
        putMemberCache(memberCache);
        if(flag == 2){
            memberAccountHistService.insertMemberAccountAndRedis(memberInfo.getId(),Constants.GoodsAction.UPDATE_INFO);
        }
        return memberCache;
    }

    @Override
    public void memberLogOut(int memberId){
        redisService.delete(Constants.RedisKeys.TICKET_PREFIX + memberId);
        MemberCache memberInfo = getMemberCache(memberId);
        memberInfo.setToken("");
        putMemberCache(memberInfo);
    }

    //=================private function========================//
    private MemberAccount generateMemberAccount(Member member){
        MemberAccount memberAccount = new MemberAccount();
        memberAccount.setMemberId(member.getId());
        memberAccount.setCreateTime(member.getCreateTime());
        memberAccount.setCreator(configService.getConfigVal(Constants.System.DEFAULT_SYSTEM_UPDATER));
        memberAccount.setVersion(0);
        memberAccount.setLevel(0);
        memberAccount.setExp(0);
        return memberAccount;
    }

}
