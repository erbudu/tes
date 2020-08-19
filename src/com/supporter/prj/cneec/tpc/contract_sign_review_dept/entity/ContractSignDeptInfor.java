package com.supporter.prj.cneec.tpc.contract_sign_review_dept.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.tpc.contract_sign_review_dept.entity.base.BaseContractSignDeptInfor;
import com.supporter.prj.cneec.tpc.contract_sign_review_dept.util.ContractSignReviewUtil;
import com.supporter.prj.cneec.tpc.util.TpcConstant;

/**   
 * @Title: Entity
 * @Description: TPC_CONTRACT_SIGN_DEPT_INFOR.
 * @author Yanweichao
 * @date 2018-03-21
 * @version V1.0   
 *
 */
@Entity
@Table(name = "TPC_CONTRACT_SIGN_DEPT_INFOR", schema = "")
public class ContractSignDeptInfor extends BaseContractSignDeptInfor {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public ContractSignDeptInfor() {
		super();
	}

	/**
	 * 构造函数.
	 * @param inforId
	 */
	public ContractSignDeptInfor(String inforId) {
		super(inforId);
	}

	private boolean add = false;// 是否新增
	private List<ContractSignDeptAmount> contractAmountList;

	@Transient
	public boolean getAdd() {
		return add;
	}

	public void setAdd(boolean add) {
		this.add = add;
	}

	@Transient
	public List<ContractSignDeptAmount> getContractAmountList() {
		return this.contractAmountList;
	}

	public void setContractAmountList(List<ContractSignDeptAmount> contractAmountList) {
		this.contractAmountList = contractAmountList;
	}

	// 是否变更效益测算表
	@Transient
	public String getIsChangeBenefitDesc() {
		return this.isChangeBenefit() ? "是" : "否";
	}

	// 合同类型
	@Transient
	public String getInforTypeDesc() {
		return ContractSignReviewUtil.getInforTypeMap().get(this.getInforType());
	}

	// 评审结论
	@Transient
	public String getReviewConclusionDesc() {
		return TpcConstant.getReviewConclusionMap().get(this.getReviewConclusion());
	}

}
