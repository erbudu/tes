package com.supporter.prj.linkworks.oa.swf_prj_proc.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.linkworks.oa.swf_prj_proc.entity.SwfPrjProc;
import com.supporter.prj.linkworks.oa.swf_prj_proc.service.SwfPrjProcService;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.log.controller.AbstractController;
import com.supporter.util.CommonUtil;

@Controller
@RequestMapping("oa/SwfPrjProc")
public class SwfPrjProcController extends AbstractController {
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private SwfPrjProcService swfPrjProcService;
	
	/**
	 * 分页查询数据
	 * @param request
	 * @param jqGridReq
	 * @param swfPrjProc
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, SwfPrjProc swfPrjProc) throws Exception{
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.swfPrjProcService.getGrid(jqGrid, swfPrjProc);
		return jqGrid;
	}
	
	/**
	 * 进入新建、编辑或查看页面时加载的信息
	 * @param procId
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody SwfPrjProc initEditOrViewPage(String procId){
		UserProfile user = this.getUserProfile();
		SwfPrjProc entity = this.swfPrjProcService.initEditOrViewPage(procId, user);
		return entity;
	}
	
	/**
	 * 保存或更新数据
	 * @param swfPrjProc
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody OperResult<SwfPrjProc> saveOrUpdate(SwfPrjProc swfPrjProc){
		UserProfile user = this.getUserProfile();
		SwfPrjProc entity = this.swfPrjProcService.saveOrUpdate(user, swfPrjProc);
		return OperResult.succeed("saveSuccess", null, entity);
	}
	
	/**
	 * 删除
	 * @param procId
	 * @return
	 */
	@RequestMapping("batchDel")
	public @ResponseBody OperResult batchDel(String procId){
		this.swfPrjProcService.batchDel(procId);
		return OperResult.succeed("deleteSuccess");
	}
	
	/**
	 * 获取应用平台MAP
	 * @return
	 */
	@RequestMapping("getAppPlatformMap")
	public @ResponseBody Map<String, String> getAppPlatformMap() {
		return this.swfPrjProcService.getAppPlatformMap();
	}
	
	/**
	 * 获取业务名称MAP
	 * @param appPlatformCede
	 * @return
	 */
	@RequestMapping("getBusinessMap")
	public @ResponseBody Map<String, String> getBusinessMap(String value) {
		if (CommonUtil.trim(value).length() > 0) {
			return this.swfPrjProcService.getBusinessMap(value);
		}
		return null;
	}
}
