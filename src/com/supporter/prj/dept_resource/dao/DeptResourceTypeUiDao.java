package com.supporter.prj.dept_resource.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.dept_resource.entity.DeptResourceTypeUi;

/**
 * @Title: Entity
 * @Description: 功能模块�?
 * @author liyinfeng
 * @date 2017-03-15 16:25:07
 * @version V1.0
 * 
 */
@Repository
public class DeptResourceTypeUiDao extends
		MainDaoSupport<DeptResourceTypeUi, String> {

	/**
	 * 根据typeCode获取ui
	 * 
	 * @param jqGrid
	 * @param ap
	 * @param typeCode
	 * @return
	 */
	public List<DeptResourceTypeUi> getUiGrid(JqGrid jqGrid, String typeCode) {
		jqGrid.addHqlFilter("resourceTypeCode = ?  ", typeCode);
		List<DeptResourceTypeUi> list = this.retrievePage(jqGrid);
		jqGrid.setRows(list);
		return list;

	}
	
	/**
	 * 根据ID获取.
	 * 
	 * @param roomId
	 * @return
	 */
	public List<DeptResourceTypeUi> getByTypeCode(String typeCode) {
		String hql = "from " + DeptResourceTypeUi.class.getName()
				+ " where resourceTypeCode = ?";
		List<DeptResourceTypeUi> list = this.find(hql, typeCode);

		if (list == null || list.size() == 0)
			return null;

		return list;
	}
	
	
}
