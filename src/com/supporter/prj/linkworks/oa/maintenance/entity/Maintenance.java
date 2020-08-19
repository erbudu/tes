package com.supporter.prj.linkworks.oa.maintenance.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

import com.supporter.util.CodeTable;
import com.supporter.prj.eip.busi_entity.IBusiEntity;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.emp.entity.IEmployee;
import com.supporter.prj.eip_service.module.entity.IModule;
import com.supporter.prj.linkworks.oa.maintenance.entity.base.BaseMaintenance;


@Entity
@Table(name="OA_MAINTENANCE"
    ,schema=""
)
public class Maintenance extends BaseMaintenance implements IBusiEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final int DRAFT = 0; //草稿
    public static final int PROCESSING = 1; //审核中 
    public static final int COMPLETED = 2; //审核完成
    public static final int REJECTED = 3; //驳回
    
    
    /**
	 * 获取报告的状态码表.
	 * @return
	 */
	public static CodeTable getMaintenanceStatusCodeTable() {
		CodeTable lcdtbl_Return = new CodeTable();
		lcdtbl_Return.insertItem(DRAFT, "草稿");
		lcdtbl_Return.insertItem(PROCESSING, "审核中");
		lcdtbl_Return.insertItem(COMPLETED, "审核完成");
		lcdtbl_Return.insertItem(REJECTED, "驳回");
		return lcdtbl_Return;
	}
	
	public static Map<Integer, String> getStatusCodeTable(){
    	Map<Integer, String> map = new LinkedHashMap<Integer, String>();
    	map.put(DRAFT, "草稿");
    	map.put(PROCESSING, "审核中");
    	map.put(COMPLETED, "审核完成");
    	map.put(REJECTED, "驳回");
		return map;
	}
    
	
    private static final String DAI_MA_WEI_HU = "代码维护";
    private static final String SHU_JU_WEI_HU = "数据维护";
    private static final String NEW_MODEL="新增模块";
    
    public static CodeTable getPropertiesCodeTable(){
    	CodeTable ct = new CodeTable();
    	ct.insertItem("", "", 1);
    	ct.insertItem(DAI_MA_WEI_HU, DAI_MA_WEI_HU, 2);
    	ct.insertItem(SHU_JU_WEI_HU, SHU_JU_WEI_HU, 3);
    	ct.insertItem(NEW_MODEL, NEW_MODEL, 4);
    	return ct;
    }
    
    public static final int SYSTEM_MODIFIED=1;
    public static final int DATA_MODIFIED=2;
    
    public static CodeTable getModifiedCodeTable(){
    	CodeTable ct = new CodeTable();
    	ct.insertItem(0, "", 1);
    	ct.insertItem(SYSTEM_MODIFIED, "系统修改", 2);
    	ct.insertItem(DATA_MODIFIED, "数据修改", 3);
    	return ct;
    }
    
	public static Map<Integer, String> getModifiedMap(){
    	Map<Integer, String> map = new LinkedHashMap<Integer, String>();
    	map.put(0, "");
    	map.put(SYSTEM_MODIFIED, "系统修改");
    	map.put(DATA_MODIFIED, "数据修改");
		return map;
	}
    
    
    private boolean add;

    @Transient
	public boolean getAdd() {
		return add;
	}

	public void setAdd(boolean add) {
		this.add = add;
	}
	//问题描述
	private String problemDescriptionDesc;

	@Transient
	public String getProblemDescriptionDesc() {
		return problemDescriptionDesc;
	}

	public void setProblemDescriptionDesc(String problemDescriptionDesc) {
		this.problemDescriptionDesc = problemDescriptionDesc;
	}
	
	public String swfStatusDesc;

	@Transient
	public String getSwfStatusDesc() {
		return getMaintenanceStatusCodeTable().getDisplay(this.getSwfStatus());
	}

	public void setSwfStatusDesc(String swfStatusDesc) {
		this.swfStatusDesc = swfStatusDesc;
	}
	
	
	private String files;

	@Transient
	public String getFiles() {
		return files;
	}

	public void setFiles(String files) {
		this.files = files;
	}
	
	private String deptName;

	@Transient
	public String getDeptName() {
		String empId=this.getCreatedById();
		//根据人员的id获取该人员
	    IEmployee userZ = EIPService.getEmpService().getEmployee(empId);
    	if(userZ.getDept()==null){
    		return "无所属部门";
	   	}else{
	   		return userZ.getDept().getName();
	   	}
	    
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	//是否在旧系统中审批过,如果审批过则 oldProcId > 0
	private long oldProcId = -1;
	@Transient
	public long getOldProcId(){
		return oldProcId;
	}
	public void setOldProcId(long procId){
		this.oldProcId = procId;
	}	
	
	//声明流程用到的参数
	@Transient
	public int getDbYear()
    {
        return 0;
    }
	@Transient
    public String getDomainObjectId()
    {
        return "Maintenance";
    }
    @Transient
    public String getEntityName()
    {
        return getClass().getName();
    }
    @Transient
    public String getModuleId()
    {
        return "OAMAINTENAN";
    }
    @Transient
    public String getModuleBusiType()
    {
        return "";
    }
    @Transient
    public String getCompanyNo()
    {
        return getDeptId();
    }


	@Override
	@Transient
	public String getKeyProps() {
		return "maintenance_id";
	}

	@Override
	@Transient
	public String getKeyValues() {
		return this.getMaintenanceId();
	}
	
	
	
	
	
	
	

}
