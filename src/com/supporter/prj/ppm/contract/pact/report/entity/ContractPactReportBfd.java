package com.supporter.prj.ppm.contract.pact.report.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.ppm.contract.pact.report.entity.base.BaseContractPactReportBfd;

@Entity
@Table(name = "PPM_CONTRACT_PACT_REPORT_BFD", schema = "")
public class ContractPactReportBfd extends BaseContractPactReportBfd {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造
	 */
	public ContractPactReportBfd() {
		super();
	}

	/**
	 * 构造函数
	 */
	public ContractPactReportBfd(String bfdId) {
		super(bfdId);
	}

	/**
	 * 全参构造
	 */
	public ContractPactReportBfd(String bfdId, String reportId, String fileTypeId, String fileTypeName) {
		super(bfdId, reportId, fileTypeId, fileTypeName);
	}

}
