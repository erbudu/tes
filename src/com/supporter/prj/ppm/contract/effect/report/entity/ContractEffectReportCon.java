package com.supporter.prj.ppm.contract.effect.report.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.ppm.contract.effect.report.entity.base.BaseContractEffectReportCon;

/**
 * @Title: Entity
 * @Description: 报审审核结果.
 * @author: YAN
 * @date: 2019-09-05
 * @version: V1.0
 */
@Entity
@Table(name = "PPM_CONTRACT_Effect_REPORT_CON", schema = "")
public class ContractEffectReportCon extends BaseContractEffectReportCon implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public ContractEffectReportCon() {
		super();
	}

	/**
	 * 构造函数.
	 * @param contractEffectRvConId
	 */
	public ContractEffectReportCon(String contractEffectRvConId) {
		super(contractEffectRvConId);
	}

}
