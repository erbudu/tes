package com.supporter.prj.pm.external_drawings.controller;

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
import com.supporter.prj.pm.external_drawings.entity.ExternalDrawings;
import com.supporter.prj.pm.external_drawings.entity.ExternalDrawingsContent;
import com.supporter.prj.pm.external_drawings.service.ExternalDrawingsService;
import com.supporter.spring_mvc.AbstractController;

@Controller
@RequestMapping("pm/external_drawings/external")
public class ExternalDrawingsController extends AbstractController{
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ExternalDrawingsService  service;	
	
	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody ExternalDrawings initEditOrViewPage(String externalId) {
		UserProfile user = this.getUserProfile();
		ExternalDrawings entity = service.initEditOrViewPage(externalId,user);
		return entity;
	}

	// /**
	// * 进入通知页面加载的信息
	// */
	// @RequestMapping("initNoticeViewPage")
	// public @ResponseBody Map initNoticeViewPage(String externalId) {
	// UserProfile user = this.getUserProfile();
	// Map entity = service.initNoticeViewPage(externalId,user);
	// return entity;
	// }

	/**
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, ExternalDrawings externalDrawings) throws Exception  {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.service.getGrid( jqGrid, externalDrawings,user,getRequestParameters());
		return jqGrid;
	}


	/**
	 * 分页表格展示数据--用于院方反馈选择会审记录
	 * @param request
	 * @param jqGridReq
	 * @param interrorgate
	 * @return
	 * @throws Exception
	 */
	// @RequestMapping("getWidgetGrid")
	// public @ResponseBody JqGrid getWidgetGrid(HttpServletRequest request,
	// JqGridReq jqGridReq,
	// ExternalDrawings externalDrawings) throws Exception {
	// UserProfile user = this.getUserProfile();
	// JqGrid jqGrid = jqGridReq.initJqGrid(request);
	// //院方反馈选择会审记录时的查询框内容
	// String keyword= this.getRequestPara("keyword");
	// this.service.getWidgetGrid( jqGrid,
	// externalDrawings,user,getRequestParameters(),keyword);
	// return jqGrid;
	// }

	@RequestMapping("getContentGrid")
	public @ResponseBody JqGrid getContentGrid(HttpServletRequest request, JqGridReq jqGridReq,
			ExternalDrawingsContent content, String externalId) throws Exception  {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		String isFor = this.getRequestPara("isFor");
		this.service.getContentGrid(user, jqGrid, content,externalId, isFor);	
		return jqGrid;
	}
	
	@RequestMapping("getSelectContentGrid")
	public @ResponseBody JqGrid getSelectContentGrid(HttpServletRequest request, JqGridReq jqGridReq,ExternalDrawingsContent content) throws Exception  {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.service.getSelectContentGrid(user, jqGrid, content);	
		return jqGrid;
	}
	
	// /**
	// * 保存或更新数据.
	// */
	// @RequestMapping("saveOrUpdate")
	// public @ResponseBody OperResult<ExternalDrawings>
	// saveOrUpdate(ExternalDrawings externalDrawings) {
	// UserProfile user = this.getUserProfile();
	// List<ExternalDrawings> externalDrawingsList =
	// service.getListByExternalNo(externalDrawings.getExternalId(),
	// externalDrawings.getExternalNo());
	// if (externalDrawingsList.size() > 0) {
	// return OperResult.fail("该外审编号已经存在！", "该外审编号已经存在！");
	// } else {
	// ExternalDrawings targetEntity =
	// this.initEditOrViewPage(externalDrawings.getExternalId());
	// this.setPropValues(targetEntity);
	// targetEntity.setContentList(externalDrawings.getContentList());
	// targetEntity.setContentTwoList(externalDrawings.getContentTwoList());
	//
	// ExternalDrawings entity = this.service.saveOrUpdate(user, targetEntity);
	// return OperResult.succeed(ModuleConstant.I18nKey.SAVE_SUCCESS, null, entity);
	// }
	// }
	
