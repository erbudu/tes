package com.supporter.prj.cneec.tpc.entityIdMipping.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.cneec.tpc.entityIdMipping.entity.base.BaseEntityIdMipping;

/**
 * @Title: Entity
 * @Description: 贸易主键映射表.
 * @date: 2019-07-16
 * @version: V1.0
 */
@Entity
@Table(name = "TPC_ENTITY_ID_MIPPING", schema = "")
public class EntityIdMipping extends BaseEntityIdMipping implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public EntityIdMipping() {
		super();
	}

	/**
	 * 构造函数.
	 * @param tpcEntityId
	 */
	public EntityIdMipping(String tpcEntityId) {
		super(tpcEntityId);
	}

}
