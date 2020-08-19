/**
 * 
 */
package com.supporter.prj.ppm.bid_startup.preparation.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 *<p>Title: 启动申报-资料清单</p>
 * @author YUEYUN
 * @date 2019年8月24日
 */
@MappedSuperclass
public class BaseBFDEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Id  
	@Column(name="BFD_ID", nullable=false, length=32)
	private String bfdId;;//BFD_ID	Varchar2(32)	主键
	@Column(name="BIDINGUP_ID")
	private String bidIngUpId;//BIDINGUP_ID	Varchar2(32)	外键
	@Column(name="BFD_TYPE_ID")
	private String bfdTypeId;//BFD_TYPE_ID	Varchar2(32)	文件类型ID
	@Column(name="BFD_TYPE_NAME")
	private String bfdTypeName;//BFD_TYPE_NAME	Varchar2(256)	文件类型名称 例如：《项目许可申请报告》
	@Column(name="IS_USE_SEAL")//是否用印
	private Integer isUseSeal;
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
	 * @return the bfdTypeId
	 */
	public String getBfdTypeId() {
		return bfdTypeId;
	}
	/**
	 * @param bfdTypeId the bfdTypeId to set
	 */
	public void setBfdTypeId(String bfdTypeId) {
		this.bfdTypeId = bfdTypeId;
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

	public BaseBFDEntity() {

	}

	public BaseBFDEntity(String bfdId) {
		this.bfdId = bfdId;
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

	
}
