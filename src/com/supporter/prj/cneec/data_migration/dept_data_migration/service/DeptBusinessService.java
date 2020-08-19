package com.supporter.prj.cneec.data_migration.dept_data_migration.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.data_migration.dept_data_migration.dao.DeptBusinessDao;
import com.supporter.prj.cneec.data_migration.dept_data_migration.entity.DeptBusiness;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;

@Service
public class DeptBusinessService {
	@Autowired
	private DeptBusinessDao codeDao;

	public List<DeptBusiness> getGrid(UserProfile user, JqGrid jqGrid,
			String deptDataMigrationId, String pagetype) {
		List<DeptBusiness> persons = codeDao.findPage(jqGrid, deptDataMigrationId);
		return persons;

	}

	public void deleteRec(String deptDataMigrationId, String delIds) {
		if (StringUtils.isNotBlank(delIds)) {
			for (String delId : delIds.split(",")) {
				DeptBusiness rpnoDb = (DeptBusiness) this.codeDao.get(delId);
				if (rpnoDb != null) {
					this.codeDao.delete(delId);
				}
			}
		}
	}

	// 删除主表信息是删除从表
	public void deleteRecByPlcId(String id) {
		List<DeptBusiness> rec = codeDao.getPersonsByApply(id);
		codeDao.delete(rec);
	}
}
