package com.supporter.prj.ppm.pay_apply.entity;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.ppm.pay_apply.entity.base.BasePayApply;
import com.supporter.util.CodeTable;

/**
 * @Title: PayApply
 * @Description: 支付申请实体类
 * @author: zmq
 * @date: 2019-9-3
 * @version: V1.0
 */
@Entity
@Table(name = "PPM_PAY_APPLY", schema = "")
public class PayApply extends BasePayApply implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	public static final String MODULE_ID = "PAYAPPLY";//应用编号
	public static final String DOMAIN_OBJECT_ID = "payApply";//业务对象编号
	public static final int DB_YEAR = 0;

	// Constructors

	/** default constructor */
	public PayApply() {
		super();
	}

	/** minimal constructor */
	public PayApply(String id) {
		super(id);
	}

	/**
	 * 全参构造
	 */
	public PayApply(String id, String prjId, String prjNo, String prjNameC, String prjNameE, String payNo, String applyType, String fundUseId,
			String fundUse, String agreConId, String agreConNo, String agreConName, String payType, double payOriCur, String payCurrencyId,
			String payCurrency, double rmbAmount, double exchangeRate, String payReasons, String receivingUnit, String ruOpenB, String ruBankAccount,
			int status, String procId, String deptName, String deptId, String createdBy, String createdById, Date createdDate, String modifiedBy,
			String modifiedById, Date modifiedDate, String submitter, String submitterId, Date submissionDate) {
		super(id, prjId, prjNo, prjNameC, prjNameE, payNo, applyType, fundUseId, fundUse, agreConId, agreConNo, agreConName, payType, payOriCur,
				payCurrencyId, payCurrency, rmbAmount, exchangeRate, payReasons, receivingUnit, ruOpenB, ruBankAccount, status, procId, deptName,
				deptId, createdBy, createdById, createdDate, modifiedBy, modifiedById, modifiedDate, submitter, submitterId, submissionDate);
	}

	private boolean add;// 是否新增
	private String keyword;// 搜索关键字

	@Transient
	public boolean getAdd() {
		return add;
	}

	public void setAdd(boolean add) {
		this.add = add;
	}

	@Transient
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	private boolean isNew;

	@Transient
	public boolean getIsNew() {
		return isNew;
	}

	public void setIsNew(boolean isNew) {
		this.isNew = isNew;
	}
	// 状态
	
	@Transient
	public String getStatusDesc() {
		return StatusCodeTable.getCodeTable().getDisplay(getStatus());
	}
	private String statusDesc;
	public static final class StatusCodeTable {
		public static final int DRAFT = 0;
		public static final int EXAM = 1;
		public static final int COMPLETE = 2;
		
		public static CodeTable getCodeTable() {
			CodeTable ct = new CodeTable();
			ct.insertItem(DRAFT, "草稿");
			ct.insertItem(EXAM, "审批中");
			ct.insertItem(COMPLETE, "审批完成");
			return ct;
		}
	}
	@Transient
	public String getModuleId() {
		return MODULE_ID;
	}

	@Transient
	public int getDbYear() {
		return DB_YEAR;
	}

	@Transient
	public String getDomainObjectId() {
		return DOMAIN_OBJECT_ID;
	}

	@Transient
	public String getEntityName() {
		return getClass().getName();
	}

	@Transient
	public String getModuleBusiType() {
		return "";
	}

	@Transient
	public String getCompanyNo() {
		return getDeptId();
	}



}
