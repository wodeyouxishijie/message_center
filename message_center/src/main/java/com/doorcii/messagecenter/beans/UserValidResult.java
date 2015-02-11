package com.doorcii.messagecenter.beans;

/**
 * 用户状态校验结果
 * @author Jacky
 * 2015年2月6日
 */

public class UserValidResult {
	
	private boolean success;
	
	private String resultCode;
	
	private String resultMsg;
	
	private AppsBean appsBean;
	
	public AppsBean getAppsBean() {
		return appsBean;
	}

	public void setAppsBean(AppsBean appsBean) {
		this.appsBean = appsBean;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
	
}
