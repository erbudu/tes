package com.supporter.prj.cneec.tpc.util;

import java.util.List;

import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.person.entity.Person;
import com.supporter.prj.eip_service.role.entity.Role;

/**
 * @Title: RoleUtil
 * @Description: 获取角色人员工具类
 * @author: yanweichao
 * @date: 2018-3-29
 * @version: V1.0
 */
public class RoleUtil {

	public static final String ROLE_ID_JINGYING = "EXAM_CONTRACT_YU";// 事业部经营经理

	/**
	 * 根据角色编号获取所有角色人员
	 * 
	 * @param roleId
	 * @return
	 */
	public static List<Person> getRolePersonsByRoleId(String roleId) {
		List<Person> list = EIPService.getRoleService().getPersonFromRole(roleId, null);
		return list;
	}

	/**
	 * 根据角色ID和部门ID获取所有负责该部门的角色人员
	 * 
	 * @param roleId
	 * @param deptId
	 * @return
	 */
	public static List<Person> getRolePersonsByDeptId(String roleId, String deptId) {
		Role role = EIPService.getRoleService().getRole(roleId);
		Dept dept = EIPService.getDeptService().getDept(deptId);
		return getRolePersonsByDept(role, dept);
	}

	/**
	 * 根据角色ID和部门获取所有负责该部门的角色人员
	 * 
	 * @param roleId
	 * @param dept
	 * @return
	 */
	public static List<Person> getRolePersonsByDept(String roleId, Dept dept) {
		Role role = EIPService.getRoleService().getRole(roleId);
		return getRolePersonsByDept(role, dept);
	}

	/**
	 * 根据角色和部门获取所有负责该部门的角色
	 * 
	 * @param role
	 * @param dept
	 * @return
	 */
	public static List<Person> getRolePersonsByDept(Role role, Dept dept) {
		return EIPService.getRoleService().getPersonsForDept(role, dept);
	}

}
