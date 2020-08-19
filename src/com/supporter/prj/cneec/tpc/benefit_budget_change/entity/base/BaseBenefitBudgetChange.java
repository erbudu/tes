package com.supporter.prj.cneec.tpc.benefit_budget_change.entity.base;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import org.hibernate.annotations.GenericGenerator;

@MappedSuperclass
public class BaseBenefitBudgetChange
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String itemId;
  private String itemOldId;
  private String projectId;
  private String projectName;
  private String serialNumber;
  private String budgetId;
  private String budgetName;
  private String parentBudgetId;
  private double totalBudgetAmount;
  private double availableBudgetAmount;
  private double onWayAmount;
  private double executeAmount;
  private double totalProportion;
  private int orderDisplay;

  public BaseBenefitBudgetChange()
  {
  }

  public BaseBenefitBudgetChange(String itemId)
  {
    this.itemId = itemId;
  }

  public BaseBenefitBudgetChange(String projectId, String projectName)
  {
    this.projectId = projectId;
    this.projectName = projectName;
  }

  @Id
  @GeneratedValue(generator="systemUUID")
  @GenericGenerator(name="systemUUID", strategy="uuid")
  @Column(name="ITEM_ID", nullable=false, length=32)
  public String getItemId() {
    return this.itemId;
  }

  public void setItemId(String itemId) {
    this.itemId = itemId; }

  @Column(name="ITEM_OLD_ID", nullable=false, length=32)
  public String getItemOldId() {
    return this.itemOldId;
  }

  public void setItemOldId(String itemOldId) {
    this.itemOldId = itemOldId; }

  @Column(name="PROJECT_ID", nullable=true, length=32)
  public String getProjectId() {
    return this.projectId;
  }

  public void setProjectId(String projectId) {
    this.projectId = projectId;
  }

  @Column(name="PROJECT_NAME", nullable=true, length=128)
  public String getProjectName() {
    return this.projectName;
  }

  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }

  @Column(name="SERIAL_NUMBER", nullable=true, length=32)
  public String getSerialNumber() {
    return this.serialNumber;
  }

  public void setSerialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  @Column(name="BUDGET_ID", nullable=true, length=64)
  public String getBudgetId() {
    return this.budgetId;
  }

  public void setBudgetId(String budgetId) {
    this.budgetId = budgetId;
  }

  @Column(name="BUDGET_NAME", nullable=true, length=64)
  public String getBudgetName() {
    return this.budgetName;
  }

  public void setBudgetName(String budgetName) {
    this.budgetName = budgetName;
  }

  @Column(name="PARENT_BUDGET_ID", nullable=true, length=64)
  public String getParentBudgetId() {
    return this.parentBudgetId;
  }

  public void setParentBudgetId(String parentBudgetId) {
    this.parentBudgetId = parentBudgetId;
  }

  @Column(name="TOTAL_BUDGET_AMOUNT", nullable=true, precision=10, scale=3)
  public double getTotalBudgetAmount()
  {
    return this.totalBudgetAmount;
  }

  public void setTotalBudgetAmount(double totalBudgetAmount)
  {
    this.totalBudgetAmount = totalBudgetAmount;
  }

  @Column(name="AVAILABLE_BUDGET_AMOUNT", nullable=true, precision=10, scale=3)
  public double getAvailableBudgetAmount()
  {
    return this.availableBudgetAmount;
  }

  public void setAvailableBudgetAmount(double availableBudgetAmount)
  {
    this.availableBudgetAmount = availableBudgetAmount;
  }

  @Column(name="ON_WAY_AMOUNT", nullable=true, precision=10, scale=3)
  public double getOnWayAmount()
  {
    return this.onWayAmount;
  }

  public void setOnWayAmount(double onWayAmount)
  {
    this.onWayAmount = onWayAmount;
  }

  @Column(name="EXECUTE_AMOUNT", nullable=true, precision=10, scale=3)
  public double getExecuteAmount()
  {
    return this.executeAmount;
  }

  public void setExecuteAmount(double executeAmount)
  {
    this.executeAmount = executeAmount;
  }

  @Column(name="TOTAL_PROPORTION", nullable=true, precision=10, scale=4)
  public double getTotalProportion() {
    return this.totalProportion;
  }

  public void setTotalProportion(double totalProportion) {
    this.totalProportion = totalProportion;
  }

  @Column(name="ORDER_DISPLAY", nullable=true, precision=10)
  public int getOrderDisplay() {
    return this.orderDisplay;
  }

  public void setOrderDisplay(int orderDisplay) {
    this.orderDisplay = orderDisplay;
  }
}