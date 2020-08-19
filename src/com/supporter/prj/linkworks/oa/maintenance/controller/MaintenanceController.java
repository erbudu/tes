package com.supporter.prj.linkworks.oa.maintenance.controller;

import java.io.IOException;
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
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.com_codetable.entity.IComCodeTableItem;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.maintenance.entity.Maintenance;
import com.supporter.prj.linkworks.oa.maintenance.service.MaintenanceService;
import com.supporter.spring_mvc.AbstractController;

@Controller
@RequestMapping("oa/maintenance")
public class MaintenanceController extends AbstractController{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Autowired
	private MaintenanceService maintenanceService; 

    /**
     * 返回列表.
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, Maintenance maintenance) throws Exception  {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.maintenanceService.getGrid(user, jqGrid, maintenance);
		return jqGrid;
	}
    

	/**
	 * 根据主键获取功能模块�?.
	 * 
	 * @param maintenanceId 主键
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("get")
	public @ResponseBody Maintenance get(String maintenanceId) {
		Maintenance maintenance = maintenanceService.get(maintenanceId);
		return maintenance;

	}

	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param maintenanceId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody Maintenance initEditOrViewPage(HttpServletRequest request,JqGridReq jqGridReq,String maintenanceId) {
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		Maintenance entity = maintenanceService.initEditOrViewPage(jqGrid,maintenanceId,this.getUserProfile());
		return entity;
	}

	/**
	 * 进入新建或编辑或查看页面时加载的信息.
	 * 
	 * @param maintenanceId 主键
	 * @return
	 */
	@RequestMapping("viewPage")
	@ResponseBody
	public Maintenance viewPage(String maintenanceId) {
		Maintenance entity = maintenanceService.viewPage(maintenanceId,this.getUserProfile());
		return entity;
	}
	
	
	
	
	
	
	
