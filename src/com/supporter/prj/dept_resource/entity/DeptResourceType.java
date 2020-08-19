package com.supporter.prj.dept_resource.entity;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import com.supporter.prj.dept_resource.entity.base.BaseDeptResourceType;

@Entity
@Table(name = "COM_DEPT_RESOURCE_TYPE", schema = "")
public class DeptResourceType extends BaseDeptResourceType {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static Map<Integer, String> getStatusCodeTable() {
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(1, "草稿");
		map.put(2, "审核中");
		map.put(3, "审核完成");
		return map;
	}

	private boolean add;

	@Transient
	public boolean getAdd() {
		return add;
	}

	public void setAdd(boolean add) {
		this.add = add;
	}

	private List<DeptResourceTypeUi> UIList;

	@Transient
	public List<DeptResourceTypeUi> getUIList() {
		return UIList;
	}

	public void setUIList(List<DeptResourceTypeUi> list) {
		UIList = list;
	}

	private String delIds;

	public void setDelIds(String delIds) {
		this.delIds = delIds;
	}

	@Transient
	public String getDelIds() {
		return delIds;
	}

	private List<DeptResourceTypeGroup> GroupList;

	@Transient
	public List<DeptResourceTypeGroup> getGroupList() {
		return GroupList;
	}

	public void setGroupList(List<DeptResourceTypeGroup> groupList) {
		GroupList = groupList;
	}
	
	private String roleName;
	@Transient
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
