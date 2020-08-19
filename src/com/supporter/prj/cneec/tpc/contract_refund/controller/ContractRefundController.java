package com.supporter.prj.cneec.tpc.contract_refund.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.cneec.tpc.contract_refund.entity.ContractRefund;
import com.supporter.prj.cneec.tpc.contract_refund.service.ContractRefundService;
import com.supporter.prj.cneec.tpc.util.CheckBoxVO;
import com.supporter.prj.cneec.tpc.util.OperResultConstant;
import com.supporter.prj.cneec.tpc.util.TpcConstant;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.spring_mvc.AbstractController;
import com.supporter.util.CommonUtil;

/**
 * @Title: ContractRefundController
 * @Description: 控制器类
 * @author: yanweichao
 * @date: 2017-11-24
 * @version: V1.0
 */
@Controller
@RequestMapping("tpc/contractRefund")
public class ContractRefundController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ContractRefundService contractRefundService;

	/**
	 * 返回列表. 分页表格展示数据.
	 * @param request
	 * @param jqGridReq
	 * @param contractRefund
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody
	JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, ContractRefund contractRefund) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.contractRefundService.getGrid(user, jqGrid, contractRefund);
		return jqGrid;
	}
	
	/**
	 * 初始化加载对象，避免前台编辑页面没有的字段保存后丢失
	 * @param refundId
	 * @param map
	 */
	@ModelAttribute
	public void getContractRefund(String refundId, Map<String, Object> map) {
		if (!StringUtils.isBlank(refundId)) {
			ContractRefund contractRefund = this.contractRefundService.get(refundId);
			if (contractRefund != null) {
				map.put("contractRefund", contractRefund);
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
	ContractRefund get(String refundId) {
		ContractRefund contractRefund = this.contractRefundService.get(refundId);
		return contractRefund;
	}

	/**
	 * 编辑或查看页面加载对象
	 * @param refundId
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody
	ContractRefund initEditOrViewPage(String refundId) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this.getRequestParameters();
		ContractRefund contractRefund = this.contractRefundService.initEditOrViewPage(refundId, valueMap, user);
		// 编辑权限验证
		boolean editOper = Boolean.valueOf(this.getRequestPara("editOper"));
		if (editOper) {
			this.contractRefundService.getAuthCanExecute(user, contractRefund);
		}
		return contractRefund;
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param contractRefund 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody
	OperResult<ContractRefund> saveOrUpdate(ContractRefund contractRefund) {
		UserProfile user = this.getUserProfile();
		
		ContractRefund targetEntity = this.initEditOrViewPage(contractRefund.getRefundId());
		this.setPropValues(targetEntity);
		targetEntity.setDetailList(contractRefund.getDetailList());
		
		this.contractRefundService.saveOrUpdate(user, targetEntity);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SAVE_SUCCESS, "保存成功", targetEntity);
	}

	/**
	 * 提交数据
	 * @param contractRefund
	 * @return
	 */
	@RequestMapping("commit")
	public @ResponseBody OperResult<ContractRefund> commit(ContractRefund contractRefund) {
		UserProfile user = this.getUserProfile();
		
		ContractRefund targetEntity = this.initEditOrViewPage(contractRefund.getRefundId());
		this.setPropValues(targetEntity);
		targetEntity.setDetailList(contractRefund.getDetailList());
		
		this.contractRefundService.commit(user, targetEntity);
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
		this.contractRefundService.delete(user, refundIds);
		return OperResult.succeed(OperResultConstant.CODE_OPER_DELETE_SUCCESS);
	}

	/**
	 * 获取状态字典数据
	 */
	@RequestMapping(value = "/selectSwfStatusData")
	public @ResponseBody
	Map<Integer, String> selectSwfStatusData() {
		return ContractRefund.getSwfStatusMap();
	}

	/**
	 * 境内外退款
	 * @return
	 */
	@RequestMapping(value = "/selectIsAbroadData")
	public @ResponseBody
	List<CheckBoxVO> selectIsAbroadData(String key) {
		return this.contractRefundService.getIsAbroadList(CommonUtil.trim(key));
	}

	/**
	 * 开户行所在省
	 * @return
	 */
	@RequestMapping(value = "/selectRemitProvinceData")
	public @ResponseBody
	Map<String, String> selectRemitProvinceData() {
		return TpcConstant.getRemitProvinceMap();
	}

	/**
	 * 开户行所在市
	 * @param value 级联选择时上级select传递过来的参数名必须为value
	 * @return
	 */
	@RequestMapping(value = "/selectRemitCityData")
	public @ResponseBody
	Map<String, String> selectRemitCityData(String value) {
		return TpcConstant.getRemitCityMap(CommonUtil.trim(value));
	}
	
	/**
	 * 根据合同获取销售合同收款单码表
	 * 
	 * @param contractId
	 *            合同ID
	 * @return
	 */
	@RequestMapping("getCollectionCodeTable")
	public @ResponseBody
	Map<String, String> getCollectionCodeTable(String contractId) {
		Map<String, String> map = contractRefundService.getCollectionCodeTable(contractId, this.getUserProfile());
		return map;
	}

	/**
	 * 根据收款单获取销售合同收款单明细码表
	 * 
	 * @param collectionId
	 *            收款单ID
	 * @return
	 */
	@RequestMapping("getCollectionDetailCodeTable")
	public @ResponseBody
	Map<String, String> getCollectionDetailCodeTable(String collectionId) {
		Map<String, String> map = contractRefundService.getCollectionDetailCodeTable(collectionId);
		return map;
	}

	//*****************************操作子表*******************************
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
		this.contractRefundService.getDetailGrid(user, jqGrid, refundId);
		return jqGrid;
	}

}