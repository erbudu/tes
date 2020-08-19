package com.supporter.prj.ppm.contract.pact.report.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.ppm.contract.pact.report.entity.base.BaseContractPactReportBfdF;

@Entity
@Table(name = "PPM_CONTRACT_PACT_REPORT_BFD_F", schema = "")
public class ContractPactReportBfdF extends BaseContractPactReportBfdF {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造
	 */
	public ContractPactReportBfdF() {
		super();
	}

	/**
	 * 全参构造
	 */
	public ContractPactReportBfdF(String recordId, String bfdId, String reportId, String fuFileId, int isUseSeal, int displayOrder) {
		super(recordId, bfdId, reportId, fuFileId, isUseSeal, displayOrder);
	}

	/**
	 * 构造函数
	 */
	public ContractPactReportBfdF(String recordId) {
		super(recordId);
	}


}
