package com.supporter.prj.dept_resource.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.dept_resource.dao.DeptResourceAuthDao;
import com.supporter.prj.dept_resource.dao.DeptResourceDao;
import com.supporter.prj.dept_resource.entity.DeptResource;
import com.supporter.prj.dept_resource.entity.DeptResourceAuth;
import com.supporter.prj.eip.dept.entity.Post;
import com.supporter.prj.eip.dept.entity.TreeNode;
import com.supporter.prj.eip.dept.service.PostService;
import com.supporter.prj.eip.dept.service.TreeNodeService;
import com.supporter.prj.eip.emp.entity.Employee;
import com.supporter.prj.eip.emp.service.EmployeeService;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.security.entity.UserProfile;

@Service
public class DeptResourceAuthService {

	@Autowired
	private DeptResourceAuthDao deptResourceAuthDao;
	@Autowired
	private DeptResourceService deptResourceServicve;

	@Autowired
	private DeptResourceDao deptResourceDao;
	@Autowired
	private TreeNodeService treeNodeService;
	@Autowired
	private PostService postService;
	@Autowired
	private EmployeeService empService;

	public DeptResourceAuth get(String moduleId) {
		return deptResourceAuthDao.get(moduleId);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息.
	 * 
	 * @param moduleId
	 * @return
	 */
	public DeptResourceAuth initEditOrViewPage(JqGrid jqGrid, String authId,
			String resourceId, String authorizeeType, String authType,
			UserProfile user) {
		DeptResourceAuth deptResourceAuth = null;
		if (StringUtils.isBlank(authId)) {// 新建
			deptResourceAuth = newDeptResourceAuth(user, resourceId);
			deptResourceAuth.setAuthType(authType);
			deptResourceAuth.setAuthorizeeType(authorizeeType);
			deptResourceAuth.setAdd(true);

		} else {// 编辑
			// 获得主表
			deptResourceAuth = deptResourceAuthDao.get(authId);
			String authorizeeId = deptResourceAuth.getAuthorizeeId();
			String authorizeeTypeZ = deptResourceAuth.getAuthorizeeType();
			String nameDesc = getNameDesc(authorizeeId, authorizeeTypeZ);
			
			// 判断是个人还是岗位还是部门
			if (authorizeeType.equals("POST")) { // 岗位
				
				deptResourceAuth.setPostName(nameDesc);
				
			} else if (authorizeeType.equals("DEPT")) {// 部门
				
				deptResourceAuth.setDeptName(nameDesc);

			} else if (authorizeeType.equals("PERSON")) {// 个人
				
				deptResourceAuth.setEmpName(nameDesc);
				
			} else {// 用户组
				
				deptResourceAuth.setRoleName(nameDesc);
				
			}
			
			
			
			//deptResourceAuth.setDeptName(nameDesc);
			deptResourceAuth.setAdd(false);

		}
		DeptResource deptResource = deptResourceServicve.initEditOrViewPage(
				jqGrid, deptResourceAuth.getResourceId(), user);
		deptResourceAuth.setDeptResource(deptResource);
		return deptResourceAuth;

	}

	// 格式化部门名称或者岗位名称或者人员姓名
	public String getNameDesc(String authorizeeId, String authorizeeType) {
		String nameDesc = "";
		if(authorizeeId!=null&&!authorizeeId.equals("")){
			// 判断是个人还是岗位还是部
			if (authorizeeType.equals("POST")) { // 岗位
				// 根据岗位id取出岗位名称
				String postName = "";
				Post post = null;
				post = postService.getById(authorizeeId);

				if (post != null) {
					postName = post.getPostNameZh();
				}
				nameDesc = postName;
			} else if (authorizeeType.equals("DEPT")) {// 部门
				// 根据部门id取出部门名称
				String deptName = "";
				TreeNode dept = null;
				dept = treeNodeService.getById(authorizeeId);
				if (dept != null) {
					deptName = dept.getDisplayNameZh();
				}
				nameDesc = deptName;

			} else if (authorizeeType.equals("PERSON")) {// 个人
				// 根据人员id取出人员姓名
				String empName = "";
				Employee emp = null;
				emp = empService.get(authorizeeId);
				if (emp != null) {
					empName = emp.getEmpName();
				}
				nameDesc = empName;
			} else {// 用户组
				//nameDesc = "用户组1";
				if(EIPService.getRoleService().getRole(authorizeeId)!=null){
					nameDesc=EIPService.getRoleService().getRole(authorizeeId).getName();
				}else{
					nameDesc="";
				}
			}
		}
		
		return nameDesc;
	}

	// 格式化部门名称或者岗位名称或者人员姓名
	public String getNameListDesc(String authorizeeId, String authorizeeType) {
		String authorizeeIdDesc = "";
		if(authorizeeId!=null&&!authorizeeId.equals("")){
			// 判断是个人还是岗位还是部门
			if (authorizeeType.equals("POST")) { // 岗位
				// 根据岗位id取出岗位名称
				String postName = "";
				Post post = null;
				post = postService.getById(authorizeeId);
				if (post != null) {
					postName = post.getPostNameZh();
				}
				authorizeeIdDesc = postName + "(岗位)";
			} else if (authorizeeType.equals("DEPT")) {// 部门
				// 根据部门id取出部门名称
				String deptName = "";
				TreeNode dept = null;
				dept = treeNodeService.getById(authorizeeId);
				if (dept != null) {
					deptName = dept.getDisplayNameZh();
				}
				authorizeeIdDesc = deptName + "(部门)";

			} else if (authorizeeType.equals("PERSON")) {// 个人
				// 根据人员id取出人员姓名
				String empName = "";
				Employee emp = null;
				emp = empService.get(authorizeeId);
				if (emp != null) {
					empName = emp.getEmpName();
				}
				authorizeeIdDesc = empName + "(个人)";
			} else if(authorizeeType.equals("GROUP")){// 用户组
				//authorizeeIdDesc = "用户组1(用户组)";
				String roleName = "";
				if(EIPService.getRoleService().getRole(authorizeeId)!=null){
					roleName=EIPService.getRoleService().getRole(authorizeeId).getName();
					authorizeeIdDesc=roleName+"(用户组)";
				}else{
					authorizeeIdDesc=roleName+"(用户组)";
				}
			}
		}		
		return authorizeeIdDesc;
	}

	// 格式化权限名称
	public String getAuthDesc(String fullAccess, String canRead, String canNew,
			String canWrite, String canDelete) {
		String authDesc = "";
		if(fullAccess==null){
			fullAccess="0";
		}
		if(canRead==null){
			canRead="0";
		}
		if(canNew==null){
			canNew="0";
		}
		if(canWrite==null){
			canWrite="0";
		}
		if(canDelete==null){
			canDelete="0";
		}
		if (fullAccess.equals("1")) {
			authDesc += "完全控制 ";
		} else {
			if (canRead.equals("1")) {
				authDesc += "可读取 ";
			}
			if (canNew.equals("1")) {
				authDesc += "可新建 ";
			}
			if (canWrite.equals("1")) {
				authDesc += "可编辑 ";
			}
			if (canDelete.equals("1")) {
				authDesc += "可删除 ";
			}
		}
		return authDesc;
	}

	/**
	 * 新建实例,并初始化必要的属性.
	 * 
	 * @param auserprf_U
	 * @return
	 */
	public DeptResourceAuth newDeptResourceAuth(UserProfile auserprf_U,
			String resourceId) {
		DeptResourceAuth ldeptResourceAuth_N = new DeptResourceAuth();
		ldeptResourceAuth_N.setAuthId(com.supporter.util.UUIDHex.newId());
		ldeptResourceAuth_N.setResourceId(resourceId);
		return ldeptResourceAuth_N;
	}

	/**
	 * 分页表格展示数据.
	 * 
	 * @param user
	 *            用户信息
	 * @param jqGridReq
	 *            jqgrid请求对象
	 * @param moduleIds
	 *            多个逗号分隔
	 * @return JqGrid
	 */
	public List<DeptResourceAuth> getGrid(UserProfile user, JqGrid jqGrid,
			DeptResourceAuth deptResourceAuth) {
		List<DeptResourceAuth> list1 = new ArrayList<DeptResourceAuth>();

		List<DeptResourceAuth> list = this.deptResourceAuthDao.findPage(jqGrid,
				deptResourceAuth);
		if (list != null) {
			for (DeptResourceAuth deptResourceAuthZ : list) {
				String fullAccess = deptResourceAuthZ.getFullAccess();
				String canRead = deptResourceAuthZ.getCanRead();
				String canNew = deptResourceAuthZ.getCanNew();
				String canWrite = deptResourceAuthZ.getCanWrite();
				String canDelete = deptResourceAuthZ.getCanDelete();
				String authDesc = getAuthDesc(fullAccess, canRead, canNew,
						canWrite, canDelete);
				deptResourceAuthZ.setAuthDesc(authDesc);
				// 可以根据这个被授权者的id获取被授权者的名称
				String authorizeeId = deptResourceAuthZ.getAuthorizeeId();
				String authorizeeTypeZ = deptResourceAuthZ.getAuthorizeeType();
				String authorizeeIdDesc = getNameListDesc(authorizeeId,
						authorizeeTypeZ);
				deptResourceAuthZ.setAuthorizeeIdDesc(authorizeeIdDesc);
				list1.add(deptResourceAuthZ);
			}
		}
		// 让管理员显示在被授权者的列表之中
		DeptResourceAuth deptResourceAuthMan = new DeptResourceAuth();
		DeptResource deptResource = this.deptResourceDao.get(deptResourceAuth
				.getResourceId());
		String managerName = deptResource.getManagerName();
		deptResourceAuthMan.setAuthorizeeIdDesc(managerName);
		deptResourceAuthMan.setAuthDesc("因为是管理员所以默认地拥有全部权限");
		list1.add(deptResourceAuthMan);
		jqGrid.setRows(list1);
		return list1;

	}

	/**
	 * 保存或更新
	 * 
	 * @param user
	 *            用户信息
	 * @param module
	 *            实体类
	 * @return
	 */
	public DeptResourceAuth saveOrUpdate(UserProfile user,
			DeptResourceAuth deptResourceAuth, Map<String, Object> valueMap) {
		DeptResourceAuth ret = null;
		if (deptResourceAuth.getAdd()) {// 新建
			// 保存主表
			this.deptResourceAuthDao.save(deptResourceAuth);
			ret = deptResourceAuth;
			// 记录日志
			/*
			 * MaterialCodeUtils.saveMaterialCodeOperateLog(user, code,
			 * MaterialCode.LogOper.MATERIALCODE_ADD.getOperName(), null);
			 */
		} else {// 编辑
			// 编辑之后保存主表
			this.deptResourceAuthDao.update(deptResourceAuth);
			ret = deptResourceAuth;
			// 记录日志
			/*
			 * MaterialCodeUtils.saveMaterialCodeOperateLog(user, code,
			 * MaterialCode.LogOper.MATERIALCODE_EDIT.getOperName(), null);
			 */
		}
		return ret;

	}

	/**
	 * 删除
	 * 
	 * @param user
	 *            用户信息
	 * @param moduleIds
	 *            主键集合，多个以逗号分隔
	 */
	public void delete(UserProfile user, String authIds) {
		if (StringUtils.isNotBlank(authIds)) {
			for (String authId : authIds.split(",")) {
				// 删除用印主表
				this.deptResourceAuthDao
						.delete(deptResourceAuthDao.get(authId));
			}
			// EIPService.getLogService("MPM_MCA").info(user,
			// Contract.LogOper.MODULE_DEL.getOperName(), "{delContractIds : " +
			// moduleIds + "}", null, null);
		}
	}
}