	/**
	 * /**
	 * 判断是否为初次外审
	 * @param libraryId 图纸主表id
	 * @return 0:非初次外审，1初次外审
	 */
	@RequestMapping("checkIsFor")
	public @ResponseBody String checkIsFor(String libraryId) {
		return this.service.checkIsFor(libraryId);
	}

	// /**
	// * 失效操作
	// */
	// @RequestMapping("invalid")
	// public @ResponseBody
	// OperResult invalid(String externalIds) {
	// UserProfile user = this.getUserProfile();
	// this.service.invalid(user, externalIds);
	// return OperResult.succeed(ModuleConstant.I18nKey.DELETE_SUCCESS);
	// }
	
	// /**
	// * 直接生效
	// */
	// @RequestMapping("valid")
	// public @ResponseBody
	// OperResult valid(ExternalDrawings externalDrawings) {
	// UserProfile user = this.getUserProfile();
	//
	// ExternalDrawings targetEntity =
	// this.initEditOrViewPage(externalDrawings.getExternalId());
	// this.setPropValues(targetEntity);
	// targetEntity.setContentList(externalDrawings.getContentList());
	// targetEntity.setContentTwoList(externalDrawings.getContentTwoList());
	//
	// this.service.valid(user, targetEntity);
	// return OperResult.succeed(ModuleConstant.I18nKey.SAVE_SUCCESS);
	// }
	
	// /**
	// * 删除操作
	// */
	// @RequestMapping("batchDel")
	// public @ResponseBody OperResult batchDel(String externalIds) {
	// UserProfile user = this.getUserProfile();
	// this.service.delete(user, externalIds);
	// return OperResult.succeed(ModuleConstant.I18nKey.DELETE_SUCCESS,null,null);
	// }
	//
	// @RequestMapping("getRecList")
	// public @ResponseBody Boolean getRecList(String externalId) {
	// List<ExternalDrawingsContent> recList = this.service.getRecList(externalId);
	// if(recList != null && recList.size() >0){
	// return true;
	// }else{
	// return false;
	// }
	// }
	
	/**
	 * 图纸类型码表.
	 * @return Map<String, String>
	 */
	@RequestMapping("getDrawingType")
	@ResponseBody
	public Map<String, String> getDrawingType() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put(ExternalDrawingsContent.TYPE_ONE, "Ⅰ类");
		map.put(ExternalDrawingsContent.TYPE_TWO, "Ⅱ类");
		map.put(ExternalDrawingsContent.TYPE_THREE, "Ⅲ类");
		map.put(ExternalDrawingsContent.TYPE_FOUR, "Ⅳ类");
		return map;
	}
	public static final int DRAFT = 0;
	public static final int EFFECT = 1;
	
	//审批状态
	@RequestMapping("getStatusCodetable")
	public @ResponseBody
	Map<Integer, String> getStatusCodetable(){
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(DRAFT, "草稿");
		map.put(EFFECT, "生效");
		return map;
	}

	// /**
	// * 验证该版本必须要有图纸
	// * @param externalId
	// * @return
	// */
	// @RequestMapping("validDrawingFiles")
	// public @ResponseBody String validDrawingFiles(String externalId) {
	// return this.service.validDrawingFiles(externalId,this.getUserProfile());
	// }

	// /**
	// * 设计研接受到反馈后立即处理
	// * @param status 处理状态
	// * @param externalId
	// * @return
	// */
	// @RequestMapping("receiveAndManage")
	// public @ResponseBody String receiveAndManage(String externalId,Integer
	// status) {
	// return
	// this.service.receiveAndManage(externalId,status,this.getUserProfile());
	// }
	
	// //验证维护权限
	// @RequestMapping("validCanEdit")
	// public @ResponseBody OperResult validCanEdit(String externalId) {
	// UserProfile user = this.getUserProfile();
	// //参数分别对应的是 当前登录用户，应用编号，权限项编号，主键，根据主键获取的验证对象
	// AuthUtils.canExecute(this.getUserProfile(), ExternalDrawings.APP_NAME,
	// AuthConstant.AUTH_OPER_NAME_EDIT, externalId, service.get(externalId));
	// return OperResult.succeed(ModuleConstant.I18nKey.OPERATE_SUCCESS,null,null);
	// }
	
}
