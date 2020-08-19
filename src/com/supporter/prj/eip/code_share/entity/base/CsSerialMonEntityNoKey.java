package com.supporter.prj.eip.code_share.entity.base;

import java.io.Serializable;


/**
 * [月度业务对象顺序号]对应的实体类的联合主键.
 * 
 * @author 康博
 * @createDate 2017-5-19
 * @since 6.0
 *
 */
public class CsSerialMonEntityNoKey implements Serializable {
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
	
	/**
	 * 月份.
	 */
	private int month;

	public CsSerialMonEntityNoKey(int year, int month, String serialNumberId, String entityId) {
		this.year = year;
		this.month = month;
		this.serialNumberId = serialNumberId;
		this.entityId = entityId;
	}
	
	public CsSerialMonEntityNoKey() {
		super();
	}

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

	/**
	 * @return 返回  {@link #month}。
	 */
	public int getMonth() {
		return month;
	}

	/**
	 * @param month 要设置的 {@link #month}。
	 */
	public void setMonth(int month) {
		this.month = month;
	}
}
