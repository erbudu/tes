package com.supporter.prj.cneec.tpc.register_project.dao;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.register_project.entity.RegisterProject;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * @Title: RegisterProjectDao
 * @Description: DAO类
 * @author: yanweichao
 * @date: 2017-8-30
 * @version: V1.0
 */
@Repository
public class RegisterProjectDao extends MainDaoSupport<RegisterProject, String> {

	/**
	 * 分页查询
	 */
	public List<RegisterProject> findPage(JqGrid jqGrid, RegisterProject registerProject, String authFilter) {
		if (registerProject != null) {
			String keyword = registerProject.getKeyword();
			if (StringUtils.isNotBlank(keyword)) {
				jqGrid.addHqlFilter(" deptName like ? or projectNo like ? or projectName like ? or purchaseContent like ? or customerName like ?",
						"%" + keyword + "%",
						"%" + keyword + "%",
						"%" + keyword + "%",
						"%" + keyword + "%",
						"%" + keyword + "%");
			}
			/* 以下是更多条件中选择项 */
			// 项目性质过滤
			if (registerProject.getProjectNatureId() != null) {
				jqGrid.addHqlFilter(" projectNatureId= ? ", registerProject.getProjectNatureId());
			}
			// 项目类别过滤
			if (registerProject.getProjectCategoryId() != null) {
				jqGrid.addHqlFilter(" projectCategoryId= ? ", registerProject.getProjectCategoryId());
			}
			// 采购类型过滤
			if (registerProject.getPurchaseTypeId() != null) {
				jqGrid.addHqlFilter(" purchaseTypeId= ? ", registerProject.getPurchaseTypeId());
			}
			// 状态过滤
			if (registerProject.getSwfStatus() != null) {
				jqGrid.addHqlFilter(" swfStatus= ? ", registerProject.getSwfStatus());
			}
			// 根据创建时间倒序排列
			jqGrid.addSortPropertyDesc("createdDate");
		}
		jqGrid.addHqlFilter(authFilter);
		return this.retrievePage(jqGrid);
	}
	
	/**
	 * 选择项目控件中非草稿状态的分页查询
	 * @param jqGrid
	 * @param registerProject
	 * @param authFilter
	 * @return
	 */
	public List<RegisterProject> findSelectPage(JqGrid jqGrid, RegisterProject registerProject, String authFilter) {
		if (registerProject != null) {
			String keyword = registerProject.getKeyword();
			if (StringUtils.isNotBlank(keyword)) {
				jqGrid.addHqlFilter(" projectDeptName like ? or projectNo like ? or projectName like ?",
						"%" + keyword + "%",
						"%" + keyword + "%",
						"%" + keyword + "%");
			}
			//排除草稿状态
			jqGrid.addHqlFilter(" swfStatus <> ? ", RegisterProject.DRAFT);
		}
		jqGrid.addHqlFilter(authFilter);
		return this.retrievePage(jqGrid);
	}

	/**
	 * 检查是否有重复项目名称
	 *
	 * @param projectId
	 * @param projectName
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public boolean checkNameIsValid(String projectId, String projectName) {
		String hql = "from " + RegisterProject.class.getName() + " where projectName = ?";
		List retList = null;
		if (StringUtils.isBlank(projectId)) {// 新建时
			retList = this.retrieve(hql, projectName);
		} else {// 编辑时
			hql += " and projectId != ?";
			retList = this.retrieve(hql, projectName, projectId);
		}
		if (CollectionUtils.isEmpty(retList)) {
			return true;
		}
		return false;
	}

	/**
	 * 检查是否有重复项目代码
	 *
	 * @param projectId
	 * @param projectAbbreviation
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public boolean checkAbbreviationIsValid(String projectId, String projectAbbreviation) {
		String hql = "from " + RegisterProject.class.getName() + " where projectAbbreviation = ?";
		List retList = null;
		if (StringUtils.isBlank(projectId)) {// 新建时
			retList = this.retrieve(hql, projectAbbreviation);
		} else {// 编辑时
			hql += " and projectId != ?";
			retList = this.retrieve(hql, projectAbbreviation, projectId);
		}
		if (CollectionUtils.isEmpty(retList)) {
			return true;
		}
		return false;
	}

	/**
	 * 根据部门id获取项目集合
	 * @param deptId
	 * @return
	 */
	public List<RegisterProject> getByDeptId(String deptId) {
		String hql = " from " + RegisterProject.class.getName() + " where deptId = ?";
		List<RegisterProject> list = this.find(hql, deptId);
		return list;
	}

	/**
	 * 根据项目所属部门id获取项目集合
	 * @param deptId
	 * @return
	 */
	public List<RegisterProject> getByProjectDeptId(String projectDeptId) {
		String hql = " from " + RegisterProject.class.getName() + " where projectDeptId = ?";
		List<RegisterProject> list = this.find(hql, projectDeptId);
		return list;
	}

}
