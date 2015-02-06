package com.doorcii.messagecenter.enums;

public enum UserStatusEnum {
	
	SUCCESS("1-001","正常"),
	USER_NOT_EXIST("1-002","用户不存在"),
	USER_STATUS_ERROR("1-003","用户状态不正确"),
	NOT_ENOUGH("1-004","短信可用条数不足");
	
	private String code;
	
	private String desc;
	
	private UserStatusEnum(String code,String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
