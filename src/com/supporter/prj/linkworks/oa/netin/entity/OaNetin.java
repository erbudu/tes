package com.supporter.prj.linkworks.oa.netin.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;


import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.linkworks.oa.netin.entity.base.BaseOaNetin;
import com.supporter.prj.linkworks.oa.netin.service.OaNetinService;
import com.supporter.util.CodeTable;

/**
 * OaNetin entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "OA_NETIN", schema = "SUPP_APP")
public class OaNetin extends BaseOaNetin implements java.io.Serializable {
	
	// Constructors

	/** default constructor */
	public OaNetin() {
	}

	/** minimal constructor */
	public OaNetin(String netinId) {
		super(netinId);
	}

	/** full constructor */
	public OaNetin(String netinId, String createdById, String createdBy,
			String createdDate, String modifiedById, String modifiedBy,
			String modifiedDate, String deptId, String deptName,
			Integer status, String procId) {
		super(netinId, createdById, createdBy, createdDate, modifiedById,
				modifiedBy, modifiedDate, deptId, deptName, status, procId);
	}
	
	public static final int DRAFT = 0;// 草稿
	public static final int PROCESSING = 1;// 审批中
	public static final int COMPLETED = 2;// 完成
	public static final String DRAFT_DESC = "草稿";
	public static final String PROCESSING_DESC = "审批中";
	public static final String COMPLETED_DESC = "审批完成";
	
	public static CodeTable getStatusCodeTable(){
		CodeTable ct = new CodeTable();
		ct.insertItem(DRAFT, DRAFT_DESC);
		ct.insertItem(PROCESSING, PROCESSING_DESC);
		ct.insertItem(COMPLETED, COMPLETED_DESC);
		return ct;
	}
	
	public static Map<Integer, String> getStatusMap(){
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(DRAFT, DRAFT_DESC);
		map.put(PROCESSING, PROCESSING_DESC);
		map.put(COMPLETED, COMPLETED_DESC);
		return map;
	}
	
	//获取审批状态
	@Transient
	public String getStatusDesc(){
		if (this.getStatus() != null){
			return getStatusCodeTable().getDisplay(this.getStatus());
		}
		return "";
	}
	
	// 搜索关键字
	private String keyword;
	
	@Transient
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	//维护单下离退休职工人数
	@Transient
	public Integer getRetirementCount(){
		OaNetinService netinService = (OaNetinService)SpringContextHolder.getBean(OaNetinService.class);
		return netinService.getRetirementCount(this.getNetinId());
	}
	
	//获取维护单下非离退休职工人数
	@Transient
	public Integer getNotRetirementCount(){
		OaNetinService netinService = (OaNetinService)SpringContextHolder.getBean(OaNetinService.class);
		return netinService.getNotRetirementCount(this.getNetinId());
	}
	
	//声明流程用到的参数
	@Transient
	public int getDbYear(){
	    return 0;
	 }
	
	@Transient
	public String getDomainObjectId(){
	    return "OaNetin";
	}
	
	@Transient
	public String getEntityName() {
		return getClass().getName();
	}

	@Transient
	public String getModuleId() {
		return "OANETIN";
	}

	@Transient
	public String getModuleBusiType(){
		 return "";
	}
	
	@Transient
	public String getCompanyNo() {
		return getDeptId();
	}

	@Transient
	public String getKeyProps() {
		return "netin_id";
	}

	@Transient
	public String getKeyValues() {
		return this.getNetinId();
	}
	
}
