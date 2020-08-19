package com.supporter.prj.cneec.pc.pre_prj_manager.export_control.controller;

import com.supporter.prj.cneec.pc.pre_prj_manager.export_control.constant.ExportControlConstant;
import com.supporter.prj.cneec.pc.pre_prj_manager.export_control.entity.ExportControl;
import com.supporter.prj.cneec.pc.pre_prj_manager.export_control.service.ExportControlService;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.com_codetable.entity.IComCodeTableItem;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.spring_mvc.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**   
 * @Title: Controller
 * @Description: 出口管制.
 * @author kangh_000
 * @date 2018-12-20 17:53:29
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("pre_prj_manager/export_control/exportControl")
public class ExportControlController extends AbstractController {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ExportControlService exportControlService;
	
	
	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param exportControlId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody ExportControl initEditOrViewPage(String exportControlId){
		ExportControl entity = exportControlService.initEditOrViewPage(exportControlId);
		return entity;
	}
	/**
	 * 分页表格展示数据.
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, ExportControl exportControl) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.exportControlService.getGrid(user, jqGrid, exportControl);
		return jqGrid;
	}
	
	/**
	 * 保存数据.
	 * 
	 * @param exportControl 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("save")
	public @ResponseBody OperResult< ExportControl > save(ExportControl exportControl){
		UserProfile user = this.getUserProfile();
		OperResult< ExportControl > op = this.exportControlService.save(user, exportControl);
		return op;
	}
	
	/**
	 * 更新数据.
	 * 
	 * @param exportControl 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("update")
	public @ResponseBody OperResult< ExportControl > update(ExportControl exportControl){
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this.getPropValues(ExportControl.class);
		OperResult< ExportControl > op = this.exportControlService.update(user, exportControl, valueMap);
		return op;
	}
	
	/**
	 * 删除前判断是否可以删除.
	 * @return
	 */
	@RequestMapping("checkBatchDelCondition")
	public  @ResponseBody List< OperResult< ? > > checkBatchDelCondition(String exportControlIds){
		return this.exportControlService.checkBatchDelCondition(this.getUserProfile(), exportControlIds);
	}
	
	/**
	 * 删除操作.
	 * 
	 * @param exportControlIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	public @ResponseBody OperResult< ? > batchDel(String exportControlIds) {
		UserProfile user = this.getUserProfile();
		OperResult< ? > op = this.exportControlService.delete(user, exportControlIds);
		return op;
	}
	
	/**
	 * 判断指定字段唯一性
	 * 
	 * @param exportControlId
	 * @param propertyName 属性名称
	 * @param propertyName 属性值
	 * @return
	 */
	@RequestMapping("checkPropertyUniquenes")
	public @ResponseBody Boolean checkPropertyUniquenes(String exportControlId, String propertyName, String propertyValue){
		return this.exportControlService.checkPropertyUniquenes(exportControlId, propertyName, propertyValue);
	}

	/**
	 * 判断指定字段唯一性
	 *
	 * @return
	 */
	@RequestMapping("countryData")
	public @ResponseBody Map<String,Object> getCountryData(){
		List<IComCodeTableItem> comCodeTableItemList =
				EIPService.getComCodeTableService().getCodeTableItems(ExportControlConstant.COUNTRY_INNERNAME);

		Map<String, Object> result = new HashMap<String, Object>();

		if (comCodeTableItemList == null) {
			return result;
		}

		for (IComCodeTableItem comCodeTableItem : comCodeTableItemList) {
			result.put(comCodeTableItem.getItemId(), comCodeTableItem.getDisplayName());
		}

		return result;
	}

	/**
	 * 判断指定字段唯一性
	 *
	 * @return
	 */
	@RequestMapping("getCountryByCountryId")
	public @ResponseBody IComCodeTableItem getCountryByCountryId(String countryId){
		IComCodeTableItem item = EIPService.getComCodeTableService().getCodetableItem(countryId);

		return item;
	}
}
