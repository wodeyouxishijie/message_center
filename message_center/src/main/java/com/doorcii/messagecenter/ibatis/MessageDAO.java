package com.doorcii.messagecenter.ibatis;

import java.util.List;

import com.doorcii.messagecenter.beans.MessageDetail;

public interface MessageDAO {
	
	public long insertOneMessage(MessageDetail messageDetail) throws Exception;
	
	public int updateOneMessageStatus(long id,int sendStatus,String errorCode) throws Exception;
	
	public int updateCallbackStatus(long id,int callbackStatus,String callbacktime) throws Exception;
	
	public List<MessageDetail> queryMessageList(int pageNum,int rows,String categoryId,String projectId) throws Exception;
	
	public long countMessage() throws Exception;
}
