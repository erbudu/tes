package com.supporter.prj.pm.design_change.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip.com_codetable.dao.CodetableItemDao;
import com.supporter.prj.eip.com_codetable.entity.CodetableItem;
import com.supporter.prj.eip.module.constant.ModuleConstant;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.com_codetable.entity.IComCodeTableItem;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.pm.PmConstant;
import com.supporter.prj.pm.design_change.entity.DesignChange;
import com.supporter.prj.pm.design_change.service.DesignChangeService;

import com.supporter.spring_mvc.AbstractController;
import com.supporter.util.CommonUtil;

@Controller
@RequestMapping("pm/design_change/apply")
public class DesignChangeController extends AbstractController{
	
	private static final long serialVersionUID = 1L;
	@Autowired
	private DesignChangeService service;	
	@Autowired
	private CodetableItemDao codetableItemDao;


	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param id 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody DesignChange initEditOrViewPage(String id) {
		UserProfile user = this.getUserProfile();
		DesignChange entity = service.initEditOrViewPage(id,user);
		return entity;
	}
	
	@RequestMapping("getToFuJian")
	public @ResponseBody DesignChange getToFuJian(String applyId) {
		UserProfile user = this.getUserProfile();
		DesignChange entity = service.getToFuJian(applyId,user);
		return entity;
	}

	/**
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, DesignChange design) throws Exception  {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.service.getGrid( jqGrid, design,user);
		return jqGrid;
	}	
	
	@RequestMapping("getExamGrid")
	public @ResponseBody JqGrid getExamGrid(HttpServletRequest request, JqGridReq jqGridReq, DesignChange design) throws Exception  {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.service.getExamGrid( jqGrid, design,user);
		return jqGrid;
	}	
	
	/**
	 * 保存或更新数据.
	 * 
	 * @param FileSend 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody OperResult<DesignChange> saveOrUpdate(DesignChange design) {
		UserProfile user = this.getUserProfile();
		Map< String, Object > valueMap = this.getPropValues(DesignChange.class);
		DesignChange entity = this.service.saveOrUpdate(user, design);
		return OperResult.succeed(ModuleConstant.I18nKey.SAVE_SUCCESS, null, entity);
	}
	
	/**
	 * 直接生效
	 * @return
	 */
	@RequestMapping("valid")
	public @ResponseBody
	OperResult valid(DesignChange design) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this.getPropValues(DesignChange.class);
		DesignChange entity = this.service.valid(user,design);
		return OperResult.succeed(ModuleConstant.I18nKey.SAVE_SUCCESS);
	}
	
	/**
	 * 删除操作
	 * 
	 * @param problemIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	public @ResponseBody OperResult batchDel(String ids) {
		UserProfile user = this.getUserProfile();
		this.service.delete(user, ids);
		return OperResult.succeed(ModuleConstant.I18nKey.DELETE_SUCCESS,null,null);
	}
	
	//获取设计变更原因
	@RequestMapping("getChangeReason")
	public @ResponseBody Map<String, String> getChangeReason() {
		return PmConstant.getChangeReason();
	}
	
	//获取专业
	@RequestMapping("getSpecialized")
	public @ResponseBody Map<String, String> getSpecialized() {
		return PmConstant.getSpecialized();
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
	public @ResponseBody Map<String, String> getModelTypeCd(String value) {
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
				if(item.getDisplayNameZh().equals("变更审批")){
					map.put(item.getItemId(), item.getDisplayName());
				}		
			}
		}
		return map;
	}
	
	/**
	 * 判断编写文档是否存在
	 */
	@RequestMapping("checkFile")
	public @ResponseBody boolean checkFile(HttpServletRequest request,String applyId){
		return service.checkFile(request,applyId);		
	}

}
