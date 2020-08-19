package com.supporter.prj.cneec.tpc.benefit_budget_change.entity;

import com.supporter.prj.cneec.tpc.benefit_budget.util.BenefitBudgetUtil;
import com.supporter.prj.cneec.tpc.benefit_budget_change.entity.base.BaseBenefitContractBudgetCh;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.Type;

@Entity
@Table(name="TPC_BENEFIT_CONTRACT_BUDGET_CH", schema="")
public class BenefitContractBudgetCh extends BaseBenefitContractBudgetCh
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private int level;
  private boolean isLeaf;
  private String parent;
  private boolean expanded = true;

  public BenefitContractBudgetCh()
  {
  }

  public BenefitContractBudgetCh(String processBudgetId)
  {
    super(processBudgetId);
  }

  @Transient
  public double getCurrency1Amount()
  {
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
  public String getCurrency1AmountDesc()
  {
    return BenefitBudgetUtil.getBudgetAmountFormat(getCurrency1Amount());
  }

  @Transient
  public String getCurrency2AmountDesc() {
    return BenefitBudgetUtil.getBudgetAmountFormat(getCurrency2Amount());
  }

  @Transient
  public String getCurrency3AmountDesc() {
    return BenefitBudgetUtil.getBudgetAmountFormat(getCurrency3Amount());
  }

  @Transient
  public String getCurrency4AmountDesc() {
    return BenefitBudgetUtil.getBudgetAmountFormat(getCurrency4Amount());
  }

  @Transient
  public String getCurrency5AmountDesc() {
    return BenefitBudgetUtil.getBudgetAmountFormat(getCurrency5Amount());
  }

  @Transient
  public String getCurrency6AmountDesc() {
    return BenefitBudgetUtil.getBudgetAmountFormat(getCurrency6Amount());
  }

  @Transient
  public String getCurrency7AmountDesc() {
    return BenefitBudgetUtil.getBudgetAmountFormat(getCurrency7Amount());
  }

  @Transient
  public String getCurrency8AmountDesc() {
    return BenefitBudgetUtil.getBudgetAmountFormat(getCurrency8Amount());
  }

  @Transient
  public String getCurrency9AmountDesc() {
    return BenefitBudgetUtil.getBudgetAmountFormat(getCurrency9Amount());
  }

  @Transient
  public String getTotalRmbAmountDesc() {
    return BenefitBudgetUtil.getTotalRmbAmountFormat(super.getBudgetName(), super.getTotalRmbAmount());
  }

  @Transient
  public String getProportionDesc() {
    return BenefitBudgetUtil.getProportionFormat(getProportion());
  }

  @Transient
  public String getCurrency1AmountDisplay()
  {
    return BenefitBudgetUtil.replaceToForeground(getCurrency1AmountDesc());
  }

  @Transient
  public String getCurrency2AmountDisplay() {
    return BenefitBudgetUtil.replaceToForeground(getCurrency2AmountDesc());
  }

  @Transient
  public String getCurrency3AmountDisplay() {
    return BenefitBudgetUtil.replaceToForeground(getCurrency3AmountDesc());
  }

  @Transient
  public String getCurrency4AmountDisplay() {
    return BenefitBudgetUtil.replaceToForeground(getCurrency4AmountDesc());
  }

  @Transient
  public String getCurrency5AmountDisplay() {
    return BenefitBudgetUtil.replaceToForeground(getCurrency5AmountDesc());
  }

  @Transient
  public String getCurrency6AmountDisplay() {
    return BenefitBudgetUtil.replaceToForeground(getCurrency6AmountDesc());
  }

  @Transient
  public String getCurrency7AmountDisplay() {
    return BenefitBudgetUtil.replaceToForeground(getCurrency7AmountDesc());
  }

  @Transient
  public String getCurrency8AmountDisplay() {
    return BenefitBudgetUtil.replaceToForeground(getCurrency8AmountDesc());
  }

  @Transient
  public String getCurrency9AmountDisplay() {
    return BenefitBudgetUtil.replaceToForeground(getCurrency9AmountDesc());
  }

  @Transient
  public String getTotalRmbAmountDisplay() {
    return BenefitBudgetUtil.replaceToForeground(getTotalRmbAmountDesc());
  }

  @Transient
  public String getProportionDisplay() {
    return BenefitBudgetUtil.replaceToForeground(getProportionDesc());
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