package com.project.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.api.service.MemberAccountService;
import com.project.common.entity.mybatis.MemberAccount;
import com.project.common.mapper.MemberAccountMapper;

/**
 * Created by dell on 2016/9/7.
 */
@Service
public class MemberAccountServiceImpl implements MemberAccountService {
    @Autowired
    private MemberAccountMapper memberAccountMapper;

    @Override
    public void insert(MemberAccount memberAccount){
        memberAccountMapper.insert(memberAccount);
    }

    @Override
    public MemberAccount selectMemberAccount(int memberId) {
        return memberAccountMapper.selectMemberAccount(memberId);
    }

    @Override
    public void updateBySelect(MemberAccount memberAccount) {
        memberAccountMapper.updateByVersion(memberAccount);
    }
}
