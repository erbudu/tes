package com.supporter.prj.ppm.contract.effect.report.entity;

import com.supporter.prj.ppm.contract.effect.report.entity.base.BaseContractEffectReportBfdF;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * @Title: Entity
 * @Description: 主合同签约评审资料清单单文件.
 * @author: YAN
 * @date: 2019-09-05
 * @version: V1.0
 */
@Entity
@Table(name = "ppm_contract_effect_rep_bfd_f", schema = "")
public class ContractEffectReportBfdF extends BaseContractEffectReportBfdF implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public ContractEffectReportBfdF() {
		super();
	}

	/**
	 * 构造函数.
	 * @param recordId
	 */
	public ContractEffectReportBfdF(String recordId) {
		super(recordId);
	}

}
