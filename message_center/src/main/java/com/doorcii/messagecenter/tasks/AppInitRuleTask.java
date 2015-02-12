package com.doorcii.messagecenter.tasks;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import com.doorcii.messagecenter.beans.AppRules;
import com.doorcii.messagecenter.beans.AppsBean;
import com.doorcii.messagecenter.enums.FrequencyTypeEnum;
import com.doorcii.messagecenter.ibatis.AppDAO;
import com.doorcii.messagecenter.ibatis.AppRuleDAO;

/**
 * 重置每个应用每天可用短信条数
 * 暂时频率能控制到天、周、月
 * @author Jacky
 * 2015年2月12日
 */
public class AppInitRuleTask {
	
	private static final Logger logger = Logger.getLogger("TASK-LOGGER");
	
	private AppRuleDAO appRuleDAO;
	
	private AppDAO appDAO;
	
	public AppInitRuleTask(AppRuleDAO appRuleDAO,AppDAO appDAO) {
		this.appRuleDAO = appRuleDAO;
		this.appDAO = appDAO;
	}
	
	public void execute() throws Exception {
		long start = System.currentTimeMillis();
		logger.info("短信频率规则初始化任务开始..");
		try {
			List<AppRules> appRuleList = appRuleDAO.queryAppRules();
			Date now = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("dd");
			// 是否是一号
			boolean isFirstDay = sdf.format(now).equals("01");
			
			GregorianCalendar now1=new GregorianCalendar();
			int day=now1.get(Calendar.DAY_OF_WEEK);
			// 是否是周一
			boolean isMonday = (day==2);
			
			if(CollectionUtils.isNotEmpty(appRuleList)) {
				for(AppRules appRule : appRuleList) {
					AppsBean appsBean = appDAO.queryAppById(appRule.getAppId());
					if(null == appsBean) {
						logger.error("appId:"+appRule.getAppId()+",查询app不存在，请核实！");
						continue;
					}
					long plus = appRule.getMax();
					// 如果是按照天，毫无疑问去重置
					if((appRule.getFrequencyType() == FrequencyTypeEnum.DAY.getId()) 
							|| (appRule.getFrequencyType() == FrequencyTypeEnum.WEEK.getId() && isMonday) || 
							(appRule.getFrequencyType() == FrequencyTypeEnum.MOUNTH.getId() && isFirstDay)) {
						if(!appDAO.initOverPlus(appRule.getAppId(), plus, 0)) {
							logger.error("appId:"+appRule.getAppId()+" 初始化短信条数失败！请手动初始化！");
						}
					}
					
				}
			}
		} catch(Exception e) {
			logger.error("",e);
		}
		long end = System.currentTimeMillis();
		logger.info("短信频率规则初始化任务结束..总耗时："+(end-start)+"ms");
		
	}

	public void setAppRuleDAO(AppRuleDAO appRuleDAO) {
		this.appRuleDAO = appRuleDAO;
	}

	public void setAppDAO(AppDAO appDAO) {
		this.appDAO = appDAO;
	}
	
}
