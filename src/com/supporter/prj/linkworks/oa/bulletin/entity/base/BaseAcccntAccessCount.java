package com.supporter.prj.linkworks.oa.bulletin.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


/**
 * @Title: Entity
 * @Description: 功能模块,字段与数据库字段一一对应.
 * @author linxiaosong
 * @version V1.0   
 */

@MappedSuperclass
public class BaseAcccntAccessCount implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "RECORD_ID", unique = true, nullable = false, precision = 32, scale = 0)
	private String recordId;
	
	//
	@Column(name="ACCESS_OBJECT" ,length=128, nullable = true)
	private String accessObject;
	
	//
	@Column(name="ACCESS_OBJECT_TYPE" ,length=128, nullable = true)
	private String accessObjectType;
	
	//
    @Column(name = "READ_COUNT", precision = 5, scale = 0, nullable = true)
	private Integer readCount = 0;
    
    //
    @Column(name = "MODIFY_COUNT", precision = 5, scale = 0, nullable = true)
	private Integer modifyCount = 0;

    
    
    
	public BaseAcccntAccessCount() {
		super();
	}

	public BaseAcccntAccessCount(String recordId, String accessObject,
			String accessObjectType, Integer readCount, Integer modifyCount) {
		super();
		this.recordId = recordId;
		this.accessObject = accessObject;
		this.accessObjectType = accessObjectType;
		this.readCount = readCount;
		this.modifyCount = modifyCount;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getAccessObject() {
		return accessObject;
	}

	public void setAccessObject(String accessObject) {
		this.accessObject = accessObject;
	}

	public String getAccessObjectType() {
		return accessObjectType;
	}

	public void setAccessObjectType(String accessObjectType) {
		this.accessObjectType = accessObjectType;
	}

	public Integer getReadCount() {
		return readCount;
	}

	public void setReadCount(Integer readCount) {
		this.readCount = readCount;
	}

	public Integer getModifyCount() {
		return modifyCount;
	}

	public void setModifyCount(Integer modifyCount) {
		this.modifyCount = modifyCount;
	}
    
    

}
