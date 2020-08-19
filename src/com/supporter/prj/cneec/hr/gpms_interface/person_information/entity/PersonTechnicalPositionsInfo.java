package com.supporter.prj.cneec.hr.gpms_interface.person_information.entity;


import javax.persistence.Entity;
import javax.persistence.Table;
import com.supporter.prj.cneec.hr.gpms_interface.person_information.entity.base.BasePersonTechnicalPositionsInfo;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.StringUtils;

/**   
 * @Title: Entity
 * @Description: OA_PERSON_TECHNICAL_POSITIONS.
 * @author T
 * @date 2017-09-16 10:40:06
 * @version V1.0   
 *
 */
@Entity
@Table(name = "OA_PERSON_TECHNICAL_POSITIONS_INFO", schema = "")
public class PersonTechnicalPositionsInfo extends BasePersonTechnicalPositionsInfo {

	private static final long serialVersionUID = 1L;
	
	//扩展数据库以外的其他属性，注意属性前加@Transient注解标示非数据库字段.
	
	/**
	 * 无参构造函数.
	 */
	public PersonTechnicalPositionsInfo(){
		super();
	}
	
	/**
	 * 构造函数.
	 * @param recordId
	 */
	public PersonTechnicalPositionsInfo(String recordId){
		super(recordId);
	} 
	
	public boolean equals(Object other)
	{		
		if ( !(other instanceof PersonTechnicalPositionsInfo) ){
			return false;
		}
		PersonTechnicalPositionsInfo castOther = (PersonTechnicalPositionsInfo) other;
		return StringUtils.equals(this.getRecordId(), castOther.getRecordId());
	}


	public int hashCode()
	{
		return new HashCodeBuilder().append(getRecordId()).toHashCode();
	}
}
