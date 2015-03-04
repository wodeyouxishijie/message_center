package com.doorcii.messagecenter.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.doorcii.messagecenter.beans.Category;
import com.doorcii.messagecenter.beans.UserValidResult;
import com.doorcii.messagecenter.service.UserService;

@Controller
public class UserController {
	private static final Logger logger = Logger.getLogger(UserController.class);
	
	@Resource
	private UserService userService;
	
	@ResponseBody
	@RequestMapping(method=RequestMethod.POST,value="/sign")
	public ModelMap login(HttpServletRequest request,ModelMap modelMap) {
		String code = ServletRequestUtils.getStringParameter(request, "verifyCode",null);
		if(StringUtils.isBlank(code)) {
			modelMap.put("msg", "验证码不能为空！");
			modelMap.put("success", false);
			return modelMap;
		}
		if(StringUtils.isNotBlank(code)) {
			String cacheCode = (String)request.getSession().getAttribute("_code_");
			if(!cacheCode.equalsIgnoreCase(code)) {
				modelMap.put("msg", "验证码不正确！");
				modelMap.put("success", false);
				return modelMap;
			}
		}
		String username = ServletRequestUtils.getStringParameter(request, "username",null);
		String password = ServletRequestUtils.getStringParameter(request, "password",null);
		if(StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
			modelMap.put("msg", "用户名密码不能为空！");
			modelMap.put("success", false);
			return modelMap;
		}
		if(!username.equals("10001")) {
			modelMap.put("msg", "其他账户暂时不允许登陆！");
			modelMap.put("success", false);
			return modelMap;
		}
		
		
		try {
			UserValidResult userResult = userService.validateUserLogin(username, DigestUtils.md5Hex(password));
			if(userResult.isSuccess()) {
				request.getSession().setAttribute("_user_", userResult.getCategory());
				modelMap.put("success", true);
				return modelMap;
			} else {
				modelMap.put("msg", "用户名或密码不正确！");
				modelMap.put("success", false);
				return modelMap;
			}
		} catch (Exception e) {
			logger.error("校验用户报错！",e);
			modelMap.put("msg", "系统错误！");
			modelMap.put("success", false);
			return modelMap;
		}
		
	}
	
	@ResponseBody
	@RequestMapping(method=RequestMethod.POST,value="/web/update_password")
	public ModelMap updatePassword(HttpServletRequest request,ModelMap modelMap) throws Exception {
		String oldPwd = ServletRequestUtils.getStringParameter(request, "oldPwd", null);
		String newPwd = ServletRequestUtils.getStringParameter(request, "newPwd", null);
		String rpPwd = ServletRequestUtils.getStringParameter(request, "rpwd", null);
		if(StringUtils.isBlank(oldPwd) || StringUtils.isBlank(newPwd) || StringUtils.isBlank(rpPwd)) {
			modelMap.put("msg", "参数不正确！");
			modelMap.put("success", false);
			return modelMap;
		}
		if(!newPwd.equals(rpPwd)) {
			modelMap.put("msg", "两次密码不一致！");
			modelMap.put("success", false);
			return modelMap;
		}
		
		Category category = (Category)request.getSession().getAttribute("_user_");
		if(null == category) {
			modelMap.put("msg", "密码修改失败，未登录！");
			modelMap.put("success", false);
			return modelMap;
		}
		
		boolean value = userService.updateUserPassword(newPwd, String.valueOf(category.getId()));
		if(!value) {
			modelMap.put("msg", "密码修改失败！");
			modelMap.put("success", false);
			return modelMap;
		} else {
			modelMap.put("success", true);
		}
		return modelMap;
	}
	
	@RequestMapping("/logout")
	public ModelAndView logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return new ModelAndView("redirect:/login");
	}
}
