package com.supporter.prj.pm.contract_balance.entity;

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
import com.supporter.prj.pm.contract_balance.entity.base.BaseContractBalanceConstSwf;
import com.supporter.util.CommonUtil;

/**   
 * @Title: 合同结算-施工合同 流程
 * @Description: PM_CONTRACT_BALANCE_CONST_SWF.
 * @author Administrator
 * @date 2018-07-04 18:08:56
 * @version V1.0   
 *
 */
@Entity
@Table(name = "PM_CONTRACT_BALANCE_CONST_SWF", schema = "")
public class ContractBalanceConstSwf extends BaseContractBalanceConstSwf implements PmSwfBusiEntity {

	private static final long serialVersionUID = 1L;

	public static final String APP_NAME = "CONTRBALANC"; //应用编号
	public static final String DOMAIN_OBJECT_ID = "BalanceSwf"; //业务对象编号
	/**
	 *搜索关键词.
	 */
	@Transient
	private String keyword;
	
	/**
	 * 无参构造函数.
	 */
	public ContractBalanceConstSwf() {
		super();
	}
	
	/**
	 * 构造函数.
	 * @param balanceId
	 */
	public ContractBalanceConstSwf(String balanceId) {
		super(balanceId);
	}
	
	@Transient
	public String getOaExamStatusDesc() {
		return OAExamStatus.getMap().get(getOaExamStatus());
	}
	
	@Transient
	private ContractBalanceConst balance;
	public ContractBalanceConst getBalance() {
		return this.balance;
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
	
	@Transient
	/** 预算周期 */
	private int period;

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	/**
	 * 设置合同结算对象
	 * @param balance 合同结算
	 */
	public void setBalance(ContractBalanceConst balance) {
		this.balance = balance;
		if (balance != null) {
			this.setApplicationNo(balance.getApplicationNo());
			this.setBalanceId(balance.getBalanceId());
			this.setPrjId(balance.getPrjId());
			this.setPrjName(balance.getPrjName());
			this.setPeriod(balance.getPeriod());
		}
	}

	/**
	 * 合同名称
	 * @return
	 */
	@Transient
	public String getContractName() {
		if (this.balance == null) {
			return null;
		} else {
			return balance.getContractName();
		}
	}

	/**
	 * 申请名称
	 * @return String
	 */
	@Transient
	public String getApplicationName() {
		if (balance == null) {
			return "";
		} else {
			return balance.getApplicationName();
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
		if (!(other instanceof ContractBalanceConstSwf)) {
			return false;
		}
		ContractBalanceConstSwf castOther = (ContractBalanceConstSwf) other;
		return StringUtils.equals(this.getBalanceId(), castOther.getBalanceId());
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getBalanceId()).toHashCode();
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
		String procTitle = super.getPrjName() + "的" + StringUtils.trimToEmpty(this.getContractName()) + "（" + this.getApplicationNo() + "）" + this.getApplicationName() + " 结算审批";
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
		return this.getBalanceId();
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
		return "balanceId";
	}

	@Transient
	@Override
	public String getKeyValues() {
		return this.getBalanceId();
	}

	@Transient
	@Override
	public double getAmount() {
		if (this.balance != null) {
			return this.balance.getApplicationAmount();
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

}
