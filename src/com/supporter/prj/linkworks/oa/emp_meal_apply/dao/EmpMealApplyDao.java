package com.supporter.prj.linkworks.oa.emp_meal_apply.dao;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.doc_in.constants.AuthConstant;
import com.supporter.prj.linkworks.oa.doc_in.util.DocInConstant;
import com.supporter.prj.linkworks.oa.emp_meal_apply.constants.EmpMealAuthConstant;
import com.supporter.prj.linkworks.oa.emp_meal_apply.entity.EmpMealApply;
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
public class EmpMealApplyDao extends MainDaoSupport<EmpMealApply, String> {
	/**
	 * 查询操作
	 * 
	 * @param budgetYear
	 * @param keyword
	 * @return List < EmpMealApply >
	 */
	public List<EmpMealApply> findByKeyword(String keyword) {
		String hql = "from " + EmpMealApply.class.getName()
				+ " where keyWords like ?";
		List<EmpMealApply> entities = this.find(hql, "%"
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
	// 查询,年月,发布者姓名,发布部门名称.
	public List<EmpMealApply> findPage(UserProfile user,JqGrid jqGrid, EmpMealApply code) {
		String authHql = "";
		if (code != null) {
			String key = code.getEmpName();
			if (StringUtils.isNotBlank(key)) {
				if(StringUtils.isNumeric(key)){
					jqGrid
					.addHqlFilter(
							" empName like ? or deptName like ? or year = ?  or month = ?",
							"%" + key + "%", "%" + key + "%", Integer
									.valueOf(key), Integer.valueOf(key));
				}else{
					jqGrid
					.addHqlFilter(
							" empName like ? or deptName like ? ",
							"%" + key + "%", "%" + key + "%");
				}
				
			}
		}
		
		//权限过滤
		authHql = EIPService.getAuthorityService().getHqlFilter(user,EmpMealAuthConstant.MODULE_ID,EmpMealAuthConstant.AUTH_OPER_NAME_PAGESHOW );
		jqGrid.addHqlFilter(authHql);
		return this.retrievePage(jqGrid);
	}

	/**
	 * 判断是否重复
	 * 
	 * @param codeId
	 * @param materialName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean checkNameIsValid(EmpMealApply entity) {
		String hql = null;
		List retList = null;
		// String id = entity.getApplyId();
		if (entity.getAdd()) {// 新建时
			hql = "from " + EmpMealApply.class.getName()
					+ " where deptId = ? and year = ? and month = ? ";
			Integer year = entity.getYear();
			Integer month = entity.getMonth();
			if (year != null && month != null) {
				retList = this.retrieve(hql, entity.getDeptId(), year, month);
			}

		} else {// 编辑时
			hql = "from "
					+ EmpMealApply.class.getName()
					+ " where deptId = ? and year = ? and month = ? and applyId != ? ";
			Integer year = entity.getYear();
			Integer month = entity.getMonth();
			if (year != null && month != null) {
				retList = this.retrieve(hql, entity.getDeptId(), entity
						.getYear(), entity.getMonth(), entity.getApplyId());
			}

		}
		if (retList==null || retList.size() == 0) {
			return true;
		}
		return false;
	}

}
