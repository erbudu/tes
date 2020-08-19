package com.supporter.prj.cneec.tpc.contract_online.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.cneec.tpc.contract_online.entity.ContractOnline;
import com.supporter.prj.cneec.tpc.contract_online.service.ContractOnlineService;
import com.supporter.prj.cneec.tpc.contract_stamp_tax_amount.service.ContractStampTaxAmountService;
import com.supporter.prj.cneec.tpc.util.OperResultConstant;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.spring_mvc.AbstractController;

/**
 * @Title: ContractOnlineController
 * @Description: 控制器类
 * @author: yanweichao
 * @date: 2017-11-06
 * @version: V1.0
 */
@Controller
@RequestMapping("tpc/contractOnline")
public class ContractOnlineController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ContractOnlineService contractOnlineService;
	
	@Autowired
	private ContractStampTaxAmountService contractStampTaxAmountService;

	/**
	 * 返回列表. 分页表格展示数据.
	 * @param request
	 * @param jqGridReq
	 * @param contractOnline
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody
	JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, ContractOnline contractOnline) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.contractOnlineService.getGrid(user, jqGrid, contractOnline);
		return jqGrid;
	}

	/**
	 * 获取合同额
	 * @param request
	 * @param jqGridReq
	 * @param contractId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getContractAmountGrid")
	public @ResponseBody
	JqGrid getContractAmountGrid(HttpServletRequest request, JqGridReq jqGridReq, String contractId) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.contractOnlineService.getContractAmountGrid(user, jqGrid, contractId);
		return jqGrid;
	}

	/**
	 * 获取货物及服务明细
	 * @param request
	 * @param jqGridReq
	 * @param contractId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGoodsGrid")
	public @ResponseBody
	JqGrid getGoodsGrid(HttpServletRequest request, JqGridReq jqGridReq, String contractId) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.contractOnlineService.getGoodsGrid(user, jqGrid, contractId);
		return jqGrid;
	}

	/**
	 * 获取收款条件
	 * @param request
	 * @param jqGridReq
	 * @param contractId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getCollectionTermsGrid")
	public @ResponseBody
	JqGrid getCollectionTermsGrid(HttpServletRequest request, JqGridReq jqGridReq, String contractId) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.contractOnlineService.getCollectionTermsGrid(user, jqGrid, contractId);
		return jqGrid;
	}

	/**
	 * 初始化加载对象，避免前台编辑页面没有的字段保存后丢失
	 * @param contractId
	 * @param map
	 */
	@ModelAttribute
	public void getContractOnline(String contractId, Map<String, Object> map) {
		if (!StringUtils.isBlank(contractId)) {
			ContractOnline contractOnline = this.contractOnlineService.get(contractId);
			if (contractOnline != null) {
				map.put("contractOnline", contractOnline);
			}
		}
	}

	/**
	 * 根据ID获取功能模块
	 * @param contractId
	 * @return
	 */
	@RequestMapping("get")
	public @ResponseBody
	ContractOnline get(String contractId) {
		ContractOnline contractOnline = this.contractOnlineService.get(contractId);
		return contractOnline;
	}

	/**
	 * 编辑或查看页面加载对象
	 * @param contractId
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody
	ContractOnline initEditOrViewPage(String contractId) {
		UserProfile user = this.getUserProfile();
		ContractOnline contractOnline = this.contractOnlineService.initEditOrViewPage(contractId, user);
		// 编辑权限验证
		boolean editOper = Boolean.valueOf(this.getRequestPara("editOper"));
		if (editOper) {
			this.contractOnlineService.getAuthCanExecute(user, contractOnline);
		}
		return contractOnline;
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param contractOnline 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody
	OperResult<ContractOnline> saveOrUpdate(ContractOnline contractOnline) {
		UserProfile user = this.getUserProfile();
		// 权限验证
		//this.contractOnlineService.getAuthCanExecute(user, contractOnline);
		Map<String, Object> valueMap = this.getPropValues(ContractOnline.class);
		ContractOnline entity = this.contractOnlineService.saveOrUpdate(user, contractOnline, valueMap);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SAVE_SUCCESS, "保存成功", entity);
	}

	/**
	 * 提交数据
	 * @param contractOnline
	 * @return
	 */
	@RequestMapping("commit")
	public @ResponseBody
	OperResult<ContractOnline> commit(ContractOnline contractOnline) {
		UserProfile user = this.getUserProfile();
		// 权限验证
		//this.contractOnlineService.getAuthCanExecute(user, contractOnline);
		Map<String, Object> valueMap = this.getPropValues(ContractOnline.class);
		ContractOnline entity = this.contractOnlineService.commit(user, contractOnline, valueMap);
		return OperResult.succeed(OperResultConstant.CODE_OPER_COMMIT_SUCCESS, "提交成功", entity);
	}

	/**
	 * 删除操作
	 * 
	 * @param contractIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	public @ResponseBody
	OperResult<?> batchDel(String contractIds) {
		UserProfile user = this.getUserProfile();
		this.contractOnlineService.delete(user, contractIds);
		return OperResult.succeed(OperResultConstant.CODE_OPER_DELETE_SUCCESS);
	}

	/**
	 * 获取状态字典数据
	 */
	@RequestMapping(value = "/selectSwfStatusData")
	public @ResponseBody
	Map<Integer, String> selectSwfStatusData() {
		return ContractOnline.getSwfStatusMap();
	}

	/**
	 * 根据合同列表生成(合同签约评审合同)
	 * @return
	 */
	@RequestMapping("initContractOnlineByInforId")
	public @ResponseBody
	ContractOnline initContractOnlineByInforId(String inforId) {
		ContractOnline contractOnline;
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this.getRequestParameters();
		if (StringUtils.isNotBlank(inforId)){
			contractOnline = this.contractOnlineService.initContractOnlineByInforId(inforId, valueMap, user);
		} else {
			String contractId = (String) valueMap.get("contractId");
			contractOnline = this.contractOnlineService.initEditOrViewPage(contractId, user);
		}
		return contractOnline;
	}

	/**
	 * 获取币别数据
	 */
	@RequestMapping(value = "/selectCurrencyData")
	public @ResponseBody
	Map<String, String> selectCurrencyData(String contractId) {
		return this.contractOnlineService.getContractCurrencyMap(contractId);
	}
	
	/**
	 * 设置印花税
	 * @param onlineId
	 * @param taxItemId
	 * @return
	 */
	@RequestMapping(value = "/setTaxItem")
	public @ResponseBody boolean setTaxItem(String onlineId, String taxItemId) {
		return this.contractStampTaxAmountService.createContractStampTaxAmountByOnline(onlineId, taxItemId);
	}

}