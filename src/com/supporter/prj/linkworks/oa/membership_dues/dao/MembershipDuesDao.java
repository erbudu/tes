package com.supporter.prj.linkworks.oa.membership_dues.dao;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.linkworks.oa.membership_dues.entity.MembershipDues;
import com.supporter.prj.linkworks.oa.salary.entity.Salary;

/**
 * @Title: Entity
 * @Description: 功能模块表
 * @author liyinfeng
 * @date 2017-03-15 16:25:07
 * @version V1.0
 * 
 */
@Repository
public class MembershipDuesDao extends MainDaoSupport<MembershipDues, String> {

	/**
	 * 分页查询
	 * 
	 * @param jqGrid
	 * @param contractIds
	 *            模块ids
	 * @return
	 */
	public List<MembershipDues> findPage(JqGrid jqGrid, MembershipDues card) {
		if (card != null) {
			// 查询
			String year = card.getDbYear();
			if (StringUtils.isNotBlank(year)) {
				jqGrid.addHqlFilter("year =  ?  ", year);
			}
		}
		return this.retrievePage(jqGrid);
	}

	public MembershipDues findMembershipDues(Salary salary) {
		if (salary != null) {
			String empNo = salary.getEmpNo();
			String yearMonth = salary.getSalaryMonth();
			String[] array = yearMonth.split("年");
			String year = "";
			String month = "";
			if (array != null && array.length > 0) {
				year = array[0];
			}
			String[] array2 = array[1].split("月");
			if (array2 != null && array2.length > 0) {
				if (array2[0].length() > 1) {
					month = array2[0];
				} else {
					month = "0" + array2[0];
				}
			}
			String hql = "from " + MembershipDues.class.getName()
					+ " where empNo = ? and dbYear = ? and dbMonth = ?";
			String[] values = new String[] { empNo, year, month };
			List<MembershipDues> entities = find(hql, values);
			if (entities != null && entities.size()>0 ) {
				return entities.get(0);
			}

			// EIPService.getLogService("MPM_MCA").info(user,
			// Contract.LogOper.MODULE_DEL.getOperName(), "{delContractIds : " +
			// moduleIds + "}", null, null);
		}
		return null;
	}
}
