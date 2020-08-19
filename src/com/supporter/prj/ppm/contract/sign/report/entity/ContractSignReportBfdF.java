package com.supporter.prj.ppm.contract.sign.report.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.ppm.contract.sign.report.entity.base.BaseContractSignReportBfdF;

/**
 * @Title: Entity
 * @Description: 主合同签约评审资料清单单文件.
 * @author: YAN
 * @date: 2019-09-05
 * @version: V1.0
 */
@Entity
@Table(name = "PPM_CONTRACT_SIGN_REPORT_BFD_F", schema = "")
public class ContractSignReportBfdF extends BaseContractSignReportBfdF implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public ContractSignReportBfdF() {
		super();
	}

	/**
	 * 构造函数.
	 * @param recordId
	 */
	public ContractSignReportBfdF(String recordId) {
		super(recordId);
	}

}
