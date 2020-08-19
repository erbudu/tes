package com.supporter.prj.cneec.tpc.derivative_contract.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.tpc.derivative_contract.entity.base.BaseDerivativeContract;
import com.supporter.prj.cneec.tpc.derivative_contract.util.DerivativeContractUtil;

/**
 * @Title: DerivativeContract
 * @Description: 衍生合同单实体类
 * @author: yanweichao
 * @date: 2018-05-21
 * @version: V1.0
 */
@Entity
@Table(name = "TPC_DERIVATIVE_CONTRACT", schema = "")
public class DerivativeContract extends BaseDerivativeContract implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	public static final String MODULE_ID = "TPCDERICONT";// 应用编号，最长12位
	public static final String BUSI_TYPE = "TPCDERIVATIVECONTRACT";// 附件参数，最长50位
	public static final String DOMAIN_OBJECT_ID = "DerivativeCont";// 业务对象编号，最长15位

	// Constructors

	/** default constructor */
	public DerivativeContract() {
		super();
	}

	/** minimal constructor */
	public DerivativeContract(String derivativeId) {
		super(derivativeId);
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

	// 合同付款类型
	@Transient
	public String getPaymentTypeDesc() {
		if (this.getPaymentType() != null) {
			return DerivativeContractUtil.getPaymentTypeMap().get(this.getPaymentType());
		}
		return "";
	}

	public static final int DRAFT = 0; // 草稿
	//public static final int PROCESSING = 10; // 审核中
	public static final int COMPLETED = 20;// 已完成

	/**
	 * 获取状态码表.
	 * @return
	 */
	public static Map<Integer, String> getSwfStatusMap() {
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(DRAFT, "草稿");
		//map.put(PROCESSING, "审核中");
		map.put(COMPLETED, "已完成");
		return map;
	}

	// 状态
	@Transient
	public String getSwfStatusDesc() {
		if (this.getSwfStatus() != null) {
			return getSwfStatusMap().get(this.getSwfStatus());
		}
		return "";
	}

}
