package com.doorcii.messagecenter.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.doorcii.messagecenter.beans.Category;

public class CategoryDAOImpl extends SqlMapClientDaoSupport implements CategoryDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<Category> queryCategoryList(String categoryName)
			throws Exception {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("categoryName", categoryName);
		return this.getSqlMapClientTemplate().queryForList("message_center.queryCategoryList", param);
	}

	@Override
	public int countCategoryList(String categoryName) throws Exception {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("categoryName", categoryName);
		return (Integer)this.getSqlMapClientTemplate().queryForObject("message_center.queryCategoryCount", param);
	}

	@Override
	public int updateCategoryDelete(long id, int delete) throws Exception {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("id", id);
		param.put("delete", delete);
		return this.getSqlMapClientTemplate().update("message_center.updateCategoryDelete", param);
	}

	@Override
	public Category queryUserInfo(String username, String password) throws Exception {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("username", username);
		param.put("password", password);
		return (Category)this.getSqlMapClientTemplate().queryForObject("message_center.queryCategoryByUser", param);
	}

	@Override
	public long insertCategory(Category category) throws Exception {
		return (Long)this.getSqlMapClientTemplate().insert("message_center.insertCategory", category);
	}

	@Override
	public Category queryCategoryId(String id) throws Exception {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("id", id);
		return (Category)this.getSqlMapClientTemplate().queryForObject("message_center.queryCategoryById", param);
	}

	@Override
	public int updateCategory(String userName, String password)
			throws Exception {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("userName", userName);
		param.put("password", password);
		return this.getSqlMapClientTemplate().update("message_center.updateCategoryPassword", param);
	}

}
