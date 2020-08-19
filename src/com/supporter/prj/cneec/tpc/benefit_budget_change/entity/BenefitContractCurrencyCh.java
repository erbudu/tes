package com.supporter.prj.cneec.tpc.benefit_budget_change.entity;

import com.supporter.prj.cneec.tpc.benefit_budget_change.entity.base.BaseBenefitContractCurrencyCh;
import com.supporter.util.CommonUtil;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="TPC_BENEFIT_CONTRACT_CUR_CH", schema="")
public class BenefitContractCurrencyCh extends BaseBenefitContractCurrencyCh
  implements Serializable
{
  private static final long serialVersionUID = 1L;

  public BenefitContractCurrencyCh()
  {
  }

  public BenefitContractCurrencyCh(String recordId)
  {
    super(recordId); }

  public BenefitContractCurrencyCh(String changeId, int orderDisplay) {
    super(changeId, orderDisplay); }

  @Transient
  public String getCurrencyDisplay() {
    return CommonUtil.trim(getCurrency()) + "[" + CommonUtil.format(getRate(), "#,###.0000") + "]";
  }
}