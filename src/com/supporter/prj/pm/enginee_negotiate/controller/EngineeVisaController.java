package com.supporter.prj.pm.enginee_negotiate.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.cneec.cxf.JsonDateValueProcessor;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip.com_codetable.dao.CodetableItemDao;
import com.supporter.prj.eip.com_codetable.entity.CodetableItem;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.com_codetable.entity.IComCodeTableItem;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.pm.PmConstant;
import com.supporter.prj.pm.enginee_negotiate.dao.EngineeVisaMeetDao;
import com.supporter.prj.pm.enginee_negotiate.dao.EngineeVisaSwfDao;
import com.supporter.prj.pm.enginee_negotiate.entity.EngineeVisa;
import com.supporter.prj.pm.enginee_negotiate.entity.EngineeVisaMeet;
import com.supporter.prj.pm.enginee_negotiate.entity.EngineeVisaSite;
import com.supporter.prj.pm.enginee_negotiate.entity.EngineeVisaSwf;
import com.supporter.prj.pm.enginee_negotiate.service.EngineeVisaService;
import com.supporter.spring_mvc.AbstractController;
import com.supporter.util.CommonUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**   
 * @Title: 签证
 * @Description: PM_ENGINEE_VISA.
 * @author Administrator
 * @date 2018-07-04 18:08:55
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("pm/enginee_negotiate/engineeVisa")
public class EngineeVisaController extends AbstractController {
private static final long serialVersionUID = 1L;
	
	@Autowired
	private EngineeVisaService engineeVisaService;
	@Autowired
	private CodetableItemDao codetableItemDao;
	@Autowired
	private EngineeVisaSwfDao swfDao;
	@Autowired
	private EngineeVisaMeetDao meetDao;
	
