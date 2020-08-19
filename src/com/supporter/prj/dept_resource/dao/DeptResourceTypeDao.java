package com.supporter.prj.dept_resource.dao;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.dept_resource.entity.DeptResourceType;
import com.supporter.util.CommonUtil;

/**
 * @Title: Entity
 * @Description: 功能模块
 * @author liyinfeng
 * @date 2017-03-15 16:25:07
 * @version V1.0
 * 
 */
@Repository
public class DeptResourceTypeDao extends MainDaoSupport < DeptResourceType, String > {

	public Integer getCount() {
		String hql = "select max(displayOrder) from "
				+ DeptResourceType.class.getName();
		List < Long > list = this.find(hql);
		if (list.size() > 0) {
			if (list.get(0) != null && !list.get(0).equals("")) {
				return list.get(0).intValue();
			} else {
				return Integer.valueOf(0);
			}
		}
		return null;
	}

	/**
	 * 分页查询.
	 * @param jqGrid
	 * @param deptResourceType
	 * @return
	 */
	public List < DeptResourceType > findPage(JqGrid jqGrid, DeptResourceType deptResourceType) {
		if (deptResourceType != null) {
			String typeName = deptResourceType.getTypeName();
			if (StringUtils.isNotBlank(typeName)) {
				jqGrid.addHqlFilter(" typeCode like ? or typeName like ?  ",
						"%" + typeName + "%", "%" + typeName + "%");
			}
			// // 状态过滤
			// if (deptResourceType.getApplyStatus() != null) {
			// jqGrid.addHqlFilter(" applyStatus= ?
			// ",deptResourceType.getApplyStatus());
			// }
			//jqGrid.addSortPropertyDesc("displayOrder");
			jqGrid.addSortPropertyAsc("displayOrder");
		}
		return this.retrievePage(jqGrid);
	}

	/**
	 * 获取所有的部门资源类型.
	 * @return
	 */
	public List < Object[] > findDeptResourceType() {
		String hql = "select typeCode,typeName from " + DeptResourceType.class.getName();
		List < Object[] > list = this.find(hql);
		return list;
	}
	
	
	/**
	 * 判断名字是否重复.
	 * @param typeCode
	 * @return
	 */
	public boolean checkNameIsValid(String typeCode) {
		List < DeptResourceType > retList = this.getByTypeCode(typeCode);
		if (CollectionUtils.isEmpty(retList)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 根据资源类型编号获取部门资源类型列表.
	 * @param typeCode
	 * @return
	 */
	public List < DeptResourceType > getByTypeCode(String typeCode){
		typeCode = CommonUtil.trim(typeCode);
		if (typeCode.length() == 0)return null;
		return this.findBy("typeCode", typeCode);
	}
	
	/**
	 * 判断当前用户在不在超级管理员组里
	 * @param sql
	 * @return
	 */
/*	public int getCountByUserGroupIds(String sql){
	List<Integer> list=this.find(sql);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}else{
			return 0;
		}
	}*/

}
