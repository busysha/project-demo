package com.project.api.service;

import com.project.common.entity.mybatis.MemberAccount;

/**
 * Created by dell on 2016/9/7.
 */
public interface MemberAccountService {
    void insert(MemberAccount memberAccount);

    MemberAccount selectMemberAccount(int memberId);

    void updateBySelect(MemberAccount memberAccount);
}
