package com.doorcii.messagecenter.ibatis;

import java.util.HashMap;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.doorcii.messagecenter.beans.MessageDetail;
import com.doorcii.messagecenter.sequence.GroupSequence;

public class MessageDAOImpl extends SqlMapClientDaoSupport  implements MessageDAO {
	
	private GroupSequence messagSequence;
	
	@Override
	public long insertOneMessage(MessageDetail messageDetail) throws Exception {
		long id = messagSequence.nextValue();
		messageDetail.setId(id);
		this.getSqlMapClientTemplate().insert("message_center.message_detail_insert",messageDetail);
		return id;
	}

	@Override
	public int updateOneMessageStatus(long id, int sendStatus,String errorCode) throws Exception {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("id", id);
		param.put("sendStatus", sendStatus);
		param.put("errorCode", errorCode);
		this.getSqlMapClientTemplate().update("message_center.message_detail_update", param);
		return 1;
	}

	public void setMessagSequence(GroupSequence messagSequence) {
		this.messagSequence = messagSequence;
	}

}
