package com.supporter.prj.pm.fund_appropriation.entity;

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
import com.supporter.prj.pm.fund_appropriation.entity.base.BaseFundAppropriationSwf;
import com.supporter.util.CommonUtil;

/**   
 * @Title: 资金拨付流程
 * @Description: pm_fund_appropriate_swf.
 * @author Administrator
 * @date 2018-07-04 18:08:56
 * @version V1.0   
 *
 */
@Entity
@Table(name = "pm_fund_appropriate_swf", schema = "")
public class FundAppropriationSwf extends BaseFundAppropriationSwf implements PmSwfBusiEntity {

	private static final long serialVersionUID = 1L;

	public static final String APP_NAME = "FUNDAPPROP"; //应用编号
	public static final String DOMAIN_OBJECT_ID = "FundApproSwf"; //业务对象编号
	/**
	 *搜索关键词.
	 */
	@Transient
	private String keyword;
	
	/**
	 * 无参构造函数.
	 */
	public FundAppropriationSwf() {
		super();
	}
	
	/**
	 * 构造函数.
	 * @param fundId
	 */
	public FundAppropriationSwf(String fundId) {
		super(fundId);
	}
	
	@Transient
	public String getOaExamStatusDesc() {
		return OAExamStatus.getMap().get(getOaExamStatus());
	}
	
	@Transient
	private FundAppropriation fund;
	public FundAppropriation getFund() {
		return this.fund;
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
	 * 设置资金拨付对象
	 * @param fund 资金拨付
	 */
	public void setFund(FundAppropriation fund) {
		this.fund = fund;
		if (fund != null) {
			this.setFundId(fund.getFundId());
			this.setFundNo(fund.getFundNo());
			this.setBudgetPeriod(fund.getBudgetPeriod());
			this.setPrjId(fund.getPrjId());
			this.setPrjName(fund.getPrjName());
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
		if (!(other instanceof FundAppropriationSwf)) {
			return false;
		}
		FundAppropriationSwf castOther = (FundAppropriationSwf) other;
		return StringUtils.equals(this.getFundId(), castOther.getFundId());
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getFundId()).toHashCode();
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
		String procTitle = super.getPrjName() + "（" + this.getFundNo() + "） 资金拨付审批";
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
		return this.getFundId();
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
		return "fundId";
	}

	@Transient
	@Override
	public String getKeyValues() {
		return this.getFundId();
	}

	@Transient
	@Override
	public double getAmount() {
		if (this.fund != null) {
			return this.fund.getAppropriationAmount();
		} else {
			return 0;
		}
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

	@Transient
	/** 预算周期 */
	private int budgetPeriod;

	public int getBudgetPeriod() {
		return budgetPeriod;
	}

	public void setBudgetPeriod(int budgetPeriod) {
		this.budgetPeriod = budgetPeriod;
	}


}
