package com.supporter.prj.pm.enginee_negotiate.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.pm.enginee_negotiate.entity.base.BaseEngineeVisaSite;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.StringUtils;

/**   
 * @Title: 签证-工程部位
 * @Description: PM_ENGINEE_VISA_SITE.
 * @author Administrator
 * @date 2018-07-04 18:08:56
 * @version V1.0   
 *
 */
@Entity
@Table(name = "PM_ENGINEE_VISA_SITE", schema = "")
public class EngineeVisaSite extends BaseEngineeVisaSite {

	private static final long serialVersionUID = 1L;
	
	//----------------------------实体类扩展属性定义,注意属性前加@Transient注解标示非数据库字段----------------------------
	@Transient
	private boolean isNew = false; //是否是新增尚未保存记录
	@Transient
	private boolean isLeaf = true; //是否为叶子节点
	//private String parent = null; //该节点的父节点ID
	@Transient
	private boolean expanded = true; //该节点是否展开
	@Transient
	private int level = -1; //该节点的树层次0,1,2,3...
	
	@Transient
	private boolean isWbs = false; //是否是WBS
	
	@Transient
	private String dxProject; //单项工程
	@Transient
	private String dwProject; //单位工程

	/**
	 * 无参构造函数.
	 */
	public EngineeVisaSite() {
		super();
	}
	
	/**
	 * 构造函数.
	 * @param siteId
	 */
	public EngineeVisaSite(String siteId) {
		super(siteId);
	}
	
	public boolean getIsNew() {
		return isNew;
	}
	public void setIsNew(boolean isNew) {
		this.isNew = isNew;
	}
	
	public boolean getIsLeaf() {
		return this.isLeaf;
	}
	public void setIsLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}
	
	//该节点的ID
	@Transient
	public String getId() {
		return this.getSiteId();
	}
	
	//该节点的父节点ID
	@Transient
	public String getParent() {
		return this.getParentSiteId();
	}
	
	public boolean isExpanded() {
		return this.expanded;
	}
	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}
	
	public int getLevel() {
		return this.level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	
	public boolean getIsWbs() {
		return isWbs;
	}
	public void setIsWbs(boolean isWbs) {
		this.isWbs = isWbs;
	}
	
	public String getDxProject() {
		return dxProject;
	}

	public void setDxProject(String dxProject) {
		this.dxProject = dxProject;
	}

	public String getDwProject() {
		return dwProject;
	}

	public void setDwProject(String dwProject) {
		this.dwProject = dwProject;
	}



	@Transient
	private String wbsNameDesc;
	
	@Transient
	public String getWbsNameDesc() {
		return wbsNameDesc;
	}

	public void setWbsNameDesc(String wbsNameDesc) {
		this.wbsNameDesc = wbsNameDesc;
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof EngineeVisaSite)) {
			return false;
		}
		EngineeVisaSite castOther = (EngineeVisaSite) other;
		return StringUtils.equals(this.getSiteId(), castOther.getSiteId());
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getSiteId()).toHashCode();
	}
	
}
