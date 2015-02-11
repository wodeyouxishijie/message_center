package com.doorcii.messagecenter.enums;

/**
 * 短信编码
 * @author Jacky
 * 2015年2月6日
 */
public enum MessageCodeEnum {
	
	SUCCESS(1,"成功"),FAILED(2,"失败"),DECODE_FAILED(6,"解密失败"),XML_FORMAT(7,"XML格式错误")
	,UNIQUE_ERROR(8,"唯一标签项重复或不存在"),UNKNOW_INTER(11,"未知接口类型"),TIMESTAMP_ERROR(13,"时间戳错误或请求超时"),
	PARAM_NEED(16,"必填字段为空"),ACCOUNT_ERROR(141,"账号或验证码错误"),CHANNEL_ERROR(145,"通道或子号码错误"),
	RECE_ERROR(161,"接收号码格式错误"),RECE_LESS(162,"接收号码数量过少"),RECE_EXECED(163,"接收号码数量过多"),MESSAGE_NO_ENOUGTH(164,"系统短信剩余条数不足"),
	NOTE_EXECED(172,"NOTE组数过多"),MESSAGE_LENGTH(173,"短信内容字数过多"),SENSITIVE(174,"短信内容喊敏感词");
	
	private long code;
	
	private String desc;
	
	private MessageCodeEnum(long code ,String desc) {
		this.code = code;
		this.desc = desc;
	}

	public long getCode() {
		return code;
	}

	public void setCode(long code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public static MessageCodeEnum getMessageCode(long code) {
		for(MessageCodeEnum message : MessageCodeEnum.values()) {
			if(message.getCode() == code) {
				return message;
			}
		}
		return null;
	}
	
}
