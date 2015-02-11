package com.doorcii.messagecenter.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import com.doorcii.messagecenter.beans.AppsBean;
import com.doorcii.messagecenter.beans.UserValidResult;
import com.doorcii.messagecenter.constants.ErrorConstants;
import com.doorcii.messagecenter.ibatis.AppDAO;
import com.doorcii.messagecenter.utils.UserInfoUtil;

public class UserServiceImpl implements UserService {
	
	private AppDAO appDAO;
	
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
		
		AppsBean appBean = appDAO.queryApp(appId, userId, DigestUtils.md5Hex(password));
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

	public void setAppDAO(AppDAO appDAO) {
		this.appDAO = appDAO;
	}
	
}
