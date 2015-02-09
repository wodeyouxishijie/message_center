package com.doorcii.messagecenter.service;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import com.doorcii.messagecenter.beans.TemplateBean;
import com.doorcii.messagecenter.beans.TemplateDTO;
import com.doorcii.messagecenter.ibatis.TemplateDAO;

public class TemplateServiceImpl implements TemplateService {

	@Resource
	private TemplateDAO templateDAO;
	
	private static final String dot = "\n";
	
	@Override
	public TemplateDTO getParamList(long templateId) throws Exception {
		TemplateBean templateBean = templateDAO.queryTemplateById(templateId);
		String paramString = templateBean.getParamList();
		TemplateDTO template = new TemplateDTO();
		if(StringUtils.isNotBlank(paramString)) {
			String param[] = paramString.split(dot);
			template.setParamList(Arrays.asList(param));
			template.setTemplateBean(templateBean);
		}
		return template;
	}

	@Override
	public String renderTemplate(Map<String, String> context) throws Exception {
		if(StringUtils.isBlank(context.get(null))) return null;
		VelocityContext velocityContext = new VelocityContext();
		putMap2VelocityContext(velocityContext,context);
		StringWriter sw = new StringWriter();
		Velocity.evaluate(velocityContext, sw, "", context.get(null));
		return sw.toString();
	}

	
	private void putMap2VelocityContext(VelocityContext velocityContext,Map<String, String> context) {
		for(Map.Entry<String, String> entry : context.entrySet()) {
			if(null != entry.getKey()) {
				velocityContext.put(entry.getKey(), entry.getValue());
			}
		}
	}
	
	public static void main(String[] args) {
		VelocityContext velocityContext = new VelocityContext();
		velocityContext.put("abc", "王八蛋");
		StringWriter sw = new StringWriter();
		Velocity.evaluate(velocityContext, sw, "", "哈哈，真好$!{abc}");
		System.out.println(sw.toString());
	}
}
