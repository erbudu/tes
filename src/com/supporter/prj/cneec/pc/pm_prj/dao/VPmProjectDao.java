package com.supporter.prj.cneec.pc.pm_prj.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.emp_mapping.service.EmpMappingService;
import com.supporter.prj.cneec.pc.pm_prj.constants.PmProjectAuthConstant;
import com.supporter.prj.cneec.pc.pm_prj.entity.VPmProject;
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
public class VPmProjectDao extends MainDaoSupport < VPmProject , String > {
	@Autowired
	VPmProjectViewAllEmpsDao vPmProjectViewAllEmpsDao;
	@Autowired
	VPmProjectViewDeptEmpsDao vPmProjectViewDEmpsDao;
	@Autowired
	VPmProjectOrgMemberEDao vPmProjectOrgMemberEDao;
	@Autowired
	EmpMappingService empMappingService;
	
	/**
	 *获取所有开拓项目
	 * 
	 * @param 
	 * @return List <CneecVPcPrj>
	 */
	public List <VPmProject> getVPmProjects(UserProfile user,JqGrid jqGrid,String prjName,String prjStatus,VPmProject vPmProject){
		if(user != null && user.getPersonId().length() > 0){
			String personProjectAuth = getPmProjectAuthItem(user.getPersonId());
			//所有权限
			if(personProjectAuth.equals(PmProjectAuthConstant.VIEW_ALL_PROJECT)){
				return getVAllPmProjects(jqGrid,prjName,prjStatus,vPmProject);
			}else if(personProjectAuth.equals(PmProjectAuthConstant.VIEW_DEPT_PROJECT)){
				//部门权限
				return getDeptVPmProject(jqGrid,prjName,user.getDeptId(),prjStatus,vPmProject);
			}else{
				//个人权限
				return getPersonalVPmProject(jqGrid,prjName,user.getPersonId(),prjStatus,vPmProject);
			}
		}else{
			return null;
		}
		
	}
	
