package com.supporter.prj.cneec.data_migration.dept_data_migration.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.data_migration.dept_data_migration.entity.base.BaseDeptDataMigration;
@Entity
@Table(name = "OA_DEPT_DATA_MIGRATION", schema = "")
public class DeptDataMigration extends BaseDeptDataMigration {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean add;
	private List<DeptBusiness> list;
	private String delIds;
	@Transient
	public String getDelIds() {
		return delIds;
	}
	public void setDelIds(String delIds) {
		this.delIds = delIds;
	}
	@Transient
	public boolean getAdd() {
		return add;
	}
	public void setAdd(boolean add) {
		this.add = add;
	}
	@Transient
	public List<DeptBusiness> getList() {
		return list;
	}
	public void setList(List<DeptBusiness> list) {
		this.list = list;
	}

}
