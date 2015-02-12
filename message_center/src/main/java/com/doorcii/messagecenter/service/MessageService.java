package com.doorcii.messagecenter.service;

import com.doorcii.messagecenter.beans.MesageCallback;
import com.doorcii.messagecenter.beans.MessageSendParam;
import com.doorcii.messagecenter.beans.MessageSendResult;

/**
 * 消息发送服务
 * @author Jacky
 * 2015-2-9
 */
public interface MessageService {
	
	public MessageSendResult sendMessage(MessageSendParam messageParam) throws Exception;
	
	public boolean updateMessageCallbackStatus(MesageCallback messageCallback) throws Exception ;
	
}
