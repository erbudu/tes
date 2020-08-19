package com.supporter.prj.linkworks.oa.emp_meal_apply.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.person.entity.Person;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.dept_meal_limit.dao.DeptMealLimitDao;
import com.supporter.prj.linkworks.oa.dept_meal_limit.entity.DeptMealLimit;
import com.supporter.prj.linkworks.oa.emp_meal_apply.dao.EmpMealRecDao;
import com.supporter.prj.linkworks.oa.emp_meal_apply.entity.EmpMealRec;

@Service
public class EmpMealRecService {
	@Autowired
	private EmpMealRecDao codeDao;
	@Autowired
	private DeptMealLimitDao limitDao;
	public List<EmpMealRec> getGrid(UserProfile user, JqGrid jqGrid,
			String applyId, String pagetype) {
		List<EmpMealRec> persons = codeDao.findPage(jqGrid, applyId);
		if (pagetype != null && pagetype.equals("view")) {
			return persons;
		}
		int a = 3;
		String deptId = user.getDeptId();
		Dept dept = user.getDept();
		String deptName = null;
		List<Person> per = new ArrayList<Person>();
		if(dept!=null){
			deptName = dept.getName();
			boolean incSubDepts=true ;
			per = EIPService.getEmpService().getEmps(dept, incSubDepts);
		}else{
			deptName = "测试系统";
		}
		Double amount = 0.00;
		if(StringUtils.isNotBlank(deptName)){
			String hql = "from "+ DeptMealLimit.class.getName()+" where deptName = ? ";
			List<DeptMealLimit> l = limitDao.find(hql, deptName);
			if(l!=null&&l.size()>0){
				amount = l.get(0).getLimitAmount();
			}
		}
		if (persons.size() < 1) {
			int i = 1 ; 
			for (Person pe : per) {
				EmpMealRec person = new EmpMealRec();
				person.setRecId(com.supporter.util.UUIDHex.newId());
				person.setDisplayOrder(Long.valueOf(i));
				person.setEmpId(pe.getPersonId());
				person.setEmpNo(pe.getPersonNo());
				person.setEmpName(pe.getName());
				person.setMealAmount(amount);
				person.setMealInAmount(amount);
				person.setMealOutAmount(0.00);
				persons.add(person);
				i++;
			}
		}
		//初始化三条
		if (persons != null) {
			for (int i = 0; i < a; i++) {
				EmpMealRec person = new EmpMealRec();
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

	public void deleteRec(String applyId, String delIds) {
		if (StringUtils.isNotBlank(delIds)) {
			for (String delId : delIds.split(",")) {
				EmpMealRec rpnoDb = (EmpMealRec) this.codeDao.get(delId);
				if (rpnoDb != null) {
					this.codeDao.delete(delId);
				}
			}
		}
	}

	// 删除主表信息是删除从表
	public void deleteRecByPlcId(String id) {
		List<EmpMealRec> rec = codeDao.getPersonsByApply(id);
		codeDao.delete(rec);
	}
}
