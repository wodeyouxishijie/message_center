package com.doorcii.messagecenter.httpcore;

import com.doorcii.messagecenter.beans.MessageInfo;
import com.doorcii.messagecenter.beans.MessageSendResult;

/**
 * 短信发送核心接口
 * @author Jacky
 * 2015年2月6日
 */
public interface MessageHttpCore {
	
	
	/**
	 * 发送短信
	 * @param messageInfo 短信实体
	 * @return result code 
	 * @throws Exception
	 */
	public MessageSendResult sendMessage(MessageInfo messageInfo) throws Exception;
	
}
