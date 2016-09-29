package com.project.api.service;

/**
 * Created by dell on 2016/9/13.
 */
public interface MemberAccountHistService {
    void insertMemberAccountAndRedis(int memberId,String actionType);
}
