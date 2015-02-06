package com.doorcii.messagecenter.beans;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("root")
public class MessageReturn {
	
	private Long code;

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}
	
}
