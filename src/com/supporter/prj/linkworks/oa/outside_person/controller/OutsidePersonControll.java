package com.supporter.prj.linkworks.oa.outside_person.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.linkworks.oa.outside_person.entity.OutsidePerson;
import com.supporter.prj.linkworks.oa.outside_person.service.OutsidePersonService;
import com.supporter.prj.log.controller.AbstractController;

@Controller
@RequestMapping("oa/outsidePerson")
public class OutsidePersonControll extends AbstractController {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private OutsidePersonService outsidePersonService;
	
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, OutsidePerson outsidePerson) throws Exception{
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.outsidePersonService.getGrid(jqGrid, outsidePerson);
		return jqGrid;
	}
	
	/**
	 * 初始化加载对象，避免前台编辑页面没有的字段保存后丢失
	 * @param id
	 * @param map
	 */
	@ModelAttribute
	public void getOutsidePerson(@RequestParam(value = "id", required = false) String id, Map<String, Object> map){
		if (StringUtils.isNotBlank(id)){
			OutsidePerson outsidePerson = this.outsidePersonService.get(id);
			if (outsidePerson != null){
				map.put("outsidePerson", outsidePerson);
			}
		}
	}
	
	/**
	 * 进入新建、编辑或查看页面时加载的信息
	 * @param id
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody OutsidePerson initEditOrViewPage(String id){
		OutsidePerson outsidePerson = outsidePersonService.initEditOrViewPage(id);
		return outsidePerson;
	}
	
	/**
	 * 保存或更新数据
	 * @param outsidePerson
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody OperResult<OutsidePerson> saveOrUpdate(OutsidePerson outsidePerson){
		OutsidePerson entity = this.outsidePersonService.saveOrUpdate(outsidePerson);
		return OperResult.succeed("saveSuccess", null, entity);
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@RequestMapping("batchDel")
	public @ResponseBody OperResult batchDel(String id){
		this.outsidePersonService.batchDel(id);
		return OperResult.succeed("deleteSuccess");
	}
	
	/**
	 * 获取状态
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getStatusMap")
	public Map<String, String> getStatusMap(){
		return OutsidePerson.getStatusMap();
	}
}
