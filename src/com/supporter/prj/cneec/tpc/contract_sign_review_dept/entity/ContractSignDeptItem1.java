package com.supporter.prj.cneec.tpc.contract_sign_review_dept.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import com.supporter.prj.cneec.tpc.contract_sign_review_dept.entity.base.BaseContractSignDeptItem;
import com.supporter.prj.cneec.tpc.contract_sign_review_dept.util.ContractSignItemBean;
import com.supporter.util.CommonUtil;

/**
 * @Title: Entity
 * @Description: 销售合同明细
 * @author Yanweichao
 * @date 2018-04-10
 * @version V1.0
 */
@Entity
@Table(name = "TPC_CONTRACT_SIGN_DEPT_ITEM1", schema = "")
public class ContractSignDeptItem1 extends BaseContractSignDeptItem implements ContractSignItemBean, Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public ContractSignDeptItem1() {
		super();
	}

	/**
	 * 构造函数.
	 * @param itemId
	 */
	public ContractSignDeptItem1(String itemId) {
		super(itemId);
	}

	public ContractSignDeptItem1(int initialReviewType, String parentItemId, String parentItemName, String saleReviewId, String saleContractId) {
		super(initialReviewType, parentItemId, parentItemName, saleReviewId, saleContractId);
	}

	public ContractSignDeptItem1(int initialReviewType, String purchaseReviewId, String purchaseContractId) {
		super(initialReviewType, purchaseReviewId, purchaseContractId);
	}

	private String displayName;// 显示名称

	@Transient
	public String getDisplayName() {
		if (displayName == null) {
			displayName = CommonUtil.trim(this.getItemName());
			if (this.getParentItemId() != null) {
				displayName = CommonUtil.trim(this.getParentItemName()) + " - " + displayName;
			}
		}
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/* 树状结构相关 */
	private int level;// 级别
	private String parent;// 上级ID
	private boolean isLeaf = true;// 是否叶节点(默认是)
	private boolean expanded = true;// 是否展开

	@Transient
	public int getLevel() {
		// 父明细ID非空
		if (this.getParentItemId() != null) {
			level = 1;
		}
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Transient
	public String getParent() {
		if (parent == null && this.getParentItemId() != null) {
			parent = this.getParentItemId();
		}
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	@Type(type = "true_false")
	@Column(name = "IS_LEAF", nullable = true, length = 1)
	public boolean isLeaf() {
		return this.isLeaf;
	}

	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	@Transient
	public boolean getIsLeaf() {
		return this.isLeaf;
	}

	@Transient
	public boolean isExpanded() {
		return this.expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

}
