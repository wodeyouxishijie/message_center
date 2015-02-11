package com.doorcii.messagecenter.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

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
	
	public static final int MARGIN = 66;
	
	public static final String SP = ",";
	
	/**
	 * 电话号码分割算法，
	 * 需要走junit测试下
	 * @param numbers
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static SplitResult splitNumbers(String numbers) throws Exception {
		
		SplitResult result = new SplitResult();
		
		String[] numberV = numbers.split(ParamValidateUtil.DOT);
		Set<String> sets = new HashSet<String>();
		// 去重过程
		for(String num : numberV) {
			String newNum = ParamValidateUtil.validateFormatNumbers(num);
			if(StringUtils.isNotBlank(newNum))
				sets.add(num);
		}
		numberV = sets.toArray(new String[]{});
		
		int count = getIndexCount(numberV);
		result.setCount(numberV.length);
		ArrayList []  array = new ArrayList [count];
		if(count < 1) return result;
		
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
		result.setNumberList(array);
		
		return result;
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
				str.append(message.getId()).append(SP).append(message.getReceNumber()).append(ParamValidateUtil.FEN);
			}
			return str.substring(0, str.length()-1);
		}
		return null;
	}
	
	public static int calculateContentCount(String content) {
		int length = content.length();
		int divValue = length / MARGIN;
		int modValue = length % MARGIN;
		return divValue + modValue==0?0:1;
	}
	
	public static class SplitResult {
		
		@SuppressWarnings("rawtypes")
		private ArrayList [] numberList;
		
		private long count;

		@SuppressWarnings("rawtypes")
		public ArrayList[] getNumberList() {
			return numberList;
		}

		@SuppressWarnings("rawtypes")
		public void setNumberList(ArrayList[] numberList) {
			this.numberList = numberList;
		}

		public long getCount() {
			return count;
		}

		public void setCount(long count) {
			this.count = count;
		}

		@Override
		public String toString() {
			return "SplitResult [numberList=" + Arrays.toString(numberList)
					+ ", count=" + count + "]";
		}
		
	}
	
	
	public static void main(String[] args) {
		String[] s = new String[]{"151","fds","5465","255"};
		System.out.println(getIndexCount(s));
	}
	
}
