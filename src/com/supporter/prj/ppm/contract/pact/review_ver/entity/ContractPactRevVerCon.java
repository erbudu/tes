package com.supporter.prj.ppm.contract.pact.review_ver.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.ppm.contract.pact.review_ver.entity.base.BaseContractPactRevVerCon;
import com.supporter.util.CodeTable;

@Entity
@Table(name = "PPM_CONTRACT_PACT_REV_VER_CON", schema = "")
public class ContractPactRevVerCon extends BaseContractPactRevVerCon implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String DOMAIN_OBJECT_ID = "conPactRevVerRe"; // 业务对象id

	/**
	 * 无参构造函数.
	 */
	public ContractPactRevVerCon() {
		super();
	}

	/**
	 * 构造函数.
	 * @param revConId
	 */
	public ContractPactRevVerCon(String revConId) {
		super(revConId);
	}

	/**
	 * 全参构造
	 */
	public ContractPactRevVerCon(String revConId, String revVerId, int revConStatus) {
		super(revConId, revVerId, revConStatus);
	}

	// 评审验证结论
	public static final class ConStatusCodeTable {
		/**不通过*/
		public static final int NOTPASS = 0;
		/**通过*/
		public static final int PASS = 1;

		// 状态
		public static CodeTable getCodeTable() {
			CodeTable ct = new CodeTable();
			ct.insertItem(NOTPASS, "不通过");
			ct.insertItem(PASS, "通过");
			return ct;
		}
	}

}
