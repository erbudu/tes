package com.supporter.prj.cneec.tpc.order_change.entity.base;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

/**
 * @Title: Entity
 * @Description: TPC_CONTRACT_CHANGE,字段与数据库字段一一对应.
 * @version V1.0
 */
@MappedSuperclass
public class BaseOrderSeal implements Serializable {

	private static final long serialVersionUID = 1L;
	private String sealId;
	private String changeId;
	private String projectId;
	private String projectName;
	private String contractId;
	private String contractNo;
	private String contractName;
	private int contractNumber;    //合同份数
	private String businessNo;  //协议编号
	private String stampPerson;
	private String stampPersonId;
	private Date stampDate;//盖章日期
	private Date sealDate;//用印申请日期
	private String remark;
	private Integer swfStatus;
	private String deptName;
	private String deptId;
	private String createdBy;
	private String createdById;
	private Date createdDate;//编制日期
	private String modifiedBy;
	private String modifiedById;
	private Date modifiedDate;
	private String procId;

	/**
	 * 无参构造函数.
	 */
	public BaseOrderSeal() {
	}

	/**
	 * 构造函数.
	 *
	 * @param sealId
	 */
	public BaseOrderSeal(String sealId) {
		this.sealId = sealId;
	}

	/**
	 * GET方法: 取得SEAL_ID.
	 *
	 * @return: String  SEAL_ID
	 */
	@Id
	@Column(name = "SEAL_ID", nullable = false, length = 32)
	public String getSealId() {
		return this.sealId;
	}

	/**
	 * SET方法: 设置SEAL_ID.
	 *
	 * @param: String  SEAL_ID
	 */
	public void setSealId(String sealId) {
		this.sealId = sealId;
	}
	
	@Column(name = "CHANGE_ID", nullable = true, length = 32)
	public String getChangeId() {
		return changeId;
	}

	public void setChangeId(String changeId) {
		this.changeId = changeId;
	}

	/**
	 * GET方法: 取得PROJECT_ID.
	 *
	 * @return: String  PROJECT_ID
	 */
	@Column(name = "PROJECT_ID", nullable = true, length = 32)
	public String getProjectId() {
		return this.projectId;
	}

	/**
	 * SET方法: 设置PROJECT_ID.
	 *
	 * @param: String  PROJECT_ID
	 */
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	/**
	 * GET方法: 取得PROJECT_NAME.
	 *
	 * @return: String  PROJECT_NAME
	 */
	@Column(name = "PROJECT_NAME", nullable = true, length = 128)
	public String getProjectName() {
		return this.projectName;
	}

	/**
	 * SET方法: 设置PROJECT_NAME.
	 *
	 * @param: String  PROJECT_NAME
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * GET方法: 取得CONTRACT_ID.
	 *
	 * @return: String  CONTRACT_ID
	 */
	@Column(name = "CONTRACT_ID", nullable = true, length = 32)
	public String getContractId() {
		return this.contractId;
	}

	/**
	 * SET方法: 设置CONTRACT_ID.
	 *
	 * @param: String  CONTRACT_ID
	 */
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	/**
	 * GET方法: 取得CONTRACT_NO.
	 *
	 * @return: String  CONTRACT_NO
	 */
	@Column(name = "CONTRACT_NO", nullable = true, length = 64)
	public String getContractNo() {
		return this.contractNo;
	}

	/**
	 * SET方法: 设置CONTRACT_NO.
	 *
	 * @param: String  CONTRACT_NO
	 */
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	/**
	 * GET方法: 取得CONTRACT_NAME.
	 *
	 * @return: String  CONTRACT_NAME
	 */
	@Column(name = "CONTRACT_NAME", nullable = true, length = 128)
	public String getContractName() {
		return this.contractName;
	}

	/**
	 * SET方法: 设置CONTRACT_NAME.
	 *
	 * @param: String  CONTRACT_NAME
	 */
	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	/**
	 * GET方法: 取得CONTRACT_NUMBER.
	 *
	 * @return: int  CONTRACT_NUMBER
	 */
	@Column(name = "CONTRACT_NUMBER", nullable = true, precision = 10)
	public int getContractNumber() {
		return this.contractNumber;
	}

	/**
	 * SET方法: 设置CONTRACT_NUMBER.
	 *
	 * @param: int  CONTRACT_NUMBER
	 */
	public void setContractNumber(int contractNumber) {
		this.contractNumber = contractNumber;
	}

	/**
	 * GET方法: 取得BUSINESS_NO.
	 *
	 * @return: String  BUSINESS_NO
	 */
	@Column(name = "BUSINESS_NO", nullable = true, length = 32)
	public String getBusinessNo() {
		return this.businessNo;
	}

