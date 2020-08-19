package com.supporter.prj.cneec.tpc.use_seal.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**   
 * @Title: Entity
 * @Description: TPC_USE_SEAL,字段与数据库字段一一对应.
 * @author Yanweichao
 * @date 2017-10-23
 * @version V1.0   
 *
 */
@MappedSuperclass
public class BaseUseSeal implements Serializable {

	private static final long serialVersionUID = 1L;
	private String sealId;
	private String projectId;
	private String projectName;
	private String relyBy;
	private String useSealNo;
	private int contractCopies;
	private String sealDeptName;
	private String sealDeptId;
	private String sealDate;
	private String stampPerson;
	private String stampPersonId;
	private String stampDate;
	private Integer swfStatus;
	private String deptName;
	private String deptId;
	private String createdBy;
	private String createdById;
	private String createdDate;
	private String modifiedBy;
	private String modifiedById;
	private String modifiedDate;

	/**
	 * 无参构造函数.
	 */
	public BaseUseSeal() {
	}

	/**
	 * 构造函数.
	 *
	 * @param sealId
	 */
	public BaseUseSeal(String sealId) {
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

	@Column(name = "RELY_BY", nullable = true, length = 32)
	public String getRelyBy() {
		return relyBy;
	}

	public void setRelyBy(String relyBy) {
		this.relyBy = relyBy;
	}

	/**
	 * GET方法: 取得USE_SEAL_NO.
	 *
	 * @return: String  USE_SEAL_NO
	 */
	@Column(name = "USE_SEAL_NO", nullable = true, length = 64)
	public String getUseSealNo() {
		return useSealNo;
	}

	/**
	 * SET方法: 设置USE_SEAL_NO.
	 *
	 * @param: String  USE_SEAL_NO
	 */
	public void setUseSealNo(String useSealNo) {
		this.useSealNo = useSealNo;
	}

	/**
	 * GET方法: 取得CONTRACT_COPIES.
	 *
	 * @return: int  CONTRACT_COPIES
	 */
	@Column(name = "CONTRACT_COPIES", nullable = true, precision = 10)
	public int getContractCopies() {
		return this.contractCopies;
	}

	/**
	 * SET方法: 设置CONTRACT_COPIES.
	 *
	 * @param: int  CONTRACT_COPIES
	 */
	public void setContractCopies(int contractCopies) {
		this.contractCopies = contractCopies;
	}

	/**
	 * GET方法: 取得SEAL_DEPT_NAME.
	 *
	 * @return: String  SEAL_DEPT_NAME
	 */
	@Column(name = "SEAL_DEPT_NAME", nullable = true, length = 128)
	public String getSealDeptName() {
		return this.sealDeptName;
	}

	/**
	 * SET方法: 设置SEAL_DEPT_NAME.
	 *
	 * @param: String  SEAL_DEPT_NAME
	 */
	public void setSealDeptName(String sealDeptName) {
		this.sealDeptName = sealDeptName;
	}

	/**
	 * GET方法: 取得SEAL_DEPT_ID.
	 *
	 * @return: String  SEAL_DEPT_ID
	 */
	@Column(name = "SEAL_DEPT_ID", nullable = true, length = 32)
	public String getSealDeptId() {
		return this.sealDeptId;
	}

	/**
	 * SET方法: 设置SEAL_DEPT_ID.
	 *
	 * @param: String  SEAL_DEPT_ID
	 */
	public void setSealDeptId(String sealDeptId) {
		this.sealDeptId = sealDeptId;
	}

	/**
	 * GET方法: 取得SEAL_DATE.
	 *
	 * @return: String  SEAL_DATE
	 */
	@Column(name = "SEAL_DATE", nullable = true, length = 27)
	public String getSealDate() {
		return this.sealDate;
	}

	/**
	 * SET方法: 设置SEAL_DATE.
	 *
	 * @param: String  SEAL_DATE
	 */
	public void setSealDate(String sealDate) {
		this.sealDate = sealDate;
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
	 * GET方法: 取得STAMP_DATE.
	 *
	 * @return: String  STAMP_DATE
	 */
	@Column(name = "STAMP_DATE", nullable = true, length = 27)
	public String getStampDate() {
		return this.stampDate;
	}

	/**
	 * SET方法: 设置STAMP_DATE.
	 *
	 * @param: String  STAMP_DATE
	 */
	public void setStampDate(String stampDate) {
		this.stampDate = stampDate;
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

	/**
	 * GET方法: 取得CREATED_DATE.
	 *
	 * @return: String  CREATED_DATE
	 */
	@Column(name = "CREATED_DATE", nullable = true, length = 27)
	public String getCreatedDate() {
		return this.createdDate;
	}

	/**
	 * SET方法: 设置CREATED_DATE.
	 *
	 * @param: String  CREATED_DATE
	 */
	public void setCreatedDate(String createdDate) {
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

	/**
	 * GET方法: 取得MODIFIED_DATE.
	 *
	 * @return: String  MODIFIED_DATE
	 */
	@Column(name = "MODIFIED_DATE", nullable = true, length = 27)
	public String getModifiedDate() {
		return this.modifiedDate;
	}

	/**
	 * SET方法: 设置MODIFIED_DATE.
	 *
	 * @param: String  MODIFIED_DATE
	 */
	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

}
