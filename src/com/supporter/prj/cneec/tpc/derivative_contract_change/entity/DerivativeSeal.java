package com.supporter.prj.cneec.tpc.derivative_contract_change.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.tpc.derivative_contract_change.entity.base.BaseDerivativeSeal;

/**
 * @Title: ContractSeal
 * @Description: 协议用印实体类
 * @version: V1.0
 */
@Entity
@Table(name = "TPC_DERIVATIVE_SEAL", schema = "")
public class DerivativeSeal extends BaseDerivativeSeal implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	public static final String MODULE_ID = "DERSEAL";
	public static final String DOMAIN_OBJECT_ID = "deriveSeal";
	public static final int DB_YEAR = 0;
	
	
	private String prjDeptId; //项目所属部门

	// Constructors

	/** default constructor */
	public DerivativeSeal() {
		super();
	}

	/** minimal constructor */
	public DerivativeSeal(String sealId) {
		super(sealId);
	}

	private boolean add;// 是否新增
	private String keyword;// 搜索关键字
	private boolean isNew;
	private String enName;
	@Transient
	public String getEnName() {
		return "衍生合同变更协议用印";
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

	/** 声明流程用到的参数  **/

	@Transient
	public String getModuleId() {
		return MODULE_ID;
	}

	@Transient
	public int getDbYear() {
		return DB_YEAR;
	}

	@Transient
	public String getDomainObjectId() {
		return DOMAIN_OBJECT_ID;
	}
	
	// 获取主键ID，用于公共流程页面的传参
	@Transient
	public String getEntityId() {
		return this.getSealId();
	}

	@Transient
	public String getEntityName() {
		return getClass().getName();
	}

	@Transient
	public String getModuleBusiType() {
		return "";
	}

	@Transient
	public String getCompanyNo() {
		return getDeptId();
	}
	
	@Transient
	public String getPrjDeptId() {
		return this.prjDeptId;
	}
	public void setPrjDeptId(String prjDeptId) {
		this.prjDeptId = prjDeptId;
	}
}
