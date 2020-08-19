package com.supporter.prj.cneec.pm.region_plus_manage.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.cneec.pm.region_plus_manage.entity.PmRegionPlusManage;
import com.supporter.prj.cneec.pm.region_plus_manage.service.PmRegionPlusManageService;
import com.supporter.prj.cneec.pm.region_plus_manage.service.PmRegionPlusManageSubtableService;
import com.supporter.prj.cneec.tpc.util.CheckBoxVO;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.log.controller.AbstractController;

@Controller
@RequestMapping("pm/regionPlusManage")
public class PmRegionPlusManageControll extends AbstractController {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private PmRegionPlusManageService pmRegionPlusManageService;
	
	@Autowired
	private PmRegionPlusManageSubtableService pmRegionPlusManageSubtableService;
	
	/**
	 * 获取列表
	 * @param request
	 * @param jqGridReq
	 * @param plus
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, PmRegionPlusManage plus) throws Exception{
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.pmRegionPlusManageService.getGrid(jqGrid, plus);
		return jqGrid;
	}
	
	/**
	 * 进入新建、编辑或查看页面时加载的信息
	 * @param manageId
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody PmRegionPlusManage initEditOrViewPage(String manageId){
		UserProfile user = this.getUserProfile();
		PmRegionPlusManage plus = this.pmRegionPlusManageService.initEditOrViewPage(manageId, user);
		return plus;
	}
	
	/**
	 * 保存或更新数据
	 * @param plus
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody OperResult<PmRegionPlusManage> saveOrUpdate(PmRegionPlusManage plus){
		UserProfile user = this.getUserProfile();
		PmRegionPlusManage entity = this.pmRegionPlusManageService.saveOrUpdate(user, plus);
		return OperResult.succeed("saveSuccess", null, entity);
	}
	
	/**
	 * 删除
	 * @param manageId
	 * @return
	 */
	@RequestMapping("batchDel")
	public @ResponseBody OperResult<String> batchDel(String manageId){
		this.pmRegionPlusManageService.batchDel(manageId);
		return OperResult.succeed("deleteSuccess");
	}
	
	/**
	 * 获取所属市场map
	 * @return
	 */
	@RequestMapping("getAreaInernameMap")
	public @ResponseBody Map<String, String> getAreaInernameMap() {
		return PmRegionPlusManage.getAreaInernameMap();
	}
	
	/**
	 * 获取所属市场map
	 * @return
	 */
	@RequestMapping("getAreaMap")
	public @ResponseBody Map<String, String> getAreaMap(String areaInername) {
		return this.pmRegionPlusManageSubtableService.getAreaMap(areaInername);
	}
	
	/**
	 * 获取大洲或地区下的国家省份date
	 * @param manageId
	 * @param areaId
	 * @return
	 */
	@RequestMapping("getAreaItemData")
	public @ResponseBody List<CheckBoxVO> getAreaItemData(String manageId, String areaId) {
		return this.pmRegionPlusManageSubtableService.getAreaItemData(manageId, areaId);
	}
	
	/**
	 * 保存选择的国家或地区
	 * @param manageId
	 * @param areaItems
	 * @return
	 */
	@RequestMapping("saveAreaItems")
	public @ResponseBody OperResult<PmRegionPlusManage> saveAreaItems(String manageId, String areaItems){
		this.pmRegionPlusManageSubtableService.saveAreaItems(manageId, areaItems);
		return OperResult.succeed("saveSuccess", null, null);
	}
	
	/**
	 * 根据用户返回可选择的区域+map
	 * @return
	 */
	@RequestMapping("getRedionPlusMap")
	public @ResponseBody Map<String, String> getRedionPlusMap(){
		UserProfile user = this.getUserProfile();
		return this.pmRegionPlusManageService.getRedionPlusMap(user);
	}
	
	/**
	 * 根据区域+管理ID获取下属国家map
	 * @param manageId
	 * @return
	 */
	@RequestMapping("getAreaItemsMap")
	public @ResponseBody Map<String, String> getAreaItemsMap(String manageId) {
		return this.pmRegionPlusManageSubtableService.getAreaItemsMap(manageId);
	}
}
