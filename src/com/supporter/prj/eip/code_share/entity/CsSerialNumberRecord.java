package com.supporter.prj.eip.code_share.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.eip.code_share.entity.base.BaseCsSerialNumberRecord;

/**
 * [应用库.编号规则明细表]对应的实体类.
 * 
 * @author 康博
 * @createDate 2017-05-02
 * @since 6.0
 * @modifier 康博
 * @modifiedDate 2017-05-02
 */
@Entity
@Table(name = "CS_SERIAL_NUMBER_RECORD")
public class CsSerialNumberRecord extends BaseCsSerialNumberRecord {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 业务对象元数据对应的元数据显示名称.
	 */
	@Transient
	private String recordTypeLabelName;
	
	/**
	 * 业务对象元数据对应的元数据类型.
	 */
	@Transient
	private int dataType;
	
	/**
	 * @return 返回  {@link #recordTypeLabelName}。
	 */
	public String getRecordTypeLabelName() {
		return recordTypeLabelName;
	}

	/**
	 * @param recordTypeLabelName 要设置的 {@link #recordTypeLabelName}。
	 */
	public void setRecordTypeLabelName(String recordTypeLabelName) {
		this.recordTypeLabelName = recordTypeLabelName;
	}

	/**
	 * @return 返回  {@link #dataType}。
	 */
	public int getDataType() {
		return dataType;
	}

	/**
	 * @param dataType 要设置的 {@link #dataType}。
	 */
	public void setDataType(int dataType) {
		this.dataType = dataType;
	}
}
