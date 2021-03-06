package com.supporter.prj.cneec.tpc.contract_online.entity;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.tpc.contract_online.entity.base.BaseContractOnline;
import com.supporter.prj.cneec.tpc.contract_online_prepare.entity.ContractOnlinePrepare;
import com.supporter.prj.cneec.tpc.contract_online_prepare.service.ContractOnlinePrepareService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.util.CommonUtil;

/**
 * @Title: ContractOnline
 * @Description: 采购合同上线实体类
 * @author: yanweichao
 * @date: 2017-11-06
 * @version: V1.0
 */
@Entity
@Table(name = "TPC_CONTRACT_ONLINE", schema = "")
public class ContractOnline extends BaseContractOnline implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	public static final String MODULE_ID = "TPCCONONLINE";
	public static final String BUSI_TYPE = "TPCCONTRACTONLINE";
	
	
	private String prjDeptId; //项目所属部门

	// Constructors

	/** default constructor */
	public ContractOnline() {
		super();
	}

	/** minimal constructor */
	public ContractOnline(String contractId) {
		super(contractId);
	}

	private boolean add;// 是否新增
	private String keyword;// 搜索关键字
	private List<ContractContractAmount> contractAmountList;
	private String delAmountIds;
	private List<ContractGoods> goodsList;
	private String delGoodsIds;
	private List<ContractCollectionTerms> collectionTermsList;
	private String delTermsIds;

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
	public List<ContractContractAmount> getContractAmountList() {
		return contractAmountList;
	}

	public void setContractAmountList(List<ContractContractAmount> contractAmountList) {
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
	public List<ContractGoods> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List<ContractGoods> goodsList) {
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
	public List<ContractCollectionTerms> getCollectionTermsList() {
		return collectionTermsList;
	}

	public void setCollectionTermsList(List<ContractCollectionTerms> collectionTermsList) {
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

	/**
	 * 获取状态码表.
	 * @return
	 */
	public static Map<Integer, String> getSwfStatusMap() {
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(DRAFT, "草稿");
		map.put(PROCESSING, "审核中");
		map.put(COMPLETED, "审批完成");
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

	/**
	 * 是否已用印（未用印需要部门经理确认）
	 * @return
	 */
	@Transient
	public boolean isUseSeal() {
		if (CommonUtil.isNullOrEmpty(super.getIsUseSeal())) {
			String inforId = CommonUtil.trim(this.getInforId());
			if (inforId.length() > 0) {
				ContractOnlinePrepareService service = SpringContextHolder.getBean(ContractOnlinePrepareService.class);
				ContractOnlinePrepare prepare = service.findUniqueResult("inforId", inforId);
				if (prepare != null && prepare.isUseSeal()) {
					return true;
				}
			}
		} else if (Boolean.valueOf(super.getIsUseSeal())) {
			return true;
		}
		return false;
	}

	/** 声明流程用到的参数  **/

	private String procId;// 流程ID
	private String procTitle;// 流程ID

	@Transient
	public String getProcTitle() {
		procTitle = CommonUtil.trim(this.getProjectName()) + "（" + this.getContractNo() + "）" + this.getContractName() + "（" + this.getDeptName() + "）";
		return procTitle;
	}

	@Column(name = "PROC_ID", length = 32)
	public String getProcId() {
		return procId;
	}

	public void setProcId(String procId) {
		this.procId = procId;
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
		return this.getClass().getSimpleName();
	}

	@Transient
	public String getCompanyNo() {
		return CommonUtil.trim(this.getDeptId());
	}

	@Transient
	public String getEntityId() {
		return this.getContractId();
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
