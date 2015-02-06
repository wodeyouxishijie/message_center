package com.doorcii.messagecenter.beans;

/**
 * 短信发送包裹类
 * @author Jacky
 * 2015年2月6日
 */
public class DeliveryInfo {
	
	private String service_type;
	
	private String timestamp;
	
	private String account;
	
	private String vilidate_code;
	
	private MessageInfo main;

	public String getService_type() {
		return service_type;
	}

	public void setService_type(String service_type) {
		this.service_type = service_type;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getVilidate_code() {
		return vilidate_code;
	}

	public void setVilidate_code(String vilidate_code) {
		this.vilidate_code = vilidate_code;
	}

	public MessageInfo getMain() {
		return main;
	}

	public void setMain(MessageInfo main) {
		this.main = main;
	}
	
}
