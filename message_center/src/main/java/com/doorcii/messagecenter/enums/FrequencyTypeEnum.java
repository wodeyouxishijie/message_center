package com.doorcii.messagecenter.enums;

/**
 * 短信发送频率规则
 * @author Jacky
 * 2015年2月6日
 */
public enum FrequencyTypeEnum {
	
	MOUNTH(1,"按月"),WEEK(2,"按周"),DAY(3,"按天");
	
	private int id;
	
	private String desc;
	
	private FrequencyTypeEnum (int id,String desc) {
		this.id = id;
		this.desc = desc;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
