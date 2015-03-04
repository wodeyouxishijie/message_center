package com.doorcii.messagecenter.service;

import javax.servlet.http.HttpServletRequest;

import com.doorcii.messagecenter.beans.UserValidResult;

public interface UserService {
	
	/**
	 * 校验用户信息和消费状态
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public UserValidResult checkUserInfoAndStatus(HttpServletRequest request) throws Exception;
	
	/**
	 * 登陆校验
	 * @param username
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public UserValidResult validateUserLogin(String username,String password) throws Exception;
	
	public boolean updateUserPassword(String password,String userName) throws Exception;
	
}
