package com.supporter.prj.cneec.tpc.order_change.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.cneec.tpc.order_change.entity.ContractChOrder;
import com.supporter.prj.cneec.tpc.order_change.entity.OrderChange;
import com.supporter.prj.cneec.tpc.order_change.entity.OrderChangeGoods;
import com.supporter.prj.cneec.tpc.order_change.entity.OrderSeal;
import com.supporter.prj.cneec.tpc.order_change.service.OrderChangeService;
import com.supporter.prj.cneec.tpc.order_change.util.OrderChangeConstant;
import com.supporter.prj.cneec.tpc.util.CheckBoxVO;
import com.supporter.prj.cneec.tpc.util.OperResultConstant;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip.module.constant.ModuleConstant;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.spring_mvc.AbstractController;
import com.supporter.util.CommonUtil;

/**
 * @Title: OrderChangeController
 * @Description: 控制器类
 */
@Controller
@RequestMapping("tpc/orderChange")
public class OrderChangeController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private OrderChangeService orderChangeService;

	/**
	 * 返回列表. 分页表格展示数据.
	 * @param request
	 * @param jqGridReq
	 * @param orderChange
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody
	JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, OrderChange orderChange,String contractId) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.orderChangeService.getGrid(user, jqGrid, orderChange,contractId);
		return jqGrid;
	}

	/**
	 * 获取合同额
	 * @param request
	 * @param jqGridReq
	 * @param changeId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getContractAmountGrid")
	public @ResponseBody
	JqGrid getContractAmountGrid(HttpServletRequest request, JqGridReq jqGridReq, String chId) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.orderChangeService.getContractAmountGrid(user, jqGrid, chId);
		return jqGrid;
	}

	/**
	 * 获取货物及服务明细
	 * @param request
	 * @param jqGridReq
	 * @param changeId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGoodsGrid")
	public @ResponseBody
	JqGrid getGoodsGrid(HttpServletRequest request, JqGridReq jqGridReq, String chId) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.orderChangeService.getGoodsGrid(user, jqGrid, chId);
		return jqGrid;
	}

	@RequestMapping("getGoodsChildGrid")
	public @ResponseBody
	JqGrid getGoodsChildGrid(HttpServletRequest request, JqGridReq jqGridReq, String goodsId) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.orderChangeService.getGoodsChildGrid(user, jqGrid, goodsId);
		return jqGrid;
	}
	@RequestMapping("getGoodsGridSingle")
	public @ResponseBody
	JqGrid getGoodsGridSingle(HttpServletRequest request, JqGridReq jqGridReq, String goodsId) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.orderChangeService.getGoodsGridSingle(user, jqGrid, goodsId);
		return jqGrid;
	}
	/**
	 * 获取收款条件
	 * @param request
	 * @param jqGridReq
	 * @param changeId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getCollectionTermsGrid")
	public @ResponseBody
	JqGrid getCollectionTermsGrid(HttpServletRequest request, JqGridReq jqGridReq, String chId) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.orderChangeService.getCollectionTermsGrid(user, jqGrid, chId);
		return jqGrid;
	}

	/**
	 * 选择销售合同上线对象并赋值给当前对象
	 * @param orderChange
	 * @return
	 */
