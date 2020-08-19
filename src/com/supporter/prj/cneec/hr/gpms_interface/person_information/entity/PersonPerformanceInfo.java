package com.supporter.prj.cneec.hr.gpms_interface.person_information.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import com.supporter.prj.cneec.hr.gpms_interface.person_information.entity.base.BasePersonPerformanceInfo;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.StringUtils;

/**   
 * @Title: Entity
 * @Description: OA_PERSON_PERFORMANCE_INFO.
 * @author T
 * @date 2017-09-16 10:34:08
 * @version V1.0   
 *
 */
@Entity
@Table(name = "OA_PERSON_PERFORMANCE_INFO", schema = "")
public class PersonPerformanceInfo extends BasePersonPerformanceInfo {

	private static final long serialVersionUID = 1L;
	
	//扩展数据库以外的其他属性，注意属性前加@Transient注解标示非数据库字段.
	
	/**
	 * 无参构造函数.
	 */
	public PersonPerformanceInfo(){
		super();
	}
	
	/**
	 * 构造函数.
	 * @param recordId
	 */
	public PersonPerformanceInfo(String recordId){
		super(recordId);
	} 
	
	public boolean equals(Object other)
	{		
		if ( !(other instanceof PersonPerformanceInfo) ){
			return false;
		}
		PersonPerformanceInfo castOther = (PersonPerformanceInfo) other;
		return StringUtils.equals(this.getRecordId(), castOther.getRecordId());
	}


	public int hashCode()
	{
		return new HashCodeBuilder().append(getRecordId()).toHashCode();
	}
}
