package com.supporter.prj.pm.procure_contract.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import com.supporter.prj.pm.procure_contract.entity.base.BaseProcureContractSite;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.StringUtils;

/**   
 * @Title: 涉及工程部位
 * @Description: PM_PROCURE_CONTRACT_SITE.
 * @author Administrator
 * @date 2018-07-04 18:04:17
 * @version V1.0   
 *
 */
@Entity
@Table(name = "PM_PROCURE_CONTRACT_SITE", schema = "")
public class ProcureContractSite extends BaseProcureContractSite {

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

	/**
	 * 无参构造函数.
	 */
	public ProcureContractSite() {
		super();
	}
	
	/**
	 * 构造函数.
	 * @param siteId
	 */
	public ProcureContractSite(String siteId) {
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

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof ProcureContractSite)) {
			return false;
		}
		ProcureContractSite castOther = (ProcureContractSite) other;
		return StringUtils.equals(this.getSiteId(), castOther.getSiteId());
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getSiteId()).toHashCode();
	}
	
}
