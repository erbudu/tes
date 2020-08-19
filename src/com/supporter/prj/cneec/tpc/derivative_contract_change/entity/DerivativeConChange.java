package com.supporter.prj.cneec.tpc.derivative_contract_change.entity;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.tpc.contract_change.entity.ContractChangeGoods;
import com.supporter.prj.cneec.tpc.derivative_contract_change.entity.base.BaseDerivativeConChange;
import com.supporter.util.CommonUtil;

/**
 * @Title: ContractChange
 * @Description: 采购合同变更实体类主表
 */
@Entity
@Table(name = "TPC_DERIVATIVE_CON_CHANGE", schema = "")
public class DerivativeConChange extends BaseDerivativeConChange implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	public static final String MODULE_ID = "DERCON";
	public static final String BUSI_TYPE = "derFile";
//	public static final String BUSI_TYPE = "TPCCONTRACTCHANGE_SUPPORTING";
	public static final String DOMAIN_OBJECT_ID = "deriveConChange";
	
	
	private String prjDeptId; //项目所属部门

	// Constructors

	/** default constructor */
	public DerivativeConChange() {
		super();
	}

	/** minimal constructor */
	public DerivativeConChange(String changeId) {
		super(changeId);
	}


	private boolean add;// 是否新增
	private String keyword;// 搜索关键字
	private List<DerivativeContractChangeAmount> contractAmountList;
	private String delAmountIds;
	private List<ContractChangeGoods> goodsList;
	private String delGoodsIds;
	private List<DerivativeContractChangeTerms> collectionTermsList;
	private String delTermsIds;
	private boolean isNew;
	
	private String enName;// 搜索关键字
	@Transient
	public String getEnName() {
		return "衍生合同";
	}
	public void setEnName(String enName) {
		this.enName = enName;
	}
	
	@Transient
	public boolean getIsNew() {
		return isNew;
	}

	public void setIsNew(boolean isNew) {
		this.isNew = isNew;
	}
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
	public List<DerivativeContractChangeAmount> getContractAmountList() {
		return contractAmountList;
	}

	public void setContractAmountList(List<DerivativeContractChangeAmount> contractAmountList) {
		this.contractAmountList = contractAmountList;
	}

	@Transient
	public String getDelAmountIds() {
		return delAmountIds;
	}

	public void setDelAmountIds(String delAmountIds) {
		this.delAmountIds = delAmountIds;
	}

	@Transient
	public List<ContractChangeGoods> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List<ContractChangeGoods> goodsList) {
		this.goodsList = goodsList;
	}

	@Transient
	public String getDelGoodsIds() {
		return delGoodsIds;
	}

	public void setDelGoodsIds(String delGoodsIds) {
		this.delGoodsIds = delGoodsIds;
	}

	@Transient
	public List<DerivativeContractChangeTerms> getCollectionTermsList() {
		return collectionTermsList;
	}

	public void setCollectionTermsList(List<DerivativeContractChangeTerms> collectionTermsList) {
		this.collectionTermsList = collectionTermsList;
	}

	@Transient
	public String getDelTermsIds() {
		return delTermsIds;
	}

	public void setDelTermsIds(String delTermsIds) {
		this.delTermsIds = delTermsIds;
	}
	public static final int DRAFT = 0; // 草稿
	public static final int PROCESSING = 10; // 审核中
	public static final int COMPLETED = 20;// 审批完成
	public static final int REJECTED = 30;// 驳回

	/**
	 * 获取状态码表.
	 * 
	 * @return
	 */
	public static Map<Integer, String> getSwfStatusMap() {
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(DRAFT, "草稿");
		map.put(PROCESSING, "审核中");
		map.put(COMPLETED, "审批完成");
		map.put(REJECTED, "驳回");
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
//
//	@Transient
//	public String getHasProtocolDesc() {
//		return this.isHasProtocol() ? "是" : "否";
//	}

	/**
	 * 选择类型.
	 * 1-合同变更 2-合同数据调整
	 */
	public static final class ChType {
		public static final int CONCH = 1; // 合同变更
		public static final int CONADJ = 2; // 合同数据调整
		
		
		/**
		 * 获取状态码表.
		 * @return Map<Integer, String>
		 */
		public static Map<Integer, String> getMap() {
			Map<Integer, String> map = new LinkedHashMap<Integer, String>();
			map.put(CONCH, "合同变更");
			map.put(CONADJ, "合同数据调整");
			return map;
		}
		
		private ChType() {

		}
	}
	
	@Transient
	public int getDbYear() {
		return 0;
	}

	@Transient
	public String getModuleId() {
		return MODULE_ID;
	}

	@Transient
	public String getDomainObjectId() {
		return DOMAIN_OBJECT_ID;
	}

	@Transient
	public String getCompanyNo() {
		return CommonUtil.trim(this.getDeptId());
	}

	@Transient
	public String getEntityId() {
		return this.getChId();
	}

	@Transient
	public String getModuleBusiType() {
		return "";
	}

	@Transient
	public String getEntityName() {
		return this.getClass().getName();
	}
	
	@Transient
	public String getPrjDeptId() {
		return this.prjDeptId;
	}
	public void setPrjDeptId(String prjDeptId) {
		this.prjDeptId = prjDeptId;
	}

}
