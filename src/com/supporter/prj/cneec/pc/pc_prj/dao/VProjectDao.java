package com.supporter.prj.cneec.pc.pc_prj.dao;



import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.emp_mapping.service.EmpMappingService;
import com.supporter.prj.cneec.pc.pc_prj.constants.ProjectAuthConstant;
import com.supporter.prj.cneec.pc.pc_prj.entity.VProject;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;


/**
 * @Title: DAO
 * @Description: 功能模块表
 * @version V1.0
 * 
 */
@Repository
public class VProjectDao extends MainDaoSupport < VProject , String > {
	@Autowired
	VProjectViewAllEmpsDao vProjectViewAllEmpsDao;
	@Autowired
	VProjectViewDeptEmpsDao vProjectViewDeptEmpsDao;
	@Autowired
	VProjectOrgMemberEDao vProjectOrgMemberEDao;
	@Autowired
	EmpMappingService empMappingService;
	
	
	/**
	 *获取所有执行项目
	 * 
	 * @param 
	 * @return List <CneecVPcPrj>
	 */
	public List <VProject> getVprojects(UserProfile user,JqGrid jqGrid,String prjName,String prjStatus,VProject vProject){
		if(user != null && user.getPersonId().length() > 0){
			String personProjectAuth = getProjectAuthItem(user.getPersonId());
			//所有权限
			if(personProjectAuth.equals(ProjectAuthConstant.VIEW_ALL_PROJECT)){
				return getAllVProject(jqGrid,prjName,prjStatus,vProject);
			}else if(personProjectAuth.equals(ProjectAuthConstant.VIEW_DEPT_PROJECT)){
				//部门权限
				return getDeptVProject(jqGrid,prjName,user.getDeptId(),prjStatus,vProject);
			}else{
				//个人权限
				return getPersonalVProject(jqGrid,prjName,user.getPersonId(),prjStatus,vProject);
			}
		}else{
			return null;
		}
		
	}
	
	/**
	 * 签报中获取所有执行项目
	 * @param user
	 * @param jqGrid
	 * @param prjName
	 * @param prjStatus
	 * @param vProject
	 * @return
	 */
	public List <VProject> getVprojectsToSignedReport(UserProfile user,JqGrid jqGrid,String prjName,String prjStatus,VProject vProject){
		if(user != null && user.getPersonId().length() > 0){
			String personProjectAuth = getProjectAuthItem(user.getPersonId());
			//所有权限
			if(personProjectAuth.equals(ProjectAuthConstant.VIEW_ALL_PROJECT)){
				return getAllVProject(jqGrid,prjName,prjStatus,vProject);
			}else{
				//部门权限
				return getDeptVProject(jqGrid,prjName,user.getDeptId(),prjStatus,vProject);
			}
		}else{
			return null;
		}
		
	}
	/**
	 * 获取当前人项目权限项.
	 * @param personId
	 * @return
	 */
	public String getProjectAuthItem(String personId){
		personId = empMappingService.getEmpId(personId);//获取新老系统人员映射ID
		if(vProjectViewAllEmpsDao.getIsAllProjectAuth(personId)){
			return ProjectAuthConstant.VIEW_ALL_PROJECT;
		}else if(vProjectViewDeptEmpsDao.getIsDeptProjectAuth(personId)){
			return ProjectAuthConstant.VIEW_DEPT_PROJECT;
		}else{
			return ProjectAuthConstant.VIEW_PERSONAL_PROJECT;
		}
	}
	
	
	/**
	 *获取所有执行项目
	 * 
	 * @param 
	 * @return List <CneecVPcPrj>
	 */
	public List<VProject> getAllVProject(JqGrid jqGrid,String prjName,String prjStatus,VProject vProject) {
		if (StringUtils.isNotBlank(prjName)) {
			jqGrid.addHqlFilter(
				" prjNo = ? or prjName like ? or deptName like ? ",
				"%" + prjName + "%", "%" + prjName + "%", "%" + prjName + "%");
		}
		if(vProject!=null){
			
			if (StringUtils.isNotBlank(vProject.getDeptId())) {
				jqGrid.addHqlFilter(
					"deptId =?", vProject.getDeptId());
			}
			
			if (StringUtils.isNotBlank(vProject.getPrjCategory())) {
				jqGrid.addHqlFilter(
					"prjCategory like ?","%" + vProject.getPrjCategory() + "%");
			}
			
			if (StringUtils.isNotBlank(vProject.getPrjRank())) {
				jqGrid.addHqlFilter(
					"prjRank like ?","%" + vProject.getPrjRank() + "%");
			}
			if(vProject.getPrjStatus()!=null&&!vProject.getPrjStatus().equals("")){
				int prjStatusId=Integer.parseInt(vProject.getPrjStatus());
					jqGrid.addHqlFilter(
							"prjStatusId = ?",prjStatusId);
			}
			
			
		}
	
		//筛选项目执行状态（首页我的项目用到）
		if (StringUtils.isNotBlank(prjStatus)) {
			jqGrid.addHqlFilter(
					" prjStatusId in ("+prjStatus+")");
		}
		jqGrid.addSortPropertyDesc("to_number(prjNo)");
		//jqGrid.addSortPropertyDesc("contractInNo");
		List<VProject> list =  this.retrievePage(jqGrid);
		return list;
	}
	
