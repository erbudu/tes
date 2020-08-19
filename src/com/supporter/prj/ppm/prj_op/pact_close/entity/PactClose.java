package com.supporter.prj.ppm.prj_op.pact_close.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.ppm.prj_op.pact_close.entity.base.BasePactClose;
import com.supporter.util.CodeTable;

/**
 * @Title: Entity
 * @Description: PPM_PACT_CLOSE.
 * @author: As
 * @date: 2019-11-05
 * @version: V1.0
 */
@Entity
@Table(name = "PPM_PACT_CLOSE", schema = "")
public class PactClose extends BasePactClose implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String MODULE_ID = "PACTCLOSE"; // 应用模块的id
	public static final String DOMAIN_OBJECT_ID = "pactClose";// 业务对象id
	public static final String BUSI_TYPE = "pactCloseFile";// 附件注册
	public static final int DB_YEAR = 0;

	/**
	 * 无参构造函数.
	 */
	public PactClose() {
		super();
	}

	/**
	 * 构造函数.
	 * @param id
	 */
	public PactClose(String id) {
		super(id);
	}

	/**
	 * 全参构造
	 */
	public PactClose(String id, String prjId, String prjNameZh, String prjNameEn, String reportId, String reportNo, String pactName,
			String closeReason, String explain, String deptId, String deptName, String contactId, String contactName, String contactNumber,
			int status, String creator, String creatorId, Date creationDate, String modifier, String modifierId, Date modificationDate,
			String submitter, String submitterId, Date submitDate, String procId) {
		super(id, prjId, prjNameZh, prjNameEn, reportId, reportNo, pactName, closeReason, explain, deptId, deptName, contactId, contactName,
				contactNumber, status, creator, creatorId, creationDate, modifier, modifierId, modificationDate, submitter, submitterId, submitDate,
				procId);
	}

	private boolean isNew;

	@Transient
	public boolean getIsNew() {
		return isNew;
	}

	public void setIsNew(boolean isNew) {
		this.isNew = isNew;
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

	// 状态
	public static final class StatusCodeTable {
		/** 草稿 */
		public static final int DRAFT = 0;
		/** 审批中 */
		public static final int EXAM = 1;
		/** 审批完成 */
		public static final int COMPLETE = 2;

		// 状态
		public static CodeTable getCodeTable() {
			CodeTable ct = new CodeTable();
			ct.insertItem(DRAFT, "草稿");
			ct.insertItem(EXAM, "审批中");
			ct.insertItem(COMPLETE, "审批完成");
			return ct;
		}
	}

}
