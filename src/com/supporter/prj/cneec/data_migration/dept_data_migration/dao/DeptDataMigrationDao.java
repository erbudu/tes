package com.supporter.prj.cneec.data_migration.dept_data_migration.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.data_migration.dept_data_migration.entity.DeptDataMigration;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.util.CommonUtil;

/**
 * @Title: Entity
 * @Description: 部门迁移
 * @author yanbingchao
 * @date 2017-03-27 14:00:00
 * @version V1.0
 * 
 */
@Repository
public class DeptDataMigrationDao extends
		MainDaoSupport<DeptDataMigration, String> {
	/**
	 * 查询操作
	 * 
	 * @param budgetYear
	 * @param keyword
	 * @return List < DeptDataMigration >
	 */
	public List<DeptDataMigration> findByKeyword(String keyword) {
		String hql = "from " + DeptDataMigration.class.getName()
				+ " where keyWords like ?";
		List<DeptDataMigration> entities = this.find(hql, "%"
				+ CommonUtil.trim(keyword) + "%");
		return entities;
	}

	/**
	 * 分页查询
	 * 
	 * @param jqGrid
	 * @param codeIds
	 *            物资ids
	 * @return
	 */
	public List<DeptDataMigration> findPage(JqGrid jqGrid,
			DeptDataMigration code) {
		if (code != null) {
			String oDeptName = code.getOriginalDeptName();
			if (oDeptName != null) {
				oDeptName = "%" + oDeptName + "%";
				jqGrid.addHqlFilter(
						" originalDeptName like ? or newDeptName like ?",
						oDeptName, oDeptName);
			}
		}
		return this.retrievePage(jqGrid);
	}
}
