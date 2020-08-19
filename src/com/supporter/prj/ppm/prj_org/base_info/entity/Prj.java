package com.supporter.prj.ppm.prj_org.base_info.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.ppm.prj_org.base_info.constant.BaseInfoConstant;
import com.supporter.prj.ppm.prj_org.base_info.entity.base.BasePrj;
import com.supporter.prj.ppm.prj_org.dev_org.entity.PrjCoreEquipmentEntity;
import com.supporter.prj.ppm.prj_org.dev_org.entity.PrjDeOrgAgent;
import com.supporter.prj.ppm.prj_org.dev_org.entity.PrjDeOrgCombo;
import com.supporter.prj.ppm.prj_org.dev_org.entity.PrjDeOrgCoop;
import com.supporter.prj.ppm.prj_org.dev_org.entity.PrjDeOrgEmp;
import com.supporter.util.CodeTable;

/**
 * PpmPrj entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "PPM_PRJ", schema = "")
public class Prj extends BasePrj {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数
	 */
	public Prj() {
		super();
	}

	private PrjPfi prjPfiData;// 融资信息
	// private List<PrjDeOrgDept> deOrgDeptListData;//组织部门
	// private List<PrjDeOrgEmp> deOrgEmpListData;//组织人员
	private List<PrjDeOrgAgent> deOrgAgentListData;// 代理商
	private List<PrjDeOrgCoop> deOrgCoopListData;// 合作方
	private List<PrjCoreEquipmentEntity> equipmentListData;// 核心设备供应商列表信息
	private List<PrjDeOrgCombo> deOrgComboDataList;// 联合体
	// private PrjDeOrgCombo deOrgComboData;
	private PrjDeOrgEmp dePrgEmpData;
	private PrjCoreEquipmentEntity equipmentData;// 核心设备供应商实体类

	
	private String prjActiveStatusDesc;// 项目状态中文显示名称

	/**
	 * @return the prjActiveStatusDesc
	 */
	@Transient
	public String getPrjActiveStatusDesc() {
		return BaseInfoConstant.getActiveDesc().get(this.getPrjActiveStatus());
	}

	
	public void setPrjActiveStatusDesc(String prjActiveStatusDesc) {
		this.prjActiveStatusDesc = prjActiveStatusDesc;
	}

	private boolean isNew = false;

	@Transient
	public boolean getIsNew() {
		return isNew;
	}

	public void setIsNew(boolean isNew) {
		this.isNew = isNew;
	}

	// 状态类
	public static final class StatusCodeTable {
		public static final int FOCUS = 0;
		public static final int START = 1;
		public static final int PREPARE = 2;
		public static final int IMPORTANT = 3;

		// 状态
		public static CodeTable getCodeTable() {
			CodeTable ct = new CodeTable();
			ct.insertItem(FOCUS, "关注");
			ct.insertItem(START, "启动项目跟踪");
			ct.insertItem(PREPARE, "准备投标");
			ct.insertItem(IMPORTANT, "重点跟踪");
			return ct;
		}
	}

	@Transient
	public String getPrjDewStatusDesc() {
		if (getPrjDewStatus() != null) {
			return StatusCodeTable.getCodeTable().getDisplay(getPrjDewStatus());
		} else {
			return null;
		}
	}

	private List<PrjSof> sofListData;

	@Transient
	public List<PrjSof> getSofListData() {
		return sofListData;
	}

	public void setSofListData(List<PrjSof> sofListData) {
		this.sofListData = sofListData;
	}

	@Transient
	public PrjPfi getPrjPfiData() {
		return prjPfiData;
	}

	public void setPrjPfiData(PrjPfi prjPfiData) {
		this.prjPfiData = prjPfiData;
	}

	/**
	 * @return the equipmentData
	 */
	@Transient
	public PrjCoreEquipmentEntity getEquipmentData() {
		return equipmentData;
	}

	/**
	 * @param equipmentData the equipmentData to set
	 */
	public void setEquipmentData(PrjCoreEquipmentEntity equipmentData) {
		this.equipmentData = equipmentData;
	}

