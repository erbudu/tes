package com.supporter.prj.ppm.ecc.base_info.partner.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.ppm.ecc.base_info.partner.entity.base.BaseEccPartnerFso;

/**
 * @Title: Entity
 * @Description: 出口管制项目合同伙伴办事处.
 * @author: YAN
 * @date: 2019-08-16
 * @version: V1.0
 */
@Entity
@Table(name = "PPM_ECC_PARTNER_FSO", schema = "")
public class EccPartnerFso extends BaseEccPartnerFso implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public EccPartnerFso() {
		super();
	}

	/**
	 * 构造函数.
	 * @param fsoId
	 */
	public EccPartnerFso(String fsoId) {
		super(fsoId);
	}

}
