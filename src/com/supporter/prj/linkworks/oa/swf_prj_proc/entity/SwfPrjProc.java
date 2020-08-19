package com.supporter.prj.linkworks.oa.swf_prj_proc.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.linkworks.oa.swf_prj_proc.entity.base.BaseSwfPrjProc;

/**
 * SwfPrjProc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "SWF_PRJ_PROC", schema = "SUPP_APP")
public class SwfPrjProc extends BaseSwfPrjProc implements
		java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	// Constructors

	/** default constructor */
	public SwfPrjProc() {
	}

	/** minimal constructor */
	public SwfPrjProc(String procId) {
		super(procId);
	}

	/** full constructor */
	public SwfPrjProc(String procId, String prjId, String prjName,
			String prjNo, String businessInnerName, String businessName,
			String procInnerName, String procName, String createdById,
			String createdBy, String createdDate, String modifiedById,
			String modifiedBy, String modifiedDate, String appPlatformCede,
			String appPlatformName) {
		super(procId, prjId, prjName, prjNo, businessInnerName, businessName,
				procInnerName, procName, createdById, createdBy, createdDate,
				modifiedById, modifiedBy, modifiedDate, appPlatformCede, appPlatformName);
	}
	
	// 搜索关键字
	private String keyword;
	
	@Transient
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

}
