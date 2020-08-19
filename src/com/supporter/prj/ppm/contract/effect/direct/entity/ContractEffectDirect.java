package com.supporter.prj.ppm.contract.effect.direct.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.ppm.contract.effect.direct.entity.base.BaseContractEffectDirect;
import com.supporter.prj.ppm.contract.sign.filing.entity.ContractEffectCondition;
import com.supporter.util.CodeTable;

/**
 * @Title: Entity
 * @Description: 主合同直接生效.
 * @author: YAN
 * @date: 2019-09-18
 * @version: V1.0
 */
@Entity
@Table(name = "PPM_CONTRACT_effect_DIRECT", schema = "")
public class ContractEffectDirect extends BaseContractEffectDirect implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public ContractEffectDirect() {
		super();
	}

	/**
	 * 构造函数.
	 * @param directId
	 */
	public ContractEffectDirect(String directId) {
		super(directId);
	}

	private String filingNo;
	@Transient
	public String getFilingNo() {
		return filingNo;
	}

	public void setFilingNo(String filingNo) {
		this.filingNo = filingNo;
	}
	private String prjNo;//项目编码
	private String prjCName;//项目中文名称
	private String prjEName;//项目英文名称

	private double contractAmout;
	private String contractRange;

	private Date contractSignDate;
	@Transient
	public double getContractAmout() {
		return contractAmout;
	}

	public void setContractAmout(double contractAmout) {
		this.contractAmout = contractAmout;
	}
	@Transient
	public String getContractRange() {
		return contractRange;
	}

	public void setContractRange(String contractRange) {
		this.contractRange = contractRange;
	}
	@Transient
	public Date getContractSignDate() {
		return contractSignDate;
	}

	public void setContractSignDate(Date contractSignDate) {
		this.contractSignDate = contractSignDate;
	}

	@Transient
	public String getPrjNo() {
		return prjNo;
	}

	public void setPrjNo(String prjNo) {
		this.prjNo = prjNo;
	}
	@Transient
	public String getPrjCName() {
		return prjCName;
	}

	public void setPrjCName(String prjCName) {
		this.prjCName = prjCName;
	}
	@Transient
	public String getPrjEName() {
		return prjEName;
	}

	public void setPrjEName(String prjEName) {
		this.prjEName = prjEName;
	}

	private List<ContractEffectCondition> conditionList;
	@Transient
	public List<ContractEffectCondition> getConditionList() {
		return conditionList;
	}

	public void setConditionList(List<ContractEffectCondition> conditionList) {
		this.conditionList = conditionList;
	}
	// 状态
	public static final class StatusCodeTable {
		/** 草稿 */
		public static final int DRAFT = 0;
		/** 审批中 */
		public static final int EXAM = 1;
		/** 审批完成(生效) */
		public static final int COMPLETE = 2;

		// 状态
		public static CodeTable getCodeTable() {
			CodeTable ct = new CodeTable();
			ct.insertItem(DRAFT, "草稿");
			ct.insertItem(EXAM, "审批中");
			ct.insertItem(COMPLETE, "审批完成");
			return ct;
		}
	}
}
