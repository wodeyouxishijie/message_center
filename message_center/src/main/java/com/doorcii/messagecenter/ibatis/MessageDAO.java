package com.doorcii.messagecenter.ibatis;

import com.doorcii.messagecenter.beans.MessageDetail;

public interface MessageDAO {
	
	public long insertOneMessage(MessageDetail messageDetail) throws Exception;
	
	public int updateOneMessageStatus(long id,int sendStatus,String errorCode) throws Exception;
}
