package com.supporter.prj.linkworks.oa.dept_meal_limit.dao;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.linkworks.oa.dept_meal_limit.entity.DeptMealLimit;
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
public class DeptMealLimitDao extends MainDaoSupport<DeptMealLimit, String> {
	/**
	 * 查询操作
	 * 
	 * @param budgetYear
	 * @param keyword
	 * @return List < DeptMealLimit >
	 */
	public List<DeptMealLimit> findByKeyword(String keyword) {
		String hql = "from " + DeptMealLimit.class.getName() + " where deptName like ?";
		List<DeptMealLimit> entities = this.find(hql, "%" + CommonUtil.trim(keyword)
				+ "%");
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
	public List<DeptMealLimit> findPage(JqGrid jqGrid, DeptMealLimit code) {
		if (code != null) {
			String key = code.getDeptName();
			if (StringUtils.isNotBlank(key)) {
				key = "%" + key + "%";
				jqGrid
						.addHqlFilter(
								"deptName like ?  ",
								key);
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
	public boolean checkNameIsValid(String deptMealLimitId, String deptName) {
		String hql = null;
		List retList = null;
		
		if (StringUtils.isBlank(deptMealLimitId)) {// 新建时
			hql = "from " + DeptMealLimit.class.getName() + " where deptName = ?";
			retList = this.retrieve(hql, deptName);
		} else {// 编辑时
			hql = "from " + DeptMealLimit.class.getName()
					+ " where deptMealLimitId != ? and deptName = ?";
			retList = this.retrieve(hql, deptMealLimitId, deptName);
		}
		if (CollectionUtils.isEmpty(retList)) {
			return true;
		}
		return false;
	}

}
