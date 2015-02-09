package com.doorcii.messagecenter.service;

import com.doorcii.messagecenter.beans.MessageSendParam;
import com.doorcii.messagecenter.beans.MessageSendResult;

public interface MessageService {
	
	public MessageSendResult sendMessage(MessageSendParam messageParam) throws Exception;
	
}
