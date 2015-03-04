package com.doorcii.messagecenter.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.doorcii.messagecenter.beans.MessageDetail;
import com.doorcii.messagecenter.ibatis.MessageDAO;

@Controller
public class MessageController {
	
	@Resource
	private MessageDAO messageDAO;
	
	
	@ResponseBody
	@RequestMapping("/web/message_list")
	public ModelMap queryMessageList(HttpServletRequest request,ModelMap modelMap) throws Exception {
		int pageNum = ServletRequestUtils.getIntParameter(request, "page",1);
		int pageSize =  ServletRequestUtils.getIntParameter(request, "rows",10);
		String categoryId = ServletRequestUtils.getStringParameter(request,"categoryId","");
		String projectId = ServletRequestUtils.getStringParameter(request,"projectId","");
		List<MessageDetail> messageList = messageDAO.queryMessageList(pageNum, pageSize,categoryId,projectId);
		modelMap.put("total", messageDAO.countMessage());
		modelMap.put("rows", messageList);
		modelMap.put("success", true);
		return modelMap;
	}
	
	@RequestMapping("/web/message_detail")
	public ModelAndView messageListPage(HttpServletRequest request) {
		return new ModelAndView("message_detail");
	}
}
