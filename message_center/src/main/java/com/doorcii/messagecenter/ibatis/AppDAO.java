package com.doorcii.messagecenter.ibatis;

import java.util.List;

import com.doorcii.messagecenter.beans.AppsBean;

public interface AppDAO {
	
	public AppsBean queryApp(long appId,String userName,String password) throws Exception;
	
	public Integer decreaseCount(long appId,long value) throws Exception;
	
	public boolean initOverPlus(long appId,long overplus,long lastOverPlus) throws Exception;
	
	public AppsBean queryAppById(long appId) throws Exception;
	
	public long insertApp(AppsBean appsBean)throws Exception;
	
	public List<AppsBean> queryAppList(long categoryId,int needFilter) throws Exception;
	
	public int updateAppDelete(long appId,int delete) throws Exception;
}
