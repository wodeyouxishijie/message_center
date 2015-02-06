package com.doorcii.messagecenter.beans;

/**
 * 发送短信HTTP接口返回结果
 * @author Jacky
 * 2015年2月6日
 */
public class PostMessageResult {

	private boolean success;
	
	private String resultCode;
	
	private String resultMsg;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
	
}
