package com.doorcii.messagecenter.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

public class IPValidator {
	
	public String ipAddress = "122.225.223.196";
	
	public boolean pass(HttpServletRequest request) {
		String ip = request.getRemoteAddr();
		return StringUtils.isNotBlank(ip) && ip.equals(ipAddress);
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	
}
