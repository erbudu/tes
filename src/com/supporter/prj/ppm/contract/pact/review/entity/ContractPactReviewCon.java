package com.supporter.prj.ppm.contract.pact.review.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.ppm.contract.pact.review.entity.base.BaseContractPactReviewCon;
import com.supporter.util.CodeTable;

@Entity
@Table(name = "PPM_CONTRACT_PACT_REV_CON ", schema = "")
public class ContractPactReviewCon extends BaseContractPactReviewCon {

	private static final long serialVersionUID = 1L;
	public static final String DOMAIN_OBJECT_ID = "conPactRevRes"; // 业务对象id

	/**
	 * 无参构造
	 */
	public ContractPactReviewCon() {
		super();
	}

	/**
	 * 全参构造
	 */
	public ContractPactReviewCon(String reviewConId, String reviewId, int reviewConStatus, String reviewVerContent) {
		super(reviewConId, reviewId, reviewConStatus, reviewVerContent);
	}

	/**
	 * 构造函数
	 */
	public ContractPactReviewCon(String reviewConId) {
		super(reviewConId);
	}

	// 评审结论
	public static final class ConStatusCodeTable {
		/**不通过*/
		public static final int NOTPASS = 0;
		/**有条件通过*/
		public static final int CONDITIONPASS = 1;
		/**通过*/
		public static final int PASS = 2;

		// 状态
		public static CodeTable getCodeTable() {
			CodeTable ct = new CodeTable();
			ct.insertItem(NOTPASS, "不通过");
			ct.insertItem(CONDITIONPASS, "有条件通过，需评审验证");
			ct.insertItem(PASS, "通过");
			return ct;
		}
	}

}
