package com.supporter.prj.pm.contract_balance.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.supporter.prj.pm.contract_balance.entity.base.BaseContractBalanceConstSite;
import com.supporter.prj.pm.procure_contract.entity.ProcureContractSite;

/**   
 * @Title: 合同结算.施工合同.涉及工程部位
 * @Description: PM_CONTRACT_BALANCE_CONST_SITE.
 * @author Administrator
 * @date 2018-07-04 18:07:39
 * @version V1.0   
 *
 */
@Entity
@Table(name = "PM_CONTRACT_BALANCE_CONST_SITE", schema = "")
public class ContractBalanceConstSite extends BaseContractBalanceConstSite {

	private static final long serialVersionUID = 1L;
	
	@Transient
	private boolean isNew = false; //是否是新增尚未保存记录
	@Transient
	private boolean isLeaf = true; //是否为叶子节点
	//private String parent = null; //该节点的父节点ID
	@Transient
	private boolean expanded = true; //该节点是否展开
	@Transient
	private int level = -1; //该节点的树层次0,1,2,3...
	
	@Transient
	private boolean isWbs = false; //是否是WBS
	
	@Transient
	private boolean isIncrease = true; //是否调增
	
	@Transient
	private boolean isContractSite = false; //是否合同内的
	
	@Transient
	private ProcureContractSite csite = null; //采购合同工程项
	
	
	//--------------------------------------构造函数定义-----------------------------------------
	/**
	 * 无参构造函数.
	 */
	public ContractBalanceConstSite() {
		super();
	}
	
	/**
	 * 构造函数.
	 * @param siteId
	 */
	public ContractBalanceConstSite(String siteId) {
		super(siteId);
	} 
	
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof ContractBalanceConstSite)) {
			return false;
		}
		ContractBalanceConstSite castOther = (ContractBalanceConstSite) other;
		return StringUtils.equals(this.getSiteId(), castOther.getSiteId());
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getSiteId()).toHashCode();
	}
	
	
	public boolean getIsNew() {
		return isNew;
	}
	public void setIsNew(boolean isNew) {
		this.isNew = isNew;
	}
	
	public boolean getIsLeaf() {
		return this.isLeaf;
	}
	public void setIsLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}
	
	//该节点的ID
	@Transient
	public String getId() {
		return this.getSiteId();
	}
	
	//该节点的父节点ID
	@Transient
	public String getParent() {
		return this.getParentSiteId();
	}
	
	public boolean isExpanded() {
		return this.expanded;
	}
	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}
	
	public int getLevel() {
		return this.level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	
	public boolean getIsWbs() {
		return isWbs;
	}
	public void setIsWbs(boolean isWbs) {
		this.isWbs = isWbs;
	}
	
	public boolean getIsIncrease() {
		return this.isIncrease;
	}
	public void setIsIncrease(boolean isIncrease) {
		this.isIncrease = isIncrease;
	}
	
	/**
	 * 包含当期的累计工程量（历史累计工程量+当期工程量）
	 * @return double
	 */
	@Transient
	public double getAccumulativeCountCur() {
		return this.getAccumulativeCount() + this.getCurrentCount();
	}
	
	/**
	 * 包含当期的累计付款额（历史累计付款额+当期付款额）
	 * @return double
	 */
	@Transient
	public double getAccumulativePayCur() {
		return this.getAccumulativePay() + this.getCurrentPay();
	}
	
	/**
	 * 合同外（签证）:包含当期的累计工程量\包含当期的累计付款额
	 * @return
	 */
	@Transient
	public double getAccumulativeCountVCur() {
		return this.getAccumulativeCountV() + this.getCurrentCountV();
	}
	@Transient
	public double getAccumulativePayVCur() {
		return this.getAccumulativePayV() + this.getCurrentPayV();
	}
	
	/**
	 * 审核确认-合同内:包含当期的累计工程量\包含当期的累计付款额
	 * @return
	 */
	@Transient
	public double getAccumulativeCountExamCur() {
		return this.getAccumulativeCountExam() + this.getCurrentCountExam();
	}
	@Transient
	public double getAccumulativePayExamCur() {
		return this.getAccumulativePayExam() + this.getCurrentPayExam();
	}
	/**
	 * 审核确认-合同外（签证）:包含当期的累计工程量\包含当期的累计付款额
	 * @return
	 */
	@Transient
	public double getAccumulativeCountExamVCur() {
		return this.getAccumulativeCountExamV() + this.getCurrentCountExamV();
	}
	@Transient
	public double getAccumulativePayExamVCur() {
		return this.getAccumulativePayExamV() + this.getCurrentPayExamV();
	}
	
	public boolean getIsContractSite() {
		return this.isContractSite;
	}
	public void setIsContractSite(boolean isContractSite) {
		this.isContractSite = isContractSite;
	}
	
	@Transient
	public double getAccumulativeCountV2Cur() {
		return this.getAccumulativeCountV2() + this.getCurrentCountV2();
	}

	@Transient
	public double getAccumulativePayV2Cur() {
		return this.getAccumulativePayV2() + this.getCurrentPayV2();
	}

	@Transient
	public double getAccumulativeCountExamV2Cur() {
		return this.getAccumulativeCountExamV2() + this.getCurrentCountExamV2();
	}

	@Transient
	public double getAccumulativePayExamV2Cur() {
		return this.getAccumulativePayExamV2() + this.getCurrentPayExamV2();
	}

	@Transient
	public double getAccumuFinRatioCur() {
		if (this.getPrice() > 0) {
			return this.getAccumulativePayCur() / this.getPrice() * 100;
		} else {
			return 0;
		}
	}

	@Transient
	public double getAccumuFinRatioExamCur() {
		if (this.getPrice() > 0) {
			return this.getAccumulativePayExamCur() / this.getPrice() * 100;
		} else {
			return 0;
		}
	}

	@Override
	public void setCurrentPay(double currentPay) {
		super.setCurrentPay(currentPay);
		if (this.getPrice() > 0) {
			this.setCurrentFinRatio(currentPay / this.getPrice() * 100);
		} else {
			this.setCurrentFinRatio(0);
		}
	}

	@Override
	public void setCurrentPayExam(double currentPayExam) {
		super.setCurrentPayExam(currentPayExam);
		if (this.getPrice() > 0) {
			this.setCurrentFinRatioExam(currentPayExam / this.getPrice() * 100);
		} else {
			this.setCurrentFinRatioExam(0);
		}
	}

	@Override
	public void setAccumulativePay(double accumulativePay) {
		super.setAccumulativePay(accumulativePay);
		if (this.getPrice() > 0) {
			this.setAccumuFinRatio(accumulativePay / this.getPrice() * 100);
		} else {
			this.setAccumuFinRatio(0);
		}
	}

	@Override
	public void setAccumulativePayExam(double accumulativePayExam) {
		super.setAccumulativePayExam(accumulativePayExam);
		if (this.getPrice() > 0) {
			this.setAccumuFinRatioExam(accumulativePayExam / this.getPrice() * 100);
		} else {
			this.setAccumuFinRatioExam(0);
		}
	}

}
