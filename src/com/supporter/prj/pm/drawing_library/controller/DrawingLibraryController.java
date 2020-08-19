package com.supporter.prj.pm.drawing_library.controller;

import java.util.HashMap;
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
import com.supporter.prj.pm.PmConstant;
import com.supporter.prj.pm.drawing_library.entity.DrawingLibrary;
import com.supporter.prj.pm.drawing_library.entity.DrawingLibraryVersion;
import com.supporter.prj.pm.drawing_library.service.DrawingLibraryService;
import com.supporter.spring_mvc.AbstractController;

@Controller
@RequestMapping("pm/drawing_library")
public class DrawingLibraryController extends AbstractController{
	
	private static final long serialVersionUID = 1L;
	@Autowired
	private DrawingLibraryService service;	
	// @Autowired
	// private InterrorgateContentService contentService;
	// @Autowired
	// private ExternalDrawingsContentService drawingContentService;
	// @Autowired
	// private DeliveryDrawingsService drawingService;
	// @Autowired
	// private DrawingLibraryVersionService versionService;

	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param id 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody DrawingLibrary initEditOrViewPage(String id) {
		UserProfile user = this.getUserProfile();
		DrawingLibrary entity = service.initEditOrViewPage(id,user);
		return entity;
	}
	
	@RequestMapping("initEditOrViewPageVersion")
	public @ResponseBody DrawingLibraryVersion initEditOrViewPageVersion(String versionId, String libraryId) {
		UserProfile user = this.getUserProfile();
		DrawingLibraryVersion entity = service.initEditOrViewPageVersion(versionId,libraryId,user);
		return entity;
	}

	/**
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, DrawingLibrary drawingLibrary) throws Exception  {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.service.getGrid( jqGrid, drawingLibrary,user);
		return jqGrid;
	}	
	
	// /**
	// * 选择图纸库控件--公用组件方法，不可擅自修改，有关联关系
	// * @param jqGrid
	// * @param drawingLibraryVersion
	// * @param idSrcsStr 页面中已选择图纸的id，用来过滤显示
	// * @param flagStr 各业务模块标志位（1：图纸会审，2图纸外审，3交付施工，4施工图交底）
	// * @param letterTypeStr 意见函类型
	// * @param completionFlagStr 竣工图用来检索图纸信息的参数
	// * @return
	// */
	// @RequestMapping("getWidgetGrid")
	// public @ResponseBody JqGrid getWidgetGrid(HttpServletRequest request,
	// JqGridReq jqGridReq,
	// DrawingLibraryVersion drawingLibraryVersion) throws Exception {
	// UserProfile user = this.getUserProfile();
	// JqGrid jqGrid = jqGridReq.initJqGrid(request);
	// String idSrcsStr = this.getRequestPara("idSrcs");
	// String flagStr = this.getRequestPara("flag");
	// String letterTypeStr = this.getRequestPara("letterType");
	// String completionFlagStr = this.getRequestPara("completionFlag");
	// this.service.getWidgetGrid( jqGrid,
	// drawingLibraryVersion,user,idSrcsStr,flagStr, completionFlagStr,
	// letterTypeStr);
	// return jqGrid;
	// }
	
	/**
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getVersionGrid")
	public @ResponseBody JqGrid getVersionGrid(HttpServletRequest request, JqGridReq jqGridReq, DrawingLibraryVersion drawingLibraryVersion) throws Exception  {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		String libraryId = this.getRequestPara("libraryId");
		this.service.getVersionGrid( jqGrid, drawingLibraryVersion,user,libraryId);
		return jqGrid;
	}	
	
//	@RequestMapping("getVersionView")
//	public @ResponseBody JqGrid getVersionView(HttpServletRequest request, JqGridReq jqGridReq, String versionId) throws Exception  {
//		System.out.println("controller");
//		UserProfile user = this.getUserProfile();
//		JqGrid jqGrid = jqGridReq.initJqGrid(request);
//		versionId = this.getRequestPara("versionId");
//		versionService.getVersionView( jqGrid, user,versionId);
//		return jqGrid;	
//	}	
	
	// /**
	// * 保存或更新数据.
	// * @param FileSend 页面传递参数自动绑定成的实体类
	// * @return
	// */
	// @RequestMapping("saveOrUpdate")
	// public @ResponseBody OperResult<DrawingLibrary> saveOrUpdate(DrawingLibrary
	// drawingLibrary) {
	// UserProfile user = this.getUserProfile();
	// List<DrawingLibrary> drawingLibraryList =
	// service.getProjectListByPrjName(drawingLibrary.getId(),
	// drawingLibrary.getDrawingNo());
	// if(CollectionUtils.isNotEmpty(drawingLibraryList)) {
	// return OperResult.fail("该图纸编号已经存在！", "该图纸编号已经存在！");
	// } else {
	// DrawingLibrary targetEntity =
	// this.initEditOrViewPage(drawingLibrary.getId());
	// this.setPropValues(targetEntity);
	//
	// DrawingLibrary entity = this.service.saveOrUpdate(user, targetEntity);
	// return OperResult.succeed(ModuleConstant.I18nKey.SAVE_SUCCESS, null, entity);
	// }
	// }
	
