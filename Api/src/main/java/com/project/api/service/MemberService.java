package com.project.api.service;

import com.project.api.common.PaginationResult;
import com.project.common.dto.MemberCache;
import com.project.api.dto.RankDto;
import com.project.common.entity.mybatis.Member;

/**
 * Created by dell on 2016/9/7.
 */
public interface MemberService {
    /**
     * mobile get member
     * @param mobile /
     * @return
     */
    Member selectMemberByMobile(String mobile);

    /**
     * 插入新用户 区分第三方/直接注册
     * @param member /
     * @return
     */
    int insert(Member member,boolean isThird);

    /**
     * 正常插入
     * @param member /
     * @return
     */
    int insert(Member member);

    /**
     * 乐观锁更新
     * @param member /
     */
    void updateBySelect(Member member);

    /**
     * 生成用户缓存
     * @param cache /
     */
    void putMemberCache(MemberCache cache);

    /**
     * 获取用户缓存
     * @param memberId /
     */
    MemberCache getMemberCache(int memberId);

    /**
     * 根据openId获取 用户
     * @param openId /
     * @return
     */
    Member selectMemberByOpenId(String openId,String channel);

    /**
     * 主键获取 member
     * @param id /
     * @return
     */
    Member selectMemberById(int id);

    /**
     * 爱心排序
     * @param key //
     * @param begin //
     * @param end //
     * @return res
     */
    PaginationResult<RankDto> getMemberWarmHeartRank(String key, long begin, long end);

    /**
     * 更新资料和缓存
     * @param memberCache
     * @param member
     * @param flag 是否获得爱心值
     * @return
     */
    MemberCache updateMemberAndCache(MemberCache memberCache,Member member,int flag);

    /**
     * 用户登出
     * @param memberId
     */
    void memberLogOut(int memberId);
}
