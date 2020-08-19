package com.supporter.prj.ppm.template_register.entity;

import java.util.Date;

// ~ File Information
// ====================================================================================================================

/**
 * 树状结构对应的JSON结构类
 * <pre>
 *  用于装载tree数据结构
 * </pre>
 * 
 * @author liyinfeng
 * @createDate 2019-08-15
 */
public class TreeVO implements java.io.Serializable {
	// ~ Static Fields
	// ================================================================================================================
	private static final long serialVersionUID = 1L;
	
	
	// ~ Fields
	// ================================================================================================================
	private String id;
	private String pId;
	private String refEntityId;
	private String name;
	private String displayName;
	private String nodeId;
	private int nodeType;
	private int level;
	private String treeId;
	private int displayOrder;
	private boolean isActive;
	private Date disabledDate;
	private boolean open = true;
	private boolean expanded = true;
	private boolean isLeaf = false;
	private String parent ;
	private String icon;
	private String companyNo;
	private String versionId;
	private String companyIcon;
	private int selfCount;
	private int allCount;
	// ~ Constructors
	// ================================================================================================================
	public TreeVO(){
		
	}
	
	public String getRefEntityId() {
		return refEntityId;
	}

	public void setRefEntityId(String refEntityId) {
		this.refEntityId = refEntityId;
	}

	public TreeVO(String id,      
	String pId,
	String name,        
	String nodeId,      
	int nodeType,       
	int level,      
	String treeId,      
	int displayOrder,   
	boolean isActive,   
	Date disabledDate,
	boolean open,
	boolean expanded,
	boolean isLeaf,
	String parent,
	String displayName,
	String refId
	){
		this.id = id;   
		this.pId = pId;
		this.name = name;   
		this.nodeId = nodeId;     
		this.nodeType = nodeType;       
		this.level = level;
		this.treeId = treeId;      
		this.displayOrder = displayOrder;   
		this.isActive = isActive;  
		this.disabledDate = disabledDate;
		this.open = open;
		this.expanded = expanded;
		this.isLeaf = isLeaf;
		this.parent = parent;
		this.displayName = displayName;
		this.refEntityId = refId;
	}
	// ~ Methods
	// ================================================================================================================
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	public int getNodeType() {
		return nodeType;
	}
	public void setNodeType(int nodeType) {
		this.nodeType = nodeType;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int nodeLevel) {
		this.level = nodeLevel;
	}
	public String getTreeId() {
		return treeId;
	}
	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}
	public int getDisplayOrder() {
		return displayOrder;
	}
	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}
	public boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}
	public Date getDisabledDate() {
		return disabledDate;
	}
	public void setDisabledDate(Date disabledDate) {
		this.disabledDate = disabledDate;
	}
	public boolean isOpen() {
		return open;
	}
	public void setOpen(boolean open) {
		this.open = open;
	}

	public boolean isExpanded() {
		return expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

	public boolean getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getCompanyNo() {
		return companyNo;
	}

	public void setCompanyNo(String companyNo) {
		this.companyNo = companyNo;
	}

	public String getVersionId() {
		return versionId;
	}

	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}

	public String getCompanyIcon() {
		return companyIcon;
	}

	public void setCompanyIcon(String companyIcon) {
		this.companyIcon = companyIcon;
	}

	public int getSelfCount() {
		return selfCount;
	}

	public void setSelfCount(int selfCount) {
		this.selfCount = selfCount;
	}

	public int getAllCount() {
		return allCount;
	}

	public void setAllCount(int allCount) {
		this.allCount = allCount;
	}
}
