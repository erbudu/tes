package com.supporter.prj.cneec.tpc.benefit_note.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import com.supporter.prj.cneec.tpc.benefit_budget.constant.BenefitBudgetItemConstant;
import com.supporter.prj.cneec.tpc.benefit_budget.util.BenefitAssembleBean;
import com.supporter.prj.cneec.tpc.benefit_budget.util.BenefitBudgetUtil;
import com.supporter.prj.cneec.tpc.benefit_note.entity.base.BaseBenefitNoteBudget;

/**
 * @Title: BenefitNoteBudget
 * @Description: 效益测算表预算项.
 * @author Yanweichao
 * @date 2018-06-01
 * @version V1.0
 */
@Entity
@Table(name = "TPC_BENEFIT_NOTE_BUDGET", schema = "")
public class BenefitNoteBudget extends BaseBenefitNoteBudget implements BenefitAssembleBean, Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public BenefitNoteBudget() {
		super();
	}

	/**
	 * 构造函数.
	 * 
	 * @param noteBudgetId
	 */
	public BenefitNoteBudget(String noteBudgetId) {
		super(noteBudgetId);
	}

	/**
	 * 多参数构造函数.
	 * 
	 * @param noteId
	 * @param budgetId
	 * @param budgetName
	 * @param parentBudgetId
	 */
	public BenefitNoteBudget(String noteId, String budgetId, String budgetName, String parentBudgetId) {
		super(noteId, budgetId, budgetName, parentBudgetId);
	}
	
	/**
	 * 多参数构造函数.
	 *
	 * @param noteId
	 * @param serialNumber
	 * @param budgetId
	 * @param budgetName
	 * @param parentBudgetId
	 * @param level
	 * @param orderDisplay
	 */
	public BenefitNoteBudget(String noteId, String serialNumber, String budgetId, String budgetName, String parentBudgetId, int level, int orderDisplay) {
		super(noteId, serialNumber, budgetId, budgetName, parentBudgetId, orderDisplay);
		this.level = level;
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

	@Transient
	public double getSumTotalProportion() {
		return BenefitBudgetUtil.getDefaultProportion(super.getBudgetName(), super.getOrderDisplay(), super.getSumTotalProportion());
	}

	/** 设置显示内容 **/

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

	@Transient
	public String getSumTotalAmountDesc() {
		return BenefitBudgetUtil.getTotalRmbAmountFormat(super.getBudgetName(), super.getSumTotalAmount());
	}

	@Transient
	public String getSumTotalProportionDesc() {
		return BenefitBudgetUtil.getProportionFormat(this.getSumTotalProportion());
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

	@Transient
	public String getSumTotalAmountDisplay() {
		return BenefitBudgetUtil.replaceToForeground(this.getSumTotalAmountDesc());
	}

	@Transient
	public String getSumTotalProportionDisplay() {
		return BenefitBudgetUtil.replaceToForeground(this.getSumTotalProportionDesc());
	}

	/**
	 * 所有原币金额和
	 * @return
	 */
	@Transient
	public double getAllBudgetAmount() {
		double totalAmount = 0;
		// 销售合同总预算计算原币和
		if (BenefitBudgetItemConstant.SUMMARY_TRADE_TOTAL.equals(super.getBudgetId())) {
			totalAmount = BigDecimal.valueOf(this.getCurrency1Amount())
					.add(BigDecimal.valueOf(this.getCurrency2Amount()))
					.add(BigDecimal.valueOf(this.getCurrency3Amount()))
					.add(BigDecimal.valueOf(this.getCurrency4Amount()))
					.add(BigDecimal.valueOf(this.getCurrency5Amount()))
					.add(BigDecimal.valueOf(this.getCurrency6Amount()))
					.add(BigDecimal.valueOf(this.getCurrency7Amount()))
					.add(BigDecimal.valueOf(this.getCurrency8Amount()))
					.add(BigDecimal.valueOf(this.getCurrency9Amount())).doubleValue();
			return totalAmount;
		}
		return totalAmount;
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
		return this.isLeaf;
	}

	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	@Transient
	public boolean getIsLeaf() {
		return this.isLeaf;
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
		return this.expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

}
