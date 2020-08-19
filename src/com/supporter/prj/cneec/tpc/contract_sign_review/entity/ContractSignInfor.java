package com.supporter.prj.cneec.tpc.contract_sign_review.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.tpc.contract_sign_review.entity.base.BaseContractSignInfor;
import com.supporter.prj.cneec.tpc.util.TpcConstant;
import com.supporter.util.CommonUtil;

/**   
 * @Title: Entity
 * @Description: TPC_CONTRACT_SIGN_INFOR.
 * @author Yanweichao
 * @date 2017-09-28
 * @version V1.0   
 *
 */
@Entity
@Table(name = "TPC_CONTRACT_SIGN_INFOR", schema = "")
public class ContractSignInfor extends BaseContractSignInfor {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public ContractSignInfor(){
		super();
	}

	/**
	 * 构造函数.
	 * @param inforId
	 */
	public ContractSignInfor(String inforId) {
		super(inforId);
	}

	private boolean add = false;// 是否新增

	@Transient
	public boolean getAdd() {
		return add;
	}

	public void setAdd(boolean add) {
		this.add = add;
	}

	// 是否变更效益测算表
	@Transient
	public String getIsChangeBenefitDesc() {
		return this.isChangeBenefit() ? "是" : "否";
	}

	// 评审结论
	@Transient
	public String getReviewConclusionDesc() {
		if (!CommonUtil.isNullOrEmpty(this.getReviewConclusion())) {
			return TpcConstant.getReviewConclusionMap().get(this.getReviewConclusion());
		}
		return "";
	}

}
