package com.supporter.prj.linkworks.oa.dept_meal_non_emps.dao;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.linkworks.oa.dept_meal_non_emps.entity.DeptMealNonEmps;
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
public class DeptMealNonEmpsDao extends MainDaoSupport<DeptMealNonEmps, String> {
	/**
	 * 查询操作
	 * 
	 * @param budgetYear
	 * @param keyword
	 * @return List < DeptMealNonEmps >
	 */
	public List<DeptMealNonEmps> findByKeyword(String keyword) {
		String hql = "from " + DeptMealNonEmps.class.getName()
				+ " where deptName like ?";
		List<DeptMealNonEmps> entities = this.find(hql, "%"
				+ CommonUtil.trim(keyword) + "%");
		return entities;
	}

	public List<DeptMealNonEmps> findByDeptId(String keyword) {
		String hql = "from " + DeptMealNonEmps.class.getName()
				+ " where deptId = ?";
		List<DeptMealNonEmps> entities = this.find(hql, CommonUtil.trim(keyword));
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
	public List<DeptMealNonEmps> findPage(JqGrid jqGrid, DeptMealNonEmps code) {
		if (code != null) {
			String key = code.getDeptName();
			if (key != null) {
				key = "%" + key + "%";
				jqGrid.addHqlFilter(" deptName like ?", key);
			}
		}
		return this.retrievePage(jqGrid);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param codeId
	 * @param materialName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean checkNameIsValid(DeptMealNonEmps entity) {
		String hql = null;
		List retList = null;
		if (entity != null) {
			String id = entity.getNonEmpIds();
			String deptName = entity.getDeptName();
			if (StringUtils.isBlank(id)) {// 新建时
				hql = "from " + DeptMealNonEmps.class.getName()
						+ " where deptName = ?";
				retList = this.retrieve(hql, deptName);
			} else {// 编辑时
				hql = "from " + DeptMealNonEmps.class.getName()
						+ " where nonEmpIds != ? and deptName = ?";
				retList = this.retrieve(hql, id, deptName);
			}
			if (CollectionUtils.isEmpty(retList)) {
				return true;
			}
		}
		return false;
	}

}
