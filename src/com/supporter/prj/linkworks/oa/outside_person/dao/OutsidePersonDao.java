package com.supporter.prj.linkworks.oa.outside_person.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.linkworks.oa.outside_person.entity.OutsidePerson;

@Repository
public class OutsidePersonDao extends MainDaoSupport<OutsidePerson, String> {
	/**
	 * 分页查询
	 * @param jqGrid
	 * @param netin
	 * @return
	 */
	public List<OutsidePerson> findPage(JqGrid jqGrid, OutsidePerson outsidePerson){
		if (outsidePerson != null){
			//查询框
			String keyword = outsidePerson.getKeyword();
			if (StringUtils.isNotBlank(keyword)){
				jqGrid.addHqlFilter(" name like ? or deptName like ? ", "%"+keyword+"%", "%"+keyword+"%");
			}
			//条件过滤
			String status = outsidePerson.getStatus();
			if (StringUtils.isNotBlank(status)){
				jqGrid.addHqlFilter(" status = ? ", status);
			}
			//按申请时间倒叙排列
			jqGrid.addSortPropertyDesc("confirmDate");
			jqGrid.addSortPropertyDesc("id");
		}
		return this.retrievePage(jqGrid);
	}
	
	/**
	 * 获取在职的外聘人员
	 * @return
	 */
	public List<OutsidePerson> getOutsidePersons(){
		String hql = " from " + OutsidePerson.class.getName() + " where status = ? ";
		List<OutsidePerson> personList = this.find(hql, "在职");
		if (personList != null){
			return personList;
		}
		return null;
	}
}
