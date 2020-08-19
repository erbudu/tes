package com.supporter.prj.ppm.contract.effect.seal_bfd.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @Title: Entity
 * @Description: 主合同出版,字段与数据库字段一一对应.
 * @author: YAN
 * @date: 2019-09-10
 * @version: V1.0
 */
@MappedSuperclass
public class BaseContractEffectSeal implements Serializable {

	private static final long serialVersionUID = 1L;
	private String effectSealId;
	private String prjId;
	private java.util.Date startDate;
	private java.util.Date endDate;
	private String linkManId;
	private String linkManName;
	private String linkTel;
	private String linkPhone;
	private String linkTax;
	private String contractEffectId;
	private String createdBy;
	private String createdById;
	private java.util.Date createdDate;
	private String deptId;
	private String deptName;
	private String modifiedById;
	private String modifiedBy;
	private java.util.Date modifiedDate;
	private int status;
	/**
	 * 无参构造函数.
	 */
	public BaseContractEffectSeal() {
	}

	/**
	 * 构造函数.
	 *
	 * @param effectSealId
	 */
	public BaseContractEffectSeal(String effectSealId) {
		this.effectSealId = effectSealId;
	}

	/**
	 * 方法: 取得EFFECT_SEAL_ID.
	 * @return: java.lang.String EFFECT_SEAL_ID
	 */
	@Id
	//@GeneratedValue(generator = "systemUUID")
	//@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@Column(name = "EFFECT_SEAL_ID", nullable = false, length = 32)
	public String getEffectSealId() {
		return this.effectSealId;
	}

	/**
	 * 方法: 设置EFFECT_SEAL_ID.
	 * @param: java.lang.String EFFECT_SEAL_ID
	 */
	public void setEffectSealId(String effectSealId) {
		this.effectSealId = effectSealId;
	}

	/**
	 * 方法: 取得项目id.
	 * @return: java.lang.String 项目id
	 */
	@Column(name = "PRJ_ID", nullable = true, length = 32)
	public String getPrjId() {
		return this.prjId;
	}

	/**
	 * 方法: 设置项目id.
	 * @param: java.lang.String 项目id
	 */
	public void setPrjId(String prjId) {
		this.prjId = prjId;
	}

	/**
	 * 方法: 取得出版开始日期.
	 * @return: java.util.Date 出版开始日期
	 */
	@Column(name = "START_DATE", nullable = true)
	public java.util.Date getStartDate() {
		return this.startDate;
	}

	/**
	 * 方法: 设置出版开始日期.
	 * @param: java.util.Date 出版开始日期
	 */
	public void setStartDate(java.util.Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * 方法: 取得赤坂完成日志.
	 * @return: java.util.Date 赤坂完成日志
	 */
	@Column(name = "END_DATE", nullable = true)
	public java.util.Date getEndDate() {
		return this.endDate;
	}

	/**
	 * 方法: 设置赤坂完成日志.
	 * @param: java.util.Date 赤坂完成日志
	 */
	public void setEndDate(java.util.Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * 方法: 取得联系人.
	 * @return: java.lang.String 联系人
	 */
	@Column(name = "LINK_MAN_ID", nullable = true, length = 32)
	public String getLinkManId() {
		return this.linkManId;
	}

	/**
	 * 方法: 设置联系人.
	 * @param: java.lang.String 联系人
	 */
	public void setLinkManId(String linkManId) {
		this.linkManId = linkManId;
	}

	/**
	 * 方法: 取得LINK_MAN_NAME.
	 * @return: java.lang.String LINK_MAN_NAME
	 */
	@Column(name = "LINK_MAN_NAME", nullable = true, length = 64)
	public String getLinkManName() {
		return this.linkManName;
	}

	/**
	 * 方法: 设置LINK_MAN_NAME.
	 * @param: java.lang.String LINK_MAN_NAME
	 */
	public void setLinkManName(String linkManName) {
		this.linkManName = linkManName;
	}

	/**
	 * 方法: 取得联系电话.
	 * @return: java.lang.String 联系电话
	 */
	@Column(name = "LINK_TEL", nullable = true, length = 64)
	public String getLinkTel() {
		return this.linkTel;
	}

	/**
	 * 方法: 设置联系电话.
	 * @param: java.lang.String 联系电话
	 */
	public void setLinkTel(String linkTel) {
		this.linkTel = linkTel;
	}

	/**
	 * 方法: 取得手机.
	 * @return: java.lang.String 手机
	 */
	@Column(name = "LINK_PHONE", nullable = true, length = 64)
	public String getLinkPhone() {
		return this.linkPhone;
	}

	/**
	 * 方法: 设置手机.
	 * @param: java.lang.String 手机
	 */
	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}

	/**
	 * 方法: 取得传真.
	 * @return: java.lang.String 传真
	 */
	@Column(name = "LINK_TAX", nullable = true, length = 64)
	public String getLinkTax() {
		return this.linkTax;
	}

	/**
	 * 方法: 设置传真.
	 * @param: java.lang.String 传真
	 */
	public void setLinkTax(String linkTax) {
		this.linkTax = linkTax;
	}

	/**
	 * 方法: 取得报审id.
	 * @return: java.lang.String 报审id
	 */
	@Column(name = "CONTRACT_EFFECT_ID", nullable = true, length = 32)
	public String getContractEffectId() {
		return this.contractEffectId;
	}

	/**
	 * 方法: 设置报审id.
	 * @param: java.lang.String 报审id
	 */
	public void setContractEffectId(String contractEffectId) {
		this.contractEffectId = contractEffectId;
	}
	/**
	 * 方法: 取得CREATED_BY.
	 * @return: java.lang.String CREATED_BY
	 */
	@Column(name = "CREATED_BY", nullable = true, length = 64)
	public String getCreatedBy() {
		return this.createdBy;
	}

	/**
	 * 方法: 设置CREATED_BY.
	 * @param: java.lang.String CREATED_BY
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * 方法: 取得CREATED_BY_ID.
	 * @return: java.lang.String CREATED_BY_ID
	 */
	@Column(name = "CREATED_BY_ID", nullable = true, length = 32)
	public String getCreatedById() {
		return this.createdById;
	}

	/**
	 * 方法: 设置CREATED_BY_ID.
	 * @param: java.lang.String CREATED_BY_ID
	 */
	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}

