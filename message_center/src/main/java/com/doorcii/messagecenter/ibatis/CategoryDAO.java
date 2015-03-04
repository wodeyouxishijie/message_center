package com.doorcii.messagecenter.ibatis;

import java.util.List;

import com.doorcii.messagecenter.beans.Category;

public interface CategoryDAO {
	
	public List<Category> queryCategoryList(String categoryName) throws Exception;
	
	public int countCategoryList(String categoryName) throws Exception;
	
	/**
	 * 
	 * @param id
	 * @param delete 0表示正常 1表示删除
	 * @return
	 * @throws Exception
	 */
	public int updateCategoryDelete(long id,int delete) throws Exception;
	
	/**
	 * 是否存在用户
	 * @param username
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public Category queryUserInfo(String username,String password) throws Exception;
	
	public long insertCategory(Category category) throws Exception;
	
	public Category queryCategoryId(String id) throws Exception;
	
	public int updateCategory(String userName,String password) throws Exception;
	
}
