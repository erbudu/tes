package com.supporter.prj.ppm.ecc.base_info.owner.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.ppm.ecc.base_info.owner.entity.base.BaseEccOwnerFso;

/**
 * @Title: Entity
 * @Description: 出口管制主办事处.
 * @author: YAN
 * @date: 2019-08-16
 * @version: V1.0
 */
@Entity
@Table(name = "PPM_ECC_OWNER_FSO", schema = "")
public class EccOwnerFso extends BaseEccOwnerFso implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public EccOwnerFso() {
		super();
	}

	/**
	 * 构造函数.
	 * @param fsoId
	 */
	public EccOwnerFso(String fsoId) {
		super(fsoId);
	}

}
