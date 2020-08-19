package com.supporter.prj.cneec.weather_report.entity.base;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

/**   
 * @Title: Entity
 * @Description: OA_WERTHER_REPORT,字段与数据库字段一一对应.
 * @author T
 * @date 2017-09-27 17:44:32
 * @version V1.0   
 *
 */
@MappedSuperclass
public  class BaseWertherReport  implements Serializable {
	private static final long serialVersionUID = 1L;
	//WEATHER_REPORT_ID.
	@Id
	@GeneratedValue(generator = "assigned")
	@GenericGenerator(name = "assigned", strategy = "assigned")
	@Column(name = "WEATHER_REPORT_ID",nullable = false,length = 32)
	private java.lang.String weatherReportId;
	//WEATHER_REPORT_CONTENT.
	@Column(name = "WEATHER_REPORT_CONTENT",nullable = true)
	private java.lang.String weatherReportContent;
	
	/**
	 *方法: 取得WEATHER_REPORT_ID.
	 *@return: java.lang.String  WEATHER_REPORT_ID
	 */
	public java.lang.String getWeatherReportId(){
		return this.weatherReportId;
	}

	/**
	 *方法: 设置WEATHER_REPORT_ID.
	 *@param: java.lang.String  WEATHER_REPORT_ID
	 */
	public void setWeatherReportId(java.lang.String weatherReportId){
		this.weatherReportId = weatherReportId;
	}
	/**
	 *方法: 取得WEATHER_REPORT_CONTENT.
	 *@return: java.lang.String  WEATHER_REPORT_CONTENT
	 */
	public java.lang.String getWeatherReportContent(){
		return this.weatherReportContent;
	}

	/**
	 *方法: 设置WEATHER_REPORT_CONTENT.
	 *@param: java.lang.String  WEATHER_REPORT_CONTENT
	 */
	public void setWeatherReportContent(java.lang.String weatherReportContent){
		this.weatherReportContent = weatherReportContent;
	}
	
	/**
	 * 无参构造函数.
	 */
	public BaseWertherReport(){
	
	}
	
	/**
	 * 构造函数.
	 * @param weatherReportId
	 */
	public BaseWertherReport(String weatherReportId){
		this.weatherReportId = weatherReportId;
	} 
}