	/**
	 * SET方法: 设置BUSINESS_NO.
	 *
	 * @param: String  BUSINESS_NO
	 */
	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}
	/**
	 * GET方法: 取得STAMP_PERSON.
	 *
	 * @return: String  STAMP_PERSON
	 */
	@Column(name = "STAMP_PERSON", nullable = true, length = 32)
	public String getStampPerson() {
		return this.stampPerson;
	}

	/**
	 * SET方法: 设置STAMP_PERSON.
	 *
	 * @param: String  STAMP_PERSON
	 */
	public void setStampPerson(String stampPerson) {
		this.stampPerson = stampPerson;
	}

	/**
	 * GET方法: 取得STAMP_PERSON_ID.
	 *
	 * @return: String  STAMP_PERSON_ID
	 */
	@Column(name = "STAMP_PERSON_ID", nullable = true, length = 32)
	public String getStampPersonId() {
		return this.stampPersonId;
	}

	/**
	 * SET方法: 设置STAMP_PERSON_ID.
	 *
	 * @param: String  STAMP_PERSON_ID
	 */
	public void setStampPersonId(String stampPersonId) {
		this.stampPersonId = stampPersonId;
	}

	/**
	 * GET方法: 取得SWF_STATUS.
	 *
	 * @return: Integer  SWF_STATUS
	 */
	@Column(name = "SWF_STATUS", nullable = true, precision = 10)
	public Integer getSwfStatus() {
		return this.swfStatus;
	}

	/**
	 * SET方法: 设置SWF_STATUS.
	 *
	 * @param: Integer  SWF_STATUS
	 */
	public void setSwfStatus(Integer swfStatus) {
		this.swfStatus = swfStatus;
	}
	/**
	 * GET方法: 取得DEPT_NAME.
	 *
	 * @return: String  DEPT_NAME
	 */
	@Column(name = "DEPT_NAME", nullable = true, length = 128)
	public String getDeptName() {
		return this.deptName;
	}

	/**
	 * SET方法: 设置DEPT_NAME.
	 *
	 * @param: String  DEPT_NAME
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	/**
	 * GET方法: 取得DEPT_ID.
	 *
	 * @return: String  DEPT_ID
	 */
	@Column(name = "DEPT_ID", nullable = true, length = 32)
	public String getDeptId() {
		return this.deptId;
	}

	/**
	 * SET方法: 设置DEPT_ID.
	 *
	 * @param: String  DEPT_ID
	 */
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	/**
	 * GET方法: 取得CREATED_BY.
	 *
	 * @return: String  CREATED_BY
	 */
	@Column(name = "CREATED_BY", nullable = true, length = 32)
	public String getCreatedBy() {
		return this.createdBy;
	}

	/**
	 * SET方法: 设置CREATED_BY.
	 *
	 * @param: String  CREATED_BY
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * GET方法: 取得CREATED_BY_ID.
	 *
	 * @return: String  CREATED_BY_ID
	 */
	@Column(name = "CREATED_BY_ID", nullable = true, length = 32)
	public String getCreatedById() {
		return this.createdById;
	}

	/**
	 * SET方法: 设置CREATED_BY_ID.
	 *
	 * @param: String  CREATED_BY_ID
	 */
	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE", nullable = true, length = 32)
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * GET方法: 取得MODIFIED_BY.
	 *
	 * @return: String  MODIFIED_BY
	 */
	@Column(name = "MODIFIED_BY", nullable = true, length = 32)
	public String getModifiedBy() {
		return this.modifiedBy;
	}

	/**
	 * SET方法: 设置MODIFIED_BY.
	 *
	 * @param: String  MODIFIED_BY
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	/**
	 * GET方法: 取得MODIFIED_BY_ID.
	 *
	 * @return: String  MODIFIED_BY_ID
	 */
	@Column(name = "MODIFIED_BY_ID", nullable = true, length = 32)
	public String getModifiedById() {
		return this.modifiedById;
	}

	/**
	 * SET方法: 设置MODIFIED_BY_ID.
	 *
	 * @param: String  MODIFIED_BY_ID
	 */
	public void setModifiedById(String modifiedById) {
		this.modifiedById = modifiedById;
	}

	@Column(name = "MODIFIED_DATE", nullable = true, length = 32)
	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	@Column(name = "STAM_DATE", nullable = true, length = 32)
	public Date getStampDate() {
		return stampDate;
	}

	public void setStampDate(Date stampDate) {
		this.stampDate = stampDate;
	}
	@Column(name = "SEAL_DATE", nullable = true, length = 32)
	public Date getSealDate() {
		return sealDate;
	}

	public void setSealDate(Date sealDate) {
		this.sealDate = sealDate;
	}
	@Column(name = "REMARK", nullable = true, length = 128)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name = "PROC_ID", length = 32)
	public String getProcId() {
		return procId;
	}

	public void setProcId(String procId) {
		this.procId = procId;
	}

}
