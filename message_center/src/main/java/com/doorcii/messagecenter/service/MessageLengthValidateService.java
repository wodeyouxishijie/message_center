package com.doorcii.messagecenter.service;

/**
 * 短信长度验证规则
 * @author Jacky
 * 2015-2-9
 */
public interface MessageLengthValidateService {
	
	/**
	 * 根据规则验证是否可以发送这么长的短信
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public String validateLength(String content) throws Exception;
}
