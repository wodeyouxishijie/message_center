package com.doorcii.messagecenter.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.doorcii.messagecenter.beans.MesageCallback;
import com.doorcii.messagecenter.beans.MessageSendParam;
import com.doorcii.messagecenter.beans.MessageSendResult;
import com.doorcii.messagecenter.beans.PostMessageResult;
import com.doorcii.messagecenter.beans.TemplateDTO;
import com.doorcii.messagecenter.beans.UserValidResult;
import com.doorcii.messagecenter.constants.ErrorConstants;
import com.doorcii.messagecenter.constants.ParamConstant;
import com.doorcii.messagecenter.service.IPValidator;
import com.doorcii.messagecenter.service.MessageService;
import com.doorcii.messagecenter.service.TemplateService;
import com.doorcii.messagecenter.service.UserService;
import com.doorcii.messagecenter.utils.MessageSplitManager;
import com.doorcii.messagecenter.utils.UserInfoUtil;
import com.doorcii.messagecenter.utils.XStreamUtils;
import com.thoughtworks.xstream.XStream;

/**
 * 接受短信消息类
 * 客户端必须传递模板ID
 * @author Jacky
 */
@Controller
public class PostMessageController {
	
	private static final Logger logger = Logger.getLogger(PostMessageController.class);
	
	private static final Logger callbackLogger = Logger.getLogger("CALLBACK-LOG");
	
	@Resource
	private UserService userService;
	
	@Resource
	private TemplateService templateService;
	
	@Resource
	private MessageService messageService;
	
	@Resource
	private IPValidator iPValidator;
	
	/**
	 * 发送短信接口
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method=RequestMethod.GET,value="/web/send")
	public PostMessageResult sendMessage(HttpServletRequest request) {
		PostMessageResult postResult = new PostMessageResult();
		try {
			UserValidResult userResult = userService.checkUserInfoAndStatus(request);
			if(null == userResult || !userResult.isSuccess()) {
				postResult.setSuccess(false);
				postResult.setResultCode(userResult.getResultCode());
				postResult.setResultMsg(userResult.getResultMsg());
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
			if(StringUtils.isBlank(messageContent)) {
				logger.error("消息体空白，请检查模板是否正确！templateId="+templateId);
				postResult.setSuccess(false);
				postResult.setResultCode(ErrorConstants.SYSTEM_ERRO);
				postResult.setResultMsg("SYSTEM ERROR：Message content blank!");
				return postResult;
			}
			// 构建参数
			MessageSendParam messageParam = new MessageSendParam();
			messageParam.setAppId(userResult.getAppsBean().getId());
			messageParam.setUsername(userResult.getAppsBean().getUserName());
			messageParam.setContent(messageContent);
			messageParam.setNumbers(numbers);
			messageParam.setAppsBean(userResult.getAppsBean());
			// 短信长度计算短信条数倍数
			int bei = MessageSplitManager.calculateContentCount(messageContent);
			messageParam.setBei(bei);
			
			MessageSendResult sendResult = messageService.sendMessage(messageParam);
			postResult.setResultCode(sendResult.getResultCode());
			postResult.setResultMsg(sendResult.getResultMsg());
			postResult.setSuccess(sendResult.isSuccess());
		} catch(Exception e) {
			logger.error("",e);
			postResult.setResultCode(ErrorConstants.SYSTEM_ERRO);
			postResult.setResultMsg("SYSTEM ERROR："+e.getMessage());
			postResult.setSuccess(true);
		}
		return postResult;
	}
	
	/**
	 * 向老大测试用的
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method=RequestMethod.POST,value="/web/test")
	public PostMessageResult sendMessageTest(HttpServletRequest request) {
		PostMessageResult postResult = new PostMessageResult();
		try {
			UserValidResult userResult = userService.checkUserInfoAndStatus(request);
			if(null == userResult || !userResult.isSuccess()) {
				postResult.setSuccess(false);
				postResult.setResultCode(userResult.getResultCode());
				postResult.setResultMsg(userResult.getResultMsg());
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
			String[] num = numbers.split("\n");
			StringBuilder sb = new StringBuilder();
			for(String n: num) {
				if(StringUtils.isNotBlank(n))
					sb.append(n).append(",");
			}
			numbers = sb.toString();
			// 获取模板，渲染模板
			Map<String,String> context = getContext(request,templateId);
			String messageContent = templateService.renderTemplate(context);
			if(StringUtils.isBlank(messageContent)) {
				logger.error("消息体空白，请检查模板是否正确！templateId="+templateId);
				postResult.setSuccess(false);
				postResult.setResultCode(ErrorConstants.SYSTEM_ERRO);
				postResult.setResultMsg("SYSTEM ERROR：Message content blank!");
				return postResult;
			}
			// 构建参数
			MessageSendParam messageParam = new MessageSendParam();
			messageParam.setAppId(userResult.getAppsBean().getId());
			messageParam.setUsername(userResult.getAppsBean().getUserName());
			messageParam.setContent(messageContent);
			messageParam.setNumbers(numbers);
			messageParam.setAppsBean(userResult.getAppsBean());
			// 短信长度计算短信条数倍数
			int bei = MessageSplitManager.calculateContentCount(messageContent);
			messageParam.setBei(bei);
			
			MessageSendResult sendResult = messageService.sendMessage(messageParam);
			postResult.setResultCode(sendResult.getResultCode());
			postResult.setResultMsg(sendResult.getResultMsg());
			postResult.setSuccess(sendResult.isSuccess());
		} catch(Exception e) {
			logger.error("",e);
			postResult.setResultCode(ErrorConstants.SYSTEM_ERRO);
			postResult.setResultMsg("SYSTEM ERROR："+e.getMessage());
			postResult.setSuccess(true);
		}
		return postResult;
	}
	
	/**
	 * 短信确认回调接口
	 * @param request
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/web/callback")
	public String callbackMessage(HttpServletRequest request) {
		
		boolean pass = iPValidator.pass(request);
		if(!pass) {
			return "shit!your IP was not good!";
		}
		
		try {
			byte[] requestBinary = IOUtils.toByteArray(request.getInputStream());
			String callbackContent = new String(requestBinary);
			callbackLogger.error("callback result:"+callbackContent);
			XStream xstream = XStreamUtils.getDefaultXStream();
			MesageCallback messageCallback = (MesageCallback)xstream.fromXML(callbackContent);
			if(!messageService.updateMessageCallbackStatus(messageCallback)) {
				// 返回失败
				return "<root><code>0</code></root>";
			}
		} catch (Exception e) {
			logger.error("",e);
		}
		// 返回结果
		return "<root><code>1</code></root>";
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
