package com.doorcii.messagecenter.ibatis;

import java.util.HashMap;
import java.util.List;
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
	public AppsBean queryAppById(long appId) throws Exception {
		return (AppsBean)this.getSqlMapClientTemplate().queryForObject("message_center.queryAppId",appId);
	}

	@Override
	public Integer decreaseCount(long appId, long value) throws Exception {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("appId", appId);
		param.put("valu", value);
		return this.getSqlMapClientTemplate().update("message_center.decreaseCounts", param);
	}

	@Override
	public boolean initOverPlus(long appId, long overplus,long lastOverPlus) throws Exception {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("appId", appId);
		param.put("overplus", overplus);
		
		return this.getSqlMapClientTemplate().update("message_center.initOverplus", param) > 0;
	}

	@Override
	public long insertApp(AppsBean appsBean) throws Exception {
		return (Long)this.getSqlMapClientTemplate().insert("message_center.app_insert", appsBean);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AppsBean> queryAppList(long categoryId, int needFilter)
			throws Exception {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("categoryId", categoryId);
		param.put("needFilter", needFilter);
		return this.getSqlMapClientTemplate().queryForList("message_center.queryAppList", param);
	}

	@Override
	public int updateAppDelete(long appId, int delete) throws Exception {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("appId", appId);
		param.put("delete",delete);
		return this.getSqlMapClientTemplate().update("message_center.updateAppDelete", param);
	}

}
