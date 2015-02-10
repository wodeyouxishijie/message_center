package com.doorcii.messagecenter.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.doorcii.messagecenter.beans.MessageSendParam;
import com.doorcii.messagecenter.beans.MessageSendResult;
import com.doorcii.messagecenter.beans.PostMessageResult;
import com.doorcii.messagecenter.beans.TemplateDTO;
import com.doorcii.messagecenter.beans.UserValidResult;
import com.doorcii.messagecenter.constants.ErrorConstants;
import com.doorcii.messagecenter.constants.ParamConstant;
import com.doorcii.messagecenter.service.MessageService;
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
	
	private static final Logger logger = Logger.getLogger(PostMessageController.class);
	
	@Resource
	private UserService userService;
	
	@Resource
	private TemplateService templateService;
	
	@Resource
	private MessageService messageService;
	
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
			long templateId = ServletRequestUtils.getLongParameter(request, ParamConstant.TEMPLATEID,0L);
			String numbers = ServletRequestUtils.getStringParameter(request,ParamConstant.NUMBERS,null);
			if(templateId < 1 || StringUtils.isBlank(numbers)) {
				logger.error("user:【"+UserInfoUtil.getUserId(request)+"】 param error!templateId:"+templateId+",number:"+numbers);
				postResult.setSuccess(false);
				postResult.setResultCode(ErrorConstants.PARAM_ERROR);
				postResult.setResultMsg("PARAM_ERROR");
				return postResult;
			}
			// 获取模板，渲染模板
			Map<String,String> context = getContext(request,templateId);
			String messageContent = templateService.renderTemplate(context);
			// 构建参数
			MessageSendParam messageParam = new MessageSendParam();
			messageParam.setUsername(userResult.getUserId());
			messageParam.setContent(messageContent);
			messageParam.setNumbers(numbers);
			
			MessageSendResult sendResult = messageService.sendMessage(messageParam);
			postResult.setResultCode(sendResult.getResultCode());
			postResult.setResultMsg(sendResult.getResultMsg());
			
		} catch(Exception e) {
			logger.error("",e);
			postResult.setResultCode(ErrorConstants.SYSTEM_ERRO);
			postResult.setResultMsg("SYSTEM ERROR："+e.getMessage());
		}
		return postResult;
	}
	
	private Map<String,String> getContext(HttpServletRequest request,long templateId) {
		Map<String,String> param = new HashMap<String,String>();
		try {
			TemplateDTO templateDTO = templateService.getParamList(templateId);
			if(null != templateDTO && CollectionUtils.isNotEmpty(templateDTO.getParamList())) {
				for(String paramName : templateDTO.getParamList()) {
					if(StringUtils.isNotBlank(paramName)) {
						String paramValue = ServletRequestUtils.getStringParameter(request,paramName,null);
						if(StringUtils.isNotBlank(paramValue))param.put(paramName, paramValue);
					}
				}
				// 把模板内容放到这里
				param.put(null, templateDTO.getTemplateBean().getTemplateString());
			}
		} catch (Exception e) {
			logger.error("查询模板参数失败！",e);
		}
		return param;
	}
	
}
