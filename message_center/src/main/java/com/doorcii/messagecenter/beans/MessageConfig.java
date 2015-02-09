package com.doorcii.messagecenter.beans;

public class MessageConfig {
	
	/**
	 * 短信服务Url
	 */
	private final String url;
	
	/**
	 * 用户id
	 */
	private final String userId;
	
	/**
	 * 短信服务密码
	 */
	private final String validateCode;
	
	/**
	 * 服务类型
	 */
	private String serviceType;
	
	/**
	 * 发送请求编码
	 */
	private String encoding;
	
	public static String DEFAULT_CHAR_SET = "utf-8";
	
	public MessageConfig(String url,String userId,String validateCode,String serviceType) {
		this.url = url;
		this.userId = userId;
		this.validateCode = validateCode;
		this.serviceType = serviceType;
	}

	
	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getUrl() {
		return url;
	}

	public String getUserId() {
		return userId;
	}

	public String getValidateCode() {
		return validateCode;
	}
	
}
