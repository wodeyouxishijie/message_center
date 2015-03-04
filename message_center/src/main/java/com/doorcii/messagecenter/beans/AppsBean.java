package com.doorcii.messagecenter.beans;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 项目bean
 * @author Jacky
 * 2015年2月6日
 */
public class AppsBean {
	
	/**
	 * 主键
	 */
	private long id;
	
	/**
	 * 项目名称
	 */
	private String projectName;
	
	/**
	 * 负责科室
	 */
	private String categoryId;
	
	private String user;
	
	/**
	 * 负责人联系方式
	 */
	private String userPhone;
	
	private long overPlus;
	
	private Date gmtCreate;
	
	private Date gmtModifed;

	private String userName;
	
	private String password;
	
	private int maxLength;
	
	private int deleted;
	
	public String getIdStr() {
		return String.valueOf(id);
	}
	
	public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}

	public int getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getOverPlus() {
		return overPlus;
	}

	public void setOverPlus(long overPlus) {
		this.overPlus = overPlus;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}
	
	public String getCreateStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(gmtCreate);
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModifed() {
		return gmtModifed;
	}

	public void setGmtModifed(Date gmtModifed) {
		this.gmtModifed = gmtModifed;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	
}
