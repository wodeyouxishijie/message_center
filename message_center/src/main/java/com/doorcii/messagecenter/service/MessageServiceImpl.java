package com.doorcii.messagecenter.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.doorcii.messagecenter.beans.MessageDetail;
import com.doorcii.messagecenter.beans.MessageInfo;
import com.doorcii.messagecenter.beans.MessageSendParam;
import com.doorcii.messagecenter.beans.MessageSendResult;
import com.doorcii.messagecenter.constants.ErrorConstants;
import com.doorcii.messagecenter.enums.CallbackStatus;
import com.doorcii.messagecenter.httpcore.MessageHttpCore;
import com.doorcii.messagecenter.ibatis.MessageDAO;
import com.doorcii.messagecenter.utils.MessageSplitManager;

public class MessageServiceImpl implements MessageService {
	
	private static final Logger logger = Logger.getLogger(MessageServiceImpl.class);
	@Resource
	private MessageValidateService messageValidateService;
	
	@Resource
	private MessageDAO messageDAO;
	
	@Resource
	private MessageHttpCore messageHttpCore;
	
	@SuppressWarnings("unchecked")
	@Override
	public MessageSendResult sendMessage(MessageSendParam messageParam)
			throws Exception {
		
		MessageSendResult result = new MessageSendResult();
		ArrayList<String>[] numberList = MessageSplitManager.splitNumbers(messageParam.getNumbers());
		if(null == numberList || numberList.length < 1) {
			result.setResultMsg("短信发送号码为空！");
			result.setResultCode(ErrorConstants.NUMBER_NO_ONE);
			return result;
		}
		
		// 短信长度规则验证
		String errorMsg = messageValidateService.validateMessage(messageParam.getAppId(),messageParam.getContent());
		if(StringUtils.isNotBlank(errorMsg)) {
			result.setResultMsg(errorMsg);
			result.setResultCode(ErrorConstants.MESSAGE_ILLEGAL);
			return result;
		}
		//MessageSplitManager
		for(ArrayList<String> list : numberList) {
			List<MessageDetail> messageList = new ArrayList<MessageDetail>();
			for(String number : list) {
				MessageDetail md = new MessageDetail();
				md.setAppId(messageParam.getAppId());
				md.setCallbackStatus(CallbackStatus.INIT);
				md.setReceNumber(number);
				md.setContent(messageParam.getContent());
				md.setSendDate(new Date());
				md.setUserId(messageParam.getUsername());
				md.setSendStatus(CallbackStatus.INIT);
				try {
					long id = messageDAO.insertOneMessage(md);
					md.setId(id);
					messageList.add(md);
				} catch(Exception e) {
					logger.error("短信实体插入失败."+md,e);
				}
			}
			
			String messageContent = MessageSplitManager.getSMSString(messageList);
			
			// 短信发送实体
			MessageInfo mi = new MessageInfo();
			mi.setContent(messageParam.getContent());
			mi.setRece_account(messageContent);
			
			MessageSendResult sendResult = messageHttpCore.sendMessage(mi);
			result.setResultMsg(sendResult.getResultMsg());
			result.setResultCode(sendResult.getResultCode());
			if(!sendResult.isSuccess()) {
				// 不成功的时候回去更新短信发送状态
				for(MessageDetail md : messageList)
					messageDAO.updateOneMessageStatus(md.getId(), CallbackStatus.FAILED,result.getResultCode());
			}
		}
		
		return result;
	}

}
