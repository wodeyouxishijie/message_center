package com.doorcii.messagecenter.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.doorcii.messagecenter.beans.MesageCallback;
import com.doorcii.messagecenter.beans.MesageCallback.Note;
import com.doorcii.messagecenter.beans.MessageDetail;
import com.doorcii.messagecenter.beans.MessageInfo;
import com.doorcii.messagecenter.beans.MessageSendParam;
import com.doorcii.messagecenter.beans.MessageSendResult;
import com.doorcii.messagecenter.constants.ErrorConstants;
import com.doorcii.messagecenter.enums.CallbackStatus;
import com.doorcii.messagecenter.httpcore.MessageHttpCore;
import com.doorcii.messagecenter.ibatis.AppDAO;
import com.doorcii.messagecenter.ibatis.MessageDAO;
import com.doorcii.messagecenter.utils.MessageSplitManager;
import com.doorcii.messagecenter.utils.MessageSplitManager.SplitResult;

public class MessageServiceImpl implements MessageService {
	
	private static final Logger logger = Logger.getLogger(MessageServiceImpl.class);
	
	private MessageValidateService messageValidateService;
	
	private MessageDAO messageDAO;
	
	private MessageHttpCore messageSender;
	
	private AppDAO appDAO;
	
	private TransactionTemplate transactionTemplate;
	
	@SuppressWarnings("unchecked")
	@Override
	public MessageSendResult sendMessage(final MessageSendParam messageParam)
			throws Exception {
		
		final MessageSendResult result = new MessageSendResult();
		final SplitResult numberResult = MessageSplitManager.splitNumbers(messageParam.getNumbers());
		if(null == numberResult.getNumberList() || numberResult.getNumberList().length < 1) {
			result.setResultMsg("短信发送号码为空！");
			result.setResultCode(ErrorConstants.NUMBER_NO_ONE);
			return result;
		}
		final long total = numberResult.getCount() * messageParam.getBei();
		// 短信规则验证
		String errorMsg = messageValidateService.validateMessage(messageParam.getContent(),messageParam.getAppsBean(),total);
		if(StringUtils.isNotBlank(errorMsg)) {
			result.setResultMsg(errorMsg);
			result.setResultCode(ErrorConstants.MESSAGE_ILLEGAL);
			return result;
		}
		// 整个发送短信中包含在事务里
		transactionTemplate.execute(new TransactionCallback<Integer>() {
			@Override
			public Integer doInTransaction(TransactionStatus status) {
				try {
					
					Integer counter = appDAO.decreaseCount(messageParam.getAppId(), total);
					if(counter < 1) {
						result.setResultCode(ErrorConstants.SYSTEM_ERRO);
						result.setResultMsg("短信数量不足！");
						return 1;
					}
					
					for(ArrayList<String> list : numberResult.getNumberList()) {
						List<MessageDetail> messageList = new ArrayList<MessageDetail>();
						for(String number : list) {
							MessageDetail md = new MessageDetail();
							md.setAppId(messageParam.getAppId());
							md.setCallbackStatus(CallbackStatus.INIT);
							md.setReceNumber(number);
							md.setContent(messageParam.getContent());
							md.setSendDate(new Date());
							md.setUserId(messageParam.getUsername());
							md.setSendStatus(CallbackStatus.INIT);
							md.setCategoryId(messageParam.getAppsBean().getCategoryId());
							md.setCounts(messageParam.getBei());
							try {
								long id = messageDAO.insertOneMessage(md);
								md.setId(id);
								messageList.add(md);
							} catch(Exception e) {
								logger.error("短信实体插入失败."+md,e);
							}
						}
						String receStr = MessageSplitManager.getSMSString(messageList);
						// 短信发送实体
						MessageInfo mi = new MessageInfo();
						mi.setContent(messageParam.getContent());
						mi.setRece_account(receStr);
						
						MessageSendResult sendResult = messageSender.sendMessage(mi);
						result.setResultMsg(sendResult.getResultMsg());
						result.setResultCode(sendResult.getResultCode());
						
						if(!sendResult.isSuccess()) {
							status.setRollbackOnly();
						} else {
							try {
								// 不成功的时候回去更新短信发送状态
								for(MessageDetail md : messageList) {
									int success = messageDAO.updateOneMessageStatus(md.getId(), sendResult.isSuccess()?CallbackStatus.SUCCESS:CallbackStatus.FAILED,result.getResultCode());
									if(success < 1) {
										logger.error("更新短信明细状态失败！"+md);
									}
								}
							} catch(Exception e) {
								logger.error("",e);
							}
						}
					}
				} catch(Exception e) {
					result.setResultCode(ErrorConstants.SYSTEM_ERRO);
					result.setResultMsg("Unknow Error:"+e.getMessage());
					status.setRollbackOnly();
				}
				return 1;
			}
		});
		
		return result;
	}
	

	@Override
	public boolean updateMessageCallbackStatus(MesageCallback messageCallback) throws Exception {
		List<Note> noteList = messageCallback.getNotelist();
		if(CollectionUtils.isNotEmpty(noteList)) {
			for(Note note : noteList) {
				String id = note.getNote_code();
				String status = note.getRece_state();
				String callbackTime = note.getRece_time();
				if(StringUtils.isBlank(id) || !NumberUtils.isDigits(id) || StringUtils.isBlank(status) || StringUtils.isBlank(callbackTime)) {
					logger.error("回调失败，回调参数有问题！"+messageCallback);
					return false;
				} else {
					int count = messageDAO.updateCallbackStatus(Long.valueOf(id), Integer.valueOf(status), callbackTime);
					return count > 0;
				}
			}
		}
		
		return false;
	}



	public void setMessageValidateService(
			MessageValidateService messageValidateService) {
		this.messageValidateService = messageValidateService;
	}

	public void setMessageDAO(MessageDAO messageDAO) {
		this.messageDAO = messageDAO;
	}

	public void setMessageSender(MessageHttpCore messageSender) {
		this.messageSender = messageSender;
	}

	public void setAppDAO(AppDAO appDAO) {
		this.appDAO = appDAO;
	}

	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}

}
