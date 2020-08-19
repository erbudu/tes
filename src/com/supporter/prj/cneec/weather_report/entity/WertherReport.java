package com.supporter.prj.cneec.weather_report.entity;


import javax.persistence.Entity;
import javax.persistence.Table;
import com.supporter.prj.cneec.weather_report.entity.base.BaseWertherReport;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.StringUtils;

/**   
 * @Title: Entity
 * @Description: OA_WERTHER_REPORT.
 * @author T
 * @date 2017-09-27 17:44:32
 * @version V1.0   
 *
 */
@Entity
@Table(name = "OA_WERTHER_REPORT", schema = "")
public class WertherReport extends BaseWertherReport {

	private static final long serialVersionUID = 1L;
	
	//扩展数据库以外的其他属性，注意属性前加@Transient注解标示非数据库字段.
	
	/**
	 * 无参构造函数.
	 */
	 
	public WertherReport(){
		super();
	}
	
	/**
	 * 构造函数.
	 * @param weatherReportId
	 */
	public WertherReport(String weatherReportId){
		super(weatherReportId);
	} 
	
	public boolean equals(Object other)
	{		
		if ( !(other instanceof WertherReport) ){
			return false;
		}
		WertherReport castOther = (WertherReport) other;
		return StringUtils.equals(this.getWeatherReportId(), castOther.getWeatherReportId());
	}


	public int hashCode()
	{
		return new HashCodeBuilder().append(getWeatherReportId()).toHashCode();
	}
}
