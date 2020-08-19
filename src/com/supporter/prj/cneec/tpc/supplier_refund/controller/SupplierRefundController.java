package com.supporter.prj.cneec.tpc.supplier_refund.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.cneec.tpc.supplier_refund.entity.SupplierRefund;
import com.supporter.prj.cneec.tpc.supplier_refund.service.SupplierRefundService;
import com.supporter.prj.cneec.tpc.util.OperResultConstant;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.spring_mvc.AbstractController;

/**
 * @Title: SupplierRefundController
 * @Description: 控制器类
 * @author: yanweichao
 * @date: 2017-11-22
 * @version: V1.0
 */
@Controller
@RequestMapping("tpc/supplierRefund")
public class SupplierRefundController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private SupplierRefundService supplierRefundService;

	/**
	 * 返回列表. 分页表格展示数据.
	 * @param request
	 * @param jqGridReq
	 * @param supplierRefund
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody
	JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, SupplierRefund supplierRefund) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.supplierRefundService.getGrid(user, jqGrid, supplierRefund);
		return jqGrid;
	}

	/**
	 * 初始化加载对象，避免前台编辑页面没有的字段保存后丢失
	 * @param refundId
	 * @param map
	 */
	@ModelAttribute
	public void getSupplierRefund(String refundId, Map<String, Object> map) {
		if (!StringUtils.isBlank(refundId)) {
			SupplierRefund supplierRefund = this.supplierRefundService.get(refundId);
			if (supplierRefund != null) {
				map.put("supplierRefund", supplierRefund);
			}
		}
	}

	/**
	 * 根据ID获取功能模块
	 * @param refundId
	 * @return
	 */
	@RequestMapping("get")
	public @ResponseBody
	SupplierRefund get(String refundId) {
		SupplierRefund supplierRefund = this.supplierRefundService.get(refundId);
		return supplierRefund;
	}

	/**
	 * 编辑或查看页面加载对象
	 * @param refundId
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody
	SupplierRefund initEditOrViewPage(String refundId) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this.getRequestParameters();
		SupplierRefund supplierRefund = this.supplierRefundService.initEditOrViewPage(refundId, valueMap, user);
		// 编辑权限验证
		boolean editOper = Boolean.valueOf(this.getRequestPara("editOper"));
		if (editOper) {
			this.supplierRefundService.getAuthCanExecute(user, supplierRefund);
		}
		return supplierRefund;
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param supplierRefund 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody OperResult<SupplierRefund> saveOrUpdate(SupplierRefund supplierRefund) {
		UserProfile user = this.getUserProfile();
		
		SupplierRefund targetEntity = this.initEditOrViewPage(supplierRefund.getRefundId());
		this.setPropValues(targetEntity);
		targetEntity.setDetailList(supplierRefund.getDetailList());
		
		this.supplierRefundService.saveOrUpdate(user, targetEntity);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SAVE_SUCCESS, "保存成功", targetEntity);
	}

	/**
	 * 提交数据
	 * @param supplierRefund
	 * @return
	 */
	@RequestMapping("commit")
	public @ResponseBody
	OperResult<SupplierRefund> commit(SupplierRefund supplierRefund) {
		UserProfile user = this.getUserProfile();
		SupplierRefund targetEntity = this.initEditOrViewPage(supplierRefund.getRefundId());
		this.setPropValues(targetEntity);
		targetEntity.setDetailList(supplierRefund.getDetailList());
		
		this.supplierRefundService.commit(user, targetEntity);
		return OperResult.succeed(OperResultConstant.CODE_OPER_COMMIT_SUCCESS, "提交成功", targetEntity);
	}

	/**
	 * 删除操作
	 * 
	 * @param refundIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	public @ResponseBody
	OperResult<?> batchDel(String refundIds) {
		UserProfile user = this.getUserProfile();
		this.supplierRefundService.delete(user, refundIds);
		return OperResult.succeed(OperResultConstant.CODE_OPER_DELETE_SUCCESS);
	}

	/**
	 * 获取状态字典数据
	 */
	@RequestMapping(value = "/selectSwfStatusData")
	public @ResponseBody
	Map<Integer, String> selectSwfStatusData() {
		return SupplierRefund.getSwfStatusMap();
	}

	/**
	 * 根据合同获取采购合同付款记录码表
	 * 
	 * @param contractId
	 *            合同ID
	 * @return
	 */
	@RequestMapping("getSettlementCodeTable")
	public @ResponseBody
	Map<String, String> getSettlementCodeTable(String contractId) {
		Map<String, String> map = supplierRefundService.getSettlementCodeTable(contractId, this.getUserProfile());
		return map;
	}

	/**
	 * 根据收款单获取销售合同收款单明细码表
	 * 
	 * @param settlementId
	 *            付款单ID
	 * @return
	 */
	@RequestMapping("getSettlementDetailCodeTable")
	public @ResponseBody
	Map<String, String> getSettlementDetailCodeTable(String settlementId) {
		Map<String, String> map = supplierRefundService.getSettlementDetailCodeTable(settlementId);
		return map;
	}

	/**
	 * 返回列表. 分页表格展示数据.
	 * @param request
	 * @param jqGridReq
	 * @param collectionConfirmation
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getDetailGrid")
	public @ResponseBody
	JqGrid getDetailGrid(HttpServletRequest request, JqGridReq jqGridReq, String refundId) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.supplierRefundService.getDetailGrid(user, jqGrid, refundId);
		return jqGrid;
	}

}