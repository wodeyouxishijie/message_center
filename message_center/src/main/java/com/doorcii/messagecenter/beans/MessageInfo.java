package com.doorcii.messagecenter.beans;

import com.thoughtworks.xstream.annotations.XStreamAlias;


/**
 * 短信
 * @author Jacky
 * 2015年2月6日
 */
@XStreamAlias("main")
public class MessageInfo {
	
	/**
	 * 短信内容
	 */
	private String content;
	
	/**
	 * 短信接收账户 id1,13786678963;id2,xxxxxxx
	 */
	private String rece_account;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public String getRece_account() {
		return rece_account;
	}

	public void setRece_account(String rece_account) {
		this.rece_account = rece_account;
	}

	@Override
	public String toString() {
		return "MessageInfo [content=" + content + ", rece_account="
				+ rece_account + "]";
	}
	
}
