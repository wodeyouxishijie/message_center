package com.doorcii.messagecenter.beans;

import java.util.Date;

/**
 * 项目发送短信规则表 
 * @author Jacky
 * 2015年2月6日
 */
public class AppRules {
	
	/**
	 * 主键
	 */
	private long id;
	/**
	 * 项目ID
	 */
	private long appId;
	
	/**
	 * 频率id
	 */
	private int frequencyType;
	
	/**
	 * 剩余条数
	 */
	private long max;
	
	private Date gmtCreate;
	
	private Date gmtModified;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getAppId() {
		return appId;
	}

	public void setAppId(long appId) {
		this.appId = appId;
	}

	public int getFrequencyType() {
		return frequencyType;
	}

	public void setFrequencyType(int frequencyType) {
		this.frequencyType = frequencyType;
	}
	
	public long getMax() {
		return max;
	}

	public void setMax(long max) {
		this.max = max;
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
	
}
