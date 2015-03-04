package com.doorcii.messagecenter.ibatis;

import java.util.List;

import com.doorcii.messagecenter.beans.AppRules;

public interface AppRuleDAO {
	
	
	public List<AppRules> queryAppRules() throws Exception;
	
	public List<AppRules> queryAppRules(String appId,int pageNum,int pageSize) throws Exception;
	
	public long countAppRules(String appId) throws Exception;
	
	public long insertAppRules(AppRules appRules) throws Exception;
	
	public int deleteAppRules(long id) throws Exception;
}
