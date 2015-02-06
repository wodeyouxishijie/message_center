package com.doorcii.messagecenter.beans;

/**
 * 短信发送返回结果
 * @author Jacky
 * 2015年2月6日
 */
public class MessageSendResult {
	
	private Long resultCode;
	
	private String resultMsg;
	
	public boolean isSuccess() {
		if(null != resultCode && resultCode.equals("1")) return true;
		else return false;
	}

	public Long getResultCode() {
		return resultCode;
	}

	public void setResultCode(Long resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
	
}