package com.supporter.prj.cneec.hr.gpms_interface.person_information.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import com.supporter.prj.cneec.hr.gpms_interface.person_information.entity.base.BasePersonBirthPlaceCode;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.StringUtils;

/**   
 * @Title: Entity
 * @Description: OA_PERSON_BIRTH_PLACE_CODE.
 * @author T
 * @date 2017-09-16 10:17:07
 * @version V1.0   
 *
 */
@Entity
@Table(name = "OA_PERSON_BIRTH_PLACE_CODE", schema = "")
public class PersonBirthPlaceCode extends BasePersonBirthPlaceCode {

	private static final long serialVersionUID = 1L;
	
	//扩展数据库以外的其他属性，注意属性前加@Transient注解标示非数据库字段.
	
	/**
	 * 无参构造函数.
	 */
	public PersonBirthPlaceCode(){
		super();
	}
	
	/**
	 * 构造函数.
	 * @param codeId
	 */
	public PersonBirthPlaceCode(String codeId){
		super(codeId);
	} 
	
	public boolean equals(Object other)
	{		
		if ( !(other instanceof PersonBirthPlaceCode) ){
			return false;
		}
		PersonBirthPlaceCode castOther = (PersonBirthPlaceCode) other;
		return StringUtils.equals(this.getCodeId(), castOther.getCodeId());
	}


	public int hashCode()
	{
		return new HashCodeBuilder().append(getCodeId()).toHashCode();
	}
}
