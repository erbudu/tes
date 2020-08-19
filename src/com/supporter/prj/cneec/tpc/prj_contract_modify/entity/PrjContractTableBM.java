package com.supporter.prj.cneec.tpc.prj_contract_modify.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.tpc.prj_contract_modify.entity.base.BasePrjContractTableBM;

/**
 * @Title: PrjContractTableBM
 * @Description: 合同信息表实体类
 * @author: yanweichao
 * @date: 2018-03-15
 * @version: V1.0
 */
@Entity
@Table(name = "TPC_PRJ_CONTRACT_TABLE_BM", schema = "")
public class PrjContractTableBM extends BasePrjContractTableBM implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	public static final String MODULE_ID = "TPCPRJCONBM";// 应用ID，最长不超过12位
	public static final String BUSI_TYPE = "TPCPRRJCONTRACTTABLEBM";// 附件参数，最长不超过50位

	// Constructors

	/** default constructor */
	public PrjContractTableBM() {
		super();
	}

	/** minimal constructor */
	public PrjContractTableBM(String bmId) {
		super(bmId);
	}

	private String keyword;// 搜索关键字
	private List<PrjContractAmountBM> contractAmountBMList;
	private List<PrjContractGoodsBM> contractGoodsBMList;
	private List<PrjContractCollectionTermsBM> collectionTermsBMList;
	private List<SettlementPlanBM> settlementPlanBMList;

	@Transient
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Transient
	public List<PrjContractAmountBM> getContractAmountBMList() {
		return contractAmountBMList;
	}

	public void setContractAmountBMList(List<PrjContractAmountBM> contractAmountBMList) {
		this.contractAmountBMList = contractAmountBMList;
	}

	@Transient
	public List<PrjContractGoodsBM> getContractGoodsBMList() {
		return contractGoodsBMList;
	}

	public void setContractGoodsBMList(List<PrjContractGoodsBM> contractGoodsBMList) {
		this.contractGoodsBMList = contractGoodsBMList;
	}

	@Transient
	public List<PrjContractCollectionTermsBM> getCollectionTermsBMList() {
		return collectionTermsBMList;
	}

	public void setCollectionTermsBMList(List<PrjContractCollectionTermsBM> collectionTermsBMList) {
		this.collectionTermsBMList = collectionTermsBMList;
	}

	@Transient
	public List<SettlementPlanBM> getSettlementPlanBMList() {
		return settlementPlanBMList;
	}

	public void setSettlementPlanBMList(List<SettlementPlanBM> settlementPlanBMList) {
		this.settlementPlanBMList = settlementPlanBMList;
	}

}
