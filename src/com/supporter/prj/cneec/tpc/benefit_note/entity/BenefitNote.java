package com.supporter.prj.cneec.tpc.benefit_note.entity;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.tpc.benefit_note.entity.base.BaseBenefitNote;
import com.supporter.prj.cneec.tpc.benefit_note.util.BenefitNoteUtil;
import com.supporter.util.CommonUtil;

/**
 * @Title: BenefitNote
 * @Description: 贸易项目效益测算表.
 * @author: yanweichao
 * @date: 2018-06-01
 * @version: V1.0
 */
@Entity
@Table(name = "TPC_BENEFIT_NOTE", schema = "")
public class BenefitNote extends BaseBenefitNote implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	public static final String MODULE_ID = "TPCBENNOTE";
	public static final String DOMAIN_OBJECT_ID = "BenefitNote";
	
	
	private String prjDeptId; //项目所属部门

	// Constructors

	/** default constructor */
	public BenefitNote() {
		super();
	}

	/** minimal constructor */
	public BenefitNote(String noteId) {
		super(noteId);
	}

	private boolean add;// 是否新增
	private String keyword;// 搜索关键字
	private List<BenefitNoteCurrency> currencyList;// 币别集合
	private String delCurrencyIds;// 删除币别ID

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
	public List<BenefitNoteCurrency> getCurrencyList() {
		return this.currencyList;
	}

	public void setCurrencyList(List<BenefitNoteCurrency> currencyList) {
		this.currencyList = currencyList;
	}

	@Transient
	public String getDelCurrencyIds() {
		return this.delCurrencyIds;
	}

	public void setDelCurrencyIds(String delCurrencyIds) {
		this.delCurrencyIds = delCurrencyIds;
	}

	public static final int DRAFT = 0; // 草稿
	public static final int CONFIRM = 10;// 待确认/待审核
	public static final int CONFIRMING = 20;// 确认中/审核中
	public static final int CONFIRMED = 30;// 已确认/审核完成

	/**
	 * 获取状态码表.
	 * @return
	 */
	public static Map<Integer, String> getSwfStatusMap(Boolean hasProc) {
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(DRAFT, "草稿");
		if (hasProc == null) {
			map.put(CONFIRM, "待确认/待审核");
			map.put(CONFIRMING, "确认中/审核中");
			map.put(CONFIRMED, "已确认/审核完成");
		} else if (hasProc) {
			map.put(CONFIRM, "待审核");
			map.put(CONFIRMING, "审核中");
			map.put(CONFIRMED, "审核完成");
		} else {
			map.put(CONFIRM, "待确认");
			map.put(CONFIRMING, "确认中");
			map.put(CONFIRMED, "已确认");
		}
		return map;
	}

	// 状态
	@Transient
	public String getSwfStatusDesc() {
		if (this.getSwfStatus() != null) {
			return getSwfStatusMap(super.isHasSwfProc()).get(super.getSwfStatus());
		}
		return "";
	}

	// 版本
	@Transient
	public String getVersionsTypeDesc() {
		if (this.getVersionsType() != null) {
			return BenefitNoteUtil.getVersionsTypeMap().get(this.getVersionsType());
		}
		return "";
	}

	// 编号
	@Transient
	public String getBenefitNoDesc() {
		if (this.getBenefitNo() != null) {
			return this.getBenefitNo() + "-" + this.getVersions();
		}
		return "";
	}

	/** 声明流程用到的参数  **/

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
		procTitle = CommonUtil.trim(this.getProjectName()) + "的" + CommonUtil.trim(this.getContractName()) + "（" + this.getDeptName() + "）";
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
		return DOMAIN_OBJECT_ID;
	}

	@Transient
	public String getCompanyNo() {
		return CommonUtil.trim(this.getDeptId());
	}

	@Transient
	public String getEntityId() {
		return this.getNoteId();
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
