package com.supporter.prj.pm.design_change.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.pm.design_change.entity.base.BaseDesignChange;
import com.supporter.prj.pm.public_proc.entity.PublicProc;
import com.supporter.util.CodeTable;

@Entity
@Table(name = "pm_designchange_apply", schema = "")
public class DesignChange extends BaseDesignChange{
	
	private static final long serialVersionUID = 1L;
	// 顶级TOP_MODULE_ID
	public static final String TOP_MODULE_ID = "0";	
	public static final String APP_NAME = "CHANGEAPPLY";//应用编号
	public static final String DOMAIN_OBJECT_ID = "designChange";//业务对象编号
	
	public static final String MODULE_ID = "DESIGNCHANGE_APPLY";

	public DesignChange() {
		super();
	}
	
	public DesignChange(String applyId, String applyNo,String projectNo,String prjId,String prjName,
			Date applyDate,String singleEngiee,String deptEngiee, String diviseEngiee,
			String drawsName,String drawsNo,String speciality,String bookNo,String bookName,
			String changeReasonId,String changeReason,String createdById,String createdBy,
			String createdDept,String createdDeptId,Date createdDate,String modifiedById,String modifiedBy,
			Date modifiedDate,Integer status,String fileType,String fileTypeId,
			String modelTypeId,String modelType,int isRewinding,int isExist) {
		super(  applyId,  applyNo,projectNo,prjId, prjName, applyDate,singleEngiee,deptEngiee,
				diviseEngiee,drawsName,drawsNo,speciality,bookNo,bookName,changeReason,changeReasonId,
				createdById,createdBy,createdDept,createdDeptId,createdDate,
				modifiedById,modifiedBy,modifiedDate,status,fileType,fileTypeId,
				modelTypeId,modelType,isRewinding,isExist);
	}
	
	// 状态
	public static final class StatusCodeTable {

		public static final int DRAFT = 0;
		public static final int EXAMING = 10;
		public static final int EXAMED = 20;
		public static final int REJECT = 30;

		public static CodeTable getCodeTable() {
			CodeTable ct = new CodeTable();
				ct.insertItem(DRAFT, "草稿");
				ct.insertItem(EXAMING, "审批中");
				ct.insertItem(EXAMED, "审批完成");
				ct.insertItem(REJECT, "驳回");
			return ct;
		}

	}
	
	private String statusDesc;
	//非数据库字段标识,用于列表显示状态
	@Transient
	public String getStatusDesc() {
		if(getStatus()!=null){
			return StatusCodeTable.getCodeTable().getDisplay(getStatus());
		}else{
			return null;
		}				
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}
	
	private boolean isNew;

	@Transient
	public boolean getIsNew() {
		return isNew;
	}

	public void setIsNew(boolean isNew) {
		this.isNew = isNew;
	}
	
	/**
	 * 
	 * 日志操作类型
	 * 
	 */
	
	public enum LogOper{
		DESIGNCHANGE_APPLY_ADD("新建设计变更申请"), DESIGNCHANGE_APPLY_EDIT("编辑设计变更申请"),
		DESIGNCHANGE_APPLY_VALID("生效设计变更申请"), DESIGNCHANGE_APPLY_INVALID("失效设计变更申请"),
		DESIGNCHANGE_APPLY_DEL("删除设计变更申请");
		private String operName;
		LogOper(String operName){
			this.operName = operName;
		}
		public String getOperName() {
			return operName;
		}
		public void setOperName(String operName) {
			this.operName = operName;
		}	
	}
	
	private String changeReasonStr;
	@Transient
	public String getChangeReasonStr() {
		if(getChangeReasonId()!= null){	
			return EIPService.getComCodeTableService().getCodeTable("change_reason").getDisplay(getChangeReasonId());
		}else{
			return null;
		}			
	}

	public void setChangeReasonStr(String changeReasonStr) {
		this.changeReasonStr = changeReasonStr;
	}
	
	//专业
	@Transient
	public String getSpecialityStr() {
		if(getSpecialityId()!= null){	
			return EIPService.getComCodeTableService().getCodeTable("Specialized").getDisplay(getSpecialityId());
		}else{
			return null;
		}			
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
		return this.getApplyId();
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
}
