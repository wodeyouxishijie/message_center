package com.doorcii.messagecenter.beans;

/**
 * 短信发送返回结果
 * @author Jacky
 * 2015年2月6日
 */
public class MessageSendResult {
	
	private String resultCode;
	
	private String resultMsg;
	
	public boolean isSuccess() {
		if(null != resultCode && resultCode.equals("1")) return true;
		else return false;
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

	@Override
	public String toString() {
		return "MessageSendResult [resultCode=" + resultCode + ", resultMsg="
				+ resultMsg + "]";
	}
	
}
