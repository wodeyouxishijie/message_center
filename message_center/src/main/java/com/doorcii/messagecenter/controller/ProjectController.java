package com.doorcii.messagecenter.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.doorcii.messagecenter.beans.AppsBean;
import com.doorcii.messagecenter.ibatis.AppDAO;

@Controller
public class ProjectController {
	
	@Resource
	private AppDAO appDAO;
	
	@RequestMapping("/web/project")
	public ModelAndView project_page() {
		return new ModelAndView("project");
	}
	
	@ResponseBody
	@RequestMapping("/web/project_list")
	public ModelMap project_list(HttpServletRequest request,ModelMap modelMap) throws Exception {
		String projectName = ServletRequestUtils.getStringParameter(request,"projectName","");
		int pageNum = ServletRequestUtils.getIntParameter(request, "page",1);
		int pageSize =  ServletRequestUtils.getIntParameter(request, "rows",10);
		List<AppsBean> appsList = appDAO.queryAppList(projectName, pageSize, pageNum);
		modelMap.put("success", true);
		modelMap.put("rows", appsList);
		modelMap.put("total", appDAO.countAppList(projectName));
		return modelMap;
	}
	
	@ResponseBody
	@RequestMapping("/web/project_save")
	public ModelMap project_save(HttpServletRequest request,ModelMap modelMap) throws Exception {
		String userName = ServletRequestUtils.getStringParameter(request,"userName","");
		String password = ServletRequestUtils.getStringParameter(request,"password","");
		String confirmPassword = ServletRequestUtils.getStringParameter(request,"confirmPassword","");
		int maxLength = ServletRequestUtils.getIntParameter(request, "maxLength", 10);
		String categoryId = ServletRequestUtils.getStringParameter(request,"categoryId","");
		String projectName = ServletRequestUtils.getStringParameter(request,"projectName","");
		String user = ServletRequestUtils.getStringParameter(request,"user","");
		String userPhone = ServletRequestUtils.getStringParameter(request,"userPhone","");
		if(StringUtils.isBlank(userName) || StringUtils.isBlank(password) || StringUtils.isBlank(confirmPassword) || 
				StringUtils.isBlank(categoryId) || StringUtils.isBlank(projectName) || StringUtils.isBlank(user) || 
				StringUtils.isBlank(userPhone) || maxLength < 1) {
			modelMap.put("success", false);
			modelMap.put("msg", "所有参数必须填写！");
			return modelMap;
		}
		if(!password.equals(confirmPassword)) {
			modelMap.put("success", false);
			modelMap.put("msg", "两次密码输入不正确！");
			return modelMap;
		}
		AppsBean appBean = new AppsBean();
		appBean.setCategoryId(categoryId);
		appBean.setDeleted(0);
		appBean.setMaxLength(maxLength);
		appBean.setOverPlus(10);
		appBean.setPassword(confirmPassword);
		appBean.setProjectName(projectName);
		appBean.setUser(user);
		appBean.setUserPhone(userPhone);
		appBean.setUserName(userName);
		long appId = appDAO.insertApp(appBean);
		if(appId > 0) {
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("msg", "添加项目失败！");
		}
		return modelMap;
	}
	
	@ResponseBody
	@RequestMapping("/web/project_delete")
	public ModelMap project_delete(HttpServletRequest request,ModelMap modelMap) throws Exception {
		Long id = ServletRequestUtils.getLongParameter(request,"id",0L);
		if(id < 1L) {
			modelMap.put("success", false);
			modelMap.put("msg", "参数不正确！");
			return modelMap;
		}
		int count = appDAO.updateAppDelete(id, 1);
		if(count > 0) {
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("msg", "删除项目失败！");
		}
		return modelMap;
	}
	
}
