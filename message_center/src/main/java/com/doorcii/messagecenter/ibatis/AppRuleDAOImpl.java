package com.doorcii.messagecenter.ibatis;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.doorcii.messagecenter.beans.AppRules;

public class AppRuleDAOImpl extends SqlMapClientDaoSupport implements AppRuleDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<AppRules> queryAppRules() throws Exception {
		return this.getSqlMapClientTemplate().queryForList("message_center.queryAppRulesList");
	}
	
}
