package com.supporter.prj.cneec.tpc.benefit_budget_change.entity;

import com.supporter.prj.cneec.tpc.benefit_budget.util.BenefitAssembleBean;
import com.supporter.prj.cneec.tpc.benefit_budget.util.BenefitBudgetUtil;
import com.supporter.prj.cneec.tpc.benefit_budget_change.entity.base.BaseBenefitBudgetChange;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.Type;

@Entity
@Table(name="TPC_BENEFIT_BUDGET_CHANGE", schema="")
public class BenefitBudgetChange extends BaseBenefitBudgetChange
  implements BenefitAssembleBean, Serializable
{
  private static final long serialVersionUID = 1L;
  private int level;
  private boolean isLeaf;
  private String parent;
  private boolean expanded = true;

  public BenefitBudgetChange()
  {
  }

  public BenefitBudgetChange(String itemId)
  {
    super(itemId);
  }

  public BenefitBudgetChange(String projectId, String projectName) {
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

  @Transient
  public String getTotalBudgetAmountDesc()
  {
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
    return BenefitBudgetUtil.getProportionFormat(getTotalProportion());
  }

  @Transient
  public String getTotalBudgetAmountDisplay()
  {
    return BenefitBudgetUtil.replaceToForeground(getTotalBudgetAmountDesc());
  }

  @Transient
  public String getAvailableBudgetAmountDisplay() {
    return BenefitBudgetUtil.replaceToForeground(getAvailableBudgetAmountDesc());
  }

  @Transient
  public String getOnWayAmountDisplay() {
    return BenefitBudgetUtil.replaceToForeground(getOnWayAmountDesc());
  }

  @Transient
  public String getExecuteAmountDisplay() {
    return BenefitBudgetUtil.replaceToForeground(getExecuteAmountDesc());
  }

  @Transient
  public String getTotalProportionDisplay() {
    return BenefitBudgetUtil.replaceToForeground(getTotalProportionDesc());
  }

  @Column(name="RANK", nullable=true, precision=10)
  public int getLevel()
  {
    return this.level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  @Type(type="true_false")
  @Column(name="IS_LEAF", nullable=true, length=1)
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
    if ((this.parent == null) && (getParentBudgetId() != null)) {
      this.parent = getParentBudgetId();
    }
    return this.parent;
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