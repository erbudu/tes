package com.supporter.prj.cneec.tpc.contract_sign_review.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.cneec.tpc.contract_sign_review.entity.ContractSignInfor;
import com.supporter.prj.cneec.tpc.contract_sign_review.entity.ContractSignReview;
import com.supporter.prj.cneec.tpc.contract_sign_review.service.ContractSignReviewService;
import com.supporter.prj.cneec.tpc.contract_sign_review_dept.entity.ContractSignDeptInfor;
import com.supporter.prj.cneec.tpc.util.OperResultConstant;
import com.supporter.prj.cneec.tpc.util.TpcConstant;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.spring_mvc.AbstractController;
import com.supporter.util.HttpUtil;

/**
 * @Title: ContractSignReviewController
 * @Description: 控制器类
 * @author: yanweichao
 * @date: 2017-09-28
 * @version: V1.0
 */
@Controller
@RequestMapping("tpc/contractSignReview")
public class ContractSignReviewController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ContractSignReviewService contractSignReviewService;

	/**
	 * 返回列表. 分页表格展示数据.
	 * @param request
	 * @param jqGridReq
	 * @param contractSignReview
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody
	JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, ContractSignReview contractSignReview) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.contractSignReviewService.getGrid(user, jqGrid, contractSignReview);
		return jqGrid;
	}

	/**
	 * 返回销售合同/采购合同列表
	 * @param request
	 * @param jqGridReq
	 * @param contractSignInfor
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getInforGrid")
	public @ResponseBody
	JqGrid getInforGrid(HttpServletRequest request, JqGridReq jqGridReq, ContractSignInfor contractSignInfor) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.contractSignReviewService.getGrid(user, jqGrid, contractSignInfor);
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
		this.contractSignReviewService.getInforAmountGrid(user, jqGrid, this.getRequestParameters());
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
		this.contractSignReviewService.getInforItemGrid(user, jqGrid, this.getRequestParameters());
		return jqGrid;
	}

	@RequestMapping("getContractInforMap")
	public @ResponseBody
	Map<String, Object> getContractInforMap(String signId) {
		return this.contractSignReviewService.getContractInforMap(signId);
	}

	/**
	 * 初始化加载对象，避免前台编辑页面没有的字段保存后丢失
	 * @param signId
	 * @param map
	 */
	@ModelAttribute
	public void getContractSignReview(String signId, Map<String, Object> map) {
		if (!StringUtils.isBlank(signId)) {
			ContractSignReview contractSignReview = this.contractSignReviewService.get(signId);
			if (contractSignReview != null) {
				map.put("contractSignReview", contractSignReview);
			}
		}
	}

	/**
	 * 根据ID获取功能模块
	 * @param signId
	 * @return
	 */
	@RequestMapping("get")
	public @ResponseBody
	ContractSignReview get(String signId) {
		ContractSignReview contractSignReview = this.contractSignReviewService.get(signId);
		return contractSignReview;
	}

	/**
	 * 编辑或查看页面加载对象
	 * @param signId
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody
	ContractSignReview initEditOrViewPage(String signId) {
		UserProfile user = this.getUserProfile();
		ContractSignReview contractSignReview = this.contractSignReviewService.initEditOrViewPage(signId, user);
		// 编辑权限验证
		boolean editOper = Boolean.valueOf(this.getRequestPara("editOper"));
		if (editOper) {
			this.contractSignReviewService.getAuthCanExecute(user, contractSignReview);
		}
		return contractSignReview;
	}

	/**
	 * 编辑或查看页面加载对象
	 * @param signId
	 * @return
	 */
	@RequestMapping("initEditOrViewByDeptReview")
	public @ResponseBody
	ContractSignReview initEditOrViewByDeptReview(String deptSignId) {
		UserProfile user = this.getUserProfile();
		ContractSignReview contractSignReview = this.contractSignReviewService.initEditOrViewByDeptReview(deptSignId, user);
		return contractSignReview;
	}

	/**
	 * 编辑或查看页面加载对象
	 * @param inforId
	 * @return
	 */
	@RequestMapping("initEditOrViewInforPage")
	public @ResponseBody
	ContractSignInfor initEditOrViewInforPage(String inforId) {
		ContractSignInfor contractSignInfor = this.contractSignReviewService.initEditOrViewInforPage(inforId, this.getUserProfile());
		return contractSignInfor;
	}

	/**
	 * 保存或更新数据.
	 * @param contractSignReview 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody
	OperResult<ContractSignReview> saveOrUpdate(ContractSignReview contractSignReview) {
		UserProfile user = this.getUserProfile();
		// 权限验证
		//this.contractSignReviewService.getAuthCanExecute(user, contractSignReview);
		Map<String, Object> valueMap = this.getPropValues(ContractSignReview.class);
		ContractSignReview entity = this.contractSignReviewService.saveOrUpdate(user, contractSignReview, valueMap);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SAVE_SUCCESS, "保存成功", entity);
	}

	/**
	 * 保存或更新数据从表.
	 * @param contractSignInfor 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdateInfor")
	public @ResponseBody
	OperResult<ContractSignInfor> saveOrUpdateInfor(ContractSignInfor contractSignInfor) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this.getRequestParameters();
		ContractSignInfor entity = this.contractSignReviewService.saveOrUpdate(user, contractSignInfor, valueMap);
		return OperResult.succeed("infor-saveSuccess", "保存成功", entity);
	}

	/**
	 * 发送会议知会
	 * @param response
	 */
	@RequestMapping("/sendMeetNotifyMsg")
	public void sendMeetNotifyMsg(ContractSignReview contractSignReview, HttpServletResponse response) {
		this.contractSignReviewService.sendMeetNotifyMsg(contractSignReview);
		String json = "{\"success\": true,\"msg\": \"\"}";
		HttpUtil.write(response, json);
	}

	/**
	 * 验证销售合同效益测算表是否审批完成
	 * @param response
	 */
	@RequestMapping("/commitByMeetReview")
	public void commitByMeetReview(ContractSignReview contractSignReview, HttpServletResponse response) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this.getRequestParameters();
		String json = this.contractSignReviewService.commitByMeetReview(user, contractSignReview, valueMap);
		HttpUtil.write(response, json);
	}

	/**
	 * 提交数据
	 * @param contractSignReview
	 * @return
	 */
	@RequestMapping("commit")
	public @ResponseBody
	OperResult<ContractSignReview> commit(ContractSignReview contractSignReview) {
		UserProfile user = this.getUserProfile();
		// 权限验证
		//this.contractSignReviewService.getAuthCanExecute(user, contractSignReview);
		Map<String, Object> valueMap = this.getPropValues(ContractSignReview.class);
		ContractSignReview entity = this.contractSignReviewService.commit(user, contractSignReview, valueMap);
		return OperResult.succeed(OperResultConstant.CODE_OPER_COMMIT_SUCCESS, "提交成功", entity);
	}

	/**
	 * 经办人确认审批完成
	 * @param signId
	 * @param response
	 */
	@RequestMapping("/completeConfirm")
	public void completeConfirm(String signId, HttpServletResponse response) {
		UserProfile user = this.getUserProfile();
		String json = this.contractSignReviewService.completeConfirm(user, signId);
		HttpUtil.write(response, json);
	}

	/**
	 * 删除操作
	 * 
	 * @param signIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	public @ResponseBody
	OperResult<?> batchDel(String signIds) {
		UserProfile user = this.getUserProfile();
		this.contractSignReviewService.delete(user, signIds);
		return OperResult.succeed(OperResultConstant.CODE_OPER_DELETE_SUCCESS);
	}

	/**
	 * 删除从表操作
	 * 
	 * @param inforIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("deleteInfor")
	public @ResponseBody
	OperResult<?> deleteInfor(String inforIds) {
		UserProfile user = this.getUserProfile();
		this.contractSignReviewService.deleteContractSignInfor(user, inforIds);
		return OperResult.succeed(OperResultConstant.CODE_OPER_DELETE_SUCCESS);
	}

	/**
	 * 获取状态字典数据
	 */
	@RequestMapping(value = "/selectSwfStatusData")
	public @ResponseBody
	Map<Integer, String> selectSwfStatusData(Boolean isMeetReview) {
		return ContractSignReview.getSwfStatusMap(isMeetReview);
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

}