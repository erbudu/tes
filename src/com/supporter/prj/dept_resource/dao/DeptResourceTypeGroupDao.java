package com.supporter.prj.dept_resource.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.dept_resource.entity.DeptResourceTypeGroup;

/**
 * @Title: Entity
 * @Description: 功能模块�?
 * @author liyinfeng
 * @date 2017-03-15 16:25:07
 * @version V1.0
 * 
 */
@Repository
public class DeptResourceTypeGroupDao extends
		MainDaoSupport<DeptResourceTypeGroup, String> {

	/**
	 * 分页查询
	 * 
	 * @param jqGrid
	 * @param contractIds
	 *            模块ids
	 * @return
	 */
	public List<DeptResourceTypeGroup> findByTypeCode(String typeCode) {
		String hql = " from " + DeptResourceTypeGroup.class.getName()
				+ " where resourceTypeCode = '" + typeCode + "'";
		return this.find(hql);
	}
}
