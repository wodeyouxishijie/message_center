package com.doorcii.messagecenter.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.doorcii.messagecenter.beans.TemplateBean;

/**
 * 模板DAO
 * @author Jacky
 * 2015年2月9日
 */
public class TemplateDAOImpl extends SqlMapClientDaoSupport implements TemplateDAO {

	@Override
	public TemplateBean queryTemplateById(long tempId) throws Exception {
		return (TemplateBean)this.getSqlMapClientTemplate().queryForObject("message_center.queryTemplateById", tempId);
	}

	@Override
	public int updateTemplate(TemplateBean templateBean) throws Exception {
		return this.getSqlMapClientTemplate().update("message_center.updateTemplate", templateBean);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TemplateBean> queryTemplateList(Long projectId, String name,int pageNum,int pageSize)
			throws Exception {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("projectId", projectId);
		param.put("name", name);
		param.put("start", (pageNum-1)*pageSize);
		param.put("rows", pageSize);
		return this.getSqlMapClientTemplate().queryForList("message_center.queryTemplate", param);
	}

	@Override
	public long countTemplate(Long projectId, String name) throws Exception {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("projectId", projectId);
		param.put("name", name);
		return (Long)this.getSqlMapClientTemplate().queryForObject("message_center.countTemplate", param);
	}

	@Override
	public int deleteTemplate(Long id) throws Exception {
		return this.getSqlMapClientTemplate().delete("message_center.deleteTemplate", id);
	}

	@Override
	public long insertTemplate(TemplateBean templateBean) throws Exception {
		return (Long)this.getSqlMapClientTemplate().insert("message_center.template_insert", templateBean);
	}
	
}