	/**
	 * 签报获取所有项目
	 * @param user
	 * @param jqGrid
	 * @param prjName
	 * @param prjStatus
	 * @param vPmProject
	 * @return
	 */
	public List <VPmProject> getVPmProjectsToSignedReport(UserProfile user,JqGrid jqGrid,String prjName,String prjStatus,VPmProject vPmProject){
		if(user != null && user.getPersonId().length() > 0){
			String personProjectAuth = getPmProjectAuthItem(user.getPersonId());
			//所有权限
			if(personProjectAuth.equals(PmProjectAuthConstant.VIEW_ALL_PROJECT)){
				return getVAllPmProjects(jqGrid,prjName,prjStatus,vPmProject);
			}else {//若不拥有所有权限，则返回部门内权限
				//部门权限
				return getDeptVPmProject(jqGrid,prjName,user.getDeptId(),prjStatus,vPmProject);
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
	public String getPmProjectAuthItem(String personId){
		personId = empMappingService.getEmpId(personId);//获取新老系统人员映射ID
		if(vPmProjectViewAllEmpsDao.getIsAllPmProjectAuth(personId)){
			return PmProjectAuthConstant.VIEW_ALL_PROJECT;
		}else if(vPmProjectViewDEmpsDao.getIsDeptPmProjectAuth(personId)){
			return PmProjectAuthConstant.VIEW_DEPT_PROJECT;
		}else{
			return PmProjectAuthConstant.VIEW_PERSONAL_PROJECT;
		}
	}
	
	/**
	 *获取开拓项目
	 * 
	 * @param 
	 * @return List <CneecVPmPrj>
	 */
	public List<VPmProject> getVAllPmProjects(JqGrid jqGrid,String prjName,String prjStatus,VPmProject vPmProject) {
		if (StringUtils.isNotBlank(prjName)) {
			jqGrid.addHqlFilter(
				" prjNo = ? or prjName like ? or deptName like ? ",
				"%" + prjName + "%", "%" + prjName + "%", "%" + prjName + "%");
		}
		if(vPmProject!=null){
			if (StringUtils.isNotBlank(vPmProject.getDeptId())) {
				jqGrid.addHqlFilter(
					"deptId =?", vPmProject.getDeptId());
			}
			if (StringUtils.isNotBlank(vPmProject.getPrjCategory())) {
				jqGrid.addHqlFilter(
					"prjCategory like ?","%" + vPmProject.getPrjCategory() + "%");
			}
			
			if (StringUtils.isNotBlank(vPmProject.getPrjRank())) {
				jqGrid.addHqlFilter(
					"prjRank like ?","%" + vPmProject.getPrjRank() + "%");
			}
			if(vPmProject.getPrjStatus()!=null&&!vPmProject.getPrjStatus().equals("")){
				int prjStatusId=Integer.parseInt(vPmProject.getPrjStatus());
					jqGrid.addHqlFilter(
							"prjStatusId = ?",prjStatusId);
			}
		}
		

			
			//筛选项目执行状态（首页我的项目用到）
			if (StringUtils.isNotBlank(prjStatus)) {
				jqGrid.addHqlFilter(
					" prjStatusId in("+prjStatus+")");
			}
			jqGrid.addSortPropertyDesc("to_number(prjNo)");
			//jqGrid.addSortPropertyDesc("contractInNo");
			List<VPmProject> list =  this.retrievePage(jqGrid);
			return list;
	}
	/**
	 *获取某个部门所有的开拓项目
	 * 
	 * @param 
	 * @return List <CneecVPcPrj>
	 */
	public List<VPmProject> getDeptVPmProject(JqGrid jqGrid,String prjName,String deptId,String prjStatus,VPmProject vPmProject) {
		if(StringUtils.isNotBlank(deptId)){
			jqGrid.addHqlFilter("deptId= ?",deptId);
			if (StringUtils.isNotBlank(prjName)) {
				jqGrid.addHqlFilter(
					" prjNo = ? or prjName like ? or deptName like ? ",
					"%" + prjName + "%", "%" + prjName + "%", "%" + prjName + "%");
			}
			
			if(vPmProject!=null){
				
				if (StringUtils.isNotBlank(vPmProject.getDeptId())) {
					jqGrid.addHqlFilter(
						"deptId =?", vPmProject.getDeptId());
				}
				if (StringUtils.isNotBlank(vPmProject.getPrjCategory())) {
					jqGrid.addHqlFilter(
						"prjCategory like ?","%" + vPmProject.getPrjCategory() + "%");
				}
				
				if (StringUtils.isNotBlank(vPmProject.getPrjRank())) {
					jqGrid.addHqlFilter(
						"prjRank like ?","%" + vPmProject.getPrjRank() + "%");
				}
				if(vPmProject.getPrjStatus()!=null&&!vPmProject.getPrjStatus().equals("")){
					int prjStatusId=Integer.parseInt(vPmProject.getPrjStatus());
						jqGrid.addHqlFilter(
								"prjStatusId = ?",prjStatusId);
				}
				
			}		
			//筛选项目执行状态（首页我的项目用到）
			if (StringUtils.isNotBlank(prjStatus)) {
//				jqGrid.addHqlFilter(
//					" prjStatus = ?",prjStatus);
				
				jqGrid.addHqlFilter(
						" prjStatusId in("+prjStatus+")");
			}
			jqGrid.addSortPropertyDesc("to_number(prjNo)");
			//jqGrid.addSortPropertyDesc("contractInNo");
			List<VPmProject> list =  this.retrievePage(jqGrid);
			return list;
		}else{
			return null;
		}
	}
	
	/**
	 *获取开拓项目
	 * 
	 * @param 
	 * @return List <CneecVPcPmPrj>
	 */
	public List<VPmProject> getPersonalVPmProject(JqGrid jqGrid,String prjName,String personId,String prjStatus,VPmProject vPmProject ) {
		String projectIds = "";
		personId = empMappingService.getEmpId(personId);//获取新老系统人员映射ID
		List<String> listOfProjectIds=  vPmProjectOrgMemberEDao.getProjectIdListByPersonId(personId);
		int count = 1;
		for(String projectId:listOfProjectIds){
			if(count == listOfProjectIds.size()){
				projectIds=projectIds+"'"+projectId+"'";
			}else{
				projectIds=projectIds+"'"+projectId+"',";
			}
			count++;
		}
				
/*		if(StringUtils.isNotBlank(personId)){//如果当前登录人是项目经理
			jqGrid.addHqlFilter("prjManagerId= ? or createdById = ? or registerId = ?",personId,personId,personId);
		}
		if(StringUtils.isNotBlank(projectIds)){
			//System.out.println("最终符合条件的projectId有：："+projectIds);
			jqGrid.addHqlFilter("or prjId in ("+projectIds+")");
		}*/
			
		String sql_add="";
		if(StringUtils.isNotBlank(personId)){//如果当前登录人是项目经理/项目创建人/项目登记人
			sql_add+=" prjManagerId="+personId+" or createdById ="+personId+" or registerId="+personId;
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
		
		if(vPmProject!=null){
			if (StringUtils.isNotBlank(vPmProject.getDeptId())) {
				jqGrid.addHqlFilter(
					"deptId =?", vPmProject.getDeptId());
			}
			if (StringUtils.isNotBlank(vPmProject.getPrjCategory())) {
				jqGrid.addHqlFilter(
					"prjCategory like ?","%" + vPmProject.getPrjCategory() + "%");
			}
			
			if (StringUtils.isNotBlank(vPmProject.getPrjRank())) {
				jqGrid.addHqlFilter(
					"prjRank like ?","%" + vPmProject.getPrjRank() + "%");
			}
			
			if(vPmProject.getPrjStatus()!=null&&!vPmProject.getPrjStatus().equals("")){
				int prjStatusId=Integer.parseInt(vPmProject.getPrjStatus());
					jqGrid.addHqlFilter(
							"prjStatusId = ?",prjStatusId);
			}
			
		}
		
		//筛选项目执行状态（首页我的项目用到）
		if (StringUtils.isNotBlank(prjStatus)) {
//				jqGrid.addHqlFilter(
//					" prjStatus = ?",prjStatus);
			
			jqGrid.addHqlFilter(
					" prjStatusId in("+prjStatus+")");
		}
		jqGrid.addSortPropertyDesc("to_number(prjNo)");
		//jqGrid.addSortPropertyDesc("contractInNo");
		List<VPmProject> list =  this.retrievePage(jqGrid);
		return list;
	}
}
