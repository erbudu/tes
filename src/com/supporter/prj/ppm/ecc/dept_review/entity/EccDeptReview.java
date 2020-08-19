package com.supporter.prj.ppm.ecc.dept_review.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.eip.busi_entity.IBusiEntity;
import com.supporter.prj.ppm.ecc.dept_review.entity.base.BaseEccDeptReview;
import com.supporter.util.CodeTable;

/**
 * @Title: Entity
 * @Description: 部门出口管制评审.
 * @author: YAN
 * @date: 2019-08-16
 * @version: V1.0
 */
@Entity
@Table(name = "PPM_ECC_DEPT_REVIEW", schema = "")
public class EccDeptReview extends BaseEccDeptReview implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public EccDeptReview() {
		super();
	}

	/**
	 * 构造函数.
	 * @param deptEccId
	 */
	public EccDeptReview(String deptEccId) {
		super(deptEccId);
	}
	public static final int DRAFT = 0;
	public static final int PROCESSING = 1;
	public static final int COMPLETE = 2;

	public static final String MODULE_ID = "PpmEcc";
	/**
	 * 获取文档状态码表.
	 *
	 * @return
	 */
	public static CodeTable getDocStatusCodeTable() {
		CodeTable lcdtbl_Return = new CodeTable();
		lcdtbl_Return.insertItem(DRAFT, "草稿");
		lcdtbl_Return.insertItem(PROCESSING, "审批中");
		lcdtbl_Return.insertItem(COMPLETE, "审批完成");

		return lcdtbl_Return;
	}
	@Transient
	public int getDbYear() {
		return 0;
	}

	@Transient
	public String getDomainObjectId() {
		return "EccDeptReview";
	}

	@Transient
	public String getEntityName() {
		return getClass().getName();
	}

	@Transient
	public String getModuleId() {
		return "PpmEcc";
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
