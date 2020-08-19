package com.supporter.prj.cneec.tpc.contract_change.entity;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.tpc.contract_change.entity.base.BaseContractOrder;
import com.supporter.util.CommonUtil;

@Entity
@Table(name = "TPC_CONTRACT_ORDER", schema = "")
public class ContractOrder extends BaseContractOrder implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String MODULE_ID = "CONORDER";
	public static final String BUSI_TYPE = "chFile";
	public static final String DOMAIN_OBJECT_ID = "contractOrder";
	private boolean add;
	private String keyword;
	private List<ContractChangeContractAmount> contractAmountList;
	private String delAmountIds;
	private List<ContractChangeGoods> goodsList;
	private String delGoodsIds;
	private List<ContractChangeCollectionTerms> collectionTermsList;
	private String delTermsIds;
	private boolean isNew;
	private String enName;
	public static final int DRAFT = 0;
	public static final int PROCESSING = 10;
	public static final int COMPLETED = 20;
	public static final int REJECTED = 30;

	
	private String prjDeptId; // 项目所属部门

	public ContractOrder() {
	}

	public ContractOrder(String changeId) {
		super(changeId);
	}

	@Transient
	public String getEnName() {
		return "采购合同变更信息上线";
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	@Transient
	public boolean getIsNew() {
		return this.isNew;
	}

	public void setIsNew(boolean isNew) {
		this.isNew = isNew;
	}

	@Transient
	public boolean getAdd() {
		return this.add;
	}

	public void setAdd(boolean add) {
		this.add = add;
	}

	@Transient
	public String getKeyword() {
		return this.keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Transient
	public List<ContractChangeContractAmount> getContractAmountList() {
		return this.contractAmountList;
	}

	public void setContractAmountList(List<ContractChangeContractAmount> contractAmountList) {
		this.contractAmountList = contractAmountList;
	}

	@Transient
	public String getDelAmountIds() {
		return this.delAmountIds;
	}

	public void setDelAmountIds(String delAmountIds) {
		this.delAmountIds = delAmountIds;
	}

	@Transient
	public List<ContractChangeGoods> getGoodsList() {
		return this.goodsList;
	}

	public void setGoodsList(List<ContractChangeGoods> goodsList) {
		this.goodsList = goodsList;
	}

	@Transient
	public String getDelGoodsIds() {
		return this.delGoodsIds;
	}

	public void setDelGoodsIds(String delGoodsIds) {
		this.delGoodsIds = delGoodsIds;
	}

	@Transient
	public List<ContractChangeCollectionTerms> getCollectionTermsList() {
		return this.collectionTermsList;
	}

	public void setCollectionTermsList(List<ContractChangeCollectionTerms> collectionTermsList) {
		this.collectionTermsList = collectionTermsList;
	}

	@Transient
	public String getDelTermsIds() {
		return this.delTermsIds;
	}

	public void setDelTermsIds(String delTermsIds) {
		this.delTermsIds = delTermsIds;
	}

	public static Map<Integer, String> getSwfStatusMap() {
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(DRAFT, "草稿");
		map.put(PROCESSING, "审核中");
		map.put(COMPLETED, "审批完成");
		map.put(REJECTED, "驳回");
		return map;
	}

	@Transient
	public String getSwfStatusDesc() {
		if (getSwfStatus() != null) {
			return ((String) getSwfStatusMap().get(getSwfStatus()));
		}
		return "";
	}

	@Transient
	public int getDbYear() {
		return 0;
	}

	@Transient
	public String getModuleId() {
		return "CONORDER";
	}

	@Transient
	public String getDomainObjectId() {
		return "contractOrder";
	}

	@Transient
	public String getCompanyNo() {
		return CommonUtil.trim(getDeptId());
	}

	@Transient
	public String getEntityId() {
		return getChId();
	}

	@Transient
	public String getModuleBusiType() {
		return "";
	}

	@Transient
	public String getEntityName() {
		return super.getClass().getName();
	}

	public static final class ChType {
		public static final int CONCH = 1;
		public static final int CONADJ = 2;

		public static Map<Integer, String> getMap() {
			Map<Integer, String> map = new LinkedHashMap<Integer, String>();
			map.put(CONCH, "合同变更");
			map.put(CONADJ, "合同数据调整");
			return map;
		}
	}
	
	@Transient
	public String getPrjDeptId() {
		return this.prjDeptId;
	}
	public void setPrjDeptId(String prjDeptId) {
		this.prjDeptId = prjDeptId;
	}
}