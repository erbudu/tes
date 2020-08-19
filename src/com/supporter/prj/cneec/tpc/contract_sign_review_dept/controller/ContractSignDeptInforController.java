package com.supporter.prj.cneec.tpc.contract_sign_review_dept.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.cneec.tpc.contract_sign_review_dept.entity.ContractSignDeptInfor;
import com.supporter.prj.cneec.tpc.contract_sign_review_dept.service.ContractSignDeptReviewService;
import com.supporter.prj.cneec.tpc.util.OperResultConstant;
import com.supporter.prj.cneec.tpc.util.TpcConstant;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.spring_mvc.AbstractController;

/**
 * @Title: ContractSignDeptInforController
 * @Description: 控制器类
 * @author: yanweichao
 * @date: 2018-03-21
 * @version: V1.0
 */
@Controller
@RequestMapping("tpc/contractSignDeptInfor")
public class ContractSignDeptInforController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ContractSignDeptReviewService contractSignDeptReviewService;

	/**
	 * 返回销售合同/采购合同列表
	 * @param request
	 * @param jqGridReq
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getInforGrid")
	public @ResponseBody
	JqGrid getInforGrid(HttpServletRequest request, JqGridReq jqGridReq) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.contractSignDeptReviewService.getInforGrid(user, jqGrid, this.getRequestParameters());
		return jqGrid;
	}

	/**
	 * 返回销售合同/采购合同预算金额
	 * @param request
	 * @param jqGridReq
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getInforAmountGrid")
	public @ResponseBody
	JqGrid getInforAmountGrid(HttpServletRequest request, JqGridReq jqGridReq) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.contractSignDeptReviewService.getInforAmountGrid(user, jqGrid, this.getRequestParameters());
		return jqGrid;
	}

	/**
	 * 返回销售合同/采购合同预算金额
	 * @param request
	 * @param jqGridReq
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getInforItemGrid")
	public @ResponseBody
	JqGrid getInforItemGrid(HttpServletRequest request, JqGridReq jqGridReq, ContractSignDeptInfor contractSignDeptInfor) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.contractSignDeptReviewService.getInforItemGrid(user, jqGrid, this.getRequestParameters());
		return jqGrid;
	}

	/**
	 * 获取评审单所有合同MAP记录
	 * @param signId
	 * @return
	 */
	@RequestMapping("getContractInforMap")
	public @ResponseBody
	Map<String, Object> getContractInforMap(String signId) {
		return this.contractSignDeptReviewService.getContractInforMap(signId);
	}

	/**
	 * 初始化加载对象，避免前台编辑页面没有的字段保存后丢失
	 * @param signId
	 * @param map
	 */
	@ModelAttribute
	public void getContractSignDeptInfor(String inforId, Map<String, Object> map) {
		if (!StringUtils.isBlank(inforId)) {
			ContractSignDeptInfor contractSignDeptInfor = this.contractSignDeptReviewService.getContractSignDeptInfor(inforId);
			if (contractSignDeptInfor != null) {
				map.put("contractSignDeptInfor", contractSignDeptInfor);
			}
		}
	}

	/**
	 * 编辑或查看页面加载对象
	 * @param inforId
	 * @return
	 */
	@RequestMapping("initEditOrViewInforPage")
	public @ResponseBody
	ContractSignDeptInfor initEditOrViewInforPage(String inforId) {
		Map<String, Object> valueMap = this.getRequestParameters();
		ContractSignDeptInfor contractSignDeptInfor = this.contractSignDeptReviewService.initEditOrViewInforPage(inforId, valueMap);
		return contractSignDeptInfor;
	}

	/**
	 * 保存或更新数据.
	 * @param contractSignDeptInfor 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdateInfor")
	public @ResponseBody
	OperResult<ContractSignDeptInfor> saveOrUpdateInfor(ContractSignDeptInfor contractSignDeptInfor) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this.getRequestParameters();
		ContractSignDeptInfor entity = this.contractSignDeptReviewService.saveOrUpdate(user, contractSignDeptInfor, valueMap);
		return OperResult.succeed("infor-saveSuccess", "保存成功", entity);
	}

	/**
	 * 获取合同金额汇率等
	 * @return
	 */
	@RequestMapping(value = "/getContractAmountRateMap")
	public @ResponseBody
	Map<String, Object> getContractAmountRateMap(String inforId) {
		Map<String, Object> valueMap = this.getRequestParameters();
		return this.contractSignDeptReviewService.getContractAmountRateMap(inforId, valueMap);
	}

	/**
	 * 保存或更新数据.
	 * @param contractSignDeptInfor 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdateContractItem")
	public @ResponseBody
	OperResult<?> saveOrUpdateContractItem() {
		Map<String, Object> valueMap = this.getRequestParameters();
		this.contractSignDeptReviewService.saveOrUpdateItem(valueMap);
		return OperResult.succeed("inforItem-saveSuccess", "保存成功", null);
	}

	/**
	 * 生成销售合同
	 * @param inforId
	 * @param response
	 */
	@RequestMapping("createSaleContract")
	public @ResponseBody
	OperResult<?> createSaleContract(String signId, Integer initialReviewType, String sheetIds) {
		boolean flag = this.contractSignDeptReviewService.createSaleContract(signId, initialReviewType, sheetIds);
		if (flag) {
			return OperResult.succeed("createSaleContractSuccess", "生成销售合同信息成功", null);
		} else {
			return OperResult.fail("createSaleContractFail", "生成销售合同信息失败");
		}
	}

	/**
	 * 删除合同操作
	 * 
	 * @param inforIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("deleteInfor")
	public @ResponseBody
	OperResult<?> deleteInfor(String inforIds) {
		UserProfile user = this.getUserProfile();
		this.contractSignDeptReviewService.deleteContractSignDeptInfor(user, inforIds);
		return OperResult.succeed(OperResultConstant.CODE_OPER_DELETE_SUCCESS);
	}

	/**
	 * 删除合同货物服务操作
	 * 
	 * @param inforIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("deleteContractItem")
	public @ResponseBody
	OperResult<?> deleteContractItem(String itemIds, int deleteType) {
		UserProfile user = this.getUserProfile();
		this.contractSignDeptReviewService.deleteContractSignDeptItem(user, itemIds, deleteType);
		return OperResult.succeed(OperResultConstant.CODE_OPER_DELETE_SUCCESS);
	}

	/**
	 * 评审结论
	 * @return
	 */
	@RequestMapping(value = "/selectReviewConclusionData")
	public @ResponseBody
	Map<String, String> selectReviewConclusionData() {
		return TpcConstant.getReviewConclusionMap();
	}

	/**
	 * 获取可选用币别
	 * @return
	 */
	@RequestMapping(value = "/selectCanUseCurrencyData")
	public @ResponseBody
	Map<String, String> selectCanUseCurrencyData() {
		return this.contractSignDeptReviewService.getCanUseCurrencyMap(this.getRequestParameters());
	}

	/**
	 * 校验合同名称
	 */
	@RequestMapping("checkNameIsValid")
	public @ResponseBody
	Boolean checkNameIsValid(String inforId, String contractName) {
		return this.contractSignDeptReviewService.checkContractNameIsValid(inforId, contractName);
	}
	
	/**
	 * 获取采购类型
	 * @return
	 */
	@RequestMapping(value = "/getPurchaseTypeMap")
	public @ResponseBody Map<String, String> getPurchaseTypeMap(){
		return TpcConstant.getPurchaseTypeMap();
	}

}