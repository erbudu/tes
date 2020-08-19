package com.supporter.prj.cneec.hr.gpms_interface.person_information.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import com.supporter.prj.cneec.hr.gpms_interface.person_information.entity.base.BasePersonNational;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.StringUtils;

/**   
 * @Title: Entity
 * @Description: OA_PERSON_NATIONAL.
 * @author T
 * @date 2017-09-16 10:31:33
 * @version V1.0   
 *
 */
@Entity
@Table(name = "OA_PERSON_NATIONAL", schema = "")
public class PersonNational extends BasePersonNational {

	private static final long serialVersionUID = 1L;
	
	//扩展数据库以外的其他属性，注意属性前加@Transient注解标示非数据库字段.
	
	/**
	 * 无参构造函数.
	 */
	public PersonNational(){
		super();
	}
	
	/**
	 * 构造函数.
	 * @param empId
	 */
	public PersonNational(String empId){
		super(empId);
	} 
	
	public boolean equals(Object other)
	{		
		if ( !(other instanceof PersonNational) ){
			return false;
		}
		PersonNational castOther = (PersonNational) other;
		return StringUtils.equals(this.getEmpId(), castOther.getEmpId());
	}


	public int hashCode()
	{
		return new HashCodeBuilder().append(getEmpId()).toHashCode();
	}
}
