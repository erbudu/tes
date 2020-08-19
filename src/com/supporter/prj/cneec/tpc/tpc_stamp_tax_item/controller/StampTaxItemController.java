package com.supporter.prj.cneec.tpc.tpc_stamp_tax_item.controller;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.spring_mvc.AbstractController;
import com.supporter.prj.cneec.tpc.tpc_stamp_tax_item.service.StampTaxItemService;
import com.supporter.prj.cneec.tpc.util.OperResultConstant;
import com.supporter.prj.cneec.tpc.tpc_stamp_tax_item.entity.StampTaxItem;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.eip_service.authority.annotation.AuthCheck;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.exception.ExceptionCode;

/**
 * @Title: Controller
 * @Description: 贸易印花税税目表.
 * @author LEGO
 * @date 2020-02-03 13:26:24
 * @version V1.0
 */
@Controller
@RequestMapping("tpc/stampTaxItem")
public class StampTaxItemController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private StampTaxItemService stampTaxItemService;

	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param itemId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody StampTaxItem initEditOrViewPage(String itemId) {
		StampTaxItem entity = stampTaxItemService.initEditOrViewPage(itemId);
		return entity;
	}

	/**
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, StampTaxItem stampTaxItem) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.stampTaxItemService.getGrid(user, jqGrid, stampTaxItem);
		return jqGrid;
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param stampTaxItem 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > saveOrUpdate(StampTaxItem stampTaxItem) {
		UserProfile user = this.getUserProfile();
		StampTaxItem entity = this.stampTaxItemService.saveOrUpdate(user, stampTaxItem);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SAVE_SUCCESS, null, entity);
	}

	/**
	 * 删除操作
	 * 
	 * @param itemIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > batchDel(String itemIds) {
		this.stampTaxItemService.delete(itemIds);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SAVE_SUCCESS, null, null);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param itemId
	 * @param itemName
	 * @return
	 */
	@RequestMapping("nameIsValid")
	public @ResponseBody Boolean nameIsValid(String itemId,String taxItemName) {
		return this.stampTaxItemService.nameIsValid(itemId, taxItemName);
	}
	
	/**
	 * 获取印花税税目map，合同选择印花税用
	 * @return
	 */
	@RequestMapping("getstampTaxItemMap")
	public @ResponseBody Map<String, String> getstampTaxItemMap(){
		return this.stampTaxItemService.getstampTaxItemMap();
	}

}
