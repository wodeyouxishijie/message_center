package com.doorcii.messagecenter.beans;

import java.util.List;

public class TemplateDTO {
	
	private TemplateBean templateBean;
	
	private List<String> paramList;

	public TemplateBean getTemplateBean() {
		return templateBean;
	}

	public void setTemplateBean(TemplateBean templateBean) {
		this.templateBean = templateBean;
	}

	public List<String> getParamList() {
		return paramList;
	}

	public void setParamList(List<String> paramList) {
		this.paramList = paramList;
	}
	
}
