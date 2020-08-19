package com.supporter.prj.cneec.tpc.benefit_budget_change.entity.base;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import org.hibernate.annotations.GenericGenerator;

@MappedSuperclass
public class BaseBenefitContractCurrencyCh
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String recordId;
  private String recordOldId;
  private String changeId;
  private String currencyId;
  private String currency;
  private double rate;
  private int orderDisplay;

  public BaseBenefitContractCurrencyCh()
  {
  }

  public BaseBenefitContractCurrencyCh(String recordId)
  {
    this.recordId = recordId; }

  public BaseBenefitContractCurrencyCh(String changeId, int orderDisplay) {
    this.changeId = changeId;
    this.orderDisplay = orderDisplay; }

  @Id
  @GeneratedValue(generator="systemUUID")
  @GenericGenerator(name="systemUUID", strategy="uuid")
  @Column(name="record_Id", nullable=false, length=32)
  public String getRecordId() {
    return this.recordId;
  }

  public void setRecordId(String recordId) {
    this.recordId = recordId; }

  @Column(name="record_old_Id", nullable=true, length=32)
  public String getRecordOldId() {
    return this.recordOldId;
  }

  public void setRecordOldId(String recordOldId) {
    this.recordOldId = recordOldId; }

  @Column(name="CHANGE_ID", nullable=true, length=32)
  public String getChangeId() {
    return this.changeId;
  }

  public void setChangeId(String changeId) {
    this.changeId = changeId;
  }

  @Column(name="CURRENCY_ID", nullable=true, length=32)
  public String getCurrencyId() {
    return this.currencyId;
  }

  public void setCurrencyId(String currencyId) {
    this.currencyId = currencyId;
  }

  @Column(name="CURRENCY", nullable=true, length=32)
  public String getCurrency() {
    return this.currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  @Column(name="RATE", nullable=true, precision=10, scale=4)
  public double getRate() {
    return this.rate;
  }

  public void setRate(double rate) {
    this.rate = rate;
  }

  @Column(name="ORDER_DISPLAY", nullable=true, precision=10)
  public int getOrderDisplay() {
    return this.orderDisplay;
  }

  public void setOrderDisplay(int orderDisplay) {
    this.orderDisplay = orderDisplay;
  }
}