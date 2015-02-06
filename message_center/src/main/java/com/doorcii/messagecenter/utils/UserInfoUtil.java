package com.doorcii.messagecenter.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.ServletRequestUtils;

import com.doorcii.messagecenter.constants.ParamConstant;

public class UserInfoUtil {
	
	public static String getUserId(HttpServletRequest request) {
		return ServletRequestUtils.getStringParameter(request, ParamConstant.USERID,"");
	}
	
}
