package com.supporter.prj.cneec.tpc.contract_sign_review_dept.entity;

import javax.persistence.Entity;
import javax.persistence.Transient;

import com.supporter.prj.cneec.tpc.contract_sign_review_dept.entity.base.BaseContractSignDeptInfor;

/**
 * @Title: ContractReviewRecord
 * @Description: 选择评审单
 * @author: yanweichao
 * @date: 2017-11-13
 * @version: V1.0
 */
@Entity
public class ContractDeptReviewRecord extends BaseContractSignDeptInfor implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// Constructors

	/** default constructor */
	public ContractDeptReviewRecord() {
		super();
	}

	/** minimal constructor */
	public ContractDeptReviewRecord(String inforId) {
		super(inforId);
	}

	private int level = 1;// 级别
	private String parent = "null";// 上级ID
	private boolean isLeaf = true;// 是否叶节点
	private boolean expanded = false;// 是否展开
	private String montage;// 拼接信息，作为选择评审单返回信息

	@Transient
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Transient
	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	@Transient
	public boolean getIsLeaf() {
		return isLeaf;
	}

	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	@Transient
	public boolean isExpanded() {
		return expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

	@Transient
	public String getMontage() {
		return montage;
	}

	public void setMontage(String montage) {
		this.montage = montage;
	}

}
