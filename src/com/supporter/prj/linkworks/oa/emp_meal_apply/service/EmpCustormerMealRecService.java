package com.supporter.prj.linkworks.oa.emp_meal_apply.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.dept_meal_limit.dao.DeptMealLimitDao;
import com.supporter.prj.linkworks.oa.dept_meal_limit.entity.DeptMealLimit;
import com.supporter.prj.linkworks.oa.dept_meal_non_emps.dao.DeptMealNonEmpsDao;
import com.supporter.prj.linkworks.oa.dept_meal_non_emps.dao.DeptMealNonEmpsRecDao;
import com.supporter.prj.linkworks.oa.dept_meal_non_emps.entity.DeptMealNonEmps;
import com.supporter.prj.linkworks.oa.dept_meal_non_emps.entity.DeptMealNonEmpsRec;
import com.supporter.prj.linkworks.oa.emp_meal_apply.dao.EmpCustormerMealRecDao;
import com.supporter.prj.linkworks.oa.emp_meal_apply.entity.EmpCustormerMealRec;
import com.supporter.prj.linkworks.oa.emp_meal_apply.entity.EmpMealRec;

@Service
public class EmpCustormerMealRecService {
	@Autowired
	private EmpCustormerMealRecDao codeDao;
	@Autowired
	private DeptMealLimitDao limitDao;

	@Autowired
	private DeptMealNonEmpsDao empDao;
	@Autowired
	private DeptMealNonEmpsRecDao empRecDao;
	public List<EmpCustormerMealRec> getGrid(UserProfile user, JqGrid jqGrid,
			String applyId, String pagetype) {
		List<EmpCustormerMealRec> persons = codeDao.findPage(jqGrid, applyId);
		if (pagetype != null && pagetype.equals("view")) {
			return persons;
		}
		int a = 3;
		String deptId = user.getDeptId();
		if(deptId==null){
			deptId = "1" ;
		}
		Dept dept = user.getDept();
		String deptName = null;
		if (dept != null) {
			deptName = dept.getName();
		} else {
			deptName = "测试系统";
		}
		Double amount = 0.00;
/*		if (StringUtils.isNotBlank(deptName)) {
			String hql = "from " + DeptMealLimit.class.getName()
					+ " where deptName = ? ";
			List<DeptMealLimit> l = limitDao.find(hql, deptName);
			if(l!=null&&l.size()>0){
				amount = l.get(0).getLimitAmount();
			}
		}*/
/*		if (persons.size() < 1) {
			List<DeptMealNonEmps> l = empDao.findByDeptId(deptId);
			String hql = " from "
					+ DeptMealNonEmpsRec.class.getName()
					+ " where nonEmpIds = :nonEmpIds and nonEmpType != 1 and isAvailable = 1";
			for (DeptMealNonEmps deptMealNonEmps : l) {
				List<DeptMealNonEmpsRec> recs = empRecDao.findByNamedParam(hql,
						"nonEmpIds", deptMealNonEmps.getNonEmpIds());
				int i =	1;
				for (DeptMealNonEmpsRec deptMealNonEmpsRec : recs) {
					EmpCustormerMealRec en = new EmpCustormerMealRec();
					en.setCustormerId(deptMealNonEmpsRec.getNonEmpId());
					//en.setCustormerName(deptMealNonEmpsRec.getNonEmpName());
					en.setEmpType(String.valueOf(deptMealNonEmpsRec.getNonEmpType()));
					en.setMealAmount(amount);
					en.setRecId(com.supporter.util.UUIDHex.newId());
					en.setDisplayOrder(Long.valueOf(i));
					persons.add(en);
					i++;
				}

			}
		}*/
		//编辑时添加三条
		if (persons != null) {
			for (int i = 0; i < a; i++) {
				EmpCustormerMealRec person = new EmpCustormerMealRec();
				person.setRecId(com.supporter.util.UUIDHex.newId());
				person.setApplyId(applyId);
				person.setMealAmount(amount);
				persons.add(person);
			}
		}
		jqGrid.setRows(persons);
		jqGrid.setRowCount(persons.size());
		return persons;
	}

	public void deleteRec(String applyId, String delIds) {
		if (StringUtils.isNotBlank(delIds)) {
			for (String delId : delIds.split(",")) {
				EmpCustormerMealRec rpnoDb = (EmpCustormerMealRec) this.codeDao
						.get(delId);
				if (rpnoDb != null) {
					this.codeDao.delete(delId);
				}
			}
		}
	}

	// 删除主表信息时删除从表
	public void deleteRecByPlcId(String id) {
		List<EmpCustormerMealRec> rec = codeDao.getPersonsByApply(id);
		codeDao.delete(rec);
	}
}
