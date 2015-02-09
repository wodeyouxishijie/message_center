package com.doorcii.messagecenter.service;

import java.util.Map;

import com.doorcii.messagecenter.beans.TemplateDTO;

/**
 * 根据模板获得可配置的参数
 * @author Jacky
 * 2015-2-8
 */
public interface TemplateService {
	
	public TemplateDTO getParamList(long templateId) throws Exception;
	
	public String renderTemplate(Map<String,String> context) throws Exception;
	
}
