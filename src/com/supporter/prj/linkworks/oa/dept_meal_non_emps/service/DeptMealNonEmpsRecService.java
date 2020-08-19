package com.supporter.prj.linkworks.oa.dept_meal_non_emps.service;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.dept_meal_non_emps.dao.DeptMealNonEmpsRecDao;
import com.supporter.prj.linkworks.oa.dept_meal_non_emps.entity.DeptMealNonEmpsRec;


@Service
public class DeptMealNonEmpsRecService {
	@Autowired
	private DeptMealNonEmpsRecDao codeDao;
	@SuppressWarnings("unchecked")
	public List<DeptMealNonEmpsRec> getGrid(UserProfile user, JqGrid jqGrid,
			String nonEmpIds,String pagetype) {
		List<DeptMealNonEmpsRec> persons= codeDao.findPage(jqGrid, nonEmpIds);
		if(pagetype!=null&&pagetype.equals("view")){
			return persons;
		}
		int a = 3;
		if(persons!=null){
			for (int i = 0; i < a ;i++) {
				DeptMealNonEmpsRec person = new DeptMealNonEmpsRec();
				person.setNonEmpId(com.supporter.util.UUIDHex.newId());
				persons.add(person);
			}		
		}
		jqGrid.setRows(persons);
		jqGrid.setRowCount(jqGrid.getRowCount()+a);
		return persons;
	}

	
	public void deleteRec(String nonEmpIds, String delIds) {
		if (StringUtils.isNotBlank(delIds)) {
			for (String delId : delIds.split(",")) {
				DeptMealNonEmpsRec rpnoDb = (DeptMealNonEmpsRec) this.codeDao
						.get(delId);
				if (rpnoDb != null) {
					this.codeDao.delete(delId);
				}
			}
		}
	}
	//删除主表信息是删除从表
	public void deleteRecByPlcId(String id) {
		List<DeptMealNonEmpsRec> rec = codeDao
				.getPersonsByApply(id);
		codeDao.delete(rec);
	}
}
