package com.doorcii.messagecenter.httpcore;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.doorcii.messagecenter.beans.DeliveryInfo;
import com.doorcii.messagecenter.beans.MessageConfig;
import com.doorcii.messagecenter.beans.MessageInfo;
import com.doorcii.messagecenter.beans.MessageReturn;
import com.doorcii.messagecenter.beans.MessageSendResult;
import com.doorcii.messagecenter.enums.MessageCodeEnum;
import com.doorcii.messagecenter.utils.XStreamUtils;
import com.thoughtworks.xstream.XStream;

/**
 * 消息发送核心实现类
 * @author Jacky
 * 2015年2月6日
 */
public class MessageHttpCoreImpl implements MessageHttpCore {
	
	private static final Logger logger = Logger.getLogger("SMS-LOG");
	
	private  HttpClient client;
	
	private MultiThreadedHttpConnectionManager connectionManager = null;
	
	private static final int TIME_OUT = 5000;
	
	private MessageConfig config;
	
	public void init() throws Exception {
		if(null == config || StringUtils.isBlank(config.getUrl()) || StringUtils.isBlank(config.getUserId()) 
				|| StringUtils.isBlank(config.getValidateCode()) ) {
			throw new Exception("init message failed.configm is error!");
		}
		
		// 默认编码
		if(StringUtils.isBlank(config.getEncoding())) {
			config.setEncoding(MessageConfig.DEFAULT_CHAR_SET);
		}
		
		HttpConnectionManagerParams connectionParam = new HttpConnectionManagerParams();
		// 超时时间
		connectionParam.setSoTimeout(TIME_OUT);
		// 单机最大连接数
		connectionParam.setDefaultMaxConnectionsPerHost(200);
		// 总连接数
		connectionParam.setMaxTotalConnections(1000);
		// 是否使用算法
		connectionParam.setTcpNoDelay(true);
		// Socket延迟关闭
		connectionParam.setLinger(1);
		// 重试1次
		connectionParam.setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(1,false));
		// 高并发线程安全管理
		connectionManager =  new MultiThreadedHttpConnectionManager();
		connectionManager.setParams(connectionParam);
		client = new HttpClient(connectionManager);
	}
	
	@Override
	public MessageSendResult sendMessage(MessageInfo messageInfo) throws Exception {
		MessageSendResult result = new MessageSendResult();
		if(null == messageInfo) {
			result.setResultCode(0L);
			result.setResultMsg("param error: messageInfo is null.");
			return result;
		}
		if(StringUtils.isBlank(messageInfo.getContent())) {
			result.setResultCode(0L);
			result.setResultMsg("param error: messageInfo'content is blank.");
			return result;
		}
		if(StringUtils.isBlank(messageInfo.getRece_account())) {
			result.setResultCode(0L);
			result.setResultMsg("param error: messageInfo'receive account is blank.");
			return result;
		}
		DeliveryInfo deliveryInfo = this.getDeliveryInfo();
		deliveryInfo.setMain(messageInfo);
		
		// xstream初始化
		XStream xstream = XStreamUtils.getDefaultXStream();
		xstream.alias("root", DeliveryInfo.class);
		String xml = xstream.toXML(deliveryInfo);
		logger.info("message request param ："+xml);
		byte[] xmlByte = Base64.encodeBase64(xml.getBytes());
		
		PostMethod postMethod=new PostMethod(config.getUrl());
	    postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, config.getEncoding());
	    RequestEntity re = new ByteArrayRequestEntity(xmlByte);
	    postMethod.setRequestEntity(re);
	    long start = System.currentTimeMillis();
	    int statusCode=client.executeMethod(postMethod);
	    long end =System.currentTimeMillis();
	    if(null == postMethod.getResponseBody()) {
	    	logger.error("return body is null.");
	    	result.setResultCode(0L);
	    	result.setResultMsg("message interface return body is null!");
	    	return result;
	    }  
	    
        String returnXML = new String(Base64.decodeBase64(postMethod.getResponseBody()));
        logger.info("http code:"+statusCode+",return content:"+returnXML+",time cost:【"+(end-start)+"】 ms");
        XStream xstreamresult = XStreamUtils.getDefaultXStream();
        xstreamresult.alias("root", MessageReturn.class);
        MessageReturn messageReturn = (MessageReturn)xstreamresult.fromXML(returnXML);
        
        result.setResultCode(messageReturn.getCode());
        if(null != messageReturn.getCode()) {
        	MessageCodeEnum messageCode = MessageCodeEnum.getMessageCode(messageReturn.getCode());
        	if(null != messageCode) {
        		result.setResultMsg(messageCode.getDesc());
        	} else {
        		result.setResultMsg("unknown result code.");
        	}
        } else {
        	result.setResultMsg("unknown result code.");
        }
        
		return result;
	}

	private DeliveryInfo getDeliveryInfo() {
		DeliveryInfo di = new DeliveryInfo();
		di.setAccount(config.getUserId());
		di.setVilidate_code(config.getValidateCode());
		di.setService_type(config.getServiceType());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		di.setTimestamp(sdf.format(new Date()));
		return di;
	}
	
	public void setConfig(MessageConfig config) {
		this.config = config;
	}

}
