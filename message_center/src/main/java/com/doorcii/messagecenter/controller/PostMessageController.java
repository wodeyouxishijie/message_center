package com.doorcii.messagecenter.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.doorcii.messagecenter.beans.PostMessageResult;
import com.doorcii.messagecenter.beans.UserValidResult;
import com.doorcii.messagecenter.constants.ErrorConstants;
import com.doorcii.messagecenter.constants.ParamConstant;
import com.doorcii.messagecenter.service.UserService;
import com.doorcii.messagecenter.utils.UserInfoUtil;

@Controller
public class PostMessageController {
	
	private static final Logger logger = Logger.getLogger("PROJECT-LOG");
	
	@Resource
	private UserService userService;
	
	@ResponseBody
	@RequestMapping("/web/send")
	public PostMessageResult sendMessage(HttpServletRequest request) {
		PostMessageResult postResult = new PostMessageResult();
		try {
			UserValidResult userResult = userService.checkUserInfoAndStatus(request);
			if(null == userResult || !userResult.isSuccess()) {
				postResult.setSuccess(false);
				postResult.setResultCode(userResult.getResultCode());
				return postResult;
			}
			String content = request.getParameter(ParamConstant.CONTENT);
			String number = request.getParameter(ParamConstant.NUMBERS);
			if(StringUtils.isBlank(content) || StringUtils.isBlank(number)) {
				logger.error("user:【"+UserInfoUtil.getUserId(request)+"】 param error!content:"+content+",number:"+number);
				postResult.setSuccess(false);
				postResult.setResultCode(ErrorConstants.PARAM_ERROR);
				postResult.setResultMsg("PARAM_ERROR");
				return postResult;
			}
			
			
			
		} catch(Exception e) {
			
		}
		return postResult;
	}
	
}
