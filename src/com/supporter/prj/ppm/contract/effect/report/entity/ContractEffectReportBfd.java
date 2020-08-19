package com.supporter.prj.ppm.contract.effect.report.entity;

import com.supporter.prj.ppm.contract.effect.report.entity.base.BaseContractEffectReportBfd;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * @Title: Entity
 * @Description: 主合同签约评审资料单.
 * @author: YAN
 * @date: 2019-09-05
 * @version: V1.0
 */
@Entity
@Table(name = "PPM_CONTRACT_effect_REPORT_BFD", schema = "")
public class ContractEffectReportBfd extends BaseContractEffectReportBfd implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public ContractEffectReportBfd() {
		super();
	}

	/**
	 * 构造函数.
	 * @param bfdId
	 */
	public ContractEffectReportBfd(String bfdId) {
		super(bfdId);
	}

}
