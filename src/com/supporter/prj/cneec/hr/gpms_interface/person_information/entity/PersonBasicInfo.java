package com.supporter.prj.cneec.hr.gpms_interface.person_information.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import com.supporter.prj.cneec.hr.gpms_interface.person_information.entity.base.BasePersonBasicInfo;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.StringUtils;

/**   
 * @Title: Entity
 * @Description: OA_PERSON_BASIC_INFO.
 * @author T
 * @date 2017-09-16 10:04:53
 * @version V1.0   
 *
 */
@Entity
@Table(name = "OA_PERSON_BASIC_INFO", schema = "")
public class PersonBasicInfo extends BasePersonBasicInfo {

	private static final long serialVersionUID = 1L;
	
	//扩展数据库以外的其他属性，注意属性前加@Transient注解标示非数据库字段.
	
	/**
	 * 无参构造函数.
	 */
	public PersonBasicInfo(){
		super();
	}
	
	/**
	 * 构造函数.
	 * @param hrId
	 */
	public PersonBasicInfo(String hrId){
		super(hrId);
	} 
	
	public boolean equals(Object other)
	{		
		if ( !(other instanceof PersonBasicInfo) ){
			return false;
		}
		PersonBasicInfo castOther = (PersonBasicInfo) other;
		return StringUtils.equals(this.getHrId(), castOther.getHrId());
	}


	public int hashCode()
	{
		return new HashCodeBuilder().append(getHrId()).toHashCode();
	}
}