	/**
	 * 保存或更新数据
	 * 
	 * @param maintenance 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody OperResult<Maintenance> saveOrUpdate(Maintenance maintenance,String notifierIds,String notifierNames,String examIds,String examNames) {
		UserProfile user = this.getUserProfile();
		Map< String, Object > valueMap = this.getPropValues(Maintenance.class);		
		Maintenance entity = this.maintenanceService.saveOrUpdate(user,maintenance,valueMap);
//		return OperResult.succeed(SalaryConstant.I18nKey.SAVE_SUCCESS, null, null);
		return OperResult.succeed("saveSuccess", null, entity);
	}
	
	/**
	 * 流程中的保存
	 * 
	 * @param maintenance 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveProc")
	public @ResponseBody void saveProc(Maintenance maintenance) {
	UserProfile user = this.getUserProfile();
		Map< String, Object > valueMap = this.getPropValues(Maintenance.class);		
		String maintenanceId=maintenance.getMaintenanceId();
		if(maintenanceId!=null){
			Maintenance maintenanceZ=this.maintenanceService.get(maintenanceId);
			if(maintenance.getModifiedContentCode()!=null){
				maintenanceZ.setModifiedContentCode(maintenance.getModifiedContentCode());
			}	
			if(maintenanceZ.getModifiedContentCode()!=null){
				if(maintenanceZ.getModifiedContentCode()==1){
					maintenanceZ.setModifiedContent("系统修改");
				}else if(maintenanceZ.getModifiedContentCode()==2){
					maintenanceZ.setModifiedContent("数据修改");
				}
			}	
			if(maintenance.getPrjDeptName()!=null){
				maintenanceZ.setPrjDeptName(maintenance.getPrjDeptName());
			}			
/*            if(maintenance.getPrjId()!=null){
            	maintenanceZ.setPrjId(maintenance.getPrjId());
            }
            if(maintenanceZ.getPrjId()!=null){
            	if(maintenanceZ.getPrjId()==0){
    				maintenanceZ.setPrjName("项目1");
    			}else if(maintenanceZ.getPrjId()==1){
    				maintenanceZ.setPrjName("项目2");
    			}else if(maintenanceZ.getPrjId()==2){
    				maintenanceZ.setPrjName("项目3");
    			}		
            }*/
			//保存项目名称
			if(maintenance.getPrjName()!=null){
            	maintenanceZ.setPrjName(maintenance.getPrjName());
            }
			if(maintenance.getSwfInnerName()!=null){
				 maintenanceZ.setSwfInnerName(maintenance.getSwfInnerName());
				//流程名称
				String swfName = EIPService.getComCodeTableService()
						.getCodeTable("SWF-INFO").getDisplay(
								maintenance.getSwfInnerName());
				maintenanceZ.setSwfName(swfName);
			}
			maintenanceZ.setMaintenanceProperties(maintenance.getMaintenanceProperties());
			maintenanceZ.setSolutions(maintenance.getSolutions());
			maintenanceZ.setProblemDescriptionDesc(maintenance.getProblemDescriptionDesc());
			maintenanceZ.setAdd(false);
			this.maintenanceService.saveOrUpdate(user,maintenanceZ,valueMap);
		}				
	}
	
	
	
	/**
	 * 保存提交.
	 * 
	 * @param apply
	 *            页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("commit")
	public @ResponseBody
	OperResult<Maintenance> commit(Maintenance maintenance) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this
				.getPropValues(Maintenance.class);
		Maintenance entity = this.maintenanceService.commit(user,
				maintenance, valueMap);
//		return OperResult.succeed(SalaryConstant.I18nKey.SAVE_SUCCESS, null, null);
		return OperResult.succeed("saveSuccess", null, entity);
	}

	/**
	 * 删除操作
	 * 
	 * @param maintenanceIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("batchDel")
	public @ResponseBody OperResult batchDel(String maintenanceIds) {
		UserProfile user = this.getUserProfile();
		this.maintenanceService.delete(user, maintenanceIds);
//		return OperResult.succeed(SalaryConstant.I18nKey.DELETE_SUCCESS, null, null);
		return OperResult.succeed("deleteSuccess", null, null);
	}
	/**
	 * 获取字典数据-用于高级查询页面的下拉显示
	 * 
	 * @param key
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getModifiedContentCodetable")
	public Map<Integer, String> getModifiedContentCodetable()
			throws IOException {
		Map<Integer, String> map = Maintenance.getModifiedMap();
		return map;
	}
	
	/**
	 * 获取字典数据-用于高级查询页面的下拉显示
	 * 
	 * @param key
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getSwfCodetable")
	public Map<String, String> getSwfCodetable()
			throws IOException {
//		Map<Integer, String> map = Maintenance.getModifiedMap();
//		return map;
		
    	List <IComCodeTableItem> list = EIPService.getComCodeTableService().getCodeTableItems("SWF-INFO");
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("", "");
		for(IComCodeTableItem item : list){
			map.put(item.getItemId(), item.getDisplayName());
		}
		return map;
		
	}
	
	/**
	 * 获取字典数据-用于高级查询页面的下拉显示
	 * 
	 * @param key
	 * @throws IOException
	 */
/*	@ResponseBody
	@RequestMapping(value = "/getModifiedContentCodetabl")
	public Map<Integer, String> getModifiedContentCodetabl()
			throws IOException {
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
    	map.put(0, "项目1");
    	map.put(1, "项目2");
    	map.put(2, "项目3");
		return map;
	}*/
	
	/**
	 * 获取字典数据-用于高级查询页面的下拉显示
	 * 
	 * @param key
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getMaintenancePropertiesCodetable")
	public Map<String, String> getMaintenancePropertiesCodetable()
			throws IOException {
		Map<String, String> map = new LinkedHashMap<String, String>();
    	map.put("代码维护", "代码维护");
    	map.put("数据维护", "数据维护");
    	map.put("新增模块", "新增模块");
		return map;
	}
	
	/**
	 * 提取历史数据文件.
	 * @param report
	 * @return
	 */
	@RequestMapping("extractFiles")
	public @ResponseBody String extractFiles(String maintenanceId){
		return maintenanceService.extractFiles(maintenanceId,this.getUserProfile());
	}
	
	/**
	 * 提取历史数据文件.
	 * @param report
	 * @return
	 */
	@RequestMapping("batchExtractFiles")
	public @ResponseBody String batchExtractFiles(){
		return maintenanceService.batchExtractFiles(this.getUserProfile());
	}


	
	
	
	
}
