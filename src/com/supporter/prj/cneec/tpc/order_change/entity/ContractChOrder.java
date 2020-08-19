package com.supporter.prj.cneec.tpc.order_change.entity;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.tpc.order_change.entity.base.BaseContractChOrder;
import com.supporter.util.CommonUtil;

/**
 * @Title: ContractChange
 * @Description: 采购合同变更实体类主表
 */
@Entity
@Table(name = "TPC_CONTRACT_CH_ORDER", schema = "")
public class ContractChOrder extends BaseContractChOrder implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	public static final String MODULE_ID = "CONORD";
	public static final String BUSI_TYPE = "ordFile";
	public static final String DOMAIN_OBJECT_ID = "contractChOrder";
	
	
	private String prjDeptId; //项目所属部门

	// Constructors

	/** default constructor */
	public ContractChOrder() {
		super();
	}

	/** minimal constructor */
	public ContractChOrder(String changeId) {
		super(changeId);
	}


	private boolean add;// 是否新增
	private String keyword;// 搜索关键字
	private List<OrderChangeContractAmount> contractAmountList;
	private String delAmountIds;
	private List<OrderChangeGoods> goodsList;
	private String delGoodsIds;
	private List<OrderChangeCollectionTerms> collectionTermsList;
	private String delTermsIds;
	private boolean isNew;
	
	private String enName;// 搜索关键字
	
	@Transient
	public String getEnName() {
		return "销售合同变更信息上线";
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
	public List<OrderChangeContractAmount> getContractAmountList() {
		return contractAmountList;
	}

	public void setContractAmountList(List<OrderChangeContractAmount> contractAmountList) {
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
	public List<OrderChangeGoods> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List<OrderChangeGoods> goodsList) {
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
	public List<OrderChangeCollectionTerms> getCollectionTermsList() {
		return collectionTermsList;
	}

	public void setCollectionTermsList(List<OrderChangeCollectionTerms> collectionTermsList) {
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
	public static final int SEALOK = 40;// 用印审批完成
	public static final int CONTRACTOK = 50;// 合同审批完成
	/**
	 * 获取状态码表.
	 * 
	 * @return
	 */
	public static Map<Integer, String> getSwfStatusMap() {
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(DRAFT, "草稿");
		map.put(PROCESSING, "审核中");
		map.put(COMPLETED, "变更单审批完成");
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
