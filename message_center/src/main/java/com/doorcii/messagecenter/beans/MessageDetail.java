package com.doorcii.messagecenter.beans;

import java.util.Date;

/**
 * 短信详细记录
 * @author Jacky
 * 2015年2月6日
 */
public class MessageDetail {
	
	/**
	 * 主键
	 */
	private long id;
	
	/**
	 * 短信内容
	 */
	private String content;
	
	/**
	 * 接受号码
	 */
	private String receNumber;
	
	/**
	 * 发送时间
	 */
	private Date sendDate;
	
	/**
	 * 短信发送状态
	 */
	private int sendStatus;
	
	/**
	 * 短信回执状态
	 */
	private int callbackStatus;
	
	/**
	 * 短信发送不成功时，错误信息
	 */
	private String errorMsg;
	
	/**
	 * 科室ID
	 */
	private String categoryId;
	
	/**
	 * 应用ID
	 */
	private long appId;
	
	/**
	 * 应用名称
	 */
	private String appName;
	
	/**
	 * 用户ID
	 */
	private String userId;
	
	private Date gmtCreate;
	
	private Date gmtModified;
	
	private int counts = 1;
	
	public int getCounts() {
		return counts;
	}

	public void setCounts(int counts) {
		this.counts = counts;
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

	public String getReceNumber() {
		return receNumber;
	}

	public void setReceNumber(String receNumber) {
		this.receNumber = receNumber;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getCallbackStatus() {
		return callbackStatus;
	}

	public void setCallbackStatus(int callbackStatus) {
		this.callbackStatus = callbackStatus;
	}

	public long getAppId() {
		return appId;
	}

	public void setAppId(long appId) {
		this.appId = appId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public int getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(int sendStatus) {
		this.sendStatus = sendStatus;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	@Override
	public String toString() {
		return "MessageDetail [id=" + id + ", content=" + content
				+ ", receNumber=" + receNumber + ", sendDate=" + sendDate
				+ ", sendStatus=" + sendStatus + ", callbackStatus="
				+ callbackStatus + ", errorMsg=" + errorMsg + ", categoryId="
				+ categoryId +  ", appId="
				+ appId + ", appName=" + appName + ", userId=" + userId
				+ ", gmtCreate=" + gmtCreate + ", gmtModified=" + gmtModified
				+ "]";
	}
	
}
