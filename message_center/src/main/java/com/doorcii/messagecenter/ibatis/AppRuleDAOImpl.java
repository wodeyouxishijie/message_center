package com.doorcii.messagecenter.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.doorcii.messagecenter.beans.AppRules;

public class AppRuleDAOImpl extends SqlMapClientDaoSupport implements AppRuleDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<AppRules> queryAppRules() throws Exception {
		return this.getSqlMapClientTemplate().queryForList("message_center.queryAppRulesList");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AppRules> queryAppRules(String appId,int pageNum,int pageSize) throws Exception {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("appId", appId);
		param.put("start", (pageNum-1)*pageSize);
		param.put("rows", pageSize);
		return this.getSqlMapClientTemplate().queryForList("message_center.queryAppRuleByAppId", param);
	}

	@Override
	public long insertAppRules(AppRules appRules) throws Exception {
		return (Long)this.getSqlMapClientTemplate().insert("message_center.insertAppRules", appRules);
	}

	@Override
	public long countAppRules(String appId) throws Exception {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("appId", appId);
		return (Long)this.getSqlMapClientTemplate().queryForObject("message_center.countAppRules", param);
	}

	@Override
	public int deleteAppRules(long id) throws Exception {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("id", id);
		return this.getSqlMapClientTemplate().delete("message_center.deleteAppRules", param);
	}
	
}
