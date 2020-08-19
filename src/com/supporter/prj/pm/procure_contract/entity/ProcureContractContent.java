package com.supporter.prj.pm.procure_contract.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import com.supporter.prj.pm.procure_contract.entity.base.BaseProcureContractContent;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.StringUtils;

/**   
 * @Title: Entity
 * @Description: PM_PROCURE_CONTRACT_CONTENT.
 * @author Administrator
 * @date 2018-07-04 18:04:17
 * @version V1.0   
 *
 */
@Entity
@Table(name = "PM_PROCURE_CONTRACT_CONTENT", schema = "")
public class ProcureContractContent extends BaseProcureContractContent {

	private static final long serialVersionUID = 1L;
	
	//----------------------------实体类扩展属性定义,注意属性前加@Transient注解标示非数据库字段----------------------------
	/**
	 *搜索关键词.
	 */
	@Transient
	private String keyword;
	
	//--------------------------------------实体枚举类定义-----------------------------------------
	
	
	//--------------------------------------构造函数定义-----------------------------------------
	/**
	 * 无参构造函数.
	 */
	public ProcureContractContent(){
		super();
	}
	
	/**
	 * 构造函数.
	 * @param recId
	 */
	public ProcureContractContent(String recId){
		super(recId);
	} 
	
	@Override
	public boolean equals(Object other)
	{		
		if ( !(other instanceof ProcureContractContent) ){
			return false;
		}
		ProcureContractContent castOther = (ProcureContractContent) other;
		return StringUtils.equals(this.getRecId(), castOther.getRecId());
	}

	@Override
	public int hashCode()
	{
		return new HashCodeBuilder().append(getRecId()).toHashCode();
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
	
	
}
