package com.supporter.prj.linkworks.oa.abroad.entity;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.linkworks.oa.abroad.entity.base.BaseAbroad;
import com.supporter.util.CodeTable;


/**   
 * @Title: Entity
 * @Description: 功能模块表
 * @version V1.0   
 *
 */
@Entity
@Table(name="OA_ABROAD",schema="")
public class Abroad extends BaseAbroad{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final int DRAFT = 0; //草稿
	public static final int PROCESSING = 1; //审核中
	public static final int COMPLETE = 3;//已通过
	
	public static final int NOPUBLICITY = 0;//不发布公告
	public static final int YESPUBLICITY = 1;//发布公告
	
	public static final String SINGLE = "SINGLE";
	public static final String TWICE = "TWICE";
	public static final String MULT = "MULT";
	
	public static final String SINGLE_DESC = "单次";
	public static final String TWICE_DESC = "二次";
	public static final String MULT_DESC = "多次";
	
	public static Map<String, String> getRoundtripTypeMap(){
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put(SINGLE, SINGLE_DESC);
		map.put(MULT, MULT_DESC);
		return map;
	}
	
	public static CodeTable getRoundtripTypeCodeTbale(){
		CodeTable ct = new CodeTable();
		ct.insertItem(SINGLE, SINGLE_DESC);
		ct.insertItem(MULT, MULT_DESC);
		return ct;
	}
	
	public static Map<String, String> getRoundtripNumberMap(){
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put(TWICE, TWICE_DESC);
		map.put(MULT, MULT_DESC);
		return map;
	}
	
	public static CodeTable getRoundtripNumberCodeTbale(){
		CodeTable ct = new CodeTable();
		ct.insertItem(TWICE, TWICE_DESC);
		ct.insertItem(MULT, MULT_DESC);
		return ct;
	}
	
	@Transient
	public String getRoundtripTypeDesc(){
		return getRoundtripTypeCodeTbale().getDisplay(this.getRoundtripType());
	}
	
	@Transient
	public String getRoundtripNumberDesc(){
		return getRoundtripNumberCodeTbale().getDisplay(this.getRoundtripNumber());
	}
	
	@Transient
	private List<AbroadPerson> materialList;
	
	
	@Transient
	private String statuts;
	//选择公司分管领导
	@Transient
	private String empId;
	@Transient
	private String empName;

	//内部员工
	@Transient
	private String empNames = "";
	@Transient
	private String empSysNos;
	@Transient
	private String delIds;
	@Transient
	private boolean isNew = true;
	@Transient
	private String fileId;
	@Transient
	private String fileName;
	@Transient
	private int isTopLeaderExam = 0;
	//出国审批流程
	@Transient
	private boolean abroadPrc = true;
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public boolean getIsNew() {
		return isNew;
	}

	public void setIsNew(boolean isNew) {
		this.isNew = isNew;
	}

	public String getDelIds() {
		return delIds;
	}

	public void setDelIds(String delIds) {
		this.delIds = delIds;
	}

	public String getStatuts() {
		return statuts;
	}

	public void setStatuts(String statuts) {
		this.statuts = statuts;
	}
	

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public List<AbroadPerson> getMaterialList() {
		return materialList;
	}

	public void setMaterialList(List<AbroadPerson> materialList) {
		this.materialList = materialList;
	}

	public String getEmpNames() {
		return empNames;
	}

	public void setEmpNames(String empNames) {
		this.empNames = empNames;
	}

	public String getEmpSysNos() {
		return empSysNos;
	}

	public void setEmpSysNos(String empSysNos) {
		this.empSysNos = empSysNos;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	
	
	
	public int getDbYear(){
	    return 0;
	 }
	
	public String getDomainObjectId(){
	    return "Abroad";
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

	public int getIsTopLeaderExam() {
		return isTopLeaderExam;
	}

	public void setIsTopLeaderExam(int isTopLeaderExam) {
		this.isTopLeaderExam = isTopLeaderExam;
	}

	public boolean getAbroadPrc() {
		return abroadPrc;
	}

	public void setAbroadPrc(boolean abroadPrc) {
		this.abroadPrc = abroadPrc;
	}

}
