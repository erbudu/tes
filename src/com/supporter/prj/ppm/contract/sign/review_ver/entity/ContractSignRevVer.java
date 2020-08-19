package com.supporter.prj.ppm.contract.sign.review_ver.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.ppm.contract.sign.review_ver.entity.base.BaseContractSignRevVer;
import com.supporter.util.CodeTable;

/**
 * @Title: Entity
 * @Description: 评审验证表.
 * @author: YAN
 * @date: 2019-09-09
 * @version: V1.0
 */
@Entity
@Table(name = "PPM_CONTRACT_SIGN_REV_VER", schema = "")
public class ContractSignRevVer extends BaseContractSignRevVer implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String MODULE_ID = "CONSIGNREVER"; // 应用模块的id
	public static final String DOMAIN_OBJECT_ID = "conSignRever";
	public static final int DB_YEAR = 0;

	/**
	 * 无参构造函数.
	 */
	public ContractSignRevVer() {
		super();
	}

	/**
	 * 构造函数.
	 * @param reviewVerId
	 */
	public ContractSignRevVer(String reviewVerId) {
		super(reviewVerId);
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
		return "";
	}

	// 状态
	public static final class StatusCodeTable {
		/** 草稿 */
		public static final int DRAFT = 0;
		/** 审批中 */
		public static final int EXAM = 1;
		/** 审批完成(生效) */
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

	private String paragraphNo;
	@Transient
	public String getParagraphNo() {
		return paragraphNo;
	}

	public void setParagraphNo(String paragraphNo) {
		this.paragraphNo = paragraphNo;
	}
}


