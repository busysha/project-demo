package com.project.cs.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.common.common.Constants;
import com.project.common.entity.mybatis.CSUser;
import com.project.common.mapper.CSUserMapper;
import com.project.common.util.DateUtil;
import com.project.common.util.XlmEncryption;
import com.project.cs.dto.ResultDto;
import com.project.cs.dto.bean.auth.UserBean;
import com.project.cs.dto.search.auth.UserSearchDto;
import com.project.cs.service.CSUserService;

@Service
public class CSUserServiceImpl implements CSUserService {

	@Autowired
	private CSUserMapper userDao;
	
	@Override
	public CSUser retrieveUserByLoginName(String loginName) {
		return userDao.selectByLoginName(loginName);
	}

	@Override
	public ResultDto<UserBean> searchUser(UserSearchDto searchDto) {
		ResultDto<UserBean> result=new ResultDto<UserBean>();
		result.setCurrentPageNo(searchDto.getPageNo());
		result.setPageSize(searchDto.getPageSize());
		List<UserBean> list=new ArrayList<UserBean>();
		result.setTotalRecords((int)userDao.countBySerach(searchDto.getLoginName(), searchDto.getStatus(), searchDto.getPhone()));
		if(result.getTotalRecords()>0){
			List<CSUser> users=userDao.selectBySerach(searchDto.getLoginName(), searchDto.getStatus(), searchDto.getPhone(), (searchDto.getPageNo()-1)*searchDto.getPageSize(), searchDto.getPageSize());
			if(users!=null&&!users.isEmpty()){
				for(CSUser user:users)
					list.add(entityToBean(user));
			}
		}
		result.setResult(list);
		
		return result;
	}
	
	
	/**
	 * entity to bean
	 */
	@Override
	public UserBean entityToBean(CSUser user) {
		UserBean bean =new UserBean();
		bean.setId(user.getId());
		bean.setLoginName(user.getLoginName());
		bean.setStatus(user.getStatus());
		bean.setRealName(user.getRealName());
		bean.setMobile(user.getMobile());
		bean.setEmail(user.getEmail());
		bean.setTime(DateUtil.longToDateAll(user.getCreateTime()));
		if(StringUtils.isNotBlank(user.getRoleIds())){
			bean.setRoIds(user.getRoleIds());
		}else{
			bean.setRoIds("");
		}
		return bean;
	}

	@Override
	public void addUser(UserBean userBean, String currentName) {
		CSUser user=new CSUser();
		user.setLoginName(userBean.getLoginName());
		user.setPassword(XlmEncryption.MD5(XlmEncryption.MD5(userBean.getPassword())));
		user.setStatus(Constants.Status.STATUS_ACTIVE);
		user.setRealName(userBean.getRealName());
		user.setEmail(userBean.getEmail());
		user.setMobile(userBean.getMobile());
		user.setCreator(currentName);
		user.setCreateTime(System.currentTimeMillis());
		user.setUpdater(currentName);
		user.setUpdateTime(System.currentTimeMillis());
		userDao.insert(user);
	}

	@Override
	public UserBean findById(int id) {
		CSUser user= userDao.selectByPrimaryKey(id);
		if(user!=null)
			return entityToBean(user);
		return null;
	}

	@Override
	public CSUser updateRole(int userId, String roleIds,String currentName) {
		CSUser user=userDao.selectByPrimaryKey(userId);
		if(user==null)
			return null;
		user.setRoleIds(roleIds);
		user.setUpdater(currentName);
		user.setUpdateTime(System.currentTimeMillis());
		userDao.updateByPrimaryKey(user);
		return user;
	}

	@Override
	public CSUser updateStatus(int userId, String status, String currentName) {
		CSUser user=userDao.selectByPrimaryKey(userId);
		if(user==null)
			return null;
		user.setStatus(status);
		user.setUpdater(currentName);
		user.setUpdateTime(System.currentTimeMillis());
		userDao.updateByPrimaryKey(user);
		return user;
	}
	
}
