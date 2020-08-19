package com.supporter.prj.cneec.tpc.custom.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.cneec.tpc.custom.entity.CustomPayaccount;
import com.supporter.prj.cneec.tpc.custom.service.CustomPayaccountService;
import com.supporter.prj.cneec.tpc.custom.service.CustomService;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.log.controller.AbstractController;

@Controller
@RequestMapping("tpc/custompayaccount")
public class CustomPayaccountController extends AbstractController {
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private CustomService customService;
	
	@Autowired
	private CustomPayaccountService custompayaccountService;
	
	/**
	 * 返回列表
	 * 分页表格展示数据
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, String customId, String pagetype) throws Exception{
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.custompayaccountService.getLinkGrid(user, jqGrid, customId, pagetype);
		return jqGrid;
	}
	
	/**
	 * 初始化加载对象，避免前台编辑页面没有的字段保存后丢失
	 * @param payaccountId
	 * @param map
	 */
	@ModelAttribute
	public void getCustomPayaccount(@RequestParam(value = "payaccountId", required = false) String payaccount, Map<String, Object> map) {
		if (StringUtils.isNotBlank(payaccount)) {
			CustomPayaccount custompayaccount = this.custompayaccountService.get(payaccount);
			if (custompayaccount != null) {
				map.put("custompayaccount", custompayaccount);
			}
		}
	}
	
	/**
	 * 进入新建、编辑或查看页面时加载的信息
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody CustomPayaccount initEditOrViewPage(String payaccountId, String customId){
		CustomPayaccount entity = custompayaccountService.initEditOrviewPage(payaccountId, customId, this.getUserProfile());
		return entity;
	}
	
	/**
	 * 保存或更新数据
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody OperResult<CustomPayaccount> saveOrUpdate(CustomPayaccount custompayaccount){
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this.getPropValues(CustomPayaccount.class);
		CustomPayaccount entity = this.custompayaccountService.saveOrUpdate(user, custompayaccount, valueMap);
		return OperResult.succeed("saveSuccess", null, entity);
	}
	
	/**
	 * 删除操作
	 */
	@RequestMapping("batchDel")
	public @ResponseBody OperResult batchDel(String payaccountId){
		UserProfile user = this.getUserProfile();
		this.custompayaccountService.delete(user, payaccountId);
		return OperResult.succeed("deleteSuccess");
	}
	
	/**
	 * 检查支付类型
	 */
	@RequestMapping("checkPayType")
	public @ResponseBody Boolean checkPayType(String payaccountId,String customId){
		return this.custompayaccountService.checkPayType(payaccountId,customId);
	}
	
}