//	@RequestMapping("selectSaleContractToInit")
//	public @ResponseBody
//	OperResult<OrderChange> selectSaleContractToInit(OrderChange orderChange) {
//		OrderChange entity = this.orderChangeService.selectSaleContractToInit(orderChange, this.getUserProfile());
//		return OperResult.succeed(OperResultConstant.CODE_OPER_SAVE_SUCCESS, "保存成功", entity);
//	}
	/**
	 * 保存盖章人
	 */
	@RequestMapping("saveStamp")
	@ResponseBody
	public OrderSeal saveStamp(String sealId, String stampPersonId, String stampPerson,Date stampDate) {
		OrderSeal orderSeal = orderChangeService.saveStamp(sealId, stampPersonId, stampPerson,stampDate);
		return orderSeal;
	}
	/**
	 * 初始化加载对象，避免前台编辑页面没有的字段保存后丢失
	 * @param changeId
	 * @param map
	 */
	@ModelAttribute
	public void getOrderChange(String changeId, Map<String, Object> map) {
		if (!StringUtils.isBlank(changeId)) {
			OrderChange orderChange = this.orderChangeService.get(changeId);
			if (orderChange != null) {
				map.put("orderChange", orderChange);
			}
		}
	}

	/**
	 * 根据ID获取功能模块
	 * @param changeId
	 * @return
	 */
	@RequestMapping("get")
	public @ResponseBody
	OrderChange get(String changeId) {
		OrderChange orderChange = this.orderChangeService.get(changeId);
		return orderChange;
	}

	/**
	 * 编辑或查看页面加载对象
	 * @param changeId
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody
	ContractChOrder initEditOrViewPage(String changeId) {
		UserProfile user = this.getUserProfile();
		ContractChOrder contractChOrder = this.orderChangeService.initEditOrViewPage(changeId, user);
		// 编辑权限验证
//		boolean editOper = Boolean.valueOf(this.getRequestPara("editOper"));
//		if (editOper) {
//			this.orderChangeService.getAuthCanExecute(user, orderChange);
//		}
		return contractChOrder;
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param orderChange 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody
	OperResult<ContractChOrder> saveOrUpdate(ContractChOrder contractChOrder) {
		UserProfile user = this.getUserProfile();
		// 权限验证
		//this.orderChangeService.getAuthCanExecute(user, orderChange);
		Map<String, Object> valueMap = this.getPropValues(ContractChOrder.class);
		ContractChOrder entity = this.orderChangeService.saveOrUpdate(user, contractChOrder, valueMap);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SAVE_SUCCESS, "保存成功", entity);
	}
	/**
	 * 保存一级货物明细
	 * @return
	 */
	@RequestMapping("saveFirstDetail")
	public @ResponseBody
	OperResult<OrderChangeGoods> saveFirstDetail(OrderChangeGoods orderChangeGoods) {
		this.orderChangeService.saveFirstDetail(orderChangeGoods);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SAVE_SUCCESS, "保存成功", orderChangeGoods);
	}
	
	@RequestMapping("saveTwoDetail")
	public @ResponseBody
	OperResult<OrderChangeGoods> saveTwoDetail(OrderChangeGoods orderChangeGoods) {
		this.orderChangeService.saveTwoDetail(orderChangeGoods);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SAVE_SUCCESS, "保存成功", null);
	}
	
	/**
	 * 保存合同额
	 * @return
	 */
	@RequestMapping("saveDetail")
	public @ResponseBody
	 boolean saveDetail(ContractChOrder order) {
		this.orderChangeService.saveDetail(order);
		return true;
	}
	

	/**
	 * 删除操作
	 * 
	 * @param changeIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	public @ResponseBody
	OperResult<?> batchDel(String changeIds) {
		UserProfile user = this.getUserProfile();
		this.orderChangeService.delete(user, changeIds);
		return OperResult.succeed(OperResultConstant.CODE_OPER_DELETE_SUCCESS);
	}

	/**
	 * 获取状态字典数据
	 */
	@RequestMapping(value = "/selectSwfStatusData")
	public @ResponseBody
	Map<Integer, String> selectSwfStatusData() {
		return OrderChange.getSwfStatusMap();
	}

	/**
	 * 调整性质(可多选)
	 * @param key
	   adjustmentNatureId
	   adjustmentNature
	 * @return
	 */
	@RequestMapping(value = "/selectAdjustmentNatureData")
	public @ResponseBody
	List<CheckBoxVO> selectAdjustmentNatureData(String key,String chType) {
		String changeId = getRequestPara("changeId");
		return this.orderChangeService.getAdjustmentNatureList(CommonUtil.trim(changeId),chType);
	}

	/**
	 * 变更方式
	 * @return
	 */
	@RequestMapping(value = "/selectChangeModeData")
	public @ResponseBody
	Map<String, String> selectChangeModeData() {
		return OrderChangeConstant.getChangeModeMap();
	}
	
	/**
	 * 增减类型
	 * @return
	 */
	@RequestMapping(value = "/selectChangeTypeData")
	public @ResponseBody
	Map<String, String> selectChangeTypeData() {
		return OrderChangeConstant.getChangeTypeMap();
	}

	/**
	 * 保存
	 * 
	 * @param CompletionPlan
	 * @return
	 */
	@RequestMapping("saveChangeOrder")
	public @ResponseBody OperResult<OrderChange> saveChangeOrder(OrderChange orderChange) {
		UserProfile user = this.getUserProfile();
		OrderChange entity = this.orderChangeService.saveChangeOrder(user, orderChange);
		return OperResult.succeed(ModuleConstant.I18nKey.SAVE_SUCCESS, null, entity);
	}
	/**
	 * 编辑或查看页面加载对象。
	 */
	@RequestMapping("initChangeOrder")
	@ResponseBody
	public OrderChange initChangeOrder(String changeId, String contractId, int chType) {
		UserProfile user = this.getUserProfile();
		OrderChange orderChange = orderChangeService.initChangeOrder(changeId, contractId, chType,user);
		return orderChange;
	}
	/**
	 * 编辑或查看页面加载对象。变更单审批页面
	 */
	@RequestMapping("initOrder")
	@ResponseBody
	public OrderChange initOrder(String changeId) {
		OrderChange orderChange = orderChangeService.initOrder(changeId);
		return orderChange;
	}
	/**
	 * 编辑或查看页面加载对象。合同审批页面
	 */
	@RequestMapping("initContract")
	@ResponseBody
	public ContractChOrder initContract(String chId) {
		ContractChOrder contractChOrder = orderChangeService.initContract(chId);
		return contractChOrder;
	}
	/**
	 * 根据变更单ID获取信息到用印单
	 * @param changeId
	 * @return
	 */
	@RequestMapping("initSealPage")
	public @ResponseBody
	OrderSeal initSealPage(String changeId) {
		UserProfile user = this.getUserProfile();
		OrderSeal orderSeal = this.orderChangeService.initSealPage(changeId,user);
		return orderSeal;
	}
	/**
	 * 编辑或查看页面加载对象。用印审批页面
	 */
	@RequestMapping("initSeal")
	@ResponseBody
	public OrderSeal initSeal(String sealId) {
		OrderSeal orderSeal = orderChangeService.initSeal(sealId);
		return orderSeal;
	}
	/**
	 * 根据changeId查询contractSeal，用于点击导航菜单的查看页面
	 */
	@RequestMapping("getContractSealByChangeId")
	@ResponseBody
	public OrderSeal getContractSealByChangeId(String changeId) {
		OrderSeal orderSeal = orderChangeService.getContractSealByChangeId(changeId);
		return orderSeal;
	}
	/**
	 * 根据changeId查询contractOrder，用于点击导航菜单的查看页面
	 */
	@RequestMapping("getContractOrderByChangeId")
	@ResponseBody
	public ContractChOrder getContractOrderByChangeId(String changeId) {
		ContractChOrder contractChOrder = orderChangeService.getContractOrderByChangeId(changeId);
		return contractChOrder;
	}
	/**
	 * 保存变更用印
	 * 
	 * @param CompletionPlan
	 * @return
	 */
	@RequestMapping("saveChangeSeal")
	public @ResponseBody OperResult<OrderSeal> saveChangeSeal(OrderSeal orderSeal) {
		UserProfile user = this.getUserProfile();
		OrderSeal entity = this.orderChangeService.saveChangeSeal(user, orderSeal);
		return OperResult.succeed(ModuleConstant.I18nKey.SAVE_SUCCESS, null, entity);
	}
	
	/**
	 * 根据contractId查询变更表有无数据
	 * @param contractId
	 */
	@RequestMapping("checkOrderChange")
	public @ResponseBody
	String checkOrderChange(String contractId) {
		return this.orderChangeService.checkOrderChange(contractId);
	}
	
	/**
	 * 根据changeId查询变更上线表
	 * @param changeId
	 */
	@RequestMapping("checkContractChOrder")
	public @ResponseBody
	boolean checkContractChOrder(String changeId) {
		return this.orderChangeService.checkContractChOrder(changeId);
	}
	
	/**
	 * 查询上线合同额是否跟变更合同额相等
	 * @param changeId
	 * @param contractRmbAmount
	 * return true
	 */
	@RequestMapping("checkRmbAmount")
	public @ResponseBody
	boolean checkRmbAmount(String changeId, double contractRmbAmount) {
		OrderChange orderChange = orderChangeService.get(changeId);
		double orderChangerebamount = orderChange.getContractRmbAmount();
		if(orderChangerebamount==contractRmbAmount) {
			return true;
		}
		return false;
	}
	/**
	 * 经办人只会
	 *
	 */
	@RequestMapping("callPaper")
	@ResponseBody
	public void callPaper(String chId) {
		 orderChangeService.callPaper(chId);
	}
	/**
	 * 判断收款条款是否可以被删除
	 * @return
	 */
	@RequestMapping("canDelTent")
	@ResponseBody
	public String canDelTent(String termsId) {
		return orderChangeService.canDelTent(termsId);
	}
	/**
	 * 是否有协议
	 * @return
	 */
//	@RequestMapping(value = "/selectHasProtocolData")
//	public @ResponseBody
//	List<CheckBoxVO> selectHasProtocolData(String changeId) {
//		return this.orderChangeService.getHasProtocolList(CommonUtil.trim(changeId));
//	}
	/**
	 * 提交数据
	 * @param orderChange
	 * @return
	 */
//	@RequestMapping("commit")
//	public @ResponseBody
//	OperResult<OrderChange> commit(OrderChange orderChange) {
//		UserProfile user = this.getUserProfile();
//		// 权限验证
//		//this.orderChangeService.getAuthCanExecute(user, orderChange);
//		Map<String, Object> valueMap = this.getPropValues(OrderChange.class);
//		OrderChange entity = this.orderChangeService.commit(user, orderChange, valueMap);
//		return OperResult.succeed(OperResultConstant.CODE_OPER_COMMIT_SUCCESS, "提交成功", entity);
//	}
}