package com.doorcii.messagecenter.test;

import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.doorcii.messagecenter.beans.AppsBean;
import com.doorcii.messagecenter.beans.MessageDetail;
import com.doorcii.messagecenter.beans.MessageSendParam;
import com.doorcii.messagecenter.beans.MessageSendResult;
import com.doorcii.messagecenter.httpcore.MessageHttpCore;
import com.doorcii.messagecenter.ibatis.AppDAO;
import com.doorcii.messagecenter.ibatis.CategoryDAO;
import com.doorcii.messagecenter.ibatis.MessageDAO;
import com.doorcii.messagecenter.ibatis.TemplateDAO;
import com.doorcii.messagecenter.service.MessageService;
import com.doorcii.messagecenter.utils.MessageSplitManager;
import com.doorcii.messagecenter.utils.MessageSplitManager.SplitResult;

public class MessageDAOTest {
	
	private ApplicationContext context = new ClassPathXmlApplicationContext("spring-*.xml"); 
	
	@Test
	public void testInsertMessage() throws Exception {
		MessageDAO messageDAO = (MessageDAO)context.getBean("messageDAO");
		MessageDetail md = new MessageDetail();
		md.setAppId(1001L);
		md.setAppName("应用名称");
		md.setCallbackStatus(0);
		md.setCategoryId("1098801");
		md.setContent("短信内容");
		md.setErrorMsg(null);
		md.setReceNumber("15115671375");
		md.setSendDate(new Date());
		md.setSendStatus(0);
		md.setUserId("admin");
		
		System.out.println(messageDAO.insertOneMessage(md));
	}
	
	@Test
	public void testQueryApp() throws Exception {
		AppDAO appDAO = (AppDAO)context.getBean("appDAO");
		System.out.println(appDAO.queryApp(1001L, "admin", "admin"));
	}
	
	@Test
	public void testQueryTemplate() throws Exception {
		TemplateDAO templateDAO = (TemplateDAO)context.getBean("templateDAO");
		Assert.assertNotNull(templateDAO.queryTemplateById(1));
	}
	
	@Test
	public void testSplitNumber() throws Exception {
		StringBuilder numbers = new StringBuilder();
		for(long i=15115671375L;i<15115672375L;i++) {
			numbers.append(i).append(",");
		}
		numbers.append("15115671375");
		SplitResult splitResult = MessageSplitManager.splitNumbers(numbers.toString());
		System.out.println(splitResult.getCount());
	}
	
	@Test
	public void testSendMessageService() throws Exception {
		MessageService messageService = (MessageService)context.getBean("messageService");
		MessageSendParam message = new MessageSendParam();
		message.setAppId(1001L);
		message.setContent("星期几测试");
		StringBuilder numbers = new StringBuilder();
		for(long i=15115671375L;i<15115671385L;i++) {
			numbers.append(i).append(",");
		}
		AppsBean appsBean = new AppsBean();
		appsBean.setCategoryId("100001");
		appsBean.setOverPlus(10);
		appsBean.setMaxLength(10);
		message.setAppsBean(appsBean);
		
		message.setNumbers(numbers.toString());
		message.setUsername("admin");
		
		MessageSendResult sendResult = messageService.sendMessage(message);
		
		System.out.println(sendResult);
		
	}
	
	@Test
	public void testMessageSender() throws Exception {
		MessageHttpCore sender = (MessageHttpCore)context.getBean("messageSender");
		System.out.println(sender);
	}
	@Test
	public void testQueryCategory() throws Exception {
		CategoryDAO categoryDAO = (CategoryDAO)context.getBean("categoryDAO");
		System.out.println(categoryDAO.queryCategoryList("1").size());
	}
	@Test
	public void testCategoryDelete() throws Exception {
		CategoryDAO categoryDAO = (CategoryDAO)context.getBean("categoryDAO");
		categoryDAO.updateCategoryDelete(10001L, 0);
	}
	
	@Test
	public void testInsertApp() throws Exception {
		AppDAO categoryDAO = (AppDAO)context.getBean("appDAO");
		AppsBean appsBean = new AppsBean();
		appsBean.setCategoryId("10001");
		appsBean.setMaxLength(1);
		appsBean.setOverPlus(1);
		appsBean.setPassword("1234");
		appsBean.setProjectName("project");
		appsBean.setUser("admin");
		appsBean.setUserName("admin");
		appsBean.setUserPhone("151");
		System.out.println(categoryDAO.insertApp(appsBean));
	}
	
	@Test
	public void testQueryAppList() throws Exception {
		AppDAO categoryDAO = (AppDAO)context.getBean("appDAO");
		System.out.println(categoryDAO.queryAppList(10001L, 1).size());
	}
}
