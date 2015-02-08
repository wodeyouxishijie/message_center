package com.doorcii.messagecenter.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.doorcii.messagecenter.beans.PostMessageResult;
import com.doorcii.messagecenter.beans.UserValidResult;
import com.doorcii.messagecenter.constants.ErrorConstants;
import com.doorcii.messagecenter.constants.ParamConstant;
import com.doorcii.messagecenter.service.TemplateService;
import com.doorcii.messagecenter.service.UserService;
import com.doorcii.messagecenter.utils.UserInfoUtil;

/**
 * 接受短信消息类
 * 客户端必须传递模板ID
 * @author Jacky
 */
@Controller
public class PostMessageController {
	
	private static final Logger logger = Logger.getLogger("PROJECT-LOG");
	
	@Resource
	private UserService userService;
	
	@Resource
	private TemplateService templateService;
	
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
			int templateId = ServletRequestUtils.getIntParameter(request, ParamConstant.TEMPLATEID,0);
			String number = ServletRequestUtils.getStringParameter(request,ParamConstant.NUMBERS,null);
			if(templateId < 1 || StringUtils.isBlank(number)) {
				logger.error("user:【"+UserInfoUtil.getUserId(request)+"】 param error!templateId:"+templateId+",number:"+number);
				postResult.setSuccess(false);
				postResult.setResultCode(ErrorConstants.PARAM_ERROR);
				postResult.setResultMsg("PARAM_ERROR");
				return postResult;
			}
			Map<String,String> context = getContext(request,templateId);
			
			
		} catch(Exception e) {
			
		}
		return postResult;
	}
	
	private Map<String,String> getContext(HttpServletRequest request,int templateId) {
		Map<String,String> param = new HashMap<String,String>();
		
		return param;
	}
	
}
