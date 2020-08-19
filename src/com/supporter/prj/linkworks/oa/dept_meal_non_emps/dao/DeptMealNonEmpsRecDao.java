package com.supporter.prj.linkworks.oa.dept_meal_non_emps.dao;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.linkworks.oa.dept_meal_non_emps.entity.DeptMealNonEmpsRec;
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
public class DeptMealNonEmpsRecDao extends
		MainDaoSupport<DeptMealNonEmpsRec, String> {
	/**
	 * 分页查询
	 * 
	 * @param jqGrid
	 * @param codeIds
	 *            物资ids
	 * @return
	 */
	public List<DeptMealNonEmpsRec> findPage(JqGrid jqGrid, String nonEmpIds) {
		if (StringUtils.isNotBlank(nonEmpIds)) {
			jqGrid.addHqlFilter("nonEmpIds = ? ", nonEmpIds);
			return this.retrievePage(jqGrid);
		}
		List<DeptMealNonEmpsRec> li = new ArrayList<DeptMealNonEmpsRec>();
		return li;

	}

	public List<DeptMealNonEmpsRec> getPersonsByApply(String nonEmpIds) {
		if (StringUtils.isNotBlank(nonEmpIds)) {
			String hql = "from " + DeptMealNonEmpsRec.class.getName()
					+ " where nonEmpIds = ?";
			List<DeptMealNonEmpsRec> entities = this.find(hql,CommonUtil.trim(nonEmpIds));
			return entities;
		}
		return null;

	}

}
