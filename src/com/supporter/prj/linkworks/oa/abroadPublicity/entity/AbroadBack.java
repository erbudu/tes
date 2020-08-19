package com.supporter.prj.linkworks.oa.abroadPublicity.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.linkworks.oa.abroadPublicity.entity.base.BaseAbroadBack;
import com.supporter.prj.linkworks.oa.abroadPublicity.service.AbroadPublicityService;


@Entity
@Table(name="OA_ABROAD_BACK",schema="")
public class AbroadBack extends BaseAbroadBack{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Transient
	private AbroadPublicity abroadPublicity;
	
	@Transient
	private String status;
	
	@Transient
	private String isCanceled;
	@Transient
	private AbroadPublicityService service = (AbroadPublicityService)SpringContextHolder.getBean(AbroadPublicityService.class);
	//回国报告流程
	@Transient
	private boolean backPrc = true;
	
	public static final int DRAFT = 0; //草稿
	public static final int PROCESSING = 1; //审核中 
	public static final int COMPLETED = 2; //审批完成
	    
	public static final int CANCELED=1;//未按时出国
	public static final int NO_CANCELED=0;//按时出国

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIsCanceled() {
		return isCanceled;
	}

	public void setIsCanceled(String isCanceled) {
		this.isCanceled = isCanceled;
	}

	public AbroadPublicity getAbroadPublicity() {
		return abroadPublicity;
	}

	public void setAbroadPublicity(AbroadPublicity abroadPublicity) {
		this.abroadPublicity = abroadPublicity;
	}
	
	public int getDbYear(){
	    return 0;
	 }
	
	public String getDomainObjectId(){
	    return "AbroadBack";
	}
	
	public String getEntityName() {
		return getClass().getName();
	}

	public String getModuleId() {
		return "OAABROAD";
	}

	public String getModuleBusiType(){
		 return "";
	}
	
	

	public boolean getBackPrc() {
		return backPrc;
	}

	public void setBackPrc(boolean backPrc) {
		this.backPrc = backPrc;
	}

	/**
	 * 获取部门id 
	 * @return
	 */
	public String getDeptId(){
		String pubId = getPubId();
		if(pubId != null && pubId !=""){
			if( service.getBypubId(pubId)!= null){
				return service.getBypubId(pubId).getApplierDeptId();
			}
		}
		 return "";
	}
	
	/**
	 * 获取前往国家
	 * @return
	 */
	public String getCountries(){
		String pubId = getPubId();
		if(pubId != null && pubId !=""){
			if( service.getBypubId(pubId)!= null){
				return service.getBypubId(pubId).getTgtCountries();
			}
		}
		 return "";
	}
	
	/**
	 * 获取申请人
	 * @return
	 */
	public String getApplier(){
		String pubId = getPubId();
		if(pubId != null && pubId !=""){
			if( service.getBypubId(pubId)!= null){
				return service.getBypubId(pubId).getApplierId();
			}
		}
		 return "";
	}
	
	/**
	 * 获取申请人
	 * @return
	 */
	public String getAbroadId(){
		String pubId = getPubId();
		if(pubId != null && pubId !=""){
			if( service.getBypubId(pubId)!= null){
				return service.getBypubId(pubId).getRecordId();
			}
		}
		 return "";
	}
}
