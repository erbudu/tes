package com.supporter.prj.cneec.tpc.contract_online_prepare.entity;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.tpc.contract_online_prepare.entity.base.BaseContractOnlinePrepare;
import com.supporter.prj.cneec.tpc.contract_sign_review_dept.util.ContractSignReviewUtil;
import com.supporter.prj.cneec.tpc.derivative_contract.util.DerivativeContractUtil;
import com.supporter.util.CommonUtil;

/**
 * @Title: ContractReviewSheet
 * @Description: 合同签约前评审完成合同选择单实体类
 * @author: yanweichao
 * @date: 2018-05-15
 * @version V1.0
 */
@Entity
@Table(name = "TPC_CONTRACT_ONLINE_PREPARE", schema = "")
public class ContractOnlinePrepare extends BaseContractOnlinePrepare implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String MODULE_ID = "TPCCONTRVPRE";// 应用编号，最长12位
	public static final String DOMAIN_OBJECT_ID = "ContOnlinePre";// 业务对象编号，最长15位

	/**
	 * 无参构造函数.
	 */
	public ContractOnlinePrepare() {
		super();
	}

	/**
	 * 构造函数.
	 * @param prepareId
	 */
	public ContractOnlinePrepare(String prepareId) {
		super(prepareId);
	}

	private boolean add;// 是否新增
	private String keyword;// 搜索关键字

	@Transient
	public boolean getAdd() {
		return add;
	}

	public void setAdd(boolean add) {
		this.add = add;
	}

	@Transient
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Transient
	public String getDerivativeId() {// 衍生合同单ID
		return this.getSignId();
	}

	@Transient
	public String getDerivativeNo() {// 衍生合同单号
		return this.getSignReviewNo();
	}

	@Transient
	public String getPaymentType() {// 衍生合同单付款类型
		return this.getCustomerId();
	}

	@Transient
	public String getPaymentTypeDesc() {
		return DerivativeContractUtil.getPaymentTypeMap().get(this.getPaymentType());
	}

	@Transient
	public double getPaymentAmount() {// 衍生合同单付款金额
		return this.getTotalRmbAmount();
	}

	public static final String STEM_FROM_DEPT_REVIEW = "CONTRACT_SIGN_DEPT_REVIEW"; // 事业部合同评审合同
	public static final String STEM_FROM_REVIEW = "CONTRACT_SIGN_REVIEW"; // 公司合同评审合同
	public static final String STEM_FROM_DERIVATIVE = "DERIVATIVE_CONTRACT"; // 衍生合同

	// 评审合同类型
	@Transient
	public String getInforTypeDesc() {
		if (this.getInforType() != 0) {
			return ContractSignReviewUtil.getInforTypeMap().get(this.getInforType());
		}
		return "";
	}

	@Transient
	public boolean isCompanyReview() {
		if (CommonUtil.trim(this.getStemFrom()).equals(STEM_FROM_REVIEW)) {
			return true;
		}
		return false;
	}

	public static final int SEAL_DRAFT = 0; // 未用印
	public static final int SEAL_PROCESS = 10; // 用印中
	public static final int SEAL_FINISH = 20; // 已用印
	public static final int FILING_DRAFT = 0; // 未备案
	public static final int FILING_PROCESS = 10; // 备案中
	public static final int FILING_FINISH = 20; // 已备案
	public static final int ONLINE_DRAFT = 0; // 未上线
	public static final int ONLINE_PROCESS = 10; // 上线中
	public static final int ONLINE_FINISH = 20; // 已上线

	/**
	 * 获取用印状态码表.
	 * 
	 * @return
	 */
	public static Map<Integer, String> getSealStatusMap() {
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(SEAL_DRAFT, "未用印");
		map.put(SEAL_PROCESS, "用印中");
		map.put(SEAL_FINISH, "已用印");
		return map;
	}

	// 用印状态
	@Transient
	public String getSealStatusDesc() {
		if (this.getSealStatus() != null) {
			return getSealStatusMap().get(this.getSealStatus());
		}
		return "";
	}

	// 是否可以用印
	@Transient
	public boolean getCanSeal() {
		// 合同评审的合同：1.未用印，>.且未备案，>.备案完成，2.隐含条件-未上线
		// 衍生合同：1.未用印，2.隐含条件-未上线
		// 备案完成即上线表明不需要再用印
		if (this.getSealStatus() == SEAL_DRAFT && this.getOnlineStatus() == ONLINE_DRAFT) {
			if (this.getFilingStatus() == FILING_DRAFT || this.getFilingStatus() == FILING_FINISH) {
				return true;
			}
		}
		return false;
	}

	// 是否已用印
	@Transient
	public boolean isUseSeal() {
		return this.getSealStatus() == SEAL_FINISH;
	}

	/**
	 * 获取备案状态码表.
	 * 
	 * @return
	 */
	public static Map<Integer, String> getFilingStatusMap() {
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(FILING_DRAFT, "未备案");
		map.put(FILING_PROCESS, "备案中");
		map.put(FILING_FINISH, "已备案");
		return map;
	}

	// 备案状态
	@Transient
	public String getFilingStatusDesc() {
		if (this.getFilingStatus() != null) {
			return getFilingStatusMap().get(this.getFilingStatus());
		}
		return "";
	}

	// 是否可以备案
	@Transient
	public boolean getCanFiling() {
		// 合同评审的合同：1.未备案，>.且未用印，>.用印完成，2.隐含条件-未上线
		// 衍生合同：1.未备案，2.隐含条件-未上线
		if (this.getFilingStatus() == FILING_DRAFT && this.getOnlineStatus() == ONLINE_DRAFT) {
			if (this.getSealStatus() == SEAL_DRAFT || this.getSealStatus() == SEAL_FINISH) {
				return true;
			}
		}
		return false;
	}

	// 是否已上线
	@Transient
	public boolean isFiling() {
		return this.getFilingStatus() == FILING_FINISH;
	}

	/**
	 * 获取上线状态码表.
	 * 
	 * @return
	 */
	public static Map<Integer, String> getOnlineStatusMap() {
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(ONLINE_DRAFT, "未信息上线");
		map.put(ONLINE_PROCESS, "信息上线中");
		map.put(ONLINE_FINISH, "已信息上线");
		return map;
	}

	// 上线状态
	@Transient
	public String getOnlineStatusDesc() {
		if (this.getOnlineStatus() != null) {
			return getOnlineStatusMap().get(this.getOnlineStatus());
		}
		return "";
	}

	// 是否可以上线
	@Transient
	public boolean getCanOnline() {
		return this.getOnlineStatus() == ONLINE_DRAFT && this.getFilingStatus() == FILING_FINISH;
	}

	// 是否已上线
	@Transient
	public boolean isContractOnline() {
		return this.getOnlineStatus() == ONLINE_FINISH;
	}

}