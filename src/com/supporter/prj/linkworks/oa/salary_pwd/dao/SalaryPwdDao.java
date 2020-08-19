package com.supporter.prj.linkworks.oa.salary_pwd.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.linkworks.oa.salary_pwd.entity.SalaryPwd;

/**
 * @Title: Entity
 * @Description: 功能模块�?
 * @author liyinfeng
 * @date 2017-03-15 16:25:07
 * @version V1.0
 * 
 */
@Repository
public class SalaryPwdDao extends MainDaoSupport<SalaryPwd, String> {

	/**
	 * 分页查询
	 */
	public List<SalaryPwd> findPage(JqGrid jqGrid, SalaryPwd salaryPwd) {
		if (salaryPwd != null) {
			String userName = salaryPwd.getUserName();
			jqGrid.addHqlFilter(" userName = ? ", userName);

			String salaryPass = salaryPwd.getSalaryPwd();
			jqGrid.addHqlFilter(" salaryPwd = ? ",  salaryPass );

		}
		return this.retrievePage(jqGrid);
	}
	
	/**
	 * 判断该用户是否是第一次查询工资
	 */
	public List<SalaryPwd> getBySalaryName(JqGrid jqGrid, SalaryPwd salaryPwd) {
		if (salaryPwd != null) {
			String userName = salaryPwd.getUserName();
			jqGrid.addHqlFilter(" userName = ? ", userName);
		}
		return this.retrievePage(jqGrid);
	}

//	// 
//	public List<Object[]> findSalaryPwd() {
//		String hql = "select roomId,roomName from "
//				+ SalaryPwd.class.getName();
//		List<Object[]> list = this.find(hql);
//		return list;
//	}

}
