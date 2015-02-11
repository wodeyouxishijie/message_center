package com.doorcii.messagecenter.beans;

public class MessageSendParam {
	
	private String content;
	
	private String numbers;
	
	private String username;
	
	private long appId;
	
	private int counts;
	
	private AppsBean appsBean;
	
	private int bei = 1;
	
	public int getBei() {
		return bei;
	}

	public void setBei(int bei) {
		this.bei = bei;
	}

	public AppsBean getAppsBean() {
		return appsBean;
	}

	public void setAppsBean(AppsBean appsBean) {
		this.appsBean = appsBean;
	}

	public long getAppId() {
		return appId;
	}

	public void setAppId(long appId) {
		this.appId = appId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getNumbers() {
		return numbers;
	}

	public void setNumbers(String numbers) {
		this.numbers = numbers;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
