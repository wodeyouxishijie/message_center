package com.doorcii.messagecenter.service;

import com.doorcii.messagecenter.beans.AppsBean;

/**
 * 短信长度验证规则
 * 短信发送次数验证
 * 用户配置规则验证
 * @author Jacky
 * 2015-2-9
 */
public interface MessageValidateService {
	
	/**
	 * 根据规则验证是否可以发送这么长的短信
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public String validateMessage(String content,AppsBean appsBean,long count) throws Exception;
}
