package com.supporter.prj.cneec.tpc.credit_letter_collecting.entity;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.supporter.prj.cneec.tpc.credit_letter_collecting.entity.base.BaseCreditLetterCollecting;
import com.supporter.prj.eip.busi_entity.IBusiEntity;
import com.supporter.prj.eip_service.module.entity.IModule;
import com.supporter.util.CommonUtil;

/**   
 * @Title: Entity
 * @Description: 信用证到款确认（该信用证与开出信用证模块的信用证无关）
 * @author liyinfeng
 * @date 2017-11-22 10:25:07
 * @version V1.0   
 *
 */
@Entity
@Table(name = "TPC_CREDIT_LETTER_COLLECTING", schema = "")
public class CreditLetterCollecting extends BaseCreditLetterCollecting implements IBusiEntity {

	private static final long serialVersionUID = 1L;
	public static final String MODULE_ID = "TPCCLC";
	public static final String DOMAIN_OBJECT_ID = "TPCCLC";

	public static final int DRAFT = 0;//草稿
	public static final int PROCESSING = 1;//审核中
	public static final int COMPLETE = 2;//审批完成

	
	private String collectingDateTem;
	//以下为流程中用到
	private String prjManagerId;
	private String prjIntegratedManager;
	private String procTitle;
	
	private List<CreditLetterCollectingR> creditLetterCollectingRList;
	
	private String delIds;
	
	private String lsCurrencyType;
	private int recordCount;
	private double sAll;
	private double sActAll;
	
	
	private String prjDeptId; //项目所属部门
	 
	public static Map<Integer, String> getSettlementStatusCodeTable(){
    	Map<Integer, String> map = new LinkedHashMap<Integer, String>();
    	map.put(-1, "全部");
    	map.put(DRAFT, "草稿");
    	map.put(PROCESSING, "审核中");
    	map.put(COMPLETE, "审批完成");
		return map;
	}
	
	public static Map<String, String> getCollectingClauseCodeTable(){
    	Map<String, String> map = new LinkedHashMap<String, String>();
    	map.put("1", "预付款");
    	map.put("2", "进度款");
    	map.put("3", "尾款");
		return map;
	}
	
	@Transient
	public String getSwfStatusDesc() {
		return CreditLetterSwfStatus.getValueByKey(this.getSwfStatus());
	}

	/**
	 * 无参构造函数
	 */
	public CreditLetterCollecting() {
		super();
	}

	public enum AuthManageType {
		normal(IModule.AuthManageType.NORMAL, "普通角色&权限项方式"), byAdmin(
				IModule.AuthManageType.BY_ADMIN, "管理员权限"), none(
				IModule.AuthManageType.NONE, "不控制");
		private int key;
		private String value;

		AuthManageType(int key, String value) {
			this.key = key;
			this.value = value;
		}

		public static int getKeyByValue(String value) {
			AuthManageType[] types = AuthManageType.values();
			for (AuthManageType type : types) {
				if (StringUtils.equals(value, type.getValue())) {
					return type.getKey();
				}
			}
			return 0;
		}

		public static String getValueByKey(int key) {
			AuthManageType[] types = AuthManageType.values();
			for (AuthManageType type : types) {
				if (key == type.getKey()) {
					return type.getValue();
				}
			}
			return null;
		}

		public int getKey() {
			return key;
		}

		public void setKey(int key) {
			this.key = key;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}

	/**
	 * 
	 * 日志操作类型
	 * @author gongjiwen
	 * @createDate 2017年3月7日
	 * @since TODO: 来源版本
	 * @modifier gongjiwen
	 * @modifiedDate 2017年3月7日
	 */
	public enum LogOper {
		MODULE_ADD("新建应用"), MODULE_EDIT("编辑应用"), MODULE_DEL("删除应用"), MODULE_CHANGE_DISPLAY_ORDER(
				"调整应用显示顺序");
		private String operName;

		LogOper(String operName) {
			this.operName = operName;
		}

		public String getOperName() {
			return operName;
		}

		public void setOperName(String operName) {
			this.operName = operName;
		}

	}

	/**
	 * 构造函数
	 * @param contractId
	 */
	public CreditLetterCollecting(String reportId) {
		super(reportId);
	}

	public boolean equals(Object other) {
		if (!(other instanceof CreditLetterCollecting)) {
			return false;
		}
		CreditLetterCollecting castOther = (CreditLetterCollecting) other;
		return StringUtils.equals(this.getCreditLetterCollectingId(), castOther.getCreditLetterCollectingId());
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getCreditLetterCollectingId()).toHashCode();
	}
	@Transient
	public List<CreditLetterCollectingR> getCreditLetterCollectingRList() {
		return creditLetterCollectingRList;
	}

