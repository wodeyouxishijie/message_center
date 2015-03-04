package com.doorcii.messagecenter.beans;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 科室类
 * @author Jacky
 * 2015年2月10日
 */
public class Category {
	
	private long id;
	
	private String categoryName;
	
	private int delete;
	
	private String password;
	
	private Date gmtCreate;
	
	private Date gmtModified;
	
	public String getIdStr() {
		return String.valueOf(id);
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getDelete() {
		return delete;
	}

	public void setDelete(int delete) {
		this.delete = delete;
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

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
}
