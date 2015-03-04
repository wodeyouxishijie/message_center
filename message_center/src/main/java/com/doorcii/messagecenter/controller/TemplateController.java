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
import com.doorcii.messagecenter.beans.TemplateBean;
import com.doorcii.messagecenter.ibatis.AppDAO;
import com.doorcii.messagecenter.ibatis.TemplateDAO;

@Controller
public class TemplateController {
	
	@Resource
	private TemplateDAO templateDAO;
	@Resource
	private AppDAO appDAO;
	
	@RequestMapping("/web/template")
	public ModelAndView template(ModelMap modelMap) throws Exception {
		List<AppsBean> list = appDAO.queryAppNoTemplate();
		modelMap.put("applist", list);
		return new ModelAndView("template",modelMap);
	}
	
	@ResponseBody
	@RequestMapping("/web/template_get")
	public ModelMap getTemplate(HttpServletRequest request,ModelMap modelMap) throws Exception {
		Long templateId = ServletRequestUtils.getLongParameter(request, "id",0L);
		TemplateBean template = templateDAO.queryTemplateById(templateId);
		modelMap.put("success", true);
		modelMap.put("data", template);
		return modelMap;
	}
	
	@ResponseBody
	@RequestMapping("/web/template_list")
	public ModelMap template_list(HttpServletRequest request,ModelMap modelMap) throws Exception {
		String name = ServletRequestUtils.getStringParameter(request, "name", "");
		Long projectId = ServletRequestUtils.getLongParameter(request, "projectId", 0L);
		int pageNum = ServletRequestUtils.getIntParameter(request, "page", 1);
		int rows = ServletRequestUtils.getIntParameter(request, "rows", 10);
		List<TemplateBean> templateList = templateDAO.queryTemplateList(projectId, name, pageNum, rows);
		modelMap.put("success", true);
		modelMap.put("rows", templateList);
		modelMap.put("total", templateDAO.countTemplate(projectId, name));
		return modelMap;
	}
	
	@ResponseBody
	@RequestMapping("/web/template_delete")
	public ModelMap template_delete(HttpServletRequest request,ModelMap modelMap) throws Exception {
		Long templateId = ServletRequestUtils.getLongParameter(request, "id",0L);
		if(templateId < 1L) {
			modelMap.put("success", false);
			modelMap.put("msg", "参数不正确！");
			return modelMap;
		}
		int count = templateDAO.deleteTemplate(templateId);
		if(count > 0) {
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("msg", "删除失败！");
		}
		return modelMap;
	}
	
	@ResponseBody
	@RequestMapping("/web/template_add")
	public ModelMap template_add(HttpServletRequest request,ModelMap modelMap) throws Exception {
		Long templateId = ServletRequestUtils.getLongParameter(request, "id", 0L);
		String name = ServletRequestUtils.getStringParameter(request, "name", null);
		String templateStr = ServletRequestUtils.getStringParameter(request, "templateString", null);
		String paramStr = ServletRequestUtils.getStringParameter(request, "paramList", null);
		Long projectId = ServletRequestUtils.getLongParameter(request, "projectId", 0L);
		if(StringUtils.isBlank(name) || StringUtils.isBlank(templateStr) || StringUtils.isBlank(paramStr) 
				|| projectId < 1) {
			modelMap.put("success", false);
			modelMap.put("msg", "参数不正确！");
			return modelMap;
		}
		TemplateBean templateBean = new TemplateBean();
		templateBean.setName(name);
		templateBean.setParamList(paramStr);
		templateBean.setProjectId(projectId);
		templateBean.setTemplateString(templateStr);
		templateBean.setId(templateId);
		if(templateId < 1L) {
			long id = templateDAO.insertTemplate(templateBean);
			if(id > 0L) {
				modelMap.put("success", true);
			} else {
				modelMap.put("success", false);
				modelMap.put("msg", "添加模板失败！");
			}
		} else {
			int count = templateDAO.updateTemplate(templateBean);
			if(count > 0L) {
				modelMap.put("success", true);
			} else {
				modelMap.put("success", false);
				modelMap.put("msg", "更新模板失败！");
			}
		}
		return modelMap;
	}
	
}
