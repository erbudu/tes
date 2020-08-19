package com.supporter.prj.cneec.tpc.entityIdMipping.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @Title: Entity
 * @Description: 贸易主键映射表,字段与数据库字段一一对应.
 * @date: 2019-07-16
 * @version: V1.0
 */
@MappedSuperclass
public class BaseEntityIdMipping implements Serializable {

	private static final long serialVersionUID = 1L;
	private String tpcEntityId;
	private Long entityId;
	private String moduleName;

	/**
	 * 无参构造函数.
	 */
	public BaseEntityIdMipping() {
	}

	/**
	 * 构造函数.
	 *
	 * @param tpcEntityId
	 */
	public BaseEntityIdMipping(String tpcEntityId) {
		this.tpcEntityId = tpcEntityId;
	}

	/**
	 * 方法: 取得贸易业务ID.
	 * @return: java.lang.String 贸易业务ID
	 */
	@Id
	//@GeneratedValue(generator = "systemUUID")
	//@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@Column(name = "TPC_ENTITY_ID", nullable = false, length = 32)
	public String getTpcEntityId() {
		return this.tpcEntityId;
	}

	/**
	 * 方法: 设置贸易业务ID.
	 * @param: java.lang.String 贸易业务ID
	 */
	public void setTpcEntityId(String tpcEntityId) {
		this.tpcEntityId = tpcEntityId;
	}

	/**
	 * 方法: 取得转换业务ID.
	 * @return: int 转换业务ID
	 */
	@Column(name = "ENTITY_ID", nullable = true, precision = 10)
	public Long getEntityId() {
		return this.entityId;
	}

	/**
	 * 方法: 设置转换业务ID.
	 * @param: int 转换业务ID
	 */
	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	/**
	 * 方法: 取得应用编号.
	 * @return: java.lang.String 应用编号
	 */
	@Column(name = "MODULE_NAME", nullable = true, length = 64)
	public String getModuleName() {
		return this.moduleName;
	}

	/**
	 * 方法: 设置应用编号.
	 * @param: java.lang.String 应用编号
	 */
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

}
