package com.supporter.prj.cneec.tpc.use_seal.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.tpc.use_seal.entity.base.BaseUseSeal;
import com.supporter.prj.cneec.tpc.util.TpcConstant;
import com.supporter.util.CommonUtil;

/**
 * @Title: UseSeal
 * @Description: 用印单实体类
 * @author: yanweichao
 * @date: 2017-10-23
 * @version: V1.0
 */
@Entity
@Table(name = "TPC_USE_SEAL", schema = "")
public class UseSeal extends BaseUseSeal implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	public static final String MODULE_ID = "TPCUSESEAL";
	
	
	private String prjDeptId; //项目所属部门

	// Constructors

	/** default constructor */
	public UseSeal() {
		super();
	}

	/** minimal constructor */
	public UseSeal(String sealId) {
		super(sealId);
	}

	private boolean add;// 是否新增
	private String keyword;// 搜索关键字
	private UseSealDetail useSealDetail;

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
	public UseSealDetail getUseSealDetail() {
		return useSealDetail;
	}

	public void setUseSealDetail(UseSealDetail useSealDetail) {
		this.useSealDetail = useSealDetail;
	}

	public static final int DRAFT = 0; // 草稿
	public static final int PROCESSING = 10; // 审核中
	public static final int COMPLETED = 20;// 审批完成
	public static final int REJECTED = 30;// 驳回

	/**
	 * 获取状态码表.
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

	@Transient
	public boolean isSingle() {
		if (CommonUtil.trim(this.getRelyBy()).equals(TpcConstant.RELY_BY_PROTOCOL_REVIEW)) {
			return true;
		} else if (this.getUseSealDetail() != null) {
			return true;
		}
		return false;
	}

	/**
	 * 声明流程用到的参数
	 * 7个参数对应声明流程用到的对象WfParms中7个参数，方法名不一定与WfParms中一样
	 * procId: 流程ID
	 * dbYear：年度
	 * moduleId：应用id，系统管理>系统工具>应用管理>当前模块的应用编号
	 * domainObjectId：业务对象id，类名
	 * companyNo：公司编号，不能为null(不管是否用到)
	 * entityId：业务单id
	 * moduleBusiType：业务类型
	 * entityName：业务类名称
	 */

	private String procId;// 流程ID
	private String procTitle;// 流程标题

	@Column(name = "PROC_ID", length = 32)
	public String getProcId() {
		return procId;
	}

	public void setProcId(String procId) {
		this.procId = procId;
	}

	@Transient
	public String getProcTitle() {
		procTitle = CommonUtil.trim(this.getProjectName()) + "的";
		if (CommonUtil.trim(this.getRelyBy()).equals(TpcConstant.RELY_BY_PROTOCOL_REVIEW)) {
			procTitle += "框架协议";
		} else if (CommonUtil.trim(this.getRelyBy()).equals(TpcConstant.RELY_BY_CONTRACT_SIGN_REVIEW)) {
			procTitle += "合同";
		}
		procTitle += "用印申请（" + this.getDeptName() + "）";
		return procTitle;
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
		return CommonUtil.trim(this.getSealDeptId());
	}

	@Transient
	public String getEntityId() {
		return this.getSealId();
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
