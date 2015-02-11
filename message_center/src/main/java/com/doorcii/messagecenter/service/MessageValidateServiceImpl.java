package com.doorcii.messagecenter.service;

import com.doorcii.messagecenter.beans.AppsBean;

public class MessageValidateServiceImpl implements
		MessageValidateService {

	@Override
	public String validateMessage(String content,AppsBean appsBean,long count) throws Exception {
		int maxLength = appsBean.getMaxLength();
		// 长度校验
		if(content.length() > maxLength) {
			return "短信内容超出所限定的长度！";
		}
		// 剩余条数,每天晚上跑任务去重置条数
		long overPlus = appsBean.getOverPlus();
		if(overPlus < count) {
			return "软件短信剩余条数不足！";
		}
		return null;
	}

}
