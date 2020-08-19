package com.supporter.prj.cneec.tpc.benefit_note.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import com.supporter.prj.cneec.tpc.benefit_budget.util.BenefitAssembleBean;
import com.supporter.prj.cneec.tpc.benefit_budget.util.BenefitBudgetUtil;
import com.supporter.prj.cneec.tpc.benefit_note.entity.base.BaseVBenefitNoteAssemble;

/**
 * @Title: VBenefitNoteAssemble
 * @Description: 效益测算表确定版预算项.
 * @author Yanweichao
 * @date 2018-05-29
 * @version V1.0
 */
@Entity
@Table(name = "TPC_V_BENEFIT_NOTE_ASSEMBLE", schema = "")
public class VBenefitNoteAssemble extends BaseVBenefitNoteAssemble implements BenefitAssembleBean, Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public VBenefitNoteAssemble() {
		super();
	}

	/** 格式化属性 **/

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
	public String getSumTotalAmountDisplay() {
		return BenefitBudgetUtil.replaceToForeground(this.getSumTotalAmountDesc());
	}

	@Transient
	public String getSumTotalProportionDisplay() {
		return BenefitBudgetUtil.replaceToForeground(this.getSumTotalProportionDesc());
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
