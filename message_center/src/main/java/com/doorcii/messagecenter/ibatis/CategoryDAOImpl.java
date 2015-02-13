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
	public int updateCategoryDelete(long id, int delete) throws Exception {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("id", id);
		param.put("delete", delete);
		return this.getSqlMapClientTemplate().update("message_center.updateCategoryDelete", param);
	}

}
