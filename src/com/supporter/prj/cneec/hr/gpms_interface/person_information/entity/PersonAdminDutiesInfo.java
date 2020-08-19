package com.supporter.prj.cneec.hr.gpms_interface.person_information.entity;


import javax.persistence.Entity;
import javax.persistence.Table;
import com.supporter.prj.cneec.hr.gpms_interface.person_information.entity.base.BasePersonAdminDutiesInfo;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.StringUtils;

/**   
 * @Title: Entity
 * @Description: oa_person_administrative_duties_info.
 * @author T
 * @date 2017-09-21 10:45:16
 * @version V1.0   
 *
 */
@Entity
@Table(name = "OA_PERSON_ADMINISTRATIVE_DUTIES_INFO", schema = "")
public class PersonAdminDutiesInfo extends BasePersonAdminDutiesInfo {

	private static final long serialVersionUID = 1L;
	
	//扩展数据库以外的其他属性，注意属性前加@Transient注解标示非数据库字段.
	
	/**
	 * 无参构造函数.
	 */
	public PersonAdminDutiesInfo(){
		super();
	}
	
	/**
	 * 构造函数.
	 * @param recordId
	 */
	public PersonAdminDutiesInfo(String recordId){
		super(recordId);
	} 
	
	public boolean equals(Object other)
	{		
		if ( !(other instanceof PersonAdminDutiesInfo) ){
			return false;
		}
		PersonAdminDutiesInfo castOther = (PersonAdminDutiesInfo) other;
		return StringUtils.equals(this.getRecordId(), castOther.getRecordId());
	}


	public int hashCode()
	{
		return new HashCodeBuilder().append(getRecordId()).toHashCode();
	}
}
