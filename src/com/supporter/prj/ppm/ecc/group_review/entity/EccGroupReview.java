package com.supporter.prj.ppm.ecc.group_review.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.ppm.ecc.group_review.entity.base.BaseEccGroupReview;
import com.supporter.util.CodeTable;

/**
 * @Title: Entity
 * @Description: 集团管制审核表.
 * @author: YAN
 * @date: 2019-08-16
 * @version: V1.0
 */
@Entity
@Table(name = "PPM_ECC_GROUP_REVIEW", schema = "")
public class EccGroupReview extends BaseEccGroupReview implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String MODULE_ID = "PpmEccGroup";
	/**
	 * 无参构造函数.
	 */
	public EccGroupReview() {
		super();
	}

	/**
	 * 构造函数.
	 * @param eccGroupId
	 */
	public EccGroupReview(String eccGroupId) {
		super(eccGroupId);
	}
	public static final int DRAFT = 0;
	public static final int PROCESSING = 1;
	public static final int COMPLETE = 2;
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
	private String prjNo;//项目编码
	private String prjCName;//项目中文名称
	private String prjEName;//项目英文名称
	@Transient
	public String getPrjNo() {
		return prjNo;
	}

	public void setPrjNo(String prjNo) {
		this.prjNo = prjNo;
	}
	@Transient
	public String getPrjCName() {
		return prjCName;
	}

	public void setPrjCName(String prjCName) {
		this.prjCName = prjCName;
	}
	@Transient
	public String getPrjEName() {
		return prjEName;
	}

	public void setPrjEName(String prjEName) {
		this.prjEName = prjEName;
	}
}
