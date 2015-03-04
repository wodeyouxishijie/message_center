package com.doorcii.messagecenter.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.doorcii.messagecenter.beans.MessageDetail;
import com.doorcii.messagecenter.enums.CallbackStatus;
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

	@Override
	public int updateCallbackStatus(long id, int callbackStatus,String callbacktime)
			throws Exception {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("id", id);
		param.put("callbackStatus", callbackStatus==1?CallbackStatus.SUCCESS:CallbackStatus.FAILED);
		param.put("callbacktime", callbacktime);
		return this.getSqlMapClientTemplate().update("message_center.update_callback_status",param);
	}

	@Override
	public List<MessageDetail> queryMessageList(int pageNum, int rows,String categoryId,String projectId)
			throws Exception {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("rows", rows);
		param.put("start", (pageNum-1)*rows);
		param.put("categoryId", categoryId);
		param.put("projectId", projectId);
		return this.getSqlMapClientTemplate().queryForList("message_center.query_message_by_page", param);
	}

	@Override
	public long countMessage() throws Exception {
		return (Long)this.getSqlMapClientTemplate().queryForObject("message_center.query_message_count");
	}

	public void setMessagSequence(GroupSequence messagSequence) {
		this.messagSequence = messagSequence;
	}

}