	/**
	 * 分页表格展示数据.
	 * @param request 请求对象
	 * @param jqGridReq 表格参数
	 * @param engineeVisa 洽商对象
	 * @return JqGrid
	 * @throws Exception 异常
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("getGrid")
	@ResponseBody
	public JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq,
			EngineeVisa engineeVisa) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		List <EngineeVisa> list = this.engineeVisaService.getGrid(user, jqGrid, engineeVisa);
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor());
		JSONArray jsonArray = JSONArray.fromObject(list, jsonConfig);
		if (jsonArray != null && jsonArray.size() > 0) {
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObj = jsonArray.getJSONObject(i);
				EngineeVisaSwf swf = swfDao.get(jsonObj.getString("visaId"));
				if (swf != null) {
					jsonObj.put("oaProcId", swf.getOaProcId());
					jsonObj.put("oaExamStatusDesc", swf.getOaExamStatusDesc());
				} else {
					jsonObj.put("oaProcId", "");
					jsonObj.put("oaExamStatusDesc", "");
				}
			}
			jqGrid.setRows(jsonArray);
		}
		return jqGrid;
	}
	
	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * @param visaId 签证ID
	 * @param negotiateId 洽商ID
	 * @return EngineeVisa
	 */
	@RequestMapping("initEditOrViewPage")
	@ResponseBody
	public Map<String, Object> initEditOrViewPage(String visaId, String negotiateId) {
		EngineeVisa entity = engineeVisaService.initEditOrViewPage(this.getUserProfile(), visaId, negotiateId, "");
		EngineeVisaSwf swf = swfDao.get(entity.getVisaId());
		swf.setEngineeVisa(entity);
		EngineeVisaMeet meet = meetDao.getMeetByProcId(swf.getOaProcId());
		if (meet == null) {
			meet = new EngineeVisaMeet();
			meet.setMeetingDate(new Date());
			meet.setProcId(swf.getOaProcId());
			meet.setVisaId(entity.getVisaId());
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("entity", entity);
		map.put("swf", swf);
		map.put("meet", meet);

		return map;
	}
	

	// /**
	// * 获取签证类型.
	// * @return Map < ?, ? >
	// */
	// @RequestMapping("getMapApplyType")
	// @ResponseBody
	// public Map < String, String > getMapApplyType() {
	// return engineeVisaService.getMapApplyType();
	// }

	/**
	 * 获取签证类型.
	 */
	@RequestMapping("getMapApplyType")
	@ResponseBody
	public Map < String, String > getMapApplyType() {
		return EngineeVisa.ComCodeTable.getVisaTypeMap();
	}
	
	/**
	 * 判断指定字段唯一性
	 * 
	 * @param visaId 签证ID
	 * @param propertyName 属性名称
	 * @param propertyValue 属性值
	 * @return Boolean
	 */
	@RequestMapping("checkPropertyUniquenes")
	@ResponseBody
	public Boolean checkPropertyUniquenes(String visaId, String propertyName, String propertyValue) {
		return this.engineeVisaService.checkPropertyUniquenes(visaId, propertyName, propertyValue);
	}
	
	//文件类型
	@RequestMapping("getFileTypeCd")
	public @ResponseBody Map<String, String> getFileTypeCd() {
		String hql = "from CodetableItem where codetableId='"+ PmConstant.FILETYPE +"' and (parentItemId is null or parentItemId = '')  order by displayOrder asc ";
		List<CodetableItem> areaItemList = codetableItemDao.find(hql);
		Map<String, String> map = new LinkedHashMap<String, String>();
		for (CodetableItem item : areaItemList) {
			if(item.getDisplayNameZh().equals("设计变更类文件")) {
				map.put(item.getItemId(), item.getItemValue());
			}
		}
		return map;
	}

	//模板类型
	@RequestMapping("getModelTypeCd")
	public @ResponseBody Map<String, String> getModelTypeCd() {
		String hql = "from CodetableItem where codetableId='"+ PmConstant.FILETYPE +"' and (parentItemId is null or parentItemId = '')  order by displayOrder asc ";
		List<CodetableItem> areaItemList = codetableItemDao.find(hql);
		Map<String, String> map = new LinkedHashMap<String, String>();
		String fileTypeId = "";
		for (CodetableItem item : areaItemList) {
			if(item.getDisplayNameZh().equals("设计变更类文件")) {
				fileTypeId = item.getItemId();
			}
		}
		if (fileTypeId != null && CommonUtil.trim(fileTypeId).length() > 0) {
			List<IComCodeTableItem> list = EIPService.getComCodeTableService().getSubItems(fileTypeId);
			for (IComCodeTableItem item : list) {
				if(item.getDisplayNameZh().equals("工程签证")){
					map.put(item.getItemId(), item.getDisplayName());
				}		
			}
		}
		return map;
	}
	
	@RequestMapping("getSiteList")
	 public @ResponseBody Boolean getSiteList(String visaId) {	 
		 List<EngineeVisaSite> recList =  this.engineeVisaService.getSiteList(visaId);
		 if(recList != null && recList.size() >0){
			 return true;
		 }else{
			 return false;
		 }	 
	 }
	
	/**
	 * 获取签证项
	 */
	@RequestMapping("/getVisaItem")
	@ResponseBody
	public Map<Integer, String> getVisaItem() {
		Map<Integer, String> map = EngineeVisa.VisaItem.getMap();
		return map;
	}

	/**
	 * 保存签证会议信息
	 */
	@RequestMapping("/saveVisaMeet")
	@ResponseBody
	public EngineeVisaMeet saveVisaMeet(EngineeVisaMeet visaMeet) {
		// EngineeVisaMeet targetObj = meetDao.get(visaMeet.getRecId());
		// if (targetObj == null) {
		// targetObj = visaMeet;
		// targetObj.setRecId(null);
		// } else {
		// this.setPropValues(targetObj);
		// }
		engineeVisaService.saveVisaMeet(visaMeet);
		return visaMeet;
	}

	/**
	 * 是否显示签证会议信息
	 */
	@RequestMapping("/showVisaMeet")
	@ResponseBody
	public Boolean showVisaMeet(String visaId) {
		Boolean result = engineeVisaService.showVisaMeet(visaId);
		return result;
	}

}
