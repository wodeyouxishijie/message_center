package com.doorcii.messagecenter.service;

import javax.servlet.http.HttpServletRequest;

import com.doorcii.messagecenter.beans.UserValidResult;
import com.doorcii.messagecenter.utils.UserInfoUtil;

public class UserServiceImpl implements UserService {

	@Override
	public UserValidResult checkUserInfoAndStatus(HttpServletRequest request)
			throws Exception {
		UserValidResult result = new UserValidResult();
		String userId = UserInfoUtil.getUserId(request);
		String password = UserInfoUtil.getPassword(request);
		
		result.setUserId(userId);
		result.setPassword(password);
		return result;
	}
}
