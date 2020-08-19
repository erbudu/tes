package com.supporter.prj.ppm.prj_op.prj_close.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.ppm.prj_op.prj_close.entity.base.BasePrjClose;
import com.supporter.util.CodeTable;

@Entity
@Table(name = "PPM_PRJ_CLOSE", schema = "")
public class PrjClose extends BasePrjClose {

	private static final long serialVersionUID = 1L;

	public static final String MODULE_ID = "PRJCLOSE"; // 应用模块的id
	public static final String DOMAIN_OBJECT_ID = "prjClose";
	public static final int DB_YEAR = 0;
	/** 项目未签订过合同及协议 */
	public static final int NOT_SIGNED = 0;
	/** 项目签订过合同及协议 */
	public static final int HAS_SIGNED = 1;

	/**
	 * 无参构造
	 */
	public PrjClose() {

	}

	public PrjClose(String id, String prjId, String prjNameZh, String prjNameEn, String closeReason, int hasSigned, String explain, String deptId,
			String deptName, String contactNameId, String contactName, String contactNumber, int status, Date submitDate, String creator,
			String creatorId, Date creationDate, String modifier, String modifierId, Date modificationDate, String procId) {
		super(id, prjId, prjNameZh, prjNameEn, closeReason, hasSigned, explain, deptId, deptName, contactNameId, contactName, contactNumber, status,
				submitDate, creator, creatorId, creationDate, modifier, modifierId, modificationDate, procId);
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
