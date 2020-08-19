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
import com.supporter.prj.ppm.contract.sign.review.service.ContractSignReviewConService;
import com.supporter.prj.ppm.ecc.OperResultConstant;
import com.supporter.spring_mvc.AbstractController;

/**
 * @Title: Controller
 * @Description: 评审结果.
 * @author YAN
 * @date 2019-09-06 18:35:32
 * @version V1.0
 */
@Controller
@RequestMapping("ppm/contract/sign/review/contractSignReviewCon")
public class ContractSignReviewConController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ContractSignReviewConService contractSignReviewConService;

	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param contractSignRevConId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody ContractSignReviewCon initEditOrViewPage(String contractSignRevConId) {
		ContractSignReviewCon entity = contractSignReviewConService.initEditOrViewPage(contractSignRevConId);
		return entity;
	}

	/**
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, ContractSignReviewCon contractSignReviewCon) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.contractSignReviewConService.getGrid(user, jqGrid, contractSignReviewCon);
		return jqGrid;
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param contractSignReviewCon 页面传递参数自动绑定成的实体类
	 * @return
	 */
	// @RequestMapping("saveOrUpdate")
	// @AuthCheck(module = "", oper = "", failCode =
	// ExceptionCode.Auth.NOT_ACCESSIBLE)
	// public @ResponseBody OperResult< ? > saveOrUpdate(ContractSignReviewCon
	// contractSignReviewCon) {
	// UserProfile user = this.getUserProfile();
	// ContractSignReviewCon entity =
	// this.contractSignReviewConService.saveOrUpdate(user, contractSignReviewCon);
	// return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, null);
	// }

	/**
	 * 删除操作
	 * 
	 * @param contractSignRevConIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > batchDel(String contractSignRevConIds) {
		UserProfile user = this.getUserProfile();
		this.contractSignReviewConService.delete(user, contractSignRevConIds);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, null);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param contractSignRevConId
	 * @param contractSignRevConName
	 * @return
	 */
	@RequestMapping("nameIsValid")
	public @ResponseBody Boolean nameIsValid(String contractSignRevConId,String contractSignRevConName) {
		return this.contractSignReviewConService.nameIsValid(contractSignRevConId, contractSignRevConName);
	}

}
