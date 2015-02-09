package com.doorcii.messagecenter.ibatis;

import com.doorcii.messagecenter.beans.TemplateBean;

/**
 * 模板DAO
 * @author Jacky
 * 2015年2月9日
 */
public interface TemplateDAO {
	
	/**
	 * 根据ID查询template
	 * @param tempId
	 * @return 模板实体
	 * @throws Exception
	 */
	public TemplateBean queryTemplateById(long tempId) throws Exception;
	
}
