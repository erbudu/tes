/**
 * 
 */
package com.supporter.prj.ppm.poa.use_seal.entity.base;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 *<p>Title: 用印文件实体类</p>
 *<p>Description: 字段跟数据库表字段一一对应</p>
 *<p>Company: </p>
 * @author YUEYUN
 * @date 2019年9月17日
 */
@MappedSuperclass
public class BaseUseSealFileEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*		The following is the base information	↓	*/
	@Id
	@Column(name="SEAL_FILE_ID",length=32)
	private String  sealFileId;//---------------用印业务主键 -DB- SEAL_FILE_ID	Varchar2(32)	主键
	
	@Column(name="USE_SEAL_ID",length=32)
	private String useSealId;//----------------- -DB- USE_SEAL_ID	 Varchar2(32)	外键(启动用印主键 )
	
	@Column(name="PRJ_ID",length=32)
	private String prjId;//-----------------------DB- PRJ_ID	Varchar2(32)	外键(项目主键)
	
	@Column(name="BUSINESS_UUID",length=32)
	private String businessUUID;//--------------------业务表单主键
	
	/*		The following is use seal information		↓	*/
	
	@Column(name="USE_SEAL_KIND",length=32)
	private String useSealKind;//-------------- -DB- USE_SEAL_KIND	 NUMBER	用印种类
	
	@Column(name="USE_SEAL_NUMBER")
	private Integer useSealNumber;//------------ -DB- USE_SEAL_NUMBER	 NUMBER	用印份数
	
	
	@Column(name="FU_SEAL_FILE_ID",length=32)
	private String fuSealFileId;//----------------DB- FU_SEAL_FILE_ID	Varchar2(32)	实际附件上传组件的文件ID(盖完章的)
	
	@Column(name="FU_SEAL_FILE_NAME")
	private String fuSealFileName;//--------------实际附件上传组件的文件名称(盖完章的)
	
	@Column(name="DISPLAY_ORDER")
	private Integer displayOrder;//------------- -DB- DISPLAY_ORDER	NUMBER(1)	同一文件类型的排序号
	
	/*		gridPath中需要的数据			*/
	
	@Column(name="FILE_UP_BUSINESS_ID",length=32)
	private String fileUpBusinessId;//
	
	@Column(name="BFD_TYPE_NAME",length=128)
	private String bfdTypeName;//-----------------用印文件类型
	
	@Column(name="FU_FILE_ID",length=32)
	private String fuFileId;//------------------ -DB- FU_FILE_ID	Varchar2(32)	实际附件上传组件的文件ID(查看下载附件)需要用印的文件类型对应的文件id
	
	@Column(name="FU_FILE_NAME",length=128)
	private String fuFileName;//------------------需要用印的文件类型对应的文件名称
	
	@Column(name="MODULE_NAME",length=128)
	private String moduleName;//------------------应用编号 注：上传盖章文件中用
	
	@Column(name="BUSI_TYPE",length=128)
	private String busiType;//--------------------注册附件编号：上传盖章文件中用
	
	@Column(name="ONE_LEAVE_ID",length=32)
	private String oneLeaveId;//------------------附件注册一级分类id ：上传盖章文件中用
	
	@Column(name="TWO_LEAVE_ID",length=32)
	private String twoLeaveId;//------------------附件注册二级分类id ：上传盖章文件中用
	/**
	 * This method is the constructor
	 */
	public  BaseUseSealFileEntity()
	{
		
	}
	
	/**
	 * This method is the constructor
	 */
	public  BaseUseSealFileEntity(String sealFileId)
	{
		this.sealFileId = sealFileId;
	}

	/*		The following is the set and get methods	↓		*/
	
	/**
	 * @return the sealFileId
	 */
	public String getSealFileId() {
		return sealFileId;
	}

	/**
	 * @param sealFileId the sealFileId to set
	 */
	public void setSealFileId(String sealFileId) {
		this.sealFileId = sealFileId;
	}

	/**
	 * @return the useSealId
	 */
	public String getUseSealId() {
		return useSealId;
	}

	/**
	 * @param useSealId the useSealId to set
	 */
	public void setUseSealId(String useSealId) {
		this.useSealId = useSealId;
	}

	/**
	 * @return the prjId
	 */
	public String getPrjId() {
		return prjId;
	}

	/**
	 * @param prjId the prjId to set
	 */
	public void setPrjId(String prjId) {
		this.prjId = prjId;
	}

	/**
	 * @return the useSealKind
	 */
	public String getUseSealKind() {
		return useSealKind;
	}

	/**
	 * @param useSealKind the useSealKind to set
	 */
	public void setUseSealKind(String useSealKind) {
		this.useSealKind = useSealKind;
	}

	/**
	 * @return the useSealNumber
	 */
	public Integer getUseSealNumber() {
		return useSealNumber;
	}

	/**
	 * @param useSealNumber the useSealNumber to set
	 */
	public void setUseSealNumber(Integer useSealNumber) {
		this.useSealNumber = useSealNumber;
	}

	/**
	 * @return the fuFileId
	 */
	public String getFuFileId() {
		return fuFileId;
	}

	/**
	 * @param fuFileId the fuFileId to set
	 */
	public void setFuFileId(String fuFileId) {
		this.fuFileId = fuFileId;
	}

	/**
	 * @return the fuSealFileId
	 */
	public String getFuSealFileId() {
		return fuSealFileId;
	}

	/**
	 * @param fuSealFileId the fuSealFileId to set
	 */
	public void setFuSealFileId(String fuSealFileId) {
		this.fuSealFileId = fuSealFileId;
	}

	/**
	 * @return the displayOrder
	 */
	public Integer getDisplayOrder() {
		return displayOrder;
	}

	/**
	 * @param displayOrder the displayOrder to set
	 */
	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	

	/**
	 * @return the businessUUID
	 */
	public String getBusinessUUID() {
		return businessUUID;
	}

	/**
	 * @param businessUUID the businessUUID to set
	 */
	public void setBusinessUUID(String businessUUID) {
		this.businessUUID = businessUUID;
	}

	/**
	 * @return the moduleName
	 */
	public String getModuleName() {
		return moduleName;
	}

	/**
	 * @param moduleName the moduleName to set
	 */
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	/**
	 * @return the busiType
	 */
	public String getBusiType() {
		return busiType;
	}

	/**
	 * @param busiType the busiType to set
	 */
	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}

	/**
	 * @return the oneLeaveId
	 */
	public String getOneLeaveId() {
		return oneLeaveId;
	}

	/**
	 * @param oneLeaveId the oneLeaveId to set
	 */
	public void setOneLeaveId(String oneLeaveId) {
		this.oneLeaveId = oneLeaveId;
	}

	/**
	 * @return the twoLeaveId
	 */
	public String getTwoLeaveId() {
		return twoLeaveId;
	}

	/**
	 * @param twoLeaveId the twoLeaveId to set
	 */
	public void setTwoLeaveId(String twoLeaveId) {
		this.twoLeaveId = twoLeaveId;
	}

	/**
	 * @return the bfdTypeName
	 */
	public String getBfdTypeName() {
		return bfdTypeName;
	}

	/**
	 * @param bfdTypeName the bfdTypeName to set
	 */
	public void setBfdTypeName(String bfdTypeName) {
		this.bfdTypeName = bfdTypeName;
	}

	/**
	 * @return the fuFileName
	 */
	public String getFuFileName() {
		return fuFileName;
	}

	/**
	 * @param fuFileName the fuFileName to set
	 */
	public void setFuFileName(String fuFileName) {
		this.fuFileName = fuFileName;
	}

	/**
	 * @return the fuSealFileName
	 */
	public String getFuSealFileName() {
		return fuSealFileName;
	}

	/**
	 * @param fuSealFileName the fuSealFileName to set
	 */
	public void setFuSealFileName(String fuSealFileName) {
		this.fuSealFileName = fuSealFileName;
	}

	/**
	 * @return the fileUpBusinessId
	 */
	public String getFileUpBusinessId() {
		return fileUpBusinessId;
	}

	/**
	 * @param fileUpBusinessId the fileUpBusinessId to set
	 */
	public void setFileUpBusinessId(String fileUpBusinessId) {
		this.fileUpBusinessId = fileUpBusinessId;
	}
	
	

}
