package com.supporter.prj.eip.code_share.entity.base;

import java.io.Serializable;


/**
 * [年度业务对象顺序号]对应的实体类的联合主键.
 * 
 * @author 康博
 * @createDate 2017-5-19
 * @since 6.0
 *
 */
public class CsSerialYearEntityNoKey implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 编号规则ID .
	 */
	private String serialNumberId;
		
	/**
	 * 业务对象ID .
	 */
	private String entityId;
	
	/**
	 * 年份.
	 */
	private int year;
	
	
	public CsSerialYearEntityNoKey(int year, String serialNumberId, String entityId) {
		this.year = year;
		this.serialNumberId = serialNumberId;
		this.entityId = entityId;
	}
	
	public CsSerialYearEntityNoKey() {
		super();
	}

	// ~ Methods
	// ================================================================================================================

	/**
	 * @return 返回  {@link #serialNumberId}。
	 */
	public String getSerialNumberId() {
		return serialNumberId;
	}

	/**
	 * @param serialNumberId 要设置的 {@link #serialNumberId}。
	 */
	public void setSerialNumberId(String serialNumberId) {
		this.serialNumberId = serialNumberId;
	}

	/**
	 * @return 返回  {@link #entityId}。
	 */
	public String getEntityId() {
		return entityId;
	}

	/**
	 * @param entityId 要设置的 {@link #entityId}。
	 */
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	/**
	 * @return 返回  {@link #year}。
	 */
	public int getYear() {
		return year;
	}

	/**
	 * @param year 要设置的 {@link #year}。
	 */
	public void setYear(int year) {
		this.year = year;
	}
}
