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
import com.supporter.prj.linkworks.oa.emp_meal_apply.dao.NonEmpMealRecDao;
import com.supporter.prj.linkworks.oa.emp_meal_apply.entity.NonEmpMealRec;

@Service
public class NonEmpMealRecService {
	@Autowired
	private NonEmpMealRecDao codeDao;
	@Autowired
	private DeptMealLimitDao limitDao;

	@Autowired
	private DeptMealNonEmpsDao empDao;
	@Autowired
	private DeptMealNonEmpsRecDao empRecDao;
	
	//获取列表
	@SuppressWarnings("unchecked")
	public List<NonEmpMealRec> getGrid(UserProfile user, JqGrid jqGrid,
			String applyId, String pagetype) {
		List<NonEmpMealRec> persons = codeDao.findPage(jqGrid, applyId);
		if (pagetype != null && pagetype.equals("view")) {
			return persons;
		}
		int a = 3;
		String deptId = user.getDeptId();
		if (StringUtils.isBlank(deptId)) {
			deptId = "1";
		}
		Dept dept = user.getDept();
		String deptName = null;
		if (dept != null) {
			deptName = dept.getName();
		} else {
			deptName = "测试系统";
		}
		Double amount = 0.00;
		if (StringUtils.isNotBlank(deptName)) {
			String hql = "from " + DeptMealLimit.class.getName()
					+ " where deptName = ? ";
			List<DeptMealLimit> l = limitDao.find(hql, deptName);
			if (l != null && l.size() > 0) {
				amount = l.get(0).getLimitAmount();
			}
		}
		if (persons.size() < 1) {
			List<DeptMealNonEmps> l = empDao.findByDeptId(deptId);
			String hql = " from "
					+ DeptMealNonEmpsRec.class.getName()
					+ " where nonEmpIds = :nonEmpIds and nonEmpType = 1 and isAvailable = 1";
			for (DeptMealNonEmps deptMealNonEmps : l) {
				List<DeptMealNonEmpsRec> recs = empRecDao.findByNamedParam(hql,
						"nonEmpIds", deptMealNonEmps.getNonEmpIds());
				int i = 1;
				for (DeptMealNonEmpsRec deptMealNonEmpsRec : recs) {
					NonEmpMealRec en = new NonEmpMealRec();
					en.setEmpId(deptMealNonEmpsRec.getNonEmpId());
					en.setEmpName(deptMealNonEmpsRec.getNonEmpName());
					en.setMealAmount(amount);
					en.setMealOutAmount(0.00);
					en.setMealInAmount(amount);
					en.setRecId(com.supporter.util.UUIDHex.newId());
					en.setEmpType(deptMealNonEmpsRec.getNonEmpType());
					en.setDisplayOrder(Long.valueOf(i));
					persons.add(en);
					i++;
				}

			}
		}
		//初始化三条
		if (persons != null) {
			for (int i = 0; i < a; i++) {
				NonEmpMealRec person = new NonEmpMealRec();
				person.setRecId(com.supporter.util.UUIDHex.newId());
				person.setApplyId(applyId);
				person.setMealAmount(amount);
				person.setMealInAmount(amount);
				person.setMealOutAmount(0.00);
				persons.add(person);
			}
		}
		jqGrid.setRows(persons);
		jqGrid.setRowCount(persons.size());
		return persons;
	}
	//删除从表
	public void deleteRec(String applyId, String delIds) {
		if (StringUtils.isNotBlank(delIds)) {
			for (String delId : delIds.split(",")) {
				NonEmpMealRec rpnoDb = (NonEmpMealRec) this.codeDao.get(delId);
				if (rpnoDb != null) {
					this.codeDao.delete(delId);
				}
			}
		}
	}

	// 删除主表信息是删除从表
	public void deleteRecByPlcId(String id) {
		List<NonEmpMealRec> rec = codeDao.getPersonsByApply(id);
		codeDao.delete(rec);
	}
}
