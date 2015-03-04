package com.doorcii.messagecenter.ibatis;

import java.util.List;

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
	
	public List<TemplateBean> queryTemplateList(Long projectId,String name,int pageNum,int pageSize) throws Exception;
	
	public long countTemplate(Long projectId,String name) throws Exception;
	
	public int deleteTemplate(Long id) throws Exception;
	
	public long insertTemplate(TemplateBean templateBean) throws Exception;
	
	public int updateTemplate(TemplateBean templateBean) throws Exception;
	
}
