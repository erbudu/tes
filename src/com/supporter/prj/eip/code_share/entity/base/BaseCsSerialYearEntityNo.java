package com.supporter.prj.eip.code_share.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.MappedSuperclass;

/**
 * [年度业务对象顺序号]对应的实体类的父类.
 * 
 * @author 康博
 * @createDate 2017-05-17
 * @since 6.0
 * @modifier 康博
 * @modifiedDate 2017-05-17
 */
@MappedSuperclass
@IdClass(value=CsSerialYearEntityNoKey.class)
public class BaseCsSerialYearEntityNo implements ISerialNoEntity, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 编号规则ID .
	 */
	@Id
	@Column(name = "SERIAL_NUMBER_ID", nullable = false, length = 32)
	private String serialNumberId;
		
	/**
	 * 年度 .
	 */
	@Id
	@Column(name = "YEAR", nullable = false)
	private int year;
		
	/**
	 * 业务对象ID .
	 */
	@Id
	@Column(name = "ENTITY_ID", nullable = false, length = 64)
	private String entityId;
		
	/**
	 * 序号 .
	 */
	@Column(name = "INDEX_NO")
	private int indexNo;
	
	public BaseCsSerialYearEntityNo(CsSerialYearEntityNoKey key) {
		this.serialNumberId = key.getSerialNumberId();
		this.entityId = key.getEntityId();
		this.year = key.getYear();
	}
	
	public BaseCsSerialYearEntityNo() {
		super();
	}

		
	/**
	 * @return 返回  {@link #serialNumberId}。
	 */
	public String getSerialNumberId() {
		return this.serialNumberId;
	}

	/**
	 * @param serialNumberId 要设置的 {@link #serialNumberId}。
	 */
	public void setSerialNumberId(String serialNumberId) {
		this.serialNumberId = serialNumberId;
	}
		
	/**
	 * @return 返回  {@link #year}。
	 */
	public int getYear() {
		return this.year;
	}

	/**
	 * @param year 要设置的 {@link #year}。
	 */
	public void setYear(int year) {
		this.year = year;
	}
		
	/**
	 * @return 返回  {@link #entityId}。
	 */
	public String getEntityId() {
		return this.entityId;
	}

	/**
	 * @param entityId 要设置的 {@link #entityId}。
	 */
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}
		
	/**
	 * @return 返回  {@link #indexNo}。
	 */
	public int getIndexNo() {
		return this.indexNo;
	}

	/**
	 * @param indexNo 要设置的 {@link #indexNo}。
	 */
	public void setIndexNo(int indexNo) {
		this.indexNo = indexNo;
	}
	
}