	/**
	 *获取某个部门所有的执行项目
	 * 
	 * @param 
	 * @return List <CneecVPcPrj>
	 */
	public List<VProject> getDeptVProject(JqGrid jqGrid,String prjName,String deptId,String prjStatus,VProject vProject) {
		if(StringUtils.isNotBlank(deptId)){
			jqGrid.addHqlFilter("deptId= ?",deptId);
			if (StringUtils.isNotBlank(prjName)) {
				jqGrid.addHqlFilter(
					" prjNo = ? or prjName like ? or deptName like ? ",
					"%" + prjName + "%", "%" + prjName + "%", "%" + prjName + "%");
			}
			
			if(vProject!=null){
				if (StringUtils.isNotBlank(vProject.getDeptId())) {
					jqGrid.addHqlFilter(
						"deptId =?", vProject.getDeptId());
				}
				if (StringUtils.isNotBlank(vProject.getPrjCategory())) {
					jqGrid.addHqlFilter(
						"prjCategory like ?","%" + vProject.getPrjCategory() + "%");
				}
				
				if (StringUtils.isNotBlank(vProject.getPrjRank())) {
					jqGrid.addHqlFilter(
						"prjRank like ?","%" + vProject.getPrjRank() + "%");
				}
				if(vProject.getPrjStatus()!=null&&!vProject.getPrjStatus().equals("")){
					int prjStatusId=Integer.parseInt(vProject.getPrjStatus());
						jqGrid.addHqlFilter(
								"prjStatusId = ?",prjStatusId);
				}
			}
			//筛选项目执行状态（首页我的项目用到）
			if (StringUtils.isNotBlank(prjStatus)) {
				jqGrid.addHqlFilter(
						" prjStatusId in ("+prjStatus+")");
			}
			jqGrid.addSortPropertyDesc("to_number(prjNo)");
			//jqGrid.addSortPropertyDesc("contractInNo");
			List<VProject> list =  this.retrievePage(jqGrid);
			return list;
		}else{
			return null;
		}
	}
	
	/**
	 *获取符合条件的执行项目
	 * 
	 * @param 
	 * @return List <CneecVPcPrj>
	 */
	public List<VProject> getPersonalVProject(JqGrid jqGrid,String prjName,String personId,String prjStatus,VProject vProject) {
		//既没有查看所有项目的权限，也没有查看某个部门项目的权限，在有权查看的项目id的集合为空时当前登录人id也为空的情况下直接返回null
		String projectIds = "";
		personId = empMappingService.getEmpId(personId);//获取新老系统人员映射ID
		List<String> listOfProjectIds=  vProjectOrgMemberEDao.getProjectIdListByPersonId(personId);
		int count = 1;
		for(String projectId:listOfProjectIds){
			if(count == listOfProjectIds.size()){
				projectIds=projectIds+"'"+projectId+"'";
			}else{
				projectIds=projectIds+"'"+projectId+"',";
			}
			count++;
		}
		if(StringUtils.isNotBlank(personId)||StringUtils.isNotBlank(projectIds)){
			String sql_add="";
			if(StringUtils.isNotBlank(personId)){//如果当前登录人是项目经理
				sql_add+=" prjManagerId= "+personId;
			}
			if(StringUtils.isNotBlank(projectIds)){
				sql_add+=" or prjId in ("+projectIds+")";
			}
			if(StringUtils.isNotBlank(sql_add)){
				jqGrid.addHqlFilter(sql_add);
			}
			
			if (StringUtils.isNotBlank(prjName)) {
				jqGrid.addHqlFilter(
					" prjNo = ? or prjName like ? or deptName like ? ",
					"%" + prjName + "%", "%" + prjName + "%", "%" + prjName + "%");
			}
			
			if(vProject!=null){
				if (StringUtils.isNotBlank(vProject.getDeptId())) {
					jqGrid.addHqlFilter(
						"deptId =?", vProject.getDeptId());
				}
				if (StringUtils.isNotBlank(vProject.getPrjCategory())) {
					jqGrid.addHqlFilter(
						"prjCategory like ?","%" + vProject.getPrjCategory() + "%");
				}
				if (StringUtils.isNotBlank(vProject.getPrjRank())) {
					jqGrid.addHqlFilter(
						"prjRank like ?","%" + vProject.getPrjRank() + "%");
				}
				if(vProject.getPrjStatus()!=null&&!vProject.getPrjStatus().equals("")){
					int prjStatusId=Integer.parseInt(vProject.getPrjStatus());
						jqGrid.addHqlFilter(
								"prjStatusId = ?",prjStatusId);
				}
			}
			
			//筛选项目执行状态（首页我的项目用到）
			if (StringUtils.isNotBlank(prjStatus)) {
				jqGrid.addHqlFilter(
					" prjStatusId in ("+prjStatus+")");
			}
			jqGrid.addSortPropertyDesc("to_number(prjNo)");
			//jqGrid.addSortPropertyDesc("contractInNo");
			List<VProject> list =  this.retrievePage(jqGrid);
			return list;
        }else{
        	return null;
        }
	}
	
	
	
	
}
