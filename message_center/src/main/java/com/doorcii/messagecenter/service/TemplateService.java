package com.doorcii.messagecenter.service;

import java.util.List;

/**
 * 根据模板获得可配置的参数
 * @author Jacky
 * 2015-2-8
 */
public interface TemplateService {
	
	public List<String> getParamList(int templateId) throws Exception;
	
}
