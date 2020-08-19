package com.supporter.prj.ppm.contract.pact.review.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.ppm.contract.pact.review.entity.ContractPactReview;
import com.supporter.prj.ppm.contract.pact.review.entity.ContractPactReviewBfd;
import com.supporter.prj.ppm.contract.pact.review.entity.ContractPactReviewCon;
import com.supporter.prj.ppm.contract.pact.review.service.ContractPactReviewBfdService;
import com.supporter.prj.ppm.contract.pact.review.service.ContractPactReviewConService;
import com.supporter.prj.ppm.contract.pact.review.service.ContractPactReviewService;
import com.supporter.spring_mvc.AbstractController;

@Controller
@RequestMapping("ppm/contract/pact/review/")
public class ContractPactReviewController extends AbstractController {

	private static final long serialVersionUID = 1L;
	@Autowired
	private ContractPactReviewService service;
	@Autowired
	private ContractPactReviewConService contentService;
	@Autowired
	private ContractPactReviewBfdService bfdService;

	/**
	 * 获取列表
	 */
	@RequestMapping("getGrid")
	@ResponseBody
	public JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, ContractPactReview review) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		service.getGrid(jqGrid, review, user);
		return jqGrid;
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息.
	 * @param reviewId 主键
	 * @return 实体对象
	 */
	@RequestMapping("initEditOrViewPage")
	@ResponseBody
	public ContractPactReview initEditOrViewPage(String reviewId, String prjId) {
		UserProfile user = this.getUserProfile();
		ContractPactReview entity = service.initEditOrViewPage(reviewId, prjId, user);
		return entity;
	}

	/**
	 * 保存或更新
	 * @param prjActive 实体对象
	 * @return 操作结果
	 */
	@RequestMapping("saveOrUpdate")
	@ResponseBody
	public OperResult<ContractPactReview> saveOrUpdate(ContractPactReview review) {
		UserProfile user = this.getUserProfile();
		ContractPactReview entity = service.saveOrUpdate(review, user);
		return OperResult.succeed("保存成功", "保存成功", entity);
	}

	/**
	 * 删除
	 * @param reviewId 主键
	 * @return	操作结果
	 */
	@RequestMapping("batchDel")
	@ResponseBody
	public OperResult<?> batchDel(String reviewId) {
		this.service.delete(reviewId);
		return OperResult.succeed("操作成功", "操作成功", null);
	}

	/**
	 * 提交
	 * @param reviewId-主键id
	 */
	@RequestMapping("valid")
	@ResponseBody
	public OperResult<?> valid(String reviewId) {
		UserProfile user = this.getUserProfile();
		this.service.valid(reviewId, user);
		return OperResult.succeed("操作成功", "操作成功", null);
	}


	/**
	 * 保存审批结果
	 * @param reviewId 主键
	 * @param reviewConStatus 评审结论
	 * @param reviewVerContent 评审验证内容
	 */
	@RequestMapping("saveSwfResult")
	@ResponseBody
	public void saveSwfResult(String reviewId, String reviewConStatus, String reviewVerContent) {
		contentService.saveSwfResult(reviewId, reviewConStatus, reviewVerContent);
	}

	/**
	 * 获取评审结果
	 * @param reviewId 评审主键
	 * @return 评审结果对象
	 */
	@RequestMapping("getSwfResult")
	@ResponseBody
	public ContractPactReviewCon getSwfResult(String reviewId) {
		ContractPactReviewCon entity = contentService.getSwfResult(reviewId);
		return entity;
	}

	/**
	 * 获取审批结论为有条件通过的评审单号
	 * @return map(评审id,评审单号)
	 */
	@RequestMapping("getVerReview")
	@ResponseBody
	public Map<String, String> getVerReview(String prjId) {
		return service.getVerReview(prjId);
	}

	/**
	 * 根据主键获取评审对象
	 * @param reviewId 评审主键
	 * @return 实体对象
	 */
	@RequestMapping("getReview")
	@ResponseBody
	public ContractPactReview getReview(String reviewId) {
		return service.getReview(reviewId);
	}

	/**
	 * 获取所有审批完成且未创建评审的合同及协议报审
	 * @param prjId 项目id
	 * @return  Map<报审id, 报审编号+"_"+报审名称>
	 */
	@RequestMapping("getAllCompleteReport")
	@ResponseBody
	public Map<String, String> getAllCompleteReport(String prjId) {
		return service.getAllCompleteReport(prjId);
	}

	/**
	 * 获取评审关联的报审单
	 * @return  Map<报审id, 报审编号+"_"+报审名称>
	 */
	@RequestMapping("getCompleteReport")
	@ResponseBody
	public Map<String, String> getCompleteReport(String reportId) {
		return service.getCompleteReport(reportId);
	}

	/**
	 * 获取所有某项目下审批完成且未创建评审的合同及协议报审(不包括当前评审关联的报审)
	 * 主要用于评审的驳回页面
	 * @param prjId 项目id
	 * @return  Map<报审id, 报审编号+"_"+报审名称>
	 */
	@RequestMapping("getAllCompleteReportPlus")
	@ResponseBody
	public Map<String, String> getAllCompleteReportPlus(String prjId, String reportId) {
		return service.getAllCompleteReportPlus(prjId, reportId);
	}

	/**
	 * 标记需要用印资料清单下的资料清单文件
	 * @param chkValueStr-用印文件主键字符串
	 * @param reviewId-主表主键
	 */
	@RequestMapping("markUsePrintFile")
	@ResponseBody
	public void markUsePrintFile(String chkValueStr, String reviewId) {
		service.markUsePrintFile(chkValueStr, reviewId);
	}

	/**
	 * 启动用印页面初始化资料清单复选框
	 * @param reviewId-主表主键
	 * @return 资料清单
	 */
	@RequestMapping("initPrintFile")
	@ResponseBody
	public List<ContractPactReviewBfd> initPrintFile(String reviewId) {
		return bfdService.getListByPactReviewId(reviewId);
	}

	/**
	 * 获取待用印资料清单文件信息
	 * @param reviewId-主表主键
	 * @return 资料清单文件信息
	 */
	@RequestMapping("getUseSealFile")
	@ResponseBody
	public List<Map<String, String>> getUseSealGrid(String businessUUID) {
		return service.getUseSealFile(businessUUID);
	}

	/**
	 * 带入关联报审的资料清单文件
	 * @param reviewId-评审id
	 * @param bfdsStr-资料清单属性拼接而成的字符串
	 * @param reportId-报审id
	 */
	@RequestMapping("initBfdFile")
	@ResponseBody
	public void initBfdFile(String reviewId, String bfdsStr, String reportId) {
		UserProfile user = this.getUserProfile();
		service.initBfdFile(reviewId, bfdsStr, reportId, user);
	}

}
