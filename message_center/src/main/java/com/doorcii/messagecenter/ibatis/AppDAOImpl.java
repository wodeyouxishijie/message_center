package com.doorcii.messagecenter.ibatis;

import java.util.HashMap;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.doorcii.messagecenter.beans.AppsBean;

public class AppDAOImpl extends SqlMapClientDaoSupport implements AppDAO {

	@Override
	public AppsBean queryApp(long appId, String userName, String password)
			throws Exception {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("appId", appId);
		param.put("userName", userName);
		param.put("password", password);
		return (AppsBean)this.getSqlMapClientTemplate().queryForObject("message_center.queryAppByAppUser", param);
	}

	@Override
	public Integer decreaseCount(long appId, long value) throws Exception {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("appId", appId);
		param.put("valu", value);
		return this.getSqlMapClientTemplate().update("message_center.decreaseCounts", param);
	}

}
