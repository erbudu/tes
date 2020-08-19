package com.supporter.prj.cneec.pc.pm_prj.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.pc.pm_prj.dao.VPmProjectDao;
import com.supporter.prj.cneec.pc.pm_prj.entity.VPmProject;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;

/**
 * @Title: Service
 * @Description: 功能模块表
 * @author tiansen
 * 
 */
@Service
public class VPmProjectService {
	 @Autowired
	VPmProjectDao vPmProjectDao;
	/**
	 * 根据主键获取功能模块表.
	 * 
	 * @param moduleId
	 *            主键
	 * @return Contract
	 */
	public VPmProject get(String projectId) {
		VPmProject vPmProject = vPmProjectDao.get(projectId);
		return vPmProject;
	}
	
	/**
	 * 获取执行开拓项目
	 */
	public List<VPmProject> getVPmProjectGrid(UserProfile user, JqGrid jqGrid,String prjName,String prjStatus,VPmProject vPmProject) {
		//获取执行开拓项目之前先通过权限过滤（查看当前登录人能看到哪些项目）
		return vPmProjectDao.getVPmProjects(user,jqGrid, prjName,prjStatus,vPmProject);
	}
	
	/**
	 * 签报获取项目
	 * @param user
	 * @param jqGrid
	 * @param prjName
	 * @param prjStatus
	 * @param vPmProject
	 * @return
	 */
	public List<VPmProject> getVPmProjectGridToSignedReport(UserProfile user, JqGrid jqGrid,String prjName,String prjStatus,VPmProject vPmProject) {
		//获取开拓项目之前先通过权限过滤（查看当前登录人能看到哪些项目）
		return vPmProjectDao.getVPmProjectsToSignedReport(user,jqGrid, prjName,prjStatus,vPmProject);
	}
	
}
