package com.supporter.prj.cneec.pc.pc_prj.service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.pc.pc_prj.dao.VProjectDao;
import com.supporter.prj.cneec.pc.pc_prj.entity.VProject;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;

/**
 * @Title: Service
 * @Description: 功能模块表
 * @author tiansen
 * 
 */
@Service
public class VProjectService {
	 @Autowired
	VProjectDao vProjectDao;
	/**
	 * 根据主键获取功能模块表.
	 * 
	 * @param moduleId
	 *            主键
	 * @return Contract
	 */
	public VProject get(String projectId) {
		VProject vProject = vProjectDao.get(projectId);
		return vProject;
	}
	
	/**
	 * 获取执行项目
	 */
	public List<VProject> getVProjectGrid(UserProfile user, JqGrid jqGrid,String prjName,String prjStatus,VProject vProject) {
		//获取执行项目之前先通过权限过滤（查看当前登录人能看到哪些项目）
		return vProjectDao.getVprojects(user, jqGrid, prjName, prjStatus,vProject);
	}
	
	/**
	 * 签报中获取执行项目
	 * @param user
	 * @param jqGrid
	 * @param prjName
	 * @param prjStatus
	 * @param vProject
	 * @return
	 */
	public List<VProject> getVProjectGridToSignedReport(UserProfile user, JqGrid jqGrid,String prjName,String prjStatus,VProject vProject) {
		//获取执行项目之前先通过权限过滤（查看当前登录人能看到哪些项目）
		return vProjectDao.getVprojectsToSignedReport(user, jqGrid, prjName, prjStatus,vProject);
	}
	
	/**
	 * 获取我的项目.
	 * @param recCount
	 * @return
	 */
	public List < Map < String, Object > > getMyPrjs(UserProfile user, int recCount){
		if (user == null)return null;
		JqGrid jqGrid1 = new JqGrid();
		if (recCount > 0){
			jqGrid1.setPageSize(recCount);
			jqGrid1.setPageNo(1);
		} else {
			//如果获取记录条数<=0，则返回所有记录
			jqGrid1.setPageSize(0);
		}
		String prjName = "";
		//获取执行项目之前先通过权限过滤（查看当前登录人能看到哪些项目）
		String prjStatus="4";//4--正在执行
		List < VProject > list1 = vProjectDao.getVprojects(user, jqGrid1, prjName, prjStatus,null);
		int size1 = 0;
		if (list1 != null)size1 = list1.size();
		if (size1 == 0)return null;		
		//返回的项目查询结果集[{prj:prjObj1},{prj:prjObj2},...]
		List < Map < String, Object > > prjs = new ArrayList < Map < String, Object > >();
	
		for (int i = 0; i < list1.size(); i++){
			VProject prj = list1.get(i);
			Map < String, Object > map = new HashMap < String, Object >();
			map.put("prj", prj);
			prjs.add(map);
		}
		return prjs;
	}

	
}
