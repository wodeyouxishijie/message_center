package com.doorcii.messagecenter.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.doorcii.messagecenter.beans.AppRules;
import com.doorcii.messagecenter.ibatis.AppRuleDAO;

@Controller
public class AppRuleController {
	
	@Resource
	private AppRuleDAO appRuleDAO;
	
	@RequestMapping("/web/rules")
	public ModelAndView query_app_rule_list(HttpServletRequest request) throws Exception {
		
		return new ModelAndView("app_rules");
	}
	
	@ResponseBody
	@RequestMapping("/web/rule_list")
	public ModelMap queryRuleList(HttpServletRequest request,ModelMap modelMap) throws Exception {
		
		String appId = ServletRequestUtils.getStringParameter(request,"projectId","");
		int pageNum = ServletRequestUtils.getIntParameter(request, "page",1);
		int pageSize =  ServletRequestUtils.getIntParameter(request, "rows",10);
		List<AppRules> appList = appRuleDAO.queryAppRules(appId,pageNum,pageSize);
		modelMap.put("success", true);
		modelMap.put("total", appRuleDAO.countAppRules(appId));
		modelMap.put("rows", appList);
		return modelMap;
	}
	@ResponseBody
	@RequestMapping("/web/rules_save")
	public ModelMap rule_save(HttpServletRequest request,ModelMap modelMap) throws Exception {
		String projectId = ServletRequestUtils.getStringParameter(request, "appId", null);
		int frequencyType = ServletRequestUtils.getIntParameter(request, "frequenceType", 0);
		int max =  ServletRequestUtils.getIntParameter(request, "max", 10);
		if(StringUtils.isBlank(projectId) || frequencyType < 1 || max < 1 || !NumberUtils.isDigits(projectId)) {
			modelMap.put("success", false);
			modelMap.put("msg", "参数不正确！");
			return modelMap;
		}
		AppRules appRule = new AppRules();
		appRule.setAppId(Long.valueOf(projectId));
		appRule.setFrequencyType(frequencyType);
		appRule.setMax(max);
		long appId = appRuleDAO.insertAppRules(appRule);
		if(appId > 0) {
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("msg", "添加规则失败！");
		}
		return modelMap;
	}
	
	@ResponseBody
	@RequestMapping("/web/rules_delete")
	public ModelMap rule_delete(HttpServletRequest request,ModelMap modelMap) throws Exception{
		Long id = ServletRequestUtils.getLongParameter(request, "id",0L);
		if(id < 1L) {
			modelMap.put("success", false);
			modelMap.put("msg", "参数不正确！");
			return modelMap;
		}
		int count = appRuleDAO.deleteAppRules(id);
		if(count > 0) {
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("msg", "删除失败！");
		}
		
		return modelMap;
	}
	
}
