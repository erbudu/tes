package com.supporter.prj.ppm.ecc.cac_review.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.eip.emp.entity.Employee;
import com.supporter.prj.eip_service.emp.entity.IEmployee;
import com.supporter.prj.ppm.ecc.cac_review.entity.base.BaseEccCacReview;
import com.supporter.util.CodeTable;

/**
 * @Title: Entity
 * @Description: 出口管制委员会审核表.
 * @author: YAN
 * @date: 2019-08-16
 * @version: V1.0
 */
@Entity
@Table(name = "PPM_ECC_CAC_REVIEW", schema = "")
public class EccCacReview extends BaseEccCacReview implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String MODULE_ID = "PpmEccCac";

	/**
	 * 无参构造函数.
	 */
	public EccCacReview() {
		super();
	}

	/**
	 * 构造函数.
	 * @param eccCacId
	 */
	public EccCacReview(String eccCacId) {
		super(eccCacId);
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

	//委员详细信息
	private List<IEmployee> employeeList;
	@Transient
	public List<IEmployee> getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(List<IEmployee> employeeList) {
		this.employeeList = employeeList;
	}
}
