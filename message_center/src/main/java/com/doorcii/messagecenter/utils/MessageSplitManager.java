package com.doorcii.messagecenter.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.doorcii.messagecenter.beans.MessageDetail;

/**
 * 短信发送管理者，
 * 决定短信是否应该拆分发送
 * 500个的上限，超过即拆分分批发送
 * @author Jacky
 * 2015-2-9
 */
public class MessageSplitManager {
	
	public static final int MAX_BATCH = 500;
	
	public static final String SP = ":";
	
	/**
	 * 电话号码分割算法，
	 * 需要走junit测试下
	 * @param numbers
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static ArrayList [] splitNumbers(String numbers) throws Exception {
		String[] numberV = numbers.split(ParamValidateUtil.DOT);
		int count = getIndexCount(numberV);
		ArrayList []  array = new ArrayList [count];
		if(count < 1) return null;
		int i=0;
		for(int j=0; j < count; j ++ ) {
			ArrayList<String> numberList = new ArrayList<String>();
			for(; i < numberV.length ; i++ ) {
				numberList.add(numberV[i]);
				if(numberList.size() == MAX_BATCH) {
					break;
				}
			}
			array[j] = numberList;
		}
		return array;
	}
	
	/**
	 * 获取需要几个数组
	 * @param numberV
	 * @return
	 */
	private static int getIndexCount(String[] numberV) {
		// 倍数
		int divValue = numberV.length / MAX_BATCH;
		// 模数
		int modValue = numberV.length % MAX_BATCH;
		return divValue + ((modValue > 0)?1:0);
	}
	
	public static String getSMSString(List<MessageDetail> messageList) {
		if(CollectionUtils.isNotEmpty(messageList)) {
			StringBuilder str = new StringBuilder();
			for(MessageDetail message : messageList) {
				str.append(message.getId()).append(SP).append(message.getReceNumber()).append(ParamValidateUtil.DOT);
			}
			return str.substring(0, str.length()-1);
		}
		return null;
	}
	
	public static void main(String[] args) {
		String[] s = new String[]{"151","fds","5465","255"};
		System.out.println(getIndexCount(s));
	}
	
}
