package com.supporter.prj.linkworks.oa.roll_information.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


/**
 * @Title: Entity
 * @Description: 功能模块,字段与数据库字段一一对应.
 * @author linxiaosong
 * @version V1.0   
 */

@MappedSuperclass
public class BaseWeatherReportContent implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//id
	@Id
	@Column(name = "WEATHER_REPORT_ID", unique = true, nullable = false, precision = 32, scale = 0)
	private String weatherReportId;
	
	//天气内容
	@Column(name="WEATHER_REPORT_CONTENT" , nullable = true)
	private String weatherReportContent;
	
	

	public BaseWeatherReportContent(String weatherReportId,
			String weatherReportContent) {
		super();
		this.weatherReportId = weatherReportId;
		this.weatherReportContent = weatherReportContent;
	}
	

	public BaseWeatherReportContent() {
		super();
	}


	public String getWeatherReportId() {
		return weatherReportId;
	}

	public void setWeatherReportId(String weatherReportId) {
		this.weatherReportId = weatherReportId;
	}

	public String getWeatherReportContent() {
		return weatherReportContent;
	}

	public void setWeatherReportContent(String weatherReportContent) {
		this.weatherReportContent = weatherReportContent;
	}
	
	

}
