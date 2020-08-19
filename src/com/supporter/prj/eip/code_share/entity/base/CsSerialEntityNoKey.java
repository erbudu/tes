package com.supporter.prj.eip.code_share.entity.base;

import java.io.Serializable;

// ~ File Information
// ====================================================================================================================

/**
 * [业务对象顺序号]对应的实体类的联合主键.
 * 
 * @author 康博
 * @createDate 2017-5-18
 * @since 6.0
 *
 */
public class CsSerialEntityNoKey implements Serializable {

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

	
	public CsSerialEntityNoKey(String serialNumberId, String entityId) {
		this.serialNumberId = serialNumberId;
		this.entityId = entityId;
	}
	
	public CsSerialEntityNoKey() {
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
}
