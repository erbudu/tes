package com.supporter.prj.ppm.contract.pact.review_ver.controller;

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
import com.supporter.prj.ppm.contract.pact.review_ver.constant.OperResultConstant;
import com.supporter.prj.ppm.contract.pact.review_ver.entity.ContractPactRevVer;
import com.supporter.prj.ppm.contract.pact.review_ver.entity.ContractPactRevVerBf;
import com.supporter.prj.ppm.contract.pact.review_ver.entity.ContractPactRevVerCon;
import com.supporter.prj.ppm.contract.pact.review_ver.service.ContractPactRevVerBfService;
import com.supporter.prj.ppm.contract.pact.review_ver.service.ContractPactRevVerConService;
import com.supporter.prj.ppm.contract.pact.review_ver.service.ContractPactRevVerService;
import com.supporter.spring_mvc.AbstractController;

@Controller
@RequestMapping("ppm/contract/pact/review_ver/")
public class ContractPactRevVerController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ContractPactRevVerService service;
	@Autowired
	private ContractPactRevVerConService contentService;
	@Autowired
	private ContractPactRevVerBfService bfdService;

	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * @param revVerId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	@ResponseBody
	public ContractPactRevVer initEditOrViewPage(String revVerId, String prjId) {
		UserProfile user = this.getUserProfile();
		ContractPactRevVer entity = service.initEditOrViewPage(revVerId, prjId, user);
		return entity;
	}

	/**
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	@ResponseBody
	public JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, ContractPactRevVer entity) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.service.getGrid(user, jqGrid, entity);
		return jqGrid;
	}

	/**
	 * 保存或更新数据.
	 * @param contractPactRevVer 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	@ResponseBody
	public OperResult<?> saveOrUpdate(ContractPactRevVer contractPactRevVer) {
		UserProfile user = this.getUserProfile();
		ContractPactRevVer entity = this.service.saveOrUpdate(user, contractPactRevVer);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, entity);
	}

	/**
	 * 删除操作
	 * @param revVerIds 主键集合，多个以逗号分隔
	 * @return 操作结果
	 */
	@RequestMapping("batchDel")
	@ResponseBody
	public OperResult<?> batchDel(String revVerId) {
		this.service.delete(revVerId);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, null);
	}

	/**
	 * 提交
	 * @param revVerId 评审验证主键
	 * @return  操作结果
	 */
	@RequestMapping("valid")
	@ResponseBody
	public OperResult<?> valid(String revVerId) {
		UserProfile user = this.getUserProfile();
		this.service.valid(revVerId, user);
		return OperResult.succeed("操作成功", "操作成功", null);
	}

	/**
	 * 保存评审验证审批结果
	 * @param revVerId 评审验证主键
	 * @param reviewConStatus 评审评审验证结论
	 */
	@RequestMapping("saveSwfResult")
	@ResponseBody
	public void saveSwfResult(String revVerId, String revConStatus) {
		contentService.saveSwfResult(revVerId, revConStatus);
	}

	/**
	 * 获取评审验证结果
	 * @param reviewId 评审验证主键
	 * @return 评审验证结果对象
	 */
	@RequestMapping("getSwfResult")
	@ResponseBody
	public ContractPactRevVerCon getSwfResult(String revVerId) {
		ContractPactRevVerCon entity = contentService.getSwfResult(revVerId);
		return entity;
	}

	/**
	 * 初始化资料清单文件(将评审资料清单文件复制给评审验证)
	 */
	@RequestMapping("initBfdFile")
	@ResponseBody
	public void initBfdFile(String reviewId, String bfdsStr, String revVerId) {
		UserProfile user = this.getUserProfile();
		service.initBfdFile(reviewId, bfdsStr, revVerId, user);
	}

	/**
	 * 获取需评审验证但未评审验证的评审单号
	 * @param prjId 项目id
	 * @return map(评审id,评审单号)
	 */
	@RequestMapping("getVerReview")
	@ResponseBody
	public Map<String, String> getVerReview(String prjId) {
		return service.getVerReview(prjId);
	}

	/**
	 * 评审验证编辑操作下,获取该条评审验证当前关联的评审单
	 * @return map(评审id,评审单号)
	 */
	@RequestMapping("getVerReviewPlus")
	@ResponseBody
	public Map<String, String> getVerReviewPlus(String reviewId) {
		return service.getVerReviewPlus(reviewId);
	}

	/**
	 * 标记需要用印资料清单下的资料清单文件
	 * @param chkValueStr-用印文件主键字符串
	 * @param revVerId-主表主键
	 */
	@RequestMapping("markUsePrintFile")
	@ResponseBody
	public void markUsePrintFile(String chkValueStr, String revVerId) {
		service.markUsePrintFile(chkValueStr, revVerId);
	}

	/**
	 * 启动用印页面初始化资料清单复选框
	 * @param revVerId-主表主键
	 * @return 资料清单
	 */
	@RequestMapping("initPrintFile")
	@ResponseBody
	public List<ContractPactRevVerBf> initPrintFile(String revVerId) {
		return bfdService.getListByRevVerId(revVerId);
	}

	/**
	 * 获取待用印资料清单文件信息
	 * @param revVerId-主表主键
	 * @return 资料清单文件信息
	 */
	@RequestMapping("getUseSealFile")
	@ResponseBody
	public List<Map<String, String>> getUseSealGrid(String businessUUID) {
		return service.getUseSealFile(businessUUID);
	}

	// /**
	// * 通过报审id获取实体对象
	// * @param reportId 协议报审主键
	// * @return 评审验证对象
	// */
	// @RequestMapping("/getRevVerByReportId")
	// @ResponseBody
	// public ContractPactRevVer getRevVerByReportId(String reportId) {
	// ContractPactRevVer entity = service.getRevVerByReportId(reportId);
	// return entity;
	// }

	// /**
	// * 判断名字是否重复
	// * @param revVerId
	// * @param revVerName
	// * @return
	// */
	// @RequestMapping("nameIsValid")
	// public @ResponseBody Boolean nameIsValid(String revVerId,String revVerName) {
	// return this.service.nameIsValid(revVerId, revVerName);
	// }

}
