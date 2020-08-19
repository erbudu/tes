/**
 * 
 */
package com.supporter.prj.ppm.bid_startup.preparation.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 *<p>Title: BaseBFD_FEntity</p>
 *<p>Description: 资料清单-附件明细</p>
 *<p>Company: </p>
 * @author YUEYUN
 * @date 2019年8月27日
 * 
 */
@MappedSuperclass
public class BaseBFD_FEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id 
	@Column(name="RECORD_ID", nullable=false, length=32)
	private String  recordId;//
	
	@Column(name="BFD_ID",length=32)
	private String bfdId;//外键-资料清单主键
	
	@Column(name="BIDINGUP_ID",length=32)
	private String  bidIngUpId;//外键-启动申报主键
	
	@Column(name="FU_FILE_ID",length=32)
	private String  fuFileId;//实际上传附件的id
	
	@Column(name="FU_FILE_NAME",length=255)
	private String fuFileName;//实际上传附件的名称
	
	@Column(name="IS_USE_SEAL")//是否用印
	private Integer isUseSeal;
	
	@Column(name="FU_SEAL_FILE_ID",length=32)
	private String  fuSealFileId;//改完章的附件id
	
	
	@Column(name="DISPLAY_ORDER")
	private Integer displayOrder;

	public BaseBFD_FEntity() {

	}

	public BaseBFD_FEntity(String recordId) {
		
		this.recordId=recordId;

	}

	/**
	 * @return the recordId
	 */
	public String getRecordId() {
		return recordId;
	}

	/**
	 * @param recordId the recordId to set
	 */
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	/**
	 * @return the bfdId
	 */
	public String getBfdId() {
		return bfdId;
	}

	/**
	 * @param bfdId the bfdId to set
	 */
	public void setBfdId(String bfdId) {
		this.bfdId = bfdId;
	}

	/**
	 * @return the bidIngUpId
	 */
	public String getBidIngUpId() {
		return bidIngUpId;
	}

	/**
	 * @param bidIngUpId the bidIngUpId to set
	 */
	public void setBidIngUpId(String bidIngUpId) {
		this.bidIngUpId = bidIngUpId;
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
	 * @return the isUseSeal
	 */
	public Integer getIsUseSeal() {
		return isUseSeal;
	}

	/**
	 * @param isUseSeal the isUseSeal to set
	 */
	public void setIsUseSeal(Integer isUseSeal) {
		this.isUseSeal = isUseSeal;
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
	 * <pre>获取实际上传附件的名称</pre>
	 * @return the fuFileName
	 */
	public String getFuFileName() {
		return fuFileName;
	}

	/**
	 * <pre>set实际上传附件的名称</pre>
	 * @param fuFileName the fuFileName to set
	 */
	public void setFuFileName(String fuFileName) {
		this.fuFileName = fuFileName;
	}
	
	
	

}
