package com.supporter.prj.cneec.pc.pm_prj.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.cneec.pc.pm_prj.entity.VPmProject;
import com.supporter.prj.cneec.pc.pm_prj.service.VPmProjectService;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.spring_mvc.AbstractController;

/**
 * @Title: Controller
 * @Description: 功能模块表.
 * @author tiansen
 * 
 */
@Controller
@RequestMapping("pm/project")
public class VPmProjectController extends AbstractController {
	private static final long serialVersionUID = 1L;
	@Autowired
	VPmProjectService vPmProjectService;
	
	/**
	 * 获取开拓项目.
	 */
	@RequestMapping("getVPmProjectGrid")
	public @ResponseBody JqGrid getVPcPrjGrid(HttpServletRequest request, JqGridReq jqGridReq,String prjName,String prjStatusOfportLet,VPmProject vPmProject) throws Exception {
		UserProfile user = this.getUserProfile();
		String prjStatus=prjStatusOfportLet;
		if(prjStatus!=null&&prjStatus.equals("prjStatusOfportLet")){//说明是首页我的项目点击更多跳转的
			//prjStatus="正在执行";
			prjStatus="0,2,7";//(0--""、2--"正在跟踪"、7--"待执行")
		}else{
			prjStatus="";
		}
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		vPmProjectService.getVPmProjectGrid(user, jqGrid,prjName,prjStatus,vPmProject);
		return jqGrid;
	}
	
	/**
	 * 签报获取开拓项目
	 * @param request
	 * @param jqGridReq
	 * @param prjName
	 * @param prjStatusOfportLet
	 * @param vPmProject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getVPmProjectGridToSignedReport")
	public @ResponseBody JqGrid getVPcPrjGridToSignedReport(HttpServletRequest request, JqGridReq jqGridReq,String prjName,String prjStatusOfportLet,VPmProject vPmProject) throws Exception {
		UserProfile user = this.getUserProfile();
		prjStatusOfportLet = "0,2,7";
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		vPmProjectService.getVPmProjectGridToSignedReport(user, jqGrid,prjName,prjStatusOfportLet,vPmProject);
		return jqGrid;
	}

}
