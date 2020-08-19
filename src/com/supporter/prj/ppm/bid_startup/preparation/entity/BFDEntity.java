/**
 * 
 */
package com.supporter.prj.ppm.bid_startup.preparation.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.ppm.bid_startup.preparation.entity.base.BaseBFDEntity;

/**
 *<p>Title: BFDEntity</p>
 *<p>Description: </p>
 *<p>Company: </p>
 * @author YUEYUN
 * @date 2019年8月24日
 * 
 */
@Entity
@Table(name="PPM_BIDING_STARTUP_BFD",schema = "" )
public class BFDEntity extends BaseBFDEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Transient
	private String fileInfo;//上传附件成功后返回的附件信息
	
	
	/**
	 * @return the fileInfo
	 */
	public String getFileInfo() {
		return fileInfo;
	}

	/**
	 * @param fileInfo the fileInfo to set
	 */
	public void setFileInfo(String fileInfo) {
		this.fileInfo = fileInfo;
	}

	public BFDEntity() {
		
	}
	
	public BFDEntity(String bfdId) {
		super(bfdId);
	}

}
