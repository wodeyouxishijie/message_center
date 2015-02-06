package com.doorcii.messagecenter.beans;

import java.util.Date;

/**
 * 项目bean
 * @author Jacky
 * 2015年2月6日
 */
public class AppsBean {
	
	/**
	 * 主键
	 */
	private long id;
	
	/**
	 * 项目名称
	 */
	private String projectName;
	
	
	private Date gmtCreate;
	
	private Date gmtModifed;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModifed() {
		return gmtModifed;
	}

	public void setGmtModifed(Date gmtModifed) {
		this.gmtModifed = gmtModifed;
	}
	
}
