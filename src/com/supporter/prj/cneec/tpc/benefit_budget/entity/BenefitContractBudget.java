package com.supporter.prj.cneec.tpc.benefit_budget.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import com.supporter.prj.cneec.tpc.benefit_budget.util.BenefitBudgetUtil;
import com.supporter.prj.cneec.tpc.benefit_budget.entity.base.BaseBenefitContractBudget;

/**
 * @Title: BenefitContractBudget
 * @Description: 贸易项目效益预算-项目合同预算表.
 * @author Yanweichao
 * @date 2018-05-29
 * @version V1.0
 */
@Entity
@Table(name = "TPC_BENEFIT_CONTRACT_BUDGET", schema = "")
public class BenefitContractBudget extends BaseBenefitContractBudget implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public BenefitContractBudget() {
		super();
	}

	/**
	 * 构造函数.
	 * 
	 * @param processBudgetId
	 */
	public BenefitContractBudget(String processBudgetId) {
		super(processBudgetId);
	}

	/** 设置默认值 **/

	@Transient
	public double getCurrency1Amount() {
		return BenefitBudgetUtil.getDefaultBudgetAmount(super.getBudgetName(), super.getCurrency1Amount());
	}

	@Transient
	public double getCurrency2Amount() {
		return BenefitBudgetUtil.getDefaultBudgetAmount(super.getBudgetName(), super.getCurrency2Amount());
	}

	@Transient
	public double getCurrency3Amount() {
		return BenefitBudgetUtil.getDefaultBudgetAmount(super.getBudgetName(), super.getCurrency3Amount());
	}

	@Transient
	public double getCurrency4Amount() {
		return BenefitBudgetUtil.getDefaultBudgetAmount(super.getBudgetName(), super.getCurrency4Amount());
	}

	@Transient
	public double getCurrency5Amount() {
		return BenefitBudgetUtil.getDefaultBudgetAmount(super.getBudgetName(), super.getCurrency5Amount());
	}

	@Transient
	public double getCurrency6Amount() {
		return BenefitBudgetUtil.getDefaultBudgetAmount(super.getBudgetName(), super.getCurrency6Amount());
	}

	@Transient
	public double getCurrency7Amount() {
		return BenefitBudgetUtil.getDefaultBudgetAmount(super.getBudgetName(), super.getCurrency7Amount());
	}

	@Transient
	public double getCurrency8Amount() {
		return BenefitBudgetUtil.getDefaultBudgetAmount(super.getBudgetName(), super.getCurrency8Amount());
	}

	@Transient
	public double getCurrency9Amount() {
		return BenefitBudgetUtil.getDefaultBudgetAmount(super.getBudgetName(), super.getCurrency9Amount());
	}

	@Transient
	public double getProportion() {
		return BenefitBudgetUtil.getDefaultProportion(super.getBudgetName(), super.getOrderDisplay(), super.getProportion());
	}

	/** 格式化属性 **/

	@Transient
	public String getCurrency1AmountDesc() {
		return BenefitBudgetUtil.getBudgetAmountFormat(this.getCurrency1Amount());
	}

	@Transient
	public String getCurrency2AmountDesc() {
		return BenefitBudgetUtil.getBudgetAmountFormat(this.getCurrency2Amount());
	}

	@Transient
	public String getCurrency3AmountDesc() {
		return BenefitBudgetUtil.getBudgetAmountFormat(this.getCurrency3Amount());
	}

	@Transient
	public String getCurrency4AmountDesc() {
		return BenefitBudgetUtil.getBudgetAmountFormat(this.getCurrency4Amount());
	}

	@Transient
	public String getCurrency5AmountDesc() {
		return BenefitBudgetUtil.getBudgetAmountFormat(this.getCurrency5Amount());
	}

	@Transient
	public String getCurrency6AmountDesc() {
		return BenefitBudgetUtil.getBudgetAmountFormat(this.getCurrency6Amount());
	}

	@Transient
	public String getCurrency7AmountDesc() {
		return BenefitBudgetUtil.getBudgetAmountFormat(this.getCurrency7Amount());
	}

	@Transient
	public String getCurrency8AmountDesc() {
		return BenefitBudgetUtil.getBudgetAmountFormat(this.getCurrency8Amount());
	}

	@Transient
	public String getCurrency9AmountDesc() {
		return BenefitBudgetUtil.getBudgetAmountFormat(this.getCurrency9Amount());
	}

	@Transient
	public String getTotalRmbAmountDesc() {
		return BenefitBudgetUtil.getTotalRmbAmountFormat(super.getBudgetName(), super.getTotalRmbAmount());
	}

	@Transient
	public String getProportionDesc() {
		return BenefitBudgetUtil.getProportionFormat(this.getProportion());
	}

	/** 前台展示属性 **/

	@Transient
	public String getCurrency1AmountDisplay() {
		return BenefitBudgetUtil.replaceToForeground(this.getCurrency1AmountDesc());
	}

	@Transient
	public String getCurrency2AmountDisplay() {
		return BenefitBudgetUtil.replaceToForeground(this.getCurrency2AmountDesc());
	}

	@Transient
	public String getCurrency3AmountDisplay() {
		return BenefitBudgetUtil.replaceToForeground(this.getCurrency3AmountDesc());
	}

	@Transient
	public String getCurrency4AmountDisplay() {
		return BenefitBudgetUtil.replaceToForeground(this.getCurrency4AmountDesc());
	}

	@Transient
	public String getCurrency5AmountDisplay() {
		return BenefitBudgetUtil.replaceToForeground(this.getCurrency5AmountDesc());
	}

	@Transient
	public String getCurrency6AmountDisplay() {
		return BenefitBudgetUtil.replaceToForeground(this.getCurrency6AmountDesc());
	}

	@Transient
	public String getCurrency7AmountDisplay() {
		return BenefitBudgetUtil.replaceToForeground(this.getCurrency7AmountDesc());
	}

	@Transient
	public String getCurrency8AmountDisplay() {
		return BenefitBudgetUtil.replaceToForeground(this.getCurrency8AmountDesc());
	}

	@Transient
	public String getCurrency9AmountDisplay() {
		return BenefitBudgetUtil.replaceToForeground(this.getCurrency9AmountDesc());
	}

	@Transient
	public String getTotalRmbAmountDisplay() {
		return BenefitBudgetUtil.replaceToForeground(this.getTotalRmbAmountDesc());
	}

	@Transient
	public String getProportionDisplay() {
		return BenefitBudgetUtil.replaceToForeground(this.getProportionDesc());
	}

	/**
	 * 树结构相关属性
	 */
	private int level;// level是oracle关键字
	private boolean isLeaf;
	private String parent;
	private boolean expanded = true;// 默认展开

	@Column(name = "RANK", nullable = true, precision = 10)
	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Type(type = "true_false")
	@Column(name = "IS_LEAF", nullable = true, length = 1)
	public boolean isLeaf() {
		return isLeaf;
	}

	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	@Transient
	public boolean getIsLeaf() {
		return isLeaf;
	}

	@Transient
	public String getParent() {
		if (parent == null && this.getParentBudgetId() != null) {
			parent = this.getParentBudgetId();
		}
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	@Transient
	public boolean isExpanded() {
		return expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

}
