package com.supporter.prj.cneec.tpc.benefit_budget.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import com.supporter.prj.cneec.tpc.benefit_budget.entity.base.BaseBenefitBudget;
import com.supporter.prj.cneec.tpc.benefit_budget.util.BenefitAssembleBean;
import com.supporter.prj.cneec.tpc.benefit_budget.util.BenefitBudgetUtil;

/**
 * @Title: BenefitBudget
 * @Description: 贸易项目效益预算-项目预算项表.
 * @author: yanweichao
 * @date: 2018-06-06
 * @version: V1.0
 */
@Entity
@Table(name = "TPC_BENEFIT_BUDGET", schema = "")
public class BenefitBudget extends BaseBenefitBudget implements BenefitAssembleBean, java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// Constructors

	/** default constructor */
	public BenefitBudget() {
		super();
	}

	/** minimal constructor */
	public BenefitBudget(String itemId) {
		super(itemId);
	}

	public BenefitBudget(String projectId, String projectName) {
		super(projectId, projectName);
	}

	@Transient
	public String getId() {
		return super.getItemId();
	}

	@Transient
	public double getSumTotalAmount() {
		return super.getTotalBudgetAmount();
	}

	@Transient
	public double getTotalProportion() {
		return BenefitBudgetUtil.getDefaultProportion(super.getBudgetName(), super.getOrderDisplay(), super.getTotalProportion());
	}

	/** 格式化属性 **/

	@Transient
	public String getTotalBudgetAmountDesc() {
		return BenefitBudgetUtil.getTotalRmbAmountFormat(super.getBudgetName(), super.getTotalBudgetAmount());
	}

	@Transient
	public String getAvailableBudgetAmountDesc() {
		return BenefitBudgetUtil.getBudgetAmountFormat(super.getAvailableBudgetAmount());
	}

	@Transient
	public String getOnWayAmountDesc() {
		return BenefitBudgetUtil.getBudgetAmountFormat(super.getOnWayAmount());
	}

	@Transient
	public String getExecuteAmountDesc() {
		return BenefitBudgetUtil.getBudgetAmountFormat(super.getExecuteAmount());
	}

	@Transient
	public String getTotalProportionDesc() {
		return BenefitBudgetUtil.getProportionFormat(this.getTotalProportion());
	}

	/** 前台展示属性 **/

	@Transient
	public String getTotalBudgetAmountDisplay() {
		return BenefitBudgetUtil.replaceToForeground(this.getTotalBudgetAmountDesc());
	}

	@Transient
	public String getAvailableBudgetAmountDisplay() {
		return BenefitBudgetUtil.replaceToForeground(this.getAvailableBudgetAmountDesc());
	}

	@Transient
	public String getOnWayAmountDisplay() {
		return BenefitBudgetUtil.replaceToForeground(this.getOnWayAmountDesc());
	}

	@Transient
	public String getExecuteAmountDisplay() {
		return BenefitBudgetUtil.replaceToForeground(this.getExecuteAmountDesc());
	}

	@Transient
	public String getTotalProportionDisplay() {
		return BenefitBudgetUtil.replaceToForeground(this.getTotalProportionDesc());
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
