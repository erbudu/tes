package com.supporter.prj.linkworks.oa.roll_information.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.linkworks.oa.roll_information.dao.WeatherReportContentDao;
import com.supporter.prj.linkworks.oa.roll_information.entity.WeatherReportContent;


/**   
 * @Title: Service
 * @Description: 功能模块表
 * @author linxiaosong
 * @version V1.0   
 *
 */
@Service
public class WeatherReportContentService {

	@Autowired
	private WeatherReportContentDao reportContentDao;
	
	/**
	 * 获取天气信息
	 * @return
	 */
	public List<WeatherReportContent> getWeatherReport(){
		return reportContentDao.getWeatherReport();
	}
}
