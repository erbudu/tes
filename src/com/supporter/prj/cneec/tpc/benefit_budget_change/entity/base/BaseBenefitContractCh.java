package com.supporter.prj.cneec.tpc.benefit_budget_change.entity.base;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

@MappedSuperclass
public class BaseBenefitContractCh
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String changeId;
  private String processId;
  private String benefitNo;
  private String projectId;
  private String projectName;
  private String contractId;
  private String contractNo;
  private String contractName;
  private int currencyNum;
  private String deptName;
  private String deptId;
  private String createdBy;
  private String createdById;
  private String createdDate;
  private String modifiedBy;
  private String modifiedById;
  private String modifiedDate;
  private Integer swfStatus;
  private String procId;
  private double amountInUsd;//折美元金额
  private double profit;//毛利
  private String telephone;
  private String changeTitle;
  
  public BaseBenefitContractCh()
  {
  }

  public BaseBenefitContractCh(String changeId)
  {
    this.changeId = changeId;
  }

  @Id
  @GeneratedValue(generator = "systemUUID")
  @GenericGenerator(name = "systemUUID", strategy = "uuid")
  @Column(name="CHANGE_ID", nullable=false, length=32)
  public String getChangeId() {
    return this.changeId;
  }

  public void setChangeId(String changeId) {
    this.changeId = changeId;
  }

  @Column(name="PROCESS_ID", nullable=true, length=32)
  public String getProcessId() {
    return this.processId;
  }

  public void setProcessId(String processId) {
    this.processId = processId;
  }

  @Column(name="BENEFIT_NO", nullable=true, length=32)
  public String getBenefitNo() {
    return this.benefitNo;
  }

  public void setBenefitNo(String benefitNo) {
    this.benefitNo = benefitNo;
  }
  @Column(name="TELEPHONE", nullable=true, length=32)
  public String getTelephone() {
	return telephone;
}

public void setTelephone(String telephone) {
	this.telephone = telephone;
}
@Column(name="CHANGETITLE", nullable=true, length=1024)
  public String getChangeTitle() {
	return changeTitle;
}

public void setChangeTitle(String changeTitle) {
	this.changeTitle = changeTitle;
}

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

  @Column(name="CONTRACT_ID", nullable=true, length=512)
  public String getContractId() {
    return this.contractId;
  }

  public void setContractId(String contractId) {
    this.contractId = contractId;
  }

  @Column(name="CONTRACT_NO", nullable=true, length=1024)
  public String getContractNo() {
    return this.contractNo;
  }

  public void setContractNo(String contractNo) {
    this.contractNo = contractNo;
  }

  @Column(name="CONTRACT_NAME", nullable=true, length=1024)
  public String getContractName() {
    return this.contractName;
  }

  public void setContractName(String contractName) {
    this.contractName = contractName;
  }

  @Column(name="CURRENCY_NUM", nullable=true, precision=10)
  public int getCurrencyNum() {
    return this.currencyNum;
  }

  public void setCurrencyNum(int currencyNum) {
    this.currencyNum = currencyNum;
  }

  @Column(name="DEPT_NAME", nullable=true, length=128)
  public String getDeptName() {
    return this.deptName;
  }

  public void setDeptName(String deptName) {
    this.deptName = deptName;
  }

  @Column(name="DEPT_ID", nullable=true, length=32)
  public String getDeptId() {
    return this.deptId;
  }

  public void setDeptId(String deptId) {
    this.deptId = deptId;
  }

  @Column(name="CREATED_BY", nullable=true, length=32)
  public String getCreatedBy() {
    return this.createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  @Column(name="CREATED_BY_ID", nullable=true, length=32)
  public String getCreatedById() {
    return this.createdById;
  }

  public void setCreatedById(String createdById) {
    this.createdById = createdById;
  }

  @Column(name="CREATED_DATE", nullable=true, length=27)
  public String getCreatedDate() {
    return this.createdDate;
  }

  public void setCreatedDate(String createdDate) {
    this.createdDate = createdDate;
  }

  @Column(name="MODIFIED_BY", nullable=true, length=32)
  public String getModifiedBy() {
    return this.modifiedBy;
  }

  public void setModifiedBy(String modifiedBy) {
    this.modifiedBy = modifiedBy;
  }

  @Column(name="MODIFIED_BY_ID", nullable=true, length=32)
  public String getModifiedById() {
    return this.modifiedById;
  }

  public void setModifiedById(String modifiedById) {
    this.modifiedById = modifiedById;
  }

  @Column(name="MODIFIED_DATE", nullable=true, length=27)
  public String getModifiedDate() {
    return this.modifiedDate;
  }

  public void setModifiedDate(String modifiedDate) {
    this.modifiedDate = modifiedDate; }

  @Column(name="SWF_STATUS", nullable=true, precision=10)
  public Integer getSwfStatus() {
    return this.swfStatus;
  }

  public void setSwfStatus(Integer swfStatus) {
    this.swfStatus = swfStatus; }

  @Column(name="PROC_ID", length=32)
  public String getProcId() {
    return this.procId;
  }

  public void setProcId(String procId) {
    this.procId = procId;
  }
  
  @Column(name="AMOUNT_IN_USD", nullable=true, precision=10, scale=4)
  public double getAmountInUsd() {
    return this.amountInUsd;
  }

  public void setAmountInUsd(double amountInUsd) {
    this.amountInUsd = amountInUsd;
  }
  @Column(name="PROFIT", nullable=true, precision=10, scale=4)
  public double getProfit() {
    return this.profit;
  }

  public void setProfit(double profit) {
    this.profit = profit;
  }
}