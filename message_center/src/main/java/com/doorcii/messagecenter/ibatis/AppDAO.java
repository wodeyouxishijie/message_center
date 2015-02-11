package com.doorcii.messagecenter.ibatis;

import com.doorcii.messagecenter.beans.AppsBean;

public interface AppDAO {
	
	public AppsBean queryApp(long appId,String userName,String password) throws Exception;
	
	public Integer decreaseCount(long appId,long value) throws Exception;
	
}
