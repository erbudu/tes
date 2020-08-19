package com.supporter.prj.cneec.tpc.order_online.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.cneec.tpc.contract_stamp_tax_amount.entity.ContractStampTaxAmount;
import com.supporter.prj.cneec.tpc.contract_stamp_tax_amount.service.ContractStampTaxAmountService;
import com.supporter.prj.cneec.tpc.order_online.entity.OrderOnline;
import com.supporter.prj.cneec.tpc.order_online.service.OrderOnlineService;
import com.supporter.prj.cneec.tpc.util.OperResultConstant;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.spring_mvc.AbstractController;

/**
 * @Title: OrderOnlineController
 * @Description: 控制器类
 * @author: yanweichao
 * @date: 2017-10-30
 * @version: V1.0
 */
@Controller
@RequestMapping("tpc/orderOnline")
public class OrderOnlineController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private OrderOnlineService orderOnlineService;
	
	@Autowired
	private ContractStampTaxAmountService contractStampTaxAmountService;

	/**
	 * 返回列表. 分页表格展示数据.
	 * @param request
	 * @param jqGridReq
	 * @param orderOnline
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody
	JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, OrderOnline orderOnline) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.orderOnlineService.getGrid(user, jqGrid, orderOnline);
		return jqGrid;
	}

	/**
	 * 获取合同额
	 * @param request
	 * @param jqGridReq
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getContractAmountGrid")
	public @ResponseBody
	JqGrid getContractAmountGrid(HttpServletRequest request, JqGridReq jqGridReq, String orderId) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.orderOnlineService.getContractAmountGrid(user, jqGrid, orderId);
		return jqGrid;
	}

	/**
	 * 获取货物及服务明细
	 * @param request
	 * @param jqGridReq
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGoodsGrid")
	public @ResponseBody
	JqGrid getGoodsGrid(HttpServletRequest request, JqGridReq jqGridReq, String orderId) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.orderOnlineService.getGoodsGrid(user, jqGrid, orderId);
		return jqGrid;
	}

	/**
	 * 获取收款条件
	 * @param request
	 * @param jqGridReq
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getCollectionTermsGrid")
	public @ResponseBody
	JqGrid getCollectionTermsGrid(HttpServletRequest request, JqGridReq jqGridReq, String orderId) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.orderOnlineService.getCollectionTermsGrid(user, jqGrid, orderId);
		return jqGrid;
	}

	/**
	 * 初始化加载对象，避免前台编辑页面没有的字段保存后丢失
	 * @param orderId
	 * @param map
	 */
	@ModelAttribute
	public void getOrderOnline(String orderId, Map<String, Object> map) {
		if (!StringUtils.isBlank(orderId)) {
			OrderOnline orderOnline = this.orderOnlineService.get(orderId);
			if (orderOnline != null) {
				map.put("orderOnline", orderOnline);
			}
		}
	}

	/**
	 * 根据ID获取功能模块
	 * @param orderId
	 * @return
	 */
	@RequestMapping("get")
	public @ResponseBody
	OrderOnline get(String orderId) {
		OrderOnline orderOnline = this.orderOnlineService.get(orderId);
		return orderOnline;
	}

	/**
	 * 编辑或查看页面加载对象
	 * @param orderId
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody
	OrderOnline initEditOrViewPage(String orderId) {
		UserProfile user = this.getUserProfile();
		OrderOnline orderOnline = this.orderOnlineService.initEditOrViewPage(orderId, user);
		// 编辑权限验证
		boolean editOper = Boolean.valueOf(this.getRequestPara("editOper"));
		if (editOper) {
			this.orderOnlineService.getAuthCanExecute(user, orderOnline);
		}
		return orderOnline;
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param orderOnline 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody
	OperResult<OrderOnline> saveOrUpdate(OrderOnline orderOnline) {
		UserProfile user = this.getUserProfile();
		// 权限验证
		//this.orderOnlineService.getAuthCanExecute(user, orderOnline);
		Map<String, Object> valueMap = this.getPropValues(OrderOnline.class);
		OrderOnline entity = this.orderOnlineService.saveOrUpdate(user, orderOnline, valueMap);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SAVE_SUCCESS, "保存成功", entity);
	}

	/**
	 * 提交数据
	 * @param orderOnline
	 * @return
	 */
	@RequestMapping("commit")
	public @ResponseBody
	OperResult<OrderOnline> commit(OrderOnline orderOnline) {
		UserProfile user = this.getUserProfile();
		// 权限验证
		//this.orderOnlineService.getAuthCanExecute(user, orderOnline);
		Map<String, Object> valueMap = this.getPropValues(OrderOnline.class);
		OrderOnline entity = this.orderOnlineService.commit(user, orderOnline, valueMap);
		return OperResult.succeed(OperResultConstant.CODE_OPER_COMMIT_SUCCESS, "提交成功", entity);
	}

	/**
	 * 删除操作
	 * 
	 * @param orderIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	public @ResponseBody
	OperResult<?> batchDel(String orderIds) {
		UserProfile user = this.getUserProfile();
		this.orderOnlineService.delete(user, orderIds);
		return OperResult.succeed(OperResultConstant.CODE_OPER_DELETE_SUCCESS);
	}

	/**
	 * 生成信息上线单
	 * @return
	 */
	@RequestMapping("createContractOnlineBySheet")
	public @ResponseBody
	OperResult<?> createContractOnlineBySheet() {
		Map<String, Object> valueMap = this.getRequestParameters();
		boolean flag = this.orderOnlineService.createContractOnlineBySheet(valueMap);
		if (flag) {
			return OperResult.succeed("createContractOnlineBySheetSuccess", "生成信息上线成功", null);
		} else {
			return OperResult.fail("createContractOnlineBySheetFail", "生成信息上线失败");
		}
	}

	/**
	 * 获取状态字典数据
	 */
	@RequestMapping(value = "/selectSwfStatusData")
	public @ResponseBody
	Map<Integer, String> selectSwfStatusData() {
		return OrderOnline.getSwfStatusMap();
	}

	/**
	 * 根据合同列表生成(合同签约评审合同)
	 * @return
	 */
	@RequestMapping("initOrderOnlineByInforId")
	public @ResponseBody
	OrderOnline initOrderOnlineByInforId(String inforId) {
		OrderOnline orderOnline;
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this.getRequestParameters();
		if (StringUtils.isNotBlank(inforId)){
			orderOnline = this.orderOnlineService.initOrderOnlineByInforId(inforId, valueMap, user);
		} else {
			String orderId = (String) valueMap.get("orderId");
			orderOnline = this.orderOnlineService.initEditOrViewPage(orderId, user);
		}
		return orderOnline;
	}

	/**
	 * 获取币别数据
	 */
	@RequestMapping(value = "/selectCurrencyData")
	public @ResponseBody
	Map<String, String> selectCurrencyData(String orderId) {
		return this.orderOnlineService.getContractCurrencyMap(orderId);
	}
	
	/**
	 * 设置印花税
	 * @param onlineId
	 * @param taxItemId
	 * @return
	 */
	@RequestMapping(value = "/setTaxItem")
	public @ResponseBody boolean setTaxItem(String onlineId, String taxItemId) {
		return this.contractStampTaxAmountService.createOrderStampTaxAmountByOnline(onlineId, taxItemId);
	}
	
}