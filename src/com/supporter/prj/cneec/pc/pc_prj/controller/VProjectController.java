package com.supporter.prj.cneec.pc.pc_prj.controller;


import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.cneec.pc.pc_prj.entity.VProject;
import com.supporter.prj.cneec.pc.pc_prj.service.VProjectService;
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
@RequestMapping("pc/project")
public class VProjectController extends AbstractController {
	private static final long serialVersionUID = 1L;
	@Autowired
	VProjectService vProjectService;
	
	/**
	 * 获取执行项目.
	 */
	@RequestMapping("getVProjectGrid")
	public @ResponseBody JqGrid getVPcPrjGrid(HttpServletRequest request, JqGridReq jqGridReq,String prjName,String prjStatusOfportLet,VProject vProject) throws Exception {
		UserProfile user = this.getUserProfile();
		String prjStatus=prjStatusOfportLet;
		if(prjStatus!=null&&prjStatus.equals("prjStatusOfportLet")){//说明是首页我的项目点击更多跳转的
			prjStatus="4";//(4--"正在执行")
		}else{
			prjStatus="";
		}
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		vProjectService.getVProjectGrid(user, jqGrid,prjName,prjStatus,vProject);
		return jqGrid;
	}
	
	/**
	 * 签报中获取执行项目
	 * @param request
	 * @param jqGridReq
	 * @param prjName
	 * @param prjStatusOfportLet
	 * @param vProject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getVProjectGridToSignedReport")
	public @ResponseBody JqGrid getVPcPrjGridToSignedReport(HttpServletRequest request, JqGridReq jqGridReq,String prjName,String prjStatusOfportLet,VProject vProject) throws Exception {
		UserProfile user = this.getUserProfile();
		prjStatusOfportLet = "4";
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		vProjectService.getVProjectGridToSignedReport(user, jqGrid,prjName,prjStatusOfportLet,vProject);
		return jqGrid;
	}
	
	
	/**
	 * 获取我的项目.
	 * @param recCount
	 * @return
	 */
	@RequestMapping("/getMyPrjs")
	@ResponseBody
	public List < Map < String, Object > > getMyPrjs(int recCount){
		return vProjectService.getMyPrjs(this.getUserProfile(), recCount);
	}
	
	/**
	 * 获取字典数据-用于高级查询页面的项目类型下拉显示
	 * 
	 * @param key
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getPrjCategoryCodetable")
	public Map<String, String> getPrjCategoryCodetable()
			throws IOException {
		Map<String, String> map = new LinkedHashMap<String, String>();
    	map.put("工程", "工程");
    	map.put("贸易", "贸易");
		return map;
	}
	
	/**
	 * 获取字典数据-用于高级查询页面的项目级别下拉显示
	 * 
	 * @param key
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getPrjRankCodetable")
	public Map<String, String> getPrjRankCodetable()
			throws IOException {
		Map<String, String> map = new LinkedHashMap<String, String>();
    	map.put("一般", "一般");
    	map.put("重要", "重要");
    	map.put("重大", "重大");
		return map;
	}
	
	
	/**
	 * 获取字典数据-用于高级查询页面的项目级别下拉显示
	 * 
	 * @param key
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getPrjStatusCodetable")
	public Map<Integer, String> getPrjStatusCodetable()
			throws IOException {
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(0, "");
		map.put(2, "正在跟踪");
    	map.put(3, "放弃执行/跟踪");
    	map.put(4, "正在执行");
    	map.put(6, "执行完毕");
    	map.put(7, "待执行");  	
		return map;
	}
	

}
