package com.supporter.prj.cneec.pc.pre_prj_manager.export_control.entity;

import com.supporter.prj.cneec.pc.pre_prj_manager.export_control.entity.base.BaseExportControl;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**   
 * @Title: Entity
 * @Description: 出口管制.
 * @author kangh_000
 * @date 2018-12-20 17:53:30
 * @version V1.0   
 *
 */
@Entity
@Table(name = "PC_PRE_EXPORT_CONTROL", schema = "")
public class ExportControl extends BaseExportControl {

	private static final long serialVersionUID = 1L;
	
	//----------------------------实体类扩展属性定义,注意属性前加@Transient注解标示非数据库字段----------------------------
	/**
	 *搜索关键词.
	 */
	@Transient
	private String keyword;
	/**
	 *类型.
	 */
	@Transient
	private String type;
	
	//--------------------------------------实体枚举类定义-----------------------------------------
	
	
	//--------------------------------------构造函数定义-----------------------------------------
	/**
	 * 无参构造函数.
	 */
	public ExportControl(){
		super();
	}
	
	/**
	 * 构造函数.
	 * @param exportControlId
	 */
	public ExportControl(String exportControlId){
		super(exportControlId);
	} 
	
	@Override
	public boolean equals(Object other)
	{		
		if ( !(other instanceof ExportControl) ){
			return false;
		}
		ExportControl castOther = (ExportControl) other;
		return StringUtils.equals(this.getExportControlId(), castOther.getExportControlId());
	}

	@Override
	public int hashCode()
	{
		return new HashCodeBuilder().append(getExportControlId()).toHashCode();
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
