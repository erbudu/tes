package com.supporter.prj.ppm.contract.sign.report.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.ppm.contract.sign.report.entity.base.BaseContractSignReportCon;

/**
 * @Title: Entity
 * @Description: 报审审核结果.
 * @author: YAN
 * @date: 2019-09-05
 * @version: V1.0
 */
@Entity
@Table(name = "PPM_CONTRACT_SIGN_REPORT_CON", schema = "")
public class ContractSignReportCon extends BaseContractSignReportCon implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public ContractSignReportCon() {
		super();
	}

	/**
	 * 构造函数.
	 * @param contractSignRvConId
	 */
	public ContractSignReportCon(String contractSignRvConId) {
		super(contractSignRvConId);
	}

}
