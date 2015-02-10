package com.doorcii.messagecenter.test;

import java.util.Date;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.doorcii.messagecenter.beans.MessageDetail;
import com.doorcii.messagecenter.ibatis.MessageDAO;

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
		md.setCategoryName("科室");
		md.setContent("短信内容");
		md.setErrorMsg(null);
		md.setReceNumber("15115671375");
		md.setSendDate(new Date());
		md.setSendStatus(0);
		md.setUserId("admin");
		
		System.out.println(messageDAO.insertOneMessage(md));
	}
}
