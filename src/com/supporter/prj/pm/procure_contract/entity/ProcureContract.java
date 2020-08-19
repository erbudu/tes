package com.supporter.prj.pm.procure_contract.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.supporter.prj.pm.procure_contract.entity.base.BaseProcureContract;
import com.supporter.prj.pm.public_proc.entity.PublicProc;
import com.supporter.util.CommonUtil;

/**   
 * @Title: Entity
 * @Description: PM_PROCURE_CONTRACT.
 * @author Administrator
 * @date 2018-07-04 18:04:16
 * @version V1.0   
 *
 */
@Entity
@Table(name = "PM_PROCURE_CONTRACT", schema = "")
public class ProcureContract extends BaseProcureContract {

	private static final long serialVersionUID = 1L;
	// 顶级TOP_MODULE_ID
	public static final String TOP_MODULE_ID = "0";	
	public static final String APP_NAME = "PURCONTRACT"; //应用编号
	public static final String DOMAIN_OBJECT_ID = "ProcureContract"; //业务对象编号
	//----------------------------实体类扩展属性定义,注意属性前加@Transient注解标示非数据库字段----------------------------
	/**
	 *搜索关键词.
	 */
	@Transient
	private String keyword;
	@Transient
	private String relativeContractName;
	@Transient
	private String singeDateDesc;
	
	/**
	 * 回盘审批结果
	 */
	@Transient
	private int oaExamResult = -1; //默认为未审批 [未审批/通过/不通过]
	@Transient
	private java.lang.String oaExamOpinion;
	
	@Transient
	private double rateOne;
	@Transient
	private double rateTwo;
	@Transient
	private double rateThree;
	
	public double getRateOne() {
		return rateOne;
	}

	public void setRateOne(double rateOne) {
		this.rateOne = rateOne;
	}

	public double getRateTwo() {
		return rateTwo;
	}

	public void setRateTwo(double rateTwo) {
		this.rateTwo = rateTwo;
	}
	
	public double getRateThree() {
		return rateThree;
	}

	public void setRateThree(double rateThree) {
		this.rateThree = rateThree;
	}

	/**
	 * 无参构造函数.
	 */
	public ProcureContract() {
		super();
	}
	
