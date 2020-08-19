package com.supporter.prj.linkworks.oa.consignment.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.eip.swf.consign.entity.WfConsignation.ConsignType;
import com.supporter.prj.linkworks.oa.consignment.entity.base.BaseConsignment;

/**   
 * @Title: Entity
 * @Description: 功能模块表
 * @version V1.0   
 *
 */
@Entity
@Table(name="OA_CONSIGNMENT",schema="")
public class Consignment extends BaseConsignment{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final Integer VALID = 1;//业务授权有效
	public static final Integer UNVALID = 0;//业务授权无效
	//办公业务流程
	@Transient
	private String is_ProcDefIds;
	//项目管控业务流程
	@Transient
    private String is_ProcDefPcIds;
	//固定资产业务流程
	@Transient
    private String is_ProcDefAmIds;
	//采购管理及产品价格库流程
	@Transient
    private String is_ProcDefPlIds;
	//项目预算流程
	@Transient
    private String is_ProcDefBmIds;
	//设计管理流程
	@Transient
    private String is_ProcDefDmIds;
	//施工管理流程
	@Transient
    private String is_ProcDefCmIds;
	
	//状态
	@Transient
    private String failureName;
	
	//被委托的业务流程
	@Transient
    private String totalProcDef;
	
	@Transient
	private boolean isNew;
	//授权类型
	@Transient
	private int consignType;
	
	//是否全部授权
	@Transient
	private String oaAll;
	@Transient
	private String pcAll;
	@Transient
	private String amAll;
	@Transient
	private String plAll;
	@Transient
	private String bmAll;
	@Transient
	private String dmAll;
	@Transient
	private String cmAll;
	@Transient
	private String oldOaAll;
	
	/**
	 * 获取委托流程
	 * @return
	 */
	public String getConsignTypeDisplay() {
	    return ConsignType.getCodeTable().getDisplay(getConsignType());
	}
	
	public String getIs_ProcDefIds() {
		return is_ProcDefIds;
	}
	public void setIs_ProcDefIds(String is_ProcDefIds) {
		this.is_ProcDefIds = is_ProcDefIds;
	}
	public String getIs_ProcDefPcIds() {
		return is_ProcDefPcIds;
	}
	public void setIs_ProcDefPcIds(String is_ProcDefPcIds) {
		this.is_ProcDefPcIds = is_ProcDefPcIds;
	}
	public String getIs_ProcDefAmIds() {
		return is_ProcDefAmIds;
	}
	public void setIs_ProcDefAmIds(String is_ProcDefAmIds) {
		this.is_ProcDefAmIds = is_ProcDefAmIds;
	}
	public String getIs_ProcDefPlIds() {
		return is_ProcDefPlIds;
	}
	public void setIs_ProcDefPlIds(String is_ProcDefPlIds) {
		this.is_ProcDefPlIds = is_ProcDefPlIds;
	}
	public String getIs_ProcDefBmIds() {
		return is_ProcDefBmIds;
	}
	public void setIs_ProcDefBmIds(String is_ProcDefBmIds) {
		this.is_ProcDefBmIds = is_ProcDefBmIds;
	}
	public String getIs_ProcDefDmIds() {
		return is_ProcDefDmIds;
	}
	public void setIs_ProcDefDmIds(String is_ProcDefDmIds) {
		this.is_ProcDefDmIds = is_ProcDefDmIds;
	}
	public String getIs_ProcDefCmIds() {
		return is_ProcDefCmIds;
	}
	public void setIs_ProcDefCmIds(String is_ProcDefCmIds) {
		this.is_ProcDefCmIds = is_ProcDefCmIds;
	}
	public String getFailureName() {
		return failureName;
	}
	public void setFailureName(String failureName) {
		this.failureName = failureName;
	}
	public String getTotalProcDef() {
		return totalProcDef;
	}
	public void setTotalProcDef(String totalProcDef) {
		this.totalProcDef = totalProcDef;
	}
	public boolean getIsNew() {
		return isNew;
	}
	public void setIsNew(boolean isNew) {
		this.isNew = isNew;
	}
	public int getConsignType() {
		return 1;
	}
	public void setConsignType(int consignType) {
		this.consignType = consignType;
	}

	public String getPcAll() {
		return pcAll;
	}

	public void setPcAll(String pcAll) {
		this.pcAll = pcAll;
	}

	public String getAmAll() {
		return amAll;
	}

	public void setAmAll(String amAll) {
		this.amAll = amAll;
	}

	public String getPlAll() {
		return plAll;
	}

	public void setPlAll(String plAll) {
		this.plAll = plAll;
	}

	public String getBmAll() {
		return bmAll;
	}

	public void setBmAll(String bmAll) {
		this.bmAll = bmAll;
	}

	public String getDmAll() {
		return dmAll;
	}

	public void setDmAll(String dmAll) {
		this.dmAll = dmAll;
	}

	public String getCmAll() {
		return cmAll;
	}

	public void setCmAll(String cmAll) {
		this.cmAll = cmAll;
	}

	public String getOldOaAll() {
		return oldOaAll;
	}

	public void setOldOaAll(String oldOaAll) {
		this.oldOaAll = oldOaAll;
	}

	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}

	public String getOaAll() {
		return oaAll;
	}

	public void setOaAll(String oaAll) {
		this.oaAll = oaAll;
	}
	
	

}
