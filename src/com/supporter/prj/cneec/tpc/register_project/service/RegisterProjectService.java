package com.supporter.prj.cneec.tpc.register_project.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.cneec.tpc.constant.AuthConstant;
import com.supporter.prj.cneec.tpc.entityIdMipping.service.EntityIdMippingService;
import com.supporter.prj.cneec.tpc.register_project.dao.RegisterProjectDao;
import com.supporter.prj.cneec.tpc.register_project.entity.RegisterProject;
import com.supporter.prj.cneec.tpc.register_project.util.RegisterProjectConstant;
import com.supporter.prj.cneec.tpc.util.AuthUtil;
import com.supporter.prj.cneec.tpc.util.CheckBoxVO;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.util.CommonUtil;
import com.supporter.util.UUIDHex;

/**
 * @Title: RegisterProjectService
 * @Description: 业务操作类
 * @author: yanweichao
 * @date: 2017-8-30
 * @version: V1.0
 */
@Service
@Transactional(TransManager.APP)
public class RegisterProjectService {

	@Autowired
	private RegisterProjectDao registerProjectDao;
	
	@Autowired
	private EntityIdMippingService entityIdMippingService;

	/**
	 * 数据显示权限过滤
	 * @param userProfile
	 * @return
	 */
	public String getAuthFilter(UserProfile userProfile) {
		String authFilter = AuthUtil.getAuthFilter(userProfile, RegisterProject.MODULE_ID, AuthConstant.AUTH_OPER_NAME_DATASHOW);
		return authFilter;
	}

	/**
	 * 数据维护权限过滤
	 * @param userProfile
	 * @param registerProject
	 */
	public void getAuthCanExecute(UserProfile userProfile, RegisterProject registerProject) {
		// 权限验证
		AuthUtil.canExecute(userProfile, AuthConstant.AUTH_OPER_NAME_SETVAL, RegisterProject.MODULE_ID, registerProject.getProjectId(), registerProject);
	}

	/**
	 * 获取注册项目对象集合
	 * @param user
	 * @param jqGrid
	 * @param registerProject
	 * @return
	 */
	public List<RegisterProject> getGrid(UserProfile user, JqGrid jqGrid, RegisterProject registerProject) {
		String authFilter = getAuthFilter(user);
		return this.registerProjectDao.findPage(jqGrid, registerProject, authFilter);
	}
	
	/**
	 * 选择项目控件中非草稿状态的分页查询
	 * @param user
	 * @param jqGrid
	 * @param registerProject
	 * @return
	 */
	public List<RegisterProject> getSelectGrid(UserProfile user, JqGrid jqGrid, RegisterProject registerProject) {
		String authFilter = getAuthFilter(user);
		return this.registerProjectDao.findSelectPage(jqGrid, registerProject, authFilter);
	}

	/**
	 * 获取单个注册项目对象
	 * @param projectId
	 * @return
	 */
	public RegisterProject get(String projectId) {
		return this.registerProjectDao.get(projectId);
	}

	/**
	 * 新建注册项目对象
	 * @param user
	 * @return
	 */
	public RegisterProject newRegisterProject(UserProfile user) {
		RegisterProject registerProject = new RegisterProject();
		loadRegisterProject(registerProject, user);
		registerProject.setSwfStatus(RegisterProject.DRAFT);
		return registerProject;
	}

	/**
	 * 添加基础信息
	 * @return
	 */
	public RegisterProject loadRegisterProject(RegisterProject registerProject, UserProfile user) {
		registerProject.setCreatedBy(user.getName());
		registerProject.setCreatedById(user.getPersonId());
		registerProject.setCreatedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		registerProject.setModifiedBy(user.getName());
		registerProject.setModifiedById(user.getPersonId());
		registerProject.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		Dept dept = user.getDept();
		if (dept != null) {
			registerProject.setDeptName(dept.getName());
			registerProject.setDeptId(dept.getDeptId());
//			registerProject.setProjectDeptName(dept.getName());
//			registerProject.setProjectDeptId(dept.getDeptId());
		}
		// 设置状态
		registerProject.setSwfStatus(RegisterProject.DRAFT);
		return registerProject;
	}

	/**
	 * 为编辑或查看页面初始化对象
	 * @param projectId
	 * @param user
	 * @return
	 */
	public RegisterProject initEditOrViewPage(String projectId, UserProfile user) {
		RegisterProject registerProject;
		if (StringUtils.isBlank(projectId)) {
			registerProject = newRegisterProject(user);
			registerProject.setProjectId(UUIDHex.newId());
			registerProject.setAdd(true);
		} else {
			registerProject = (RegisterProject) this.registerProjectDao.get(projectId);
			registerProject.setAdd(false);
		}
		return registerProject;
	}

	/**
	 * 保存或修改对象
	 * @param user
	 * @param registerProject
	 * @param valueMap
	 * @return
	 */
	public RegisterProject saveOrUpdate(UserProfile user, RegisterProject registerProject, Map<String, Object> valueMap) {
		if (registerProject.getAdd()) {
			// 装配基础信息
			loadRegisterProject(registerProject, user);
			this.registerProjectDao.save(registerProject);
		} else {
			// 设置更新时间
			registerProject.setModifiedBy(user.getName());
			registerProject.setModifiedById(user.getPersonId());
			registerProject.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			this.registerProjectDao.update(registerProject);
		}
		return registerProject;
	}

