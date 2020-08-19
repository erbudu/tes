package com.supporter.prj.linkworks.oa.emp_meal_apply.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.linkworks.oa.emp_meal_apply.entity.EmpMealRec;
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
public class EmpMealRecDao extends
		MainDaoSupport<EmpMealRec, String> {
	/**
	 * 分页查询
	 * 
	 * @param jqGrid
	 * @param codeIds
	 *            物资ids
	 * @return
	 */
	public List<EmpMealRec> findPage(JqGrid jqGrid, String applyId) {
		if (StringUtils.isNotBlank(applyId)) {
			jqGrid.addHqlFilter("applyId = ? ", applyId);
			return this.retrievePage(jqGrid);
		}
		List<EmpMealRec> li = new ArrayList<EmpMealRec>();
		return li;

	}

	public List<EmpMealRec> getPersonsByApply(String applyId) {
		if (StringUtils.isNotBlank(applyId)) {
			String hql = "from " + EmpMealRec.class.getName()
					+ " where applyId = ?";
			List<EmpMealRec> entities = this.find(hql,CommonUtil.trim(applyId));
			return entities;
		}
		return null;

	}

}
