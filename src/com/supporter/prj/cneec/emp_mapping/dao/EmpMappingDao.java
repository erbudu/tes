package com.supporter.prj.cneec.emp_mapping.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.emp_mapping.entity.EmpMapping;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;

@Repository
public class EmpMappingDao extends MainDaoSupport<EmpMapping, String> {
	/**
	 * 根据6.0人员ID获取映射ID
	 * @param persionId
	 * @return
	 */
	public String getEmpId(String personId){
		String Hql = " from " + EmpMapping.class.getName() + " where 1=1 and eip6EmpId = '" + personId + "' ";
		List<EmpMapping> list = this.find(Hql);
		if (list.size() > 0){
			personId = list.get(0).getEmpId().toString();
		}
		return personId;
	}
}