	/**
	 * 保存提交
	 * @param user
	 * @param registerProject
	 * @param valueMap
	 * @return
	 */
	public RegisterProject commit(UserProfile user, RegisterProject registerProject, Map<String, Object> valueMap) {
		// 生成编号
		if (StringUtils.isBlank(registerProject.getProjectNo())) {
			registerProject.setProjectNo(generatorProjectNo(registerProject));
		}
		// 设置自营贸易/代理贸易
		Map<String, String> map = RegisterProjectConstant.getProjectClassificationMap();
		String projectNatureId = CommonUtil.trim(registerProject.getProjectNatureId());
		if (projectNatureId.contains(RegisterProjectConstant.SELF_SUPPORT)) {
			registerProject.setProjectClassificationId(RegisterProjectConstant.SELF_SUPPORT);
			registerProject.setProjectClassification(map.get(RegisterProjectConstant.SELF_SUPPORT));
		} else {
			registerProject.setProjectClassificationId(RegisterProjectConstant.AGENT);
			registerProject.setProjectClassification(map.get(RegisterProjectConstant.AGENT));
		}
		saveOrUpdate(user, registerProject, valueMap);
		// 注册项目不需要审批,直接置为下一步节点状态
		registerProject.setSwfStatus(RegisterProject.PROCESSING);
		this.registerProjectDao.update(registerProject);
		//设置主键映射，为开立保函使用
		this.entityIdMippingService.createEntityIdMipping(registerProject.getProjectId(), RegisterProject.MODULE_ID);
		// 记录日志
		//EIPService.getLogService("PURCHASE_APPLY").info(user, Application.LogOper.MODULE_ADD.getOperName(), "{EditApplication : " + registerProject + "}", null, null);
		return registerProject;
	}

	/**
	 * 终止项目
	 * @param user 操作用户
	 * @param projectId 项目ID
	 * @return
	 */
	public boolean termination(UserProfile user, String projectId) {
		RegisterProject registerProject = this.get(projectId);
		if (registerProject != null) {
			registerProject.setSwfStatus(RegisterProject.TERMINATION);
			if (user != null) {
				registerProject.setModifiedBy(user.getName());
				registerProject.setModifiedById(user.getPersonId());
			}
			// 设置更新时间
			registerProject.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			this.registerProjectDao.update(registerProject);
			return true;
		}
		return false;
	}

	/**
	 * 生成项目编号
	 * 编号规则：CNEEC+M+年度+部门编号+项目英文简拼+顺序号
	 * @return
	 */
	public synchronized String generatorProjectNo(RegisterProject project) {
		String projectNo = null;
		Calendar date = Calendar.getInstance();
		String NoHead = "CNEECM" + String.valueOf(date.get(Calendar.YEAR)) + CommonUtil.trim(project.getProjectDeptNo()) + CommonUtil.trim(project.getProjectAbbreviation());
		projectNo = NoHead;
		return projectNo;
	}

	/**
	 * 批量删除对象
	 * @param user
	 * @param projectIds
	 */
	public void delete(UserProfile user, String projectIds) {
		if (StringUtils.isNotBlank(projectIds)) {
			RegisterProject registerProject;
			for (String projectId : projectIds.split(",")) {
				registerProject = this.get(projectId);
				if (registerProject == null) continue;
				// 权限验证
				this.getAuthCanExecute(user, registerProject);
				this.registerProjectDao.delete(registerProject);
			}
		}
	}

	/**
	 * 是否有框架协议单选框
	 * @param projectId
	 * @return
	 */
	public List<CheckBoxVO> getFrameworkAgreementList(String projectId) {
		List<CheckBoxVO> list = new ArrayList<CheckBoxVO>();
		int choose = -1;// 不设置默认
		if (projectId.length() > 0) {
			RegisterProject project = this.get(projectId);
			if (project != null) {
				choose = project.isHasFrameworkAgreement() ? 1 : 0;
			}
		}
		Map<Integer, String> map = RegisterProjectConstant.getFrameworkAgreementMap();
		for (Integer key : map.keySet()) {
			CheckBoxVO vo = new CheckBoxVO("hasFrameworkAgreement_" + key, "hasFrameworkAgreement", key.toString(), map.get(key), key == choose);
			list.add(vo);
		}
		return list;
	}

	/**
	 * 判断字符串数组是否包含特定值
	 * @param arr
	 * @param tarVal
	 * @return
	 */
	public static boolean existByLoop(String[] arr, String tarVal) {
		for (String s : arr) {
			if (s.equals(tarVal)) return true;
		}
		return false;
	}

	/**
	 * 判断项目名称是否重复
	 * @param projectId
	 * @param projectName
	 * @return
	 */
	public boolean checkNameIsValid(String projectId, String projectName) {
		return this.registerProjectDao.checkNameIsValid(projectId, projectName);
	}

	/**
	 * 判断项目代码是否重复
	 *
	 * @param projectId
	 * @param projectAbbreviation
	 * @return
	 */
	public boolean checkAbbreviationIsValid(String projectId, String projectAbbreviation) {
		return this.registerProjectDao.checkAbbreviationIsValid(projectId, projectAbbreviation);
	}

}
