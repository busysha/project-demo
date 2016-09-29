package com.project.cs.service;

import java.util.List;

import com.project.common.dto.MemberCache;
import com.project.common.entity.mybatis.Member;
import com.project.cs.dto.MemberQueryBean;
import com.project.cs.dto.ResultDto;

public interface MemberService {
	ResultDto<Member> retrieveMembersByStatus(MemberQueryBean memberQueryBean,int pageNo, int pageSize);

	void updateMemberStatus(int memberId, String status);

	void updateMemberInfo(Member member);
	
	ResultDto<Member> retrievePendingApprovalMembers();

	Member selectMemberById(int memberId);
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
	
	List<Member> selectMemberByType(String type);
	
	Member selectMemberByName(String name);
}
