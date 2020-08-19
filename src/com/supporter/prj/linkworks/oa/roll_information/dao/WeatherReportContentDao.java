package com.supporter.prj.linkworks.oa.roll_information.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.linkworks.oa.roll_information.entity.WeatherReportContent;


/**   
 * @Title: Entity
 * @Description: 功能模块表
 * @author linxiaosong
 * @version V1.0   
 *
 */
@Repository
public class WeatherReportContentDao extends MainDaoSupport < WeatherReportContent, String >{

	/**
	 * 获取今天与明天的天气预报
	 * @param abroadRecordId
	 * @return
	 */
	public List<WeatherReportContent> getWeatherReport() {
		String hql = " from " + WeatherReportContent.class.getName() + " where 1 = ?";
		List<WeatherReportContent> managers = this.find(hql,1);
		return managers;
	}
}
