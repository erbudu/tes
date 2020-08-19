package com.supporter.prj.cneec.tpc.contract_sign_review_dept.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.cneec.tpc.contract_sign_review_dept.entity.ContractSignDeptReview;
import com.supporter.prj.cneec.tpc.contract_sign_review_dept.service.ContractSignDeptReviewService;
import com.supporter.prj.cneec.tpc.contract_sign_review_dept.util.ContractSignReviewUtil;
import com.supporter.prj.cneec.tpc.util.OperResultConstant;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.spring_mvc.AbstractController;
import com.supporter.util.HttpUtil;

/**
 * @Title: ContractSignDeptReviewController
 * @Description: 控制器类
 * @author: yanweichao
 * @date: 2018-03-21
 * @version: V1.0
 */
@Controller
@RequestMapping("tpc/contractSignDeptReview")
public class ContractSignDeptReviewController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ContractSignDeptReviewService contractSignDeptReviewService;

	/**
	 * 返回列表. 分页表格展示数据.
	 * @param request
	 * @param jqGridReq
	 * @param contractSignDeptReview
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody
	JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, ContractSignDeptReview contractSignDeptReview) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.contractSignDeptReviewService.getGrid(user, jqGrid, contractSignDeptReview);
		return jqGrid;
	}

	/**
	 * 初始化加载对象，避免前台编辑页面没有的字段保存后丢失
	 * @param signId
	 * @param map
	 */
	@ModelAttribute
	public void getContractSignDeptReview(String signId, Map<String, Object> map) {
		if (!StringUtils.isBlank(signId)) {
			ContractSignDeptReview contractSignDeptReview = this.contractSignDeptReviewService.get(signId);
			if (contractSignDeptReview != null) {
				map.put("contractSignDeptReview", contractSignDeptReview);
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
	ContractSignDeptReview get(String signId) {
		ContractSignDeptReview contractSignDeptReview = this.contractSignDeptReviewService.get(signId);
		return contractSignDeptReview;
	}

	/**
	 * 初始化对象
	 * @return
	 */
	@RequestMapping("initNew")
	public @ResponseBody
	ContractSignDeptReview initNew(ContractSignDeptReview contractSignDeptReview) {
		UserProfile user = this.getUserProfile();
		ContractSignDeptReview entity = this.contractSignDeptReviewService.initNew(contractSignDeptReview, this.getRequestParameters(), user);
		return entity;
	}

	/**
	 * 初始化页面进入编辑页面更新数据.
	 * @param contractSignDeptReview 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdateByNew")
	public void saveOrUpdateByNew(ContractSignDeptReview contractSignDeptReview, HttpServletResponse response) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this.getRequestParameters();
		String json = this.contractSignDeptReviewService.saveOrUpdateByNew(user, contractSignDeptReview, valueMap);
		HttpUtil.write(response, json);
	}

	/**
	 * 编辑或查看页面加载对象
	 * @param signId
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody
	ContractSignDeptReview initEditOrViewPage(String signId) {
		UserProfile user = this.getUserProfile();
		ContractSignDeptReview contractSignDeptReview = this.contractSignDeptReviewService.initEditOrViewPage(signId, user);
		// 编辑权限验证
		boolean editOper = Boolean.valueOf(this.getRequestPara("editOper"));
		if (editOper) {
			this.contractSignDeptReviewService.getAuthCanExecute(user, contractSignDeptReview);
		}
		return contractSignDeptReview;
	}

	/**
	 * 保存或更新数据.
	 * @param contractSignDeptReview 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody
	OperResult<ContractSignDeptReview> saveOrUpdate(ContractSignDeptReview contractSignDeptReview) {
		UserProfile user = this.getUserProfile();
		// 权限验证
		//this.contractSignDeptReviewService.getAuthCanExecute(user, contractSignDeptReview);
		Map<String, Object> valueMap = this.getPropValues(ContractSignDeptReview.class);
		ContractSignDeptReview entity = this.contractSignDeptReviewService.saveOrUpdate(user, contractSignDeptReview, valueMap);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SAVE_SUCCESS, "保存成功", entity);
	}

	/**
	 * 发送会议知会
	 * @param response
	 */
	@RequestMapping("/sendMeetNotifyMsg")
	public void sendMeetNotifyMsg(ContractSignDeptReview contractSignDeptReview, HttpServletResponse response) {
		this.contractSignDeptReviewService.sendMeetNotifyMsg(contractSignDeptReview);
		String json = "{\"success\": true,\"msg\": \"\"}";
		HttpUtil.write(response, json);
	}

	/**
	 * 验证销售合同效益测算表是否审批完成
	 * @param response
	 */
	@RequestMapping("/commitByMeetReview")
	public void commitByMeetReview(ContractSignDeptReview contractSignDeptReview, HttpServletResponse response) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this.getRequestParameters();
		String json = this.contractSignDeptReviewService.commitByMeetReview(user, contractSignDeptReview, valueMap);
		HttpUtil.write(response, json);
	}

	/**
	 * 提交数据
	 * @param contractSignDeptReview
	 * @return
	 */
	@RequestMapping("commit")
	public @ResponseBody
	OperResult<ContractSignDeptReview> commit(ContractSignDeptReview contractSignDeptReview) {
		UserProfile user = this.getUserProfile();
		// 权限验证
		//this.contractSignDeptReviewService.getAuthCanExecute(user, contractSignDeptReview);
		Map<String, Object> valueMap = this.getPropValues(ContractSignDeptReview.class);
		ContractSignDeptReview entity = this.contractSignDeptReviewService.commit(user, contractSignDeptReview, valueMap);
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
		String json = this.contractSignDeptReviewService.completeConfirm(user, signId);
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
		this.contractSignDeptReviewService.delete(user, signIds);
		return OperResult.succeed(OperResultConstant.CODE_OPER_DELETE_SUCCESS);
	}

	/**
	 * 获取状态字典数据
	 */
	@RequestMapping(value = "/selectSwfStatusData")
	public @ResponseBody
	Map<Integer, String> selectSwfStatusData(Boolean isMeetReview) {
		return ContractSignDeptReview.getSwfStatusMap(isMeetReview);
	}

	/**
	 * 获取评审类型
	 */
	@RequestMapping(value = "/selectReviewTypeData")
	public @ResponseBody
	Map<Integer, String> selectReviewTypeData() {
		return ContractSignReviewUtil.getReviewTypeMap();
	}

}