package com.doorcii.messagecenter.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import com.doorcii.messagecenter.beans.AppsBean;
import com.doorcii.messagecenter.beans.Category;
import com.doorcii.messagecenter.beans.UserValidResult;
import com.doorcii.messagecenter.constants.ErrorConstants;
import com.doorcii.messagecenter.ibatis.AppDAO;
import com.doorcii.messagecenter.ibatis.CategoryDAO;
import com.doorcii.messagecenter.utils.UserInfoUtil;

public class UserServiceImpl implements UserService {
	
	private AppDAO appDAO;
	
	private CategoryDAO categoryDAO;
	
	@Override
	public UserValidResult checkUserInfoAndStatus(HttpServletRequest request)
			throws Exception {
		UserValidResult result = new UserValidResult();
		String userId = UserInfoUtil.getUserId(request);
		String password = UserInfoUtil.getPassword(request);
		long appId = UserInfoUtil.getAppId(request);
		if(StringUtils.isBlank(userId) || StringUtils.isBlank(password) || appId < 1L) {
			result.setResultCode(ErrorConstants.PARAM_ERROR);
			result.setResultMsg("发送账户信息不正确！");
			return result;
		}
		AppsBean appBean = appDAO.queryApp(appId, userId, password);
		if(null == appBean) {
			result.setSuccess(false);
			result.setResultCode(ErrorConstants.PARAM_ERROR);
			result.setResultMsg("用户信息不存在，或者账号密码不正确！");
			return result;
		}
		result.setAppsBean(appBean);
		result.setSuccess(true);
		return result;
	}

	@Override
	public UserValidResult validateUserLogin(String username, String password)
			throws Exception {
		UserValidResult userResult = new UserValidResult();
		Category category = categoryDAO.queryUserInfo(username, password);
		userResult.setCategory(category);
		userResult.setSuccess(category!=null);
		userResult.setResultCode(category!=null?null:ErrorConstants.USER_NOT_EXIST);
		userResult.setResultMsg(category!=null?null:"用户不存在或者密码不正确");
		return userResult;
	}

	@Override
	public boolean updateUserPassword(String password, String userName)
			throws Exception {
		return categoryDAO.updateCategory(userName, password) > 0;
	}

	public void setAppDAO(AppDAO appDAO) {
		this.appDAO = appDAO;
	}

	public void setCategoryDAO(CategoryDAO categoryDAO) {
		this.categoryDAO = categoryDAO;
	}
	
}
