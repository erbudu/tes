package com.supporter.prj.pm.procure_contract.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.supporter.prj.pm.constant.PmSwfBusiEntity;
import com.supporter.prj.pm.procure_contract.entity.base.BaseProcureContractSwf;

/**   
 * @Title: 采购合同流程
 * @Description: PM_PROCURE_CONTRACT_SWF.
 * @author Administrator
 * @date 2018-07-04 18:08:56
 * @version V1.0   
 *
 */
@Entity
@Table(name = "PM_PROCURE_CONTRACT_SWF", schema = "")
public class ProcureContractSwf extends BaseProcureContractSwf implements PmSwfBusiEntity {

	private static final long serialVersionUID = 1L;

	public static final String APP_NAME = "PURCONTRACT"; //应用编号
	public static final String DOMAIN_OBJECT_ID = "PurContractSwf"; //业务对象编号
	/**
	 *搜索关键词.
	 */
	@Transient
	private String keyword;
	
	/**
	 * 无参构造函数.
	 */
	public ProcureContractSwf() {
		super();
	}
	
	/**
	 * 构造函数.
	 * @param id
	 */
	public ProcureContractSwf(String id) {
		super(id);
	}
	
	@Transient
	public String getOaExamStatusDesc() {
		return OAExamStatus.getMap().get(getOaExamStatus());
	}

	@Transient
	private ProcureContract procureContract;
	

	public ProcureContract getProcureContract() {
		return procureContract;
	}

	public void setProcureContract(ProcureContract procureContract) {
		this.procureContract = procureContract;
		if (procureContract != null) {
			this.setContractId(procureContract.getContractId());
			this.setContractNo(procureContract.getContractNo());
			this.setPrjId(procureContract.getPrjId());
			this.setPrjName(procureContract.getPrjName());
		}
	}

	@Transient
	private int contractType;
	public int getContractType() {
		if (this.procureContract == null) {
			return -1;
		} else {
			return procureContract.getContractType();
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

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof ProcureContractSwf)) {
			return false;
		}
		ProcureContractSwf castOther = (ProcureContractSwf) other;
		return StringUtils.equals(this.getContractId(), castOther.getContractId());
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getContractId()).toHashCode();
	}
	
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Transient
	public String getProcTitle() {
		String procTitle = super.getPrjName() + "的（" + super.getContractNo() + "）" + super.getContractName() + "采购合同审批";
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
		return this.getContractId();
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
		return "contractId";
	}

	@Transient
	@Override
	public String getKeyValues() {
		return this.getContractId();
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
		
		
		/**
		 * 获取采购类型码表.
		 * @return Map<Integer, String>
		 */
		public static Map<Integer, String> getMap() {
			Map<Integer, String> map = new LinkedHashMap<Integer, String>();
			map.put(DRAFT, "草稿");
			map.put(EXAM, "审批中");
			map.put(COMPLETE, "审批完成");
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
