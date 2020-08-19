package com.supporter.prj.pm.enginee_negotiate.entity;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.supporter.prj.pm.enginee_negotiate.entity.base.BaseEngineeVisa;
import com.supporter.prj.pm.public_proc.entity.PublicProc;
import com.supporter.util.CodeTable;


/**   
 * @Title: 签证
 * @Description: PM_ENGINEE_VISA.
 * @author Administrator
 * @date 2018-07-04 18:08:56
 * @version V1.0   
 *
 */
@Entity
@Table(name = "PM_ENGINEE_VISA", schema = "")
public class EngineeVisa extends BaseEngineeVisa {

	private static final long serialVersionUID = 1L;
	// 顶级TOP_MODULE_ID
	public static final String TOP_MODULE_ID = "0";	
	public static final String APP_NAME = "NEGOTIATE"; //应用编号
	public static final String DOMAIN_OBJECT_ID = "EngineeVisa"; //业务对象编号
	//----------------------------实体类扩展属性定义,注意属性前加@Transient注解标示非数据库字段----------------------------
	/**
	 *搜索关键词.
	 */
	@Transient
	private String keyword;
	
	@Transient
	private boolean isNewVisa = false; //是否是新增尚未保存记录
	
	@Transient
	private List < EngineeVisaSite > sites; //涉及工程部位
	@Transient
	private String delSitesIds; //删除的工程部位IDS
	
	/**
	 * 回盘审批结果
	 */
	@Transient
	private int oaExamResult = -1; //默认为未审批 [未审批/通过/不通过]
	@Transient
	private java.lang.String oaExamOpinion;
	
	/**
	 * 无参构造函数.
	 */
	public EngineeVisa() {
		super();
	}
	
	/**
	 * 构造函数.
	 * @param visaId
	 */
	public EngineeVisa(String visaId) {
		super(visaId);
	} 
	
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof EngineeVisa)) {
			return false;
		}
		EngineeVisa castOther = (EngineeVisa) other;
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
	
	public boolean getIsNewVisa() {
		return isNewVisa;
	}
	public void setIsNewVisa(boolean isNewVisa) {
		this.isNewVisa = isNewVisa;
	}
	
	// 状态
	@Transient
	public String getStatusDesc() {
		return Status.getMap().get(this.getStatus());
	}
	
	public List < EngineeVisaSite > getSites() {
		return this.sites;
	}
	public void setSites(List < EngineeVisaSite > sites) {
		this.sites = sites;
	}
	
	public String getDelSitesIds() {
		return delSitesIds;
	}
	public void setDelSitesIds(String delSitesIds) {
		this.delSitesIds = delSitesIds;
	}
	
	/**
	 * 状态.
	 */
	public static final class Status {
		public static final int DRAFT = 0; // 草稿
		public static final int EXAM = 1; // 审批中
		public static final int WAIT_COMPANY_EXAM = 3; // 总部审批中
		public static final int COMPANY_EXAM_FAILED = 4; // 总部驳回
		public static final int COMPLETE  = 2; // 审批完成
		
		
		/**
		 * 获取状态码表.
		 * @return Map<Integer, String>
		 */
		public static Map<Integer, String> getMap() {
			Map<Integer, String> map = new LinkedHashMap<Integer, String>();
			map.put(DRAFT, "草稿");
			map.put(EXAM, "审批中");
			map.put(WAIT_COMPANY_EXAM, "总部审批中");
			map.put(COMPANY_EXAM_FAILED, "总部驳回");
			map.put(COMPLETE, "审批完成");
			return map;
		}
		
		private Status() {

		}
	}
	
	
	//用于时间段查询
	@Transient
	private Date startDate;
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@Transient
	private Date endDate;
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
		return this.getVisaId();
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
	
	//工程签证新建-费用申请-工程部位的树结构
	@Transient
	private String treePath;

	public String getTreePath() {
		return treePath;
	}

	public void setTreePath(String treePath) {
		this.treePath = treePath;
	}
	
	public static final class StatusCodeTable {
		public static final int SINGLE_ITEM = 0; // 单一项
		public static final int MUTI_ITEM = 1; // 单一累积项

		public static CodeTable getCodeTable() {
			CodeTable ct = new CodeTable();
			ct.insertItem(SINGLE_ITEM, "单一项");
			ct.insertItem(MUTI_ITEM, "单一累积项");
			return ct;
		}
	}

	public static final class ComCodeTable {
		/** 工程签证 */
		public static final String ENGINEE_VISA = "0";
		/** 施工签证 */
		public static final String CONSTRUCT_VISA = "1";

		/**
		 * 签证类型
		 * @return
		 */
		public static Map<String, String> getVisaTypeMap() {
			Map<String, String> map = new LinkedHashMap<String, String>();
			map.put("0", "工程签证");
			map.put("1", "施工签证");
			return map;
		}
	}

	public static final class VisaItem {
		/** 签证项_单一项 */
		public static final int SINGLE_ITEM = 0;
		/** 签证项_单一累积项 */
		public static final int MULTI_ITEM = 1;

		/**
		 * 获取签证项名称码表.
		 * @return Map<Integer, String>
		 */
		public static Map<Integer, String> getMap() {
			Map<Integer, String> map = new LinkedHashMap<Integer, String>();
			map.put(SINGLE_ITEM, "单一项");
			map.put(MULTI_ITEM, "单一累积项");
			return map;
		}
	}

	// 签证项名称
	@Transient
	public String getVisaItemDesc() {
		return VisaItem.getMap().get(getVisaItem());
	}

}
