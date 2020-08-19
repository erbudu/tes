package com.supporter.prj.linkworks.oa.bulletin.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.linkworks.oa.bulletin.entity.base.BaseVBulletin;
@Entity
@Table(name="OA_V_BULLETIN_OA",schema="")
public class VBulletin  extends BaseVBulletin{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int DRAFT = 0; //草稿
    public static final int PUBLISHED = 1; //已发布
	
	//发布状态
	@Transient
	private String publishName;
	//阅读次数
	@Transient	
	private int readCount;
	//部门与部门公告板
	@Transient
	private String fullDeptResourceName;
	@Transient
	public String getPublishName() {
		return publishName;
	}
	@Transient
	public void setPublishName(String publishName) {
		this.publishName = publishName;
	}
	@Transient
	public int getReadCount() {
		return readCount;
	}
	@Transient
	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}
	@Transient
	public String getFullDeptResourceName() {
		return fullDeptResourceName;
	}
	@Transient
	public void setFullDeptResourceName(String fullDeptResourceName) {
		this.fullDeptResourceName = fullDeptResourceName;
	}
}
