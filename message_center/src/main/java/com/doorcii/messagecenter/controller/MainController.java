package com.doorcii.messagecenter.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
	
	@ResponseBody
	@RequestMapping("/web/getActionBtn")
	public ModelMap getActionBtn(HttpServletRequest request,ModelMap modelMap) {
		modelMap.put("success", true);
		modelMap.put("allType", true);
		return modelMap;
	}
	
}
