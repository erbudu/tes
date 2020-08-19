package com.supporter.prj.ppm.ecc.group_review.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.ppm.ecc.group_review.entity.base.BaseEccGroupRevieCon;

/**
 * @Title: Entity
 * @Description: 集团评审结论.
 * @author: YAN
 * @date: 2019-08-16
 * @version: V1.0
 */
@Entity
@Table(name = "PPM_ECC_GROUP_REVIE_CON", schema = "")
public class EccGroupRevieCon extends BaseEccGroupRevieCon implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public EccGroupRevieCon() {
		super();
	}

	/**
	 * 构造函数.
	 * @param groupEccRvConId
	 */
	public EccGroupRevieCon(String groupEccRvConId) {
		super(groupEccRvConId);
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
