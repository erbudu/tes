package com.supporter.prj.cneec.pc.pre_prj_manager.develop_agreement.entity;

import com.supporter.prj.cneec.pc.pre_prj_manager.develop_agreement.constant.AgreementStatusEnum;
import com.supporter.prj.cneec.pc.pre_prj_manager.develop_agreement.entity.base.BaseDevelopAgreement;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**   
 * @Title: Entity
 * @Description: 开发合作.
 * @author kangh_000
 * @date 2018-12-14 17:09:40
 * @version V1.0   
 *
 */
@Entity
@Table(name = "PC_PRE_DEVELOP_AGREEMENT", schema = "")
public class DevelopAgreement extends BaseDevelopAgreement {

	private static final long serialVersionUID = 1L;
	
	//----------------------------实体类扩展属性定义,注意属性前加@Transient注解标示非数据库字段----------------------------
	/**
	 *搜索关键词.
	 */
	@Transient
	private String keyword;

	/**
	 *状态描述.
	 */
	@Transient
	private String agreementStatusDesc;
	
	//--------------------------------------实体枚举类定义-----------------------------------------
	
	
	//--------------------------------------构造函数定义-----------------------------------------
	/**
	 * 无参构造函数.
	 */
	public DevelopAgreement(){
		super();
	}
	
	/**
	 * 构造函数.
	 * @param developAgreementId
	 */
	public DevelopAgreement(String developAgreementId){
		super(developAgreementId);
	} 
	
	@Override
	public boolean equals(Object other)
	{		
		if ( !(other instanceof DevelopAgreement) ){
			return false;
		}
		DevelopAgreement castOther = (DevelopAgreement) other;
		return StringUtils.equals(this.getDevelopAgreementId(), castOther.getDevelopAgreementId());
	}

	@Override
	public int hashCode()
	{
		return new HashCodeBuilder().append(getDevelopAgreementId()).toHashCode();
	}
	
	
	//--------------------------------------get和set方法-----------------------------------------
	/**
	 *方法: 取得keyword.
	 *@return: String  
	 */
	public String getKeyword() {
		return keyword;
	}
	/**
	 *方法: 设置keyword.
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getAgreementStatusDesc() {
		if (StringUtils.isNotEmpty(agreementStatusDesc)) {
			return agreementStatusDesc;
		}

		for (AgreementStatusEnum agreementStatusEnum : AgreementStatusEnum.values()) {
			if (agreementStatusEnum.getInnerName() == getAgreementStatus()) {
				agreementStatusDesc = agreementStatusEnum.getDisplayName();

				break;
			}
		}

		return agreementStatusDesc;
	}

	public void setAgreementStatusDesc(String agreementStatusDesc) {
		this.agreementStatusDesc = agreementStatusDesc;
	}
}