	public void setCreditLetterCollectingRList(List<CreditLetterCollectingR> creditLetterCollectingRList) {
		this.creditLetterCollectingRList = creditLetterCollectingRList;
	}
	public enum CreditLetterSwfStatus {
		//zero(0, ""), 
		DRAFT(CreditLetterCollecting.DRAFT, "草稿"),
		PROCESSING(CreditLetterCollecting.PROCESSING, "审核中"),
		COMPLETE(CreditLetterCollecting.COMPLETE, "审批完成");
		private int key;
		private String value;

		CreditLetterSwfStatus(int key, String value) {
			this.key = key;
			this.value = value;
		}

		public static int getKeyByValue(String value) {
			CreditLetterSwfStatus[] types = CreditLetterSwfStatus.values();
			for (CreditLetterSwfStatus type : types) {
				if (StringUtils.equals(value, type.getValue())) {
					return type.getKey();
				}
			}
			return 1;
		}

		public static String getValueByKey(int key) {
			CreditLetterSwfStatus[] types = CreditLetterSwfStatus.values();
			for (CreditLetterSwfStatus type : types) {
				if (key == type.getKey()) {
					return type.getValue();
				}
			}
			return null;
		}

		public int getKey() {
			return key;
		}

		public void setKey(int key) {
			this.key = key;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}

	@Override
	@Transient
	public String getKeyProps() {
		return "report_id";
	}

	@Override
	@Transient
	public String getKeyValues() {
		return this.getCreditLetterCollectingId();
	}

	/**
	 * 当前实例是否完成了审批.
	 */
	@Transient
	public boolean getIsComplete(){
		return this.getSwfStatus() == CreditLetterCollecting.CreditLetterSwfStatus.COMPLETE.key;
	}
	
	@Transient
	public int getBudgetYear(){
		Date ld_Year = new Date();					
		String ls_Year = CommonUtil.formatDate(ld_Year,"yyyy");
		return Integer.parseInt(ls_Year);
	}
	@Transient
	public int getBudgetMonth(){
		Date ld_Year = new Date();					
		String ls_Month = CommonUtil.formatDate(ld_Year,"MM");
		return Integer.parseInt(ls_Month);
	}
	
	@Transient
	public String getPrjManagerId() {
		return prjManagerId;
	}

	public void setPrjManagerId(String prjManagerId) {
		this.prjManagerId = prjManagerId;
	}

	@Transient
	public String getPrjIntegratedManager() {
		return prjIntegratedManager;
	}

	public void setPrjIntegratedManager(String prjIntegratedManager) {
		this.prjIntegratedManager = prjIntegratedManager;
	}

	@Transient
	public String getDelIds() {
		return delIds;
	}

	public void setDelIds(String delIds) {
		this.delIds = delIds;
	}

	@Transient
	public String getLsCurrencyType() {
		return lsCurrencyType;
	}

	public void setLsCurrencyType(String lsCurrencyType) {
		this.lsCurrencyType = lsCurrencyType;
	}

	@Transient
	public int getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	@Transient
	public double getSAll() {
		return sAll;
	}

	public void setSAll(double all) {
		sAll = all;
	}

	@Transient
	public double getSActAll() {
		return sActAll;
	}

	public void setSActAll(double actAll) {
		sActAll = actAll;
	}

	@Transient
	public String getCollectingDateTem() {
		return collectingDateTem;
	}

	public void setCollectingDateTem(String collectingDateTem) {
		this.collectingDateTem = collectingDateTem;
	}


	@Transient
	public String getProcTitle() {
		procTitle = CommonUtil.trim(this.getProjectName())
				+ "（" + CommonUtil.trim(this.getContractNo()) + "）"
				+ CommonUtil.trimToLength(this.getContractName(), 23)
				+ "（" + CommonUtil.trim(this.getDeptName()) + "）";
		return procTitle;
	}

	@Transient
	public double getDbYear() {
		return 0;
	}

	@Transient
	public String getDomainObjectId() {
		return DOMAIN_OBJECT_ID;
	}

	@Transient
	public String getEntityId() {
		return this.getCreditLetterCollectingId();
	}

	@Transient
	public String getEntityName() {
		return getClass().getName();
	}

	@Transient
	public String getModuleId() {
		return MODULE_ID;
	}

	@Transient
	public String getModuleBusiType() {
		return "";
	}

	@Transient
	public String getCompanyNo() {
		return this.getDeptId();
	}
	
	@Transient
	public String getPrjDeptId() {
		return this.prjDeptId;
	}
	public void setPrjDeptId(String prjDeptId) {
		this.prjDeptId = prjDeptId;
	}

}