	// @SuppressWarnings({"rawtypes"})
	// @RequestMapping("saveOrUpdateVersion")
	// public @ResponseBody OperResult saveOrUpdateVersion(DrawingLibraryVersion
	// drawingLibraryVersion) {
	// UserProfile user = this.getUserProfile();
	// List<DrawingLibraryVersion> drawingLibraryVersionList = service
	// .getVersionByVersionNo(drawingLibraryVersion.getVersionId(),
	// drawingLibraryVersion.getLibraryId(), drawingLibraryVersion.getVersionNo());
	// if (CollectionUtils.isNotEmpty(drawingLibraryVersionList)) {
	// return OperResult.fail("该版本号已经存在！", "该版本号已经存在！");
	// } else {
	// DrawingLibraryVersion targetEntity =
	// this.initEditOrViewPageVersion(drawingLibraryVersion.getVersionId(),
	// drawingLibraryVersion.getLibraryId());
	// this.setPropValues(targetEntity);
	//
	// DrawingLibraryVersion entity = this.service.saveOrUpdateVersion(user,
	// targetEntity);
	//
	// return OperResult.succeed(ModuleConstant.I18nKey.SAVE_SUCCESS,
	// (entity.getVersionRule()+entity.getVersionNo()).equals("")?"初稿":entity.getVersionRule()+entity.getVersionNo(),entity);
	// }
	// }
	//
	// @RequestMapping("saveOrUpdateFile")
	// public @ResponseBody OperResult<DrawingLibraryVersion>
	// saveOrUpdateFile(String versionId, String scanId, String scanName,
	// String scanIds, String scanNames ) {
	// UserProfile user = this.getUserProfile();
	// Map< String, Object > valueMap =
	// this.getPropValues(DrawingLibraryVersion.class);
	// DrawingLibraryVersion entity =
	// versionService.saveOrUpdateFile(user,versionId,scanId,scanName,scanIds,scanNames);
	// return OperResult.succeed(ModuleConstant.I18nKey.SAVE_SUCCESS, null,entity);
	// }
	
	//获取专业
	@RequestMapping("getSpecialty")
	public @ResponseBody Map<String, String> getSpecialty() {
		return PmConstant.getSpecialty();
	}
	
	//获取部位
	@RequestMapping("getRegionName")
	public @ResponseBody Map<String, String> getRegionName() {
		return PmConstant.getRegionName();
	}

	//获取状态
	@RequestMapping("getStatusCodeTable")
	public @ResponseBody Map<String, String> getStatusCodeTable() {
		Map<String,String > map = new HashMap<String,String>();
		map.put("0","未审核");
		map.put("1","内审中");
		map.put("2","内审完成");
		map.put("3","外审中");
		map.put("4","外审完成");
		map.put("5","已交付");
		map.put("6","已交底");
		map.put("7","已完成");
		map.put("11","反馈完成");
		return map;
	}
	
	/**
	 * 图纸作废
	 * @return
	 */
//	@SuppressWarnings({"rawtypes"})
//	@RequestMapping("toVoid")
//	public @ResponseBody OperResult toVoid(DrawingLibrary drawingLibrary) {
//		UserProfile user = this.getUserProfile();
//		Map<String, Object> valueMap = this.getPropValues(DrawingLibrary.class);
//		DrawingLibrary entity = this.service.toVoid(user,drawingLibrary);
//		return OperResult.succeed(ModuleConstant.I18nKey.SAVE_SUCCESS);
//	}
	
