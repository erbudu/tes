package com.supporter.prj.pm.enginee_negotiate.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.com_codetable.entity.IComCodeTableItem;
import com.supporter.prj.pm.constant.PmSwfBusiEntity;
import com.supporter.prj.pm.enginee_negotiate.entity.base.BaseEngineeVisaSwf;
import com.supporter.util.CommonUtil;

/**   
 * @Title: 付款申请流程
 * @Description: pm_payment_onsite_swf.
 * @author Administrator
 * @date 2018-07-04 18:08:56
 * @version V1.0   
 *
 */
@Entity
@Table(name = "PM_ENGINEE_VISA_SWF", schema = "")
public class EngineeVisaSwf extends BaseEngineeVisaSwf implements PmSwfBusiEntity {

	private static final long serialVersionUID = 1L;

	public static final String APP_NAME = "NEGOTIATE"; //应用编号
	public static final String DOMAIN_OBJECT_ID = "EngineeVisaSwf"; //业务对象编号
	/**
	 *搜索关键词.
	 */
	@Transient
	private String keyword;
	
	/**
	 * 无参构造函数.
	 */
	public EngineeVisaSwf() {
		super();
	}
	
	/**
	 * 构造函数.
	 * @param id
	 */
	public EngineeVisaSwf(String id) {
		super(id);
	}
	
	@Transient
	public String getOaExamStatusDesc() {
		return OAExamStatus.getMap().get(getOaExamStatus());
	}
	
	@Transient
	private EngineeVisa engineeVisa;
	public EngineeVisa getEngineeVisa() {
		return this.engineeVisa;
	}
	/**
	 * 设置签证对象
	 * @param engineeVisa 签证
	 */
	public void setEngineeVisa(EngineeVisa engineeVisa) {
		this.engineeVisa = engineeVisa;
		if (engineeVisa != null) {
			this.setApplyNo(engineeVisa.getApplyNo());
			this.setPrjId(engineeVisa.getPrjId());
			this.setPrjName(engineeVisa.getPrjName());
			this.setVisaId(engineeVisa.getVisaId());
		}
	}
	
	//获取项目代码
	@Transient
	public String getPrjCode() {
		return EIPService.getComCodeTableService().getCodeTable("PROJECT_CODE").getDisplay(this.getPrjId());
	}
	
	/**
	 * 项目经理是否在国内
	 * @return boolean
	 */
	@Transient
	public boolean isInHome() {
		IComCodeTableItem item = EIPService.getComCodeTableService().getCodetableItem(this.getPrjId());
		if (item == null) {
			return true;
		} else {
			return CommonUtil.parseInt(item.getExtField2(), 1) == 1;
		}
		
	}

	/**
	 * 合同名称
	 * @return String
	 */
	@Transient
	public String getContractName() {
		if (this.engineeVisa == null) {
			return null;
		} else {
			return engineeVisa.getContractName();
		}
	}

	/**
	 * 申请名称
	 * @return String
	 */
	@Transient
	public String getApplyName() {
		if (engineeVisa == null) {
			return "";
		} else {
			return engineeVisa.getApplyName();
		}
	}
	
	@Transient
	private String prjManagerId;
	public String getPrjManagerId() {
		return this.prjManagerId;
	}
	public void setPrjManagerId(String prjManagerId) {
		this.prjManagerId = prjManagerId;
	}
	@Transient
	private String prjManagerName;
	public String getPrjManagerName() {
		return this.prjManagerName;
	}
	public void setPrjManagerName(String prjManagerName) {
		this.prjManagerName = prjManagerName;
	}
	@Transient
	private String prjDeptId;
	public String getPrjDeptId() {
		return this.prjDeptId;
	}
	public void setPrjDeptId(String prjDeptId) {
		this.prjDeptId = prjDeptId;
	}
	
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof EngineeVisaSwf)) {
			return false;
		}
		EngineeVisaSwf castOther = (EngineeVisaSwf) other;
		return StringUtils.equals(this.getVisaId(), castOther.getVisaId());
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getVisaId()).toHashCode();
	}
	
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Transient
	@Override
	public String getProcTitle() {
		String procTitle = super.getPrjName() + "的" + StringUtils.trimToEmpty(this.getContractName()) + "（" + this.getApplyNo() + "）" + this.getApplyName() + " 签证审批";
		return procTitle;
	}

	@Transient
	@Override
	public int getDbYear() {
		return 0;
	}
	
	@Transient
	@Override
	public String getDomainObjectId() {	
		return DOMAIN_OBJECT_ID;
	}
	//获取主键ID，用于公共流程页面的传参
	@Transient
	@Override
	public String getEntityId() {
		return this.getVisaId();
	}
	
	@Transient
	@Override
	public String getEntityName() {
		return this.getClass().getName();
	}
	
	@Transient
	@Override
	public String getModuleId() {
		return APP_NAME;
	}
	
	@Transient
	@Override
	public String getModuleBusiType() {
		return "";
	}
	
	@Transient
	@Override
	public String getCompanyNo() {
		return "";
	}

	@Transient
	@Override
	public String getKeyProps() {
		return "visaId";
	}

	@Transient
	@Override
	public String getKeyValues() {
		return this.getVisaId();
	}

	@Transient
	@Override
	public double getAmount() {
		return 0;
	}

	@Transient
	@Override
	public String getDeptId() {
		return null;
	}

	@Transient
	@Override
	public String getOperatorId() {
		return null;
	}

	@Transient
	@Override
	public String getProjectId() {
		return this.getPrjId();
	}
	
	/**
	 * OA审批状态
	 * @author Administrator
	 *
	 */
	public static final class OAExamStatus {
		public static final int DRAFT = 0; // 草稿
		public static final int EXAM = 1; // 审批中
		public static final int COMPLETE  = 2; // 审批完成
		public static final int FAIL  = 3; // 驳回给现场
		
		/**
		 * 获取采购类型码表.
		 * @return Map<Integer, String>
		 */
		public static Map<Integer, String> getMap() {
			Map<Integer, String> map = new LinkedHashMap<Integer, String>();
			map.put(DRAFT, "草稿");
			map.put(EXAM, "审批中");
			map.put(COMPLETE, "审批完成");
			map.put(FAIL, "驳回");
			return map;
		}
		
		private OAExamStatus() {

		}
	}
	
	/**
	 * OA审批状态
	 * @author Administrator
	 *
	 */
	public static final class OAExamResult {
		public static final int FAILED = 0; // 未通过
		public static final int SUCCESS = 1; // 通过
		
		
		/**
		 * 获取审批状态码表.
		 * @return Map<Integer, String>
		 */
		public static Map<Integer, String> getMap() {
			Map<Integer, String> map = new LinkedHashMap<Integer, String>();
			map.put(FAILED, "未通过");
			map.put(SUCCESS, "通过");
			return map;
		}
		
		private OAExamResult() {

		}
	}
}