	/**
	 * 构造函数.
	 * @param contractId
	 */
	public ProcureContract(String contractId) {
		super(contractId);
	} 
	
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof ProcureContract)) {
			return false;
		}
		ProcureContract castOther = (ProcureContract) other;
		return StringUtils.equals(this.getContractId(), castOther.getContractId());
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getContractId()).toHashCode();
	}
	
	//是否是新增尚未保存记录
	@Transient
	private boolean isNew = false;
	public boolean getIsNew() {
		return isNew;
	}
	public void setIsNew(boolean isNew) {
		this.isNew = isNew;
	}
	
	
	
	/**
	 *方法: 取得keyword.
	 *@return String  
	 */
	public String getKeyword() {
		return keyword;
	}
	/**
	 * 方法: 设置keyword.
	 * @param keyword 查询关键字
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	// 状态
	@Transient
	public String getStatusDesc() {
		return Status.getMap().get(this.getStatus());
	}
	
	// 是否双币别
	@Transient
	public String getIsDoubleCoinDesc() {
		return BuyType.getIsDoubleCoinMap().get(this.getIsDoubleCoin());
	}
	
	// 采购类型
	@Transient
	public String getContractTypeDesc() {
		return ContractType.getMap().get(this.getContractType());
	}
	
	// 采购性质
	@Transient
	public String getBuyTypeDesc() {
		return BuyType.getMap().get(this.getBuyType());
	}
	
	//关联合同名称
	public void setRelativeContractName(String relativeContractName) {
		this.relativeContractName = relativeContractName;
	}
	public String getRelativeContractName() {
		return this.relativeContractName;
	}
	
	/**
	 * 采购类型.
	 */
	public static final class ContractType {
		public static final int ZERO_PURCHASE = 0; // 零星采购
		public static final int CIVIL_ENGINEERING = 1; // 土建工程
		public static final int INSTALLATION_ENGINEERING = 2; // 安装工程
		public static final int JIANAN_ENGINEERING =3; // 建安工程
		public static final int SERVICE = 4; // 服务类
		public static final int BULK_MATERIAL = 5; // 设备及大宗材料
//		public static final int EQUIPMENT = 6; // 设备
		public static final int LABOUR_SERVICE = 7; // 劳务
		public static final int OTHER = 8; // 其他
		/**
		 * 获取采购类型码表.
		 * @return Map<Integer, String>
		 */
		public static Map<Integer, String> getMap() {
			Map<Integer, String> map = new LinkedHashMap<Integer, String>();
			map.put(ZERO_PURCHASE, "零星采购");
			map.put(CIVIL_ENGINEERING, "土建工程");
			map.put(INSTALLATION_ENGINEERING, "安装工程");
			map.put(JIANAN_ENGINEERING, "建安工程");
			map.put(SERVICE, "服务类");
			map.put(BULK_MATERIAL, "设备及大宗材料");
//			map.put(EQUIPMENT, "设备");
			map.put(LABOUR_SERVICE, "劳务");
			map.put(OTHER, "其他");
			return map;
		}
		
		private ContractType() {

		}
	}
	
	/**
	 * 状态.
	 */
	public static final class Status {
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
		
		private Status() {

		}
	}
	
	/**
	 * 采购性质.
	 */
	public static final class BuyType {
		public static final int FOR_SELF = 0; // 自采
		public static final int FOR_OTHER = 1; // 代采
		
		/**
		 * 获取采购类型码表.
		 * @return Map<Integer, String>
		 */
		public static Map<Integer, String> getMap() {
			Map<Integer, String> map = new LinkedHashMap<Integer, String>();
			map.put(FOR_SELF, "自采");
			map.put(FOR_OTHER, "代采");
			return map;
		}
		
		/**
		 * 是否双币别
		 * @return Map<Integer, String>
		 */
		public static Map<Integer, String> getIsDoubleCoinMap() {
			Map<Integer, String> map = new LinkedHashMap<Integer, String>();
			map.put(FOR_SELF, "否");
			map.put(FOR_OTHER, "是");
			return map;
		}
		
		private BuyType() {
		}
	}
	
	
	/**
	 * 采购级别.
	 */
	public static final class ContractLevel {
		/**
		 * 获取码表.
		 * @return Map<String, String>
		 */
		public static Map<String, String> getMap() {
			Map<String, String> map = new LinkedHashMap<String, String>();
			map.put("", "");
			map.put("一类", "一类");
			map.put("二类", "二类");
			map.put("三类", "三类");
			return map;
		}
		
		private ContractLevel() {
		}
	}
	
	public String getSingeDateDesc() {
		if (getSignDate() != null) {
			singeDateDesc = CommonUtil.format(getSignDate(), "yyyy-MM-dd");
		}
		return singeDateDesc;
	}

	public void setSingeDateDesc(String singeDateDesc) {
		
		this.singeDateDesc = singeDateDesc;
	}
	
	@Transient
	public int getDbYear() {
		return 0;
	}
	@Transient
	public String getDomainObjectId() {	
		return DOMAIN_OBJECT_ID;
	}
	//获取主键ID，用于公共流程页面的传参
	@Transient
	public String getEntityId() {
		return this.getContractId();
	}
	@Transient
	public String getEntityName() {
		return this.getClass().getName();
	}
	@Transient
	public String getModuleId() {
		return APP_NAME;
	}
	@Transient
	public String getModuleBusiType() {
		return "";
	}
	@Transient
	public String getCompanyNo() {
		return "";
	}
	
	//公共流程相关publicProc、getExamOne、getExamTwo、getExamThree、getExamFour
	@Transient
	private PublicProc publicProc;
	public void setPublicProc(PublicProc publicProc) {
		this.publicProc = publicProc;
	}

	@Transient
	public String getExamOne() {
		if (publicProc != null) {
		     return publicProc.getExamOne();
		} else {
		     return "";
		}
	}
	@Transient
	public String getExamTwo() {
		if (publicProc != null) {
		     return publicProc.getExamTwo();
		} else {
		     return "";
		}
	}
	
	@Transient
	public String getExamThree() {
		if (publicProc != null) {
		     return publicProc.getExamThree();
		} else {
		     return "";
		}
	}
	@Transient
	public String getExamFour() {
		if (publicProc != null) {
		     return publicProc.getExamFour();
		} else {
		     return "";
		}
	}
	
	public int getOaExamResult() {
		return this.oaExamResult;
	}
	public void setOaExamResult(int oaExamResult) {
		this.oaExamResult = oaExamResult;
	}
	public java.lang.String getOaExamOpinion() {
		return this.oaExamOpinion;
	}
	public void setOaExamOpinion(java.lang.String oaExamOpinion) {
		this.oaExamOpinion = oaExamOpinion;
	}
	
	@Transient
	private String curreyStr;

	public String getCurreyStr() {
		return curreyStr;
	}

	public void setCurreyStr(String curreyStr) {
		this.curreyStr = curreyStr;
	}
	
	@Transient
	private String amountStr;

	public String getAmountStr() {
		return amountStr;
	}

	public void setAmountStr(String amountStr) {
		this.amountStr = amountStr;
	}
	
}
