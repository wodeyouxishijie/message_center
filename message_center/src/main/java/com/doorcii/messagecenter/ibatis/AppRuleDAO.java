package com.doorcii.messagecenter.ibatis;

import java.util.List;

import com.doorcii.messagecenter.beans.AppRules;

public interface AppRuleDAO {
	
	
	public List<AppRules> queryAppRules() throws Exception;
	
}