	// /**
	// * 删除操作
	// * @param problemIds 主键集合，多个以逗号分隔
	// * @return
	// */
	// @RequestMapping("batchDel")
	// public @ResponseBody OperResult batchDel(String ids) {
	// if (StringUtils.isNotBlank(ids)) {
	// String[] idArray = ids.split(",");
	// for (String id : idArray) {
	// if (id == null) {
	// continue;
	// }
	// List<DrawingLibraryVersion> drawingLibraryVersionList =
	// service.getListByLibraryId(id);
	// boolean flag=true;
	// if(drawingLibraryVersionList != null) {
	// for(DrawingLibraryVersion drawingLibraryVersion : drawingLibraryVersionList)
	// {
	// if (drawingLibraryVersion == null) {
	// continue;
	// }
	//
	// //验证图纸会审中是否存在该图纸
	// List<InterrorgateContent> contentList =
	// contentService.getContentListByAtlasId(drawingLibraryVersion.getVersionId());
	// if(contentList != null) {
	// flag=false;
	// return OperResult.fail(drawingLibraryVersion.getDrawingNo()+"已进行内审，不可删除！",
	// drawingLibraryVersion.getDrawingNo()+"已进行内审，不可删除！");
	// }
	// //验证图纸外审中是否存在该图纸
	// List<ExternalDrawingsContent> drawingsContentList =
	// drawingContentService.getContentListByDrawingId(drawingLibraryVersion.getVersionId());
	// if(drawingsContentList != null) {
	// flag=false;
	// return OperResult.fail(drawingLibraryVersion.getDrawingNo()+"已进行外审，不可删除！",
	// drawingLibraryVersion.getDrawingNo()+"已进行外审，不可删除！");
	// }
	// //验证交付施工中是否存在该图纸（既不内审也不外审的）
	// List<DeliveryDrawings> deliveryDrawingsList =
	// drawingService.getContentListByDrawingId(drawingLibraryVersion.getVersionId());
	// if(deliveryDrawingsList != null) {
	// flag=false;
	// return OperResult.fail(drawingLibraryVersion.getDrawingNo()+"已交付施工，不可删除！",
	// drawingLibraryVersion.getDrawingNo()+"已交付施工，不可删除！");
	// }
	// }
	// if(flag) {
	// UserProfile user = this.getUserProfile();
	// this.service.delete(user, id);
	// return OperResult.succeed(ModuleConstant.I18nKey.DELETE_SUCCESS,null,null);
	// }
	// }else{
	// UserProfile user = this.getUserProfile();
	// this.service.delete(user, ids);
	// return OperResult.succeed(ModuleConstant.I18nKey.DELETE_SUCCESS,null,null);
	// }
	// }
	// }
	// return OperResult.succeed(ModuleConstant.I18nKey.DELETE_SUCCESS,null,null);
	// }
	
	// /**
	// * 删除明细表
	// * @return
	// */
	// @SuppressWarnings({"rawtypes"})
	// @RequestMapping("batchDelVersion")
	// public @ResponseBody OperResult batchDelVersion(String versionId) {
	// DrawingLibraryVersion version = service.getVersionById(versionId);
	//
	// //验证图纸会审中是否存在该图纸
	// List<InterrorgateContent> contentList =
	// contentService.getContentListByAtlasId(versionId);
	// if(contentList != null) {
	// return OperResult.fail("该图纸已进行内审，不可删除！", "该图纸已进行内审，不可删除！");
	// }
	// //验证图纸外审中是否存在该图纸
	// List<ExternalDrawingsContent> drawingsContentList =
	// drawingContentService.getContentListByDrawingId(versionId);
	// if(drawingsContentList != null) {
	// return OperResult.fail("该图纸已进行外审，不可删除！", "该图纸已进行外审，不可删除！");
	// }
	// //验证交付施工中是否存在该图纸（既不内审也不外审的）
	// List<DeliveryDrawings> deliveryDrawingsList =
	// drawingService.getContentListByDrawingId(versionId);
	// if(deliveryDrawingsList != null) {
	// return OperResult.fail("该图纸已交付施工，不可删除！", "该图纸已交付施工，不可删除！");
	// }else {
	// //都不存在的情况下才能正常删除
	// String drawingVersion = this.service.delDrawingLibraryVersion(version);
	// return
	// OperResult.succeed(ModuleConstant.I18nKey.DELETE_SUCCESS,drawingVersion,"");
	// }
	// }
	
	 /**
	  * 是否内审
	  * @return 
	  */
	 @RequestMapping("getIsIn")
	 @ResponseBody
	 public Map<Integer, String>geIsIn() {
		 Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		 map.put(0, "否");
		 map.put(1, "是");
		 return map;
	}
	 
	 /**
	  * 是否内审
	  * @return 
	  */
	 @RequestMapping("getIsOut")
	 @ResponseBody
	 public Map<Integer, String>geIsOut() {
		 Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		 map.put(0, "否");
		 map.put(1, "是");
		 return map;
	}
	 /**
	  * 是否历史记录
	  * @return 
	  */
	 @RequestMapping("getIsHistory")
	 @ResponseBody
	 public Map<Integer, String>geIsHistory() {
		 Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		 map.put(0, "否");
		 map.put(1, "是");
		 return map;
	}
	 
	// //维护权限校验
	// @RequestMapping("validCanEdit")
	// public @ResponseBody OperResult validCanEdit(String id) {
	// UserProfile user = this.getUserProfile();
	// //参数分别对应的是 当前登录用户，应用编号，权限项编号，主键，根据主键获取的验证对象
	// AuthUtils.canExecute(this.getUserProfile(),DrawingLibrary.APP_NAME,AuthConstant.AUTH_OPER_NAME_EDIT,id,service.get(id));
	// return OperResult.succeed(ModuleConstant.I18nKey.OPERATE_SUCCESS,null,null);
	// }


	// /**
	// * 变更图纸
	// * @param problemIds 主键集合，多个以逗号分隔
	// * @return
	// */
	// @RequestMapping("drawingChange")
	// public @ResponseBody OperResult drawingChange(String ids) {
	// String message = this.service.drawingChange(ids,this.getUserProfile());
	// return
	// OperResult.succeed(ModuleConstant.I18nKey.OPERATE_SUCCESS,message,message);
	// }

}
