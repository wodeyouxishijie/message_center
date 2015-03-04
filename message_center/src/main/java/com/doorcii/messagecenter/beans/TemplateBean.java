package com.doorcii.messagecenter.beans;

import java.util.Date;

/**
 * 模板类
 * @author Jacky
 * 2015年2月9日
 */
public class TemplateBean {
	
	private long id;
	
	/**
	 * 模板名称
	 */
	private String name;
	
	/**
	 * 模板内容
	 */
	private String templateString;
	
	/**
	 * 参数名称列表
	 */
	private String paramList;
	
	/**
	 * 项目id
	 */
	private long projectId;
	
	private Date gmtCreate;
	
	private Date gmtModified;
	
	public String getIdStr() {
		return String.valueOf(id);
	}
	
	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTemplateString() {
		return templateString;
	}

	public void setTemplateString(String templateString) {
		this.templateString = templateString;
	}

	public String getParamList() {
		return paramList;
	}

	public void setParamList(String paramList) {
		this.paramList = paramList;
	}

	public long getProjectId() {
		return projectId;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}
	
}
