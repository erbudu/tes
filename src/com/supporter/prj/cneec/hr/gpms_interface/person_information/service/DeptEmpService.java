package com.supporter.prj.cneec.hr.gpms_interface.person_information.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.supporter.prj.eip.dept.entity.DeptTreeVO;
import com.supporter.prj.eip.dept.util.Params;
import com.supporter.prj.eip.emp.entity.Employee;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.person.entity.Person;
import com.supporter.util.CommonUtil;

@Service("hrDeptEmpService")
public class DeptEmpService {

	public List<DeptTreeVO> getTreeData(List<DeptTreeVO> list, Params paramMap) {
		List<DeptTreeVO> l = new ArrayList<DeptTreeVO>();
		l.addAll(list);
		String nodeTyoe = paramMap.getPara("nodeType");
		String pId = paramMap.getPara("id");
		if(StringUtils.isBlank(pId)){
			return l;
		}
		// 如果是部门的话
		if (nodeTyoe.equals("2")) {
			Dept dept = EIPService.getDeptService().getDept(pId);
			List<Person> per = new ArrayList<Person>();
			boolean incSubDepts = true;
			per = EIPService.getEmpService().getEmps(dept, incSubDepts);
			if (per != null) {
				for (Person person : per) {
					String personNo = "";
					Employee employee = (Employee) EIPService.getEmpService()
							.getEmp(CommonUtil.trim(person.getPersonId()));
					if (employee != null && employee.getIdCard() != null) {
						personNo = employee.getIdCard();
					}
					DeptTreeVO vo = new DeptTreeVO();
					vo.setId(person.getPersonId());
					vo.setpId(pId);
					vo.setName(person.getName());
					vo.setNodeId(person.getPersonId());
					vo.setDisplayName(person.getName());
					vo.setNodeName(person.getName());
					vo.setIcon("img/platformIcon.png");
					vo.setTreeId("ORG_TREE");
					vo.setIsActive(true);
					vo.setIsLeaf(true);
					vo.setParent(pId);
					vo.setCompanyNo(dept.getDeptNo());
					vo.setPersonInfoSrc(personNo);
					l.add(vo);
				}
			}
		}

		return l;
	}

}
