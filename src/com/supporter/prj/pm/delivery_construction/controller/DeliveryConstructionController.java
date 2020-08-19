package com.supporter.prj.pm.delivery_construction.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.pm.delivery_construction.entity.DeliveryConstruction;
import com.supporter.prj.pm.delivery_construction.entity.DeliveryDrawings;
import com.supporter.prj.pm.delivery_construction.service.DeliveryConstructionService;
import com.supporter.spring_mvc.AbstractController;

@Controller
@RequestMapping("pm/delivery_construction/delivery")
public class DeliveryConstructionController extends AbstractController{
	
	private static final long serialVersionUID = 1L;
	private static final int DRAFT = 0;
	private static final int EFFECT = 1;
	@Autowired
	private DeliveryConstructionService  service;	
	
	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * @param id 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody DeliveryConstruction initEditOrViewPage(String deliveryId) {
		UserProfile user = this.getUserProfile();
		DeliveryConstruction entity = service.initEditOrViewPage(deliveryId,user);
		return entity;
	}

	/**
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, DeliveryConstruction deliveryConstruction) throws Exception  {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.service.getGrid( jqGrid, deliveryConstruction,user,getRequestParameters());
		return jqGrid;
	}	
	
	@RequestMapping("getContentGrid")
	public @ResponseBody JqGrid getContentGrid(HttpServletRequest request, JqGridReq jqGridReq,DeliveryDrawings content, String deliveryId) throws Exception  {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.service.getContentGrid(user, jqGrid, content,deliveryId);	
		return jqGrid;
	}
	
	//审批状态
	@RequestMapping("getStatusCodetable")
	public @ResponseBody
	Map<Integer, String> getStatusCodetable(){
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(DRAFT, "草稿");
		map.put(EFFECT, "生效");
		return map;
	}
	
	// //验证维护权限
	// @RequestMapping("validCanEdit")
	// public @ResponseBody OperResult validCanEdit(String deliveryId) {
	// UserProfile user = this.getUserProfile();
	// //参数分别对应的是 当前登录用户，应用编号，权限项编号，主键，根据主键获取的验证对象
	// AuthUtils.canExecute(this.getUserProfile(), DeliveryConstruction.APP_NAME,
	// AuthConstant.AUTH_OPER_NAME_EDIT, deliveryId, service.get(deliveryId));
	// return OperResult.succeed(ModuleConstant.I18nKey.OPERATE_SUCCESS,null,null);
	// }
	
	
	/**
	 * 保存子表
	 */
//	@RequestMapping("saveOrUpdateDrawings")
//	public @ResponseBody OperResult saveOrUpdateDrawings(HttpServletRequest request,
//			String deliveryId) {
//		String jsonStr = request.getParameter("jsona");
//		JSONArray json = JSONArray.fromObject(jsonStr);
//		this.service.saveSupplier(json, deliveryId);
//		return OperResult.succeed(ModuleConstant.I18nKey.SAVE_SUCCESS);
//	}
}