	/**
	 * 方法: 取得CREATED_DATE.
	 * @return: java.util.Date CREATED_DATE
	 */
	@Column(name = "CREATED_DATE", nullable = true)
	public java.util.Date getCreatedDate() {
		return this.createdDate;
	}

	/**
	 * 方法: 设置CREATED_DATE.
	 * @param: java.util.Date CREATED_DATE
	 */
	public void setCreatedDate(java.util.Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * 方法: 取得DEPT_ID.
	 * @return: java.lang.String DEPT_ID
	 */
	@Column(name = "DEPT_ID", nullable = true, length = 32)
	public String getDeptId() {
		return this.deptId;
	}

	/**
	 * 方法: 设置DEPT_ID.
	 * @param: java.lang.String DEPT_ID
	 */
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	/**
	 * 方法: 取得DEPT_NAME.
	 * @return: java.lang.String DEPT_NAME
	 */
	@Column(name = "DEPT_NAME", nullable = true, length = 128)
	public String getDeptName() {
		return this.deptName;
	}

	/**
	 * 方法: 设置DEPT_NAME.
	 * @param: java.lang.String DEPT_NAME
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	/**
	 * 方法: 取得MODIFIED_BY_ID.
	 * @return: java.lang.String MODIFIED_BY_ID
	 */
	@Column(name = "MODIFIED_BY_ID", nullable = true, length = 32)
	public String getModifiedById() {
		return this.modifiedById;
	}

	/**
	 * 方法: 设置MODIFIED_BY_ID.
	 * @param: java.lang.String MODIFIED_BY_ID
	 */
	public void setModifiedById(String modifiedById) {
		this.modifiedById = modifiedById;
	}

	/**
	 * 方法: 取得MODIFIED_BY.
	 * @return: java.lang.String MODIFIED_BY
	 */
	@Column(name = "MODIFIED_BY", nullable = true, length = 64)
	public String getModifiedBy() {
		return this.modifiedBy;
	}

	/**
	 * 方法: 设置MODIFIED_BY.
	 * @param: java.lang.String MODIFIED_BY
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	/**
	 * 方法: 取得MODIFIED_DATE.
	 * @return: java.util.Date MODIFIED_DATE
	 */
	@Column(name = "MODIFIED_DATE", nullable = true)
	public java.util.Date getModifiedDate() {
		return this.modifiedDate;
	}

	/**
	 * 方法: 设置MODIFIED_DATE.
	 * @param: java.util.Date MODIFIED_DATE
	 */
	public void setModifiedDate(java.util.Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	/**
	 * 方法: 取得状态.
	 * @return: int 状态
	 */
	@Column(name = "STATUS", nullable = true, precision = 10)
	public int getStatus() {
		return this.status;
	}

	/**
	 * 方法: 设置状态.
	 * @param: int 状态
	 */
	public void setStatus(int status) {
		this.status = status;
	}

}
