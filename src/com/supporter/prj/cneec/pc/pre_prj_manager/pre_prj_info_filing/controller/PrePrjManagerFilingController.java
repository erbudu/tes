package com.supporter.prj.cneec.pc.pre_prj_manager.pre_prj_info_filing.controller;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.cneec.pc.pre_prj_manager.pre_prj_info_filing.constant.PrePrjManagerFilingConstant;
import com.supporter.prj.cneec.pc.pre_prj_manager.pre_prj_info_filing.entity.PrePrjManagerFiling;
import com.supporter.prj.cneec.pc.pre_prj_manager.pre_prj_info_filing.service.PrePrjManagerFilingService;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.com_codetable.entity.IComCodeTableItem;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.spring_mvc.AbstractController;
import com.supporter.util.CommonUtil;

/**
 * @Title: Controller
 * @Description: 功能模块表.
 * @author tiansen
 * 
 */
@Controller
@RequestMapping("pc/prj_info_filinng")
public class PrePrjManagerFilingController extends AbstractController {
	private static final long serialVersionUID = 1L;
	@Autowired
	PrePrjManagerFilingService filingService;
	
	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param cideId
	 *            主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody
	PrePrjManagerFiling initEditOrViewPage(String filingId) {
		UserProfile user = this.getUserProfile();
		PrePrjManagerFiling entity = filingService.initEditOrViewPage(filingId, user);
		return entity;
	}
	/**
	 * 分页表格展示数据.
	 * 
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody
	JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq,
			PrePrjManagerFiling filing) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.filingService.getGrid(user, jqGrid, filing);
		return jqGrid;
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param code
	 *            页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody
	OperResult<PrePrjManagerFiling> saveOrUpdate(PrePrjManagerFiling filing) {
		UserProfile user = this.getUserProfile();
		//权限验证
		PrePrjManagerFiling entity = this.filingService.saveOrUpdate(filing,user);
		return OperResult.succeed(PrePrjManagerFilingConstant.I18nKey.SAVE_SUCCESS, "保存成功",
				entity);
	}
	
	/**
	 * 删除操作
	 * 
	 * @param docIds
	 *            主键集合，多个以逗号分隔
	 * @return
	 */

	@SuppressWarnings("unchecked")
	@RequestMapping("batchDel")
	public @ResponseBody
	OperResult batchDel(String filingIds) {
		UserProfile user = this.getUserProfile();
		this.filingService.delete(filingIds,user);
		return OperResult.succeed(PrePrjManagerFilingConstant.I18nKey.DELETE_SUCCESS, null,
				null);
	}
	
	
	@RequestMapping("getFilingByIds")
	public @ResponseBody
	List<PrePrjManagerFiling> getFilingByIds(String filingIds) {
		UserProfile user = this.getUserProfile();
		List<PrePrjManagerFiling> entitys = filingService.getFilingByIds(filingIds, user);
		return entitys;
	}
	//数据字典
	/**
	 * 获取地区下拉框
	 * @param projectId
	 * @return
	 */
	@RequestMapping(value = "/getAreaCodetable")
	public @ResponseBody Map<String, String> getAreaCodetable(String value){
//		if (CommonUtil.trim(value).length() > 0) {
//			boolean isForeign = String.valueOf(1).equals(value);
//			return TpcConstant.getAreaMap(isForeign);
//		}
		System.out.println(value);
		if (CommonUtil.trim(value).length() > 0) {
		boolean isForeign = String.valueOf(1).equals(value);
		String areaCode = isForeign ? "FOREIGN" : "INTERNAL";
		Map<String, String> map = new LinkedHashMap<String, String>();
		// 取国内/国外下地区
		List<IComCodeTableItem> list = EIPService.getComCodeTableService().getSubItems("AREA", areaCode);
		if (list != null && list.size() > 0) {
			for (IComCodeTableItem item : list) {
				map.put(item.getItemId(), item.getDisplayName());
			}
		}
		return map;}
		return null;
	}
	
	/**
	 * 获取省份下拉框
	 * @param projectId
	 * @return
	 */
	@RequestMapping(value = "/getAreaItemtable")
	public @ResponseBody Map<String, String> getAreaItemtable(String value){
		//return TpcConstant.getAreaItemMap(value);
		Map<String, String> map = new LinkedHashMap<String, String>();
		if (CommonUtil.trim(value).length() > 0) {
			// 取地区下国家/省份
			List<IComCodeTableItem> list = EIPService.getComCodeTableService().getSubItems("AREA", value);
			if (list != null && list.size() > 0) {
				for (IComCodeTableItem item : list) {
					map.put(item.getItemId(), item.getDisplayName());
				}
			}
		}
		return map;
	}
	@RequestMapping(value = "/getCountrytable")
	public @ResponseBody Map<String, String> getCountrytable(){
		Map<String, String> map = filingService.getCountrytable();
		return map;
	}
	@RequestMapping(value = "/getprjAreatable")
	public @ResponseBody Map<String, String> getprjAreatable(){
		Map<String, String> map = filingService.getprjAreatable();
		return map;
	}
}