package com.supporter.prj.cneec.data_migration.dept_data_migration.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.data_migration.dept_data_migration.entity.DeptBusiness;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.util.CommonUtil;

/**
 * @Title: Entity
 * @Description: 物资档案设置
 * @author yanbingchao
 * @date 2017-03-27 14:00:00
 * @version V1.0
 * 
 */
@Repository
public class DeptBusinessDao extends
		MainDaoSupport<DeptBusiness, String> {
	/**
	 * 分页查询
	 * 
	 * @param jqGrid
	 * @param codeIds
	 *            物资ids
	 * @return
	 */
	public List<DeptBusiness> findPage(JqGrid jqGrid, String deptDataMigrationId) {
		if (StringUtils.isNotBlank(deptDataMigrationId)) {
			jqGrid.addHqlFilter("deptDataMigrationId = ? ", deptDataMigrationId);
			return this.retrievePage(jqGrid);
		}
		List<DeptBusiness> li = new ArrayList<DeptBusiness>();
		return li;

	}

	public List<DeptBusiness> getPersonsByApply(String deptDataMigrationId) {
		if (StringUtils.isNotBlank(deptDataMigrationId)) {
			String hql = "from " + DeptBusiness.class.getName()
					+ " where deptDataMigrationId = ?";
			List<DeptBusiness> entities = this.find(hql,CommonUtil.trim(deptDataMigrationId));
			return entities;
		}
		return null;

	}

}
