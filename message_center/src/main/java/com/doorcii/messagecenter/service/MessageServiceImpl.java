package com.doorcii.messagecenter.service;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.doorcii.messagecenter.beans.MessageSendParam;
import com.doorcii.messagecenter.beans.MessageSendResult;
import com.doorcii.messagecenter.constants.ErrorConstants;
import com.doorcii.messagecenter.utils.MessageSplitManager;

public class MessageServiceImpl implements MessageService {

	@Resource
	private MessageLengthValidateService messageLengthValidateService;
	
	@Override
	public MessageSendResult sendMessage(MessageSendParam messageParam)
			throws Exception {
		MessageSendResult result = new MessageSendResult();
		ArrayList<String>[] numberList = MessageSplitManager.splitNumbers(messageParam.getNumbers());
		if(null == numberList || numberList.length < 1) {
			result.setResultMsg("短信内容过长！");
			result.setResultCode(ErrorConstants.NUMBER_NO_ONE);
			return result;
		}
		
		// 短信长度规则验证
		String errorCode = messageLengthValidateService.validateLength(messageParam.getContent());
		if(StringUtils.isNotBlank(errorCode)) {
			result.setResultMsg("短信内容过长！");
			result.setResultCode(ErrorConstants.CONTENT_TOO_LONG);
			return result;
		}
		
		
		return result;
	}

}
