package com.doorcii.messagecenter.service;

import javax.servlet.http.HttpServletRequest;

import com.doorcii.messagecenter.beans.UserValidResult;

public class UserServiceImpl implements UserService {

	@Override
	public UserValidResult checkUserInfoAndStatus(HttpServletRequest request)
			throws Exception {
		UserValidResult result = new UserValidResult();
		
		return result;
	}
}
