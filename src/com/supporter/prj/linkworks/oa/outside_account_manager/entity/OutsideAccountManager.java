package com.supporter.prj.linkworks.oa.outside_account_manager.entity;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.linkworks.oa.netin.service.OaNetinService;
import com.supporter.prj.linkworks.oa.outside_account_manager.dao.OutsideAccountManagerDao;
import com.supporter.prj.linkworks.oa.outside_account_manager.entity.base.BaseOutsideAccountManager;
import com.supporter.prj.linkworks.oa.outside_account_manager.service.OutsideAccountManagerService;
import com.supporter.util.CodeTable;
import com.supporter.util.CommonUtil;

/**
 * OutsideAccountManager entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "OA_OUTSIDE_ACCOUNT_MANAGER", schema = "SUPP_APP")
public class OutsideAccountManager extends BaseOutsideAccountManager
		implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public OutsideAccountManager() {
	}

	/** minimal constructor */
	public OutsideAccountManager(String managerId) {
		super(managerId);
	}

	/** full constructor */
	public OutsideAccountManager(String managerId, String createdById,
			String createdBy, String createdDate, String modifiedById,
			String modifiedBy, String modifiedDate, Integer status,
			String procId) {
		super(managerId, createdById, createdBy, createdDate, modifiedById,
				modifiedBy, modifiedDate, status, procId);
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
	
	private boolean isNew;
	
	@Transient
	public boolean getIsNew(){
		return isNew;
	}
	
	public void setIsNew(boolean isNew){
		this.isNew = isNew;
	}
	
	
	//人员的相关部门
	private String personDeptIds;
	
	@Transient
	public String getPersonDeptIds(){
		OutsideAccountManagerService outsideAccountManagerService = (OutsideAccountManagerService)SpringContextHolder.getBean(OutsideAccountManagerService.class);
		return outsideAccountManagerService.getRecDeptIds(this.getManagerId());
	}
	
	//人员的相关部门
	@Transient
	public String getDeptManagerIds(){
		OutsideAccountManagerService outsideAccountManagerService = (OutsideAccountManagerService)SpringContextHolder.getBean(OutsideAccountManagerService.class);
		return outsideAccountManagerService.getDeptManagerIds(this.getManagerId());
	}
	
	//流程标题用创建日期
	@Transient
	public String getCreatedDateToTitle(){
		return CommonUtil.format(this.getCreatedDate(), "yyyy-MM");
	}
	
	//声明流程用到的参数
	@Transient
	public int getDbYear(){
	    return 0;
	 }
	
	@Transient
	public String getDomainObjectId(){
	    return "OutsideAccountM";
	}
	
	@Transient
	public String getEntityName() {
		return getClass().getName();
	}
	
	@Transient
	public String getModuleId() {
		return "OUTSIDEACCOU";
	}
	
	@Transient
	public String getModuleBusiType(){
		 return "";
	}
	
	@Transient
	public String getCompanyNo() {
		return "";
	}
	
	@Transient
	public String getKeyProps() {
		return "namager_id";
	}
	
	@Transient
	public String getKeyValues() {
		return this.getManagerId();
	}
}
