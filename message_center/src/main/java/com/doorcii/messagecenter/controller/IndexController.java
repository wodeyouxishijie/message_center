package com.doorcii.messagecenter.controller;

import java.awt.image.BufferedImage;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.doorcii.messagecenter.beans.Category;
import com.google.code.kaptcha.Producer;

@Controller
public class IndexController {
	
	private static final Logger logger = Logger.getLogger(IndexController.class);
	
	@Resource
	private Producer captchaProducer = null;  
	
	@RequestMapping("/web/index")
	public ModelAndView handRequest(HttpServletRequest request,ModelMap modelMap) {
		Category category = (Category)request.getSession().getAttribute("_user_");
		modelMap.put("user", category);
		return new ModelAndView("welcome");
	}
	
	@RequestMapping("/login")
	public ModelAndView loginPage(ModelMap modelMap) {
		
		return new ModelAndView("login");
	}
	
	@RequestMapping("/captcha")
	public void getCheckCode(HttpServletRequest request,HttpServletResponse response) {
		HttpSession session = request.getSession();  
        response.setDateHeader("Expires", 0);  
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");  
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");  
        response.setHeader("Pragma", "no-cache");  
        response.setContentType("image/jpeg");
        String capText = captchaProducer.createText();
        session.setAttribute("_code_", capText);
        try {
	        BufferedImage bi = captchaProducer.createImage(capText);  
	        ServletOutputStream out = response.getOutputStream();
	        ImageIO.write(bi, "jpg", out);  
	        try {  
	            out.flush();  
	        } finally {  
	            out.close();  
	        }  
        } catch(Exception e) {
        	logger.error("",e);
        }
	}
}