//
//		@Transient
//		public List<PrjDeOrgDept> getDeOrgDeptListData() {
//			return deOrgDeptListData;
//		}
//
//		public void setDeOrgDeptListData(List<PrjDeOrgDept> deOrgDeptListData) {
//			this.deOrgDeptListData = deOrgDeptListData;
//		}
//		@Transient
//		public List<PrjDeOrgEmp> getDeOrgEmpListData() {
//			return deOrgEmpListData;
//		}
//
//		public void setDeOrgEmpListData(List<PrjDeOrgEmp> deOrgEmpListData) {
//			this.deOrgEmpListData = deOrgEmpListData;
//		}
	@Transient
	public List<PrjDeOrgAgent> getDeOrgAgentListData() {
		return deOrgAgentListData;
	}

	public void setDeOrgAgentListData(List<PrjDeOrgAgent> deOrgAgentListData) {
		this.deOrgAgentListData = deOrgAgentListData;
	}

	@Transient
	public List<PrjDeOrgCoop> getDeOrgCoopListData() {
		return deOrgCoopListData;
	}

	public void setDeOrgCoopListData(List<PrjDeOrgCoop> deOrgCoopListData) {
		this.deOrgCoopListData = deOrgCoopListData;
	}

//		@Transient
//		public PrjDeOrgCombo getDeOrgComboData() {
//			return deOrgComboData;
//		}
//
//		public void setDeOrgComboData(PrjDeOrgCombo deOrgComboData) {
//			this.deOrgComboData = deOrgComboData;
//		}

	/**
	 * @return the deOrgComboDataList
	 */
	@Transient
	public List<PrjDeOrgCombo> getDeOrgComboDataList() {
		return deOrgComboDataList;
	}

	/**
	 * @param deOrgComboDataList the deOrgComboDataList to set
	 */
	public void setDeOrgComboDataList(List<PrjDeOrgCombo> deOrgComboDataList) {
		this.deOrgComboDataList = deOrgComboDataList;
	}

	@Transient
	public PrjDeOrgEmp getDePrgEmpData() {
		return dePrgEmpData;
	}

	public void setDePrgEmpData(PrjDeOrgEmp dePrgEmpData) {
		this.dePrgEmpData = dePrgEmpData;
	}

	/**
	 * @return the equipmentListData
	 */
	@Transient
	public List<PrjCoreEquipmentEntity> getEquipmentListData() {
		return equipmentListData;
	}

	/**
	 * @param equipmentListData the equipmentListData to set
	 */
	public void setEquipmentListData(List<PrjCoreEquipmentEntity> equipmentListData) {
		this.equipmentListData = equipmentListData;
	}

	private String countryId;// 国家ID

	private String countryEName;// 国家名称-YUEYUN-2019/09/25

	private String countryCName;

	private String provinceId;// 省份或州ID

	private String provinceEName;// 省份或州中文名称-YUEYUN-2019/09/25

	private String provinceCName;

	private String addrC;// 地址中文

	private String addrE;// 地址英文

	/**
	 * @return the countryId
	 */
	@Transient
	public String getCountryId() {
		return countryId;
	}

	/**
	 * @param countryId the countryId to set
	 */
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	/**
	 * @return the provinceId
	 */
	@Transient
	public String getProvinceId() {
		return provinceId;
	}

	/**
	 * @param provinceId the provinceId to set
	 */
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	/**
	 * @return the addrC
	 */
	@Transient
	public String getAddrC() {
		return addrC;
	}

	/**
	 * @param addrC the addrC to set
	 */
	public void setAddrC(String addrC) {
		this.addrC = addrC;
	}

	/**
	 * @return the addrE
	 */
	@Transient
	public String getAddrE() {
		return addrE;
	}

	/**
	 * @param addrE the addrE to set
	 */
	public void setAddrE(String addrE) {
		this.addrE = addrE;
	}

	/**
	 * @return the countryEName
	 */
	@Transient
	public String getCountryEName() {
		return countryEName;
	}

	/**
	 * @param countryEName the countryEName to set
	 */
	public void setCountryEName(String countryEName) {
		this.countryEName = countryEName;
	}

	/**
	 * @return the countryCName
	 */
	@Transient
	public String getCountryCName() {
		return countryCName;
	}

	/**
	 * @param countryCName the countryCName to set
	 */
	public void setCountryCName(String countryCName) {
		this.countryCName = countryCName;
	}

	/**
	 * @return the provinceEName
	 */
	@Transient
	public String getProvinceEName() {
		return provinceEName;
	}

	/**
	 * @param provinceEName the provinceEName to set
	 */
	public void setProvinceEName(String provinceEName) {
		this.provinceEName = provinceEName;
	}

	/**
	 * @return the provinceCName
	 */
	@Transient
	public String getProvinceCName() {
		return provinceCName;
	}

	/**
	 * @param provinceCName the provinceCName to set
	 */
	public void setProvinceCName(String provinceCName) {
		this.provinceCName = provinceCName;
	}

}
