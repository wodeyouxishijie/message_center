package com.doorcii.messagecenter.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.doorcii.messagecenter.beans.Category;
import com.doorcii.messagecenter.ibatis.CategoryDAO;

@Controller
public class CategoryController {
	
	@Resource
	private CategoryDAO categoryDAO;
	
	@ResponseBody
	@RequestMapping("/web/category_list")
	public ModelMap queryCategoryList(HttpServletRequest request,ModelMap modelMap) throws Exception {
		List<Category> categoryList = categoryDAO.queryCategoryList(ServletRequestUtils.getStringParameter(request,"categoryName",""));
		int count = categoryDAO.countCategoryList(ServletRequestUtils.getStringParameter(request,"categoryName",""));
		modelMap.put("total", count);
		modelMap.put("rows", categoryList);
		modelMap.put("success", true);
		return modelMap;
	}
	
	@RequestMapping("/web/category")
	public ModelAndView category_page(HttpServletRequest request,ModelMap modelMap) throws Exception {
		return new ModelAndView("category");
	}
	@ResponseBody
	@RequestMapping("/web/get_category_id")
	public ModelMap getCategoryId(HttpServletRequest request,ModelMap modelMap) throws Exception {
		String userName = ServletRequestUtils.getStringParameter(request,"id",null);
		if(StringUtils.isBlank(userName)) {
			modelMap.put("success", false);
			modelMap.put("msg", "参数不正确！");
			return modelMap;
		}
		Category category = categoryDAO.queryCategoryId(userName);
		if(null == category) {
			modelMap.put("success", false);
			modelMap.put("msg", "科室不存在！");
			return modelMap;
		}
		modelMap.put("success", true);
		modelMap.put("objs", category);
		return modelMap;
	}
	@ResponseBody
	@RequestMapping("/web/category_delete")
	public ModelMap deleteCategory(HttpServletRequest request,ModelMap modelMap) throws Exception {
		String userName = ServletRequestUtils.getStringParameter(request,"id",null);
		if(StringUtils.isBlank(userName)) {
			modelMap.put("success", false);
			modelMap.put("msg", "参数不正确！");
			return modelMap;
		}
		int count = categoryDAO.updateCategoryDelete(Long.valueOf(userName), 1);
		if(count > 0) {
			modelMap.put("success",true);
		} else {
			modelMap.put("success",false);
			modelMap.put("msg", "删除失败！");
		}
		return modelMap;
	}
	
	@ResponseBody
	@RequestMapping("/web/category_save")
	public ModelMap category_save(HttpServletRequest request,ModelMap modelMap) throws Exception {
		String categoryName = ServletRequestUtils.getStringParameter(request,"categoryName",null);
		String password = ServletRequestUtils.getStringParameter(request,"password",null);
		String confirmPassword = ServletRequestUtils.getStringParameter(request,"confirmPassword",null);
		if(StringUtils.isBlank(categoryName) 
				|| StringUtils.isBlank(password) || StringUtils.isBlank(confirmPassword)) {
			modelMap.put("success", false);
			modelMap.put("msg", "请填写完成信息！");
			return modelMap;
		}
		if(!password.equals(confirmPassword)) {
			modelMap.put("success", false);
			modelMap.put("msg", "两次密码输出不一致！");
			return modelMap;
		}
		Category category = new Category();
		category.setDelete(0);
		category.setCategoryName(categoryName);
		category.setPassword(DigestUtils.md5Hex(password));
		long id = categoryDAO.insertCategory(category);
		if(id > 0) {
			modelMap.put("success", true);
			modelMap.put("msg", "添加成功！");
		} else {
			modelMap.put("success", false);
			modelMap.put("msg", "添加失败！");
		}
		return modelMap;
	}
	
}
