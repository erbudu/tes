package com.supporter.prj.cneec.tpc.prj_contract_table.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.tpc.prj_contract_table.entity.base.BasePrjContractTable;

/**
 * @Title: PrjContractTable
 * @Description: 合同信息表实体类
 * @author: yanweichao
 * @date: 2018-03-15
 * @version: V1.0
 */
@Entity
@Table(name = "TPC_PRJ_CONTRACT_TABLE", schema = "")
public class PrjContractTable extends BasePrjContractTable implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	public static final String MODULE_ID = "TPCPRJCONTAB";// 应用ID，最长不超过12位
	public static final String BUSI_TYPE = "TPCPRRJCONTRACTTABLE";// 附件参数，最长不超过50位

	// Constructors

	/** default constructor */
	public PrjContractTable() {
		super();
	}

	/** minimal constructor */
	public PrjContractTable(String contractId) {
		super(contractId);
	}

	private String keyword;// 搜索关键字
	private List<PrjContractAmount> contractAmountList;
	private List<PrjContractGoods> contractGoodsList;
	private List<PrjContractCollectionTerms> collectionTermsList;

	@Transient
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Transient
	public List<PrjContractAmount> getContractAmountList() {
		return contractAmountList;
	}

	public void setContractAmountList(List<PrjContractAmount> contractAmountList) {
		this.contractAmountList = contractAmountList;
	}

	@Transient
	public List<PrjContractGoods> getContractGoodsList() {
		return contractGoodsList;
	}

	public void setContractGoodsList(List<PrjContractGoods> contractGoodsList) {
		this.contractGoodsList = contractGoodsList;
	}

	@Transient
	public List<PrjContractCollectionTerms> getCollectionTermsList() {
		return collectionTermsList;
	}

	public void setCollectionTermsList(List<PrjContractCollectionTerms> collectionTermsList) {
		this.collectionTermsList = collectionTermsList;
	}

}
