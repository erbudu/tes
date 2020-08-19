package com.supporter.prj.ppm.contract.sign.review.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.authority.annotation.AuthCheck;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.exception.ExceptionCode;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.ppm.contract.sign.review.entity.ContractSignReviewCon;
import com.supporter.prj.ppm.contract.sign.review.entity.PpmContractSignReview;
import com.supporter.prj.ppm.contract.sign.review.service.ContractSignReviewConService;
import com.supporter.prj.ppm.contract.sign.review.service.PpmContractSignReviewService;
import com.supporter.prj.ppm.ecc.OperResultConstant;
import com.supporter.spring_mvc.AbstractController;

import java.util.List;
import java.util.Map;

/**
 * @Title: Controller
 * @Description: 签约评审表.
 * @author YAN
 * @date 2019-09-06 18:35:28
 * @version V1.0
 */
@Controller("PpmContractSignReviewController")
@RequestMapping("ppm/contract/sign/review/contractSignReview/")
public class ContractSignReviewController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private PpmContractSignReviewService service;
	@Autowired
	private ContractSignReviewConService contentService;

	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param contractSignReviewId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody PpmContractSignReview initEditOrViewPage(String contractSignReviewId) {
		PpmContractSignReview entity = service.initEditOrViewPage(contractSignReviewId);
		return entity;
	}

	/**
	 * 根据项目id初始化项目
	 * @param prjId
	 * @return
	 */
	@RequestMapping("initPageByPrjId")
	public @ResponseBody PpmContractSignReview initPageByPrjId(String prjId,String contractSignReviewId) {
		PpmContractSignReview entity = service.initPageByPrjId(prjId,contractSignReviewId);
		return entity;
	}
	/**
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, PpmContractSignReview contractSignReview) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.service.getGrid(user, jqGrid, contractSignReview);
		return jqGrid;
	}

	/**
	 * 获取需要评审验证的评审列表
	 * 
	 * @param jqGrid
	 */
	@RequestMapping("getVerGrid")
	@ResponseBody
	public JqGrid getVerGrid(HttpServletRequest request, JqGridReq jqGridReq, PpmContractSignReview contractSignReview)
			throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.service.getVerGrid(user, jqGrid, contractSignReview);
		return jqGrid;
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param contractSignReview 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > saveOrUpdate(PpmContractSignReview contractSignReview) {
		UserProfile user = this.getUserProfile();
		PpmContractSignReview entity = this.service.saveOrUpdate(user, contractSignReview);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, entity);
	}

	@RequestMapping("commit")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > commit(PpmContractSignReview contractSignReview) {
		UserProfile user = this.getUserProfile();
		PpmContractSignReview entity = this.service.commit(user, contractSignReview);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, entity);
	}
	/**
	 * 删除操作
	 * 
	 * @param contractSignReviewIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > batchDel(String contractSignReviewIds) {
		UserProfile user = this.getUserProfile();
		this.service.delete(user, contractSignReviewIds);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, null);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param contractSignReviewId
	 * @param contractSignReviewName
	 * @return
	 */
	@RequestMapping("nameIsValid")
	public @ResponseBody Boolean nameIsValid(String contractSignReviewId,String contractSignReviewName) {
		return this.service.nameIsValid(contractSignReviewId, contractSignReviewName);
	}
	
	/**
	 * 保存审批结果
	 * @param contractSignReviewId-
	 * @param rvConStatus
	 * @param reviewVerContent
	 */
	@RequestMapping("saveSwfResult")
	@ResponseBody
	public void saveSwfResult(ContractSignReviewCon content) {
		UserProfile user = this.getUserProfile();
		contentService.saveOrUpdate(user, content);
	}

	/**
	 * 获取评审结果
	 * @param reviewId 评审主键
	 * @return 评审结果对象
	 */
	@RequestMapping("getSwfResult")
	@ResponseBody
	public ContractSignReviewCon getSwfResult(String contractSignReviewId) {
		ContractSignReviewCon entity = contentService.getByReviewId(contractSignReviewId);
		return entity;
	}


	/**
	 * 用印文件列表
	 * @param businessUUID
	 * @return
	 */
	@RequestMapping("getUseSealGrid")
	public @ResponseBody
	List<Map<String,String>> getUseSealGrid(String businessUUID){
		List<Map<String,String>> useSealFileGrid = service.getUseSealGrid(businessUUID);
		return useSealFileGrid;
	}
	/**
	 * 标记用印文件
	 * @param chkValueStr
	 * @param bidIngUpId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("markUsePrintFile")
	public @ResponseBody OperResult markUsePrintFile(String chkValueStr,String contractSigneReportId) {
		if(chkValueStr == null || chkValueStr == "") return OperResult.succeed(null, null, null);
		service.markUsePrintFile(chkValueStr,contractSigneReportId);
		return OperResult.succeed("success", null, null);
	}

}
