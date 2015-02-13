package com.doorcii.messagecenter.ibatis;

import java.util.List;

import com.doorcii.messagecenter.beans.Category;

public interface CategoryDAO {
	
	public List<Category> queryCategoryList(String categoryName) throws Exception;
	
	/**
	 * 
	 * @param id
	 * @param delete 0表示正常 1表示删除
	 * @return
	 * @throws Exception
	 */
	public int updateCategoryDelete(long id,int delete) throws Exception;
	
}
