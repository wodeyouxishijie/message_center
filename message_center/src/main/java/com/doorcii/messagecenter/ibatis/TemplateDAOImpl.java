package com.doorcii.messagecenter.ibatis;

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

}
