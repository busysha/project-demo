package com.project.cs.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.project.common.common.Constants;
import com.project.common.dto.MemberCache;
import com.project.cs.service.RedisService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.common.entity.mybatis.Member;
import com.project.common.mapper.MemberMapper;
import com.project.cs.dto.MemberQueryBean;
import com.project.cs.dto.ResultDto;
import com.project.cs.service.MemberService;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberMapper memberDao;

	@Autowired
	private RedisService redisService;
	
	@Override
	public ResultDto<Member> retrieveMembersByStatus(MemberQueryBean memberQueryBean,int pageNo, int pageSize) {
		
		Map<String , Object> params = new HashMap<String,Object>();
		
		if(memberQueryBean != null){
			
			if( !StringUtils.isEmpty(memberQueryBean.getId()) ){
				params.put("id", memberQueryBean.getId());
			}
			
			if( !StringUtils.isEmpty(memberQueryBean.getNickName()) ){
				params.put("nickname", memberQueryBean.getNickName());
			}
			
			if( !StringUtils.isEmpty(memberQueryBean.getStatus())){
				params.put("status", memberQueryBean.getStatus());
			}
		}
		
		List<Member> results = memberDao.selectMemberByParams(params, (pageNo-1) * pageSize, pageSize);
		int totalRecords = memberDao.countByParams(params);
		
		ResultDto<Member> dto = new ResultDto<Member>();
		
		dto.setResult(results);
		dto.setCurrentPageNo(pageNo);
		dto.setPageSize(pageSize);
		dto.setTotalRecords(totalRecords);
		
		return dto;
	}

	@Override
	@Transactional
	public void updateMemberStatus(int memberId, String status) {
		Member member = memberDao.selectByPrimaryKey(memberId);
		if(member!=null){
			member.setStatus(status);
			memberDao.updateByVersion(member);
		}

	}

	@Override
	public void updateMemberInfo(Member member) {
		memberDao.updateByVersion(member);
	}

	@Override
	public ResultDto<Member> retrievePendingApprovalMembers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Member selectMemberById(int memberId) {
		return memberDao.selectByPrimaryKey(memberId);
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
	public List<Member> selectMemberByType(String type) {
		return memberDao.selectMemberByType(type);
	}

	@Override
	public Member selectMemberByName(String name) {
		return memberDao.selectMemberByName(name);
	}
}
