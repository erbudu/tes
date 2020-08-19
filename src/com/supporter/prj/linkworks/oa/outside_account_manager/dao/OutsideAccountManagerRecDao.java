package com.supporter.prj.linkworks.oa.outside_account_manager.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.linkworks.oa.outside_account_manager.entity.OutsideAccountManagerRec;
import com.supporter.prj.linkworks.oa.outside_person.dao.OutsidePersonDao;
import com.supporter.prj.linkworks.oa.outside_person.entity.OutsidePerson;

@Repository
public class OutsideAccountManagerRecDao extends MainDaoSupport<OutsideAccountManagerRec, String> {
	@Autowired
	private OutsidePersonDao outsidePersonDao;
	
	/**
	 * 获取人员列表
	 * @param jqGrid
	 * @param managerId
	 * @return
	 */
	public List<OutsideAccountManagerRec> findPage(JqGrid jqGrid, String managerId){
		if (StringUtils.isNotBlank(managerId)){
			jqGrid.addHqlFilter(" managerId = ? ", managerId);
			jqGrid.addSortPropertyAsc("deptId");
		}
		return this.retrievePage(jqGrid);
	}
	
	/**
	 * 根据部门ID获取对应人员列表
	 * @param jqGrid
	 * @param managerId
	 * @param DeptId
	 * @return
	 */
	public List<OutsideAccountManagerRec> findPageByDeptId(JqGrid jqGrid, String managerId, String DeptId){
		if (StringUtils.isNotBlank(managerId)){
			jqGrid.addHqlFilter(" managerId = ? ", managerId);
			jqGrid.addHqlFilter(" deptId = ? ", DeptId);
			jqGrid.addSortPropertyAsc("deptId");
		}
		return this.retrievePage(jqGrid);
	}
	
	/**
	 * 获取所有在职的外聘人员
	 * @return
	 */
	public List<OutsidePerson> getOutsidePersonList(){
		List<OutsidePerson> outsidePersons = this.outsidePersonDao.getOutsidePersons();
		if (outsidePersons != null){
			return outsidePersons;
		}
		return null;
	}
	
	/**
	 * 删除维护单下所有人员明细
	 * @param managerId
	 */
	public void deletedInner(String managerId){
		List<OutsideAccountManagerRec> recList = getOutsideAccountManagerRecsByDeptId(managerId, "all");
		if (recList != null){
			this.delete(recList);
		}
	}
	
	/**
	 * 根据部门id获取维护单下的人员列表(按部门排序)
	 * @param managerId
	 * @param deptId
	 * @return
	 */
	public List<OutsideAccountManagerRec> getOutsideAccountManagerRecsByDeptId(String managerId, String deptId){
		List<OutsideAccountManagerRec> recList = null;
		String hql = " from " + OutsideAccountManagerRec.class.getName() + " where managerId = ? ";
		if ("all".equals(deptId)){
			hql += " order by deptId asc ";
			recList = this.find(hql, managerId);
		}else {
			hql += " and deptId = ? order by deptId asc ";
			recList = this.find(hql, managerId, deptId);
		}
		return recList;
	}
}
