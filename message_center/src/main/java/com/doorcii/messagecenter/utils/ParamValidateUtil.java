package com.doorcii.messagecenter.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class ParamValidateUtil {
	
	private static final Logger logger = Logger.getLogger(ParamValidateUtil.class);
	
	public static final String DOT = ",";
	
	public static final String FEN = ";";
	
	public static String pattern = "^((\\+86)|(86))?((13[0-9])|(15[^4,\\D])|(18[0-9])|(147))\\d{8}$";
	
	
	public static String validateFormatNumbers(String numbers) {
		String[] number = numbers.split(DOT);
		StringBuilder sb = new StringBuilder();
		for(String n : number) {
			n = n.trim().replaceAll("-", "");
			if(isMobile(n)) {
				String newNumber = filter86(n);
				sb.append(newNumber).append(DOT);
			} else {
				logger.error("号码格式不正确："+n);
				return null;
			}
		}
		return sb.toString();
	}
	
	public static String filter86(String number) {
		Pattern p2 = Pattern.compile("^((\\+86)|(86))?");  
		Matcher m = p2.matcher(number);
		StringBuffer sb= new StringBuffer();
	    while(m.find()) {
	    	m.appendReplacement(sb, "");
	    }
	    m.appendTail(sb);
	    return sb.toString();
	}
	
	private static boolean isMobile(String number) {
		Pattern p = Pattern.compile(pattern);  
		Matcher m = p.matcher(number);
	    return m.matches();
	}
	
	public void setPattern(String pattern) {
		ParamValidateUtil.pattern = pattern;
	}
	
	public static void main(String[] args) {
		String n = "+8615168381275,13549604330";
		System.out.println(ParamValidateUtil.validateFormatNumbers(n));
	}
}
