package com.supporter.prj.linkworks.oa.emp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.supporter.prj.eip.dept.entity.DeptTreeVO;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.person.entity.Person;

@Service("oaDeptEmpService")
public class DeptEmpService {

	public List<DeptTreeVO> getTreeData(List<DeptTreeVO> list) {
		List<DeptTreeVO> l = new ArrayList<DeptTreeVO>();
		for (DeptTreeVO deptTreeVO : list) {
			l.add(deptTreeVO);
			int nodeTyoe = deptTreeVO.getNodeType();
			// 如果是部门的话
			if (nodeTyoe == 2) {
				Dept dept = EIPService.getDeptService().getDept(
						deptTreeVO.getNodeId());
				List<Person> per = new ArrayList<Person>();
				boolean incSubDepts = true;
				per = EIPService.getEmpService().getEmps(dept, incSubDepts);
				if (per != null) {
					for (Person person : per) {
						DeptTreeVO vo = new DeptTreeVO();
						vo.setId(person.getPersonId());
						vo.setpId(deptTreeVO.getId());
						vo.setName(person.getName());
						vo.setNodeId(person.getPersonId());
						vo.setDisplayName(person.getName());
						vo.setNodeName(person.getName());
						vo.setIcon("img/platformIcon.png");
						vo.setTreeId("ORG_TREE");
						vo.setIsActive(true);
						vo.setIsLeaf(true);
						vo.setParent(deptTreeVO.getId());
						vo.setCompanyNo(deptTreeVO.getCompanyNo());						
						l.add(vo);
					}
				}
			}
		}
		return l;
	}

}
