package com.supporter.prj.ppm.contract.effect.review.controller;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.supporter.prj.ppm.ecc.OperResultConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.spring_mvc.AbstractController;
import com.supporter.prj.ppm.contract.effect.review.service.ContractEffectReviewConService;
import com.supporter.prj.ppm.contract.effect.review.entity.ContractEffectReviewCon;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.eip_service.authority.annotation.AuthCheck;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.exception.ExceptionCode;

/**
 * @Title: Controller
 * @Description: 评审结果.
 * @author YAN
 * @date 2019-09-06 18:35:32
 * @version V1.0
 */
@Controller
@RequestMapping("ppm/contract/effect/review/contractEffectReviewCon")
public class ContractEffectReviewConController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ContractEffectReviewConService contractEffectReviewConService;

	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param contractEffectRevConId 主键
	 * @return
	 */

	@RequestMapping("initEditOrViewPage")
	public @ResponseBody ContractEffectReviewCon initEditOrViewPage(String contractEffectRevConId) {
		ContractEffectReviewCon entity = contractEffectReviewConService.initEditOrViewPage(contractEffectRevConId);
		return entity;
	}
	@RequestMapping("initPageByReviewId")
	public @ResponseBody ContractEffectReviewCon initPageByReviewId(String contractEffectReviewId) {
		ContractEffectReviewCon entity = contractEffectReviewConService.initPageByReviewId(contractEffectReviewId);
		return entity;
	}
	/**
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, ContractEffectReviewCon contractEffectReviewCon) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.contractEffectReviewConService.getGrid(user, jqGrid, contractEffectReviewCon);
		return jqGrid;
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param contractEffectReviewCon 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > saveOrUpdate(ContractEffectReviewCon contractEffectReviewCon) {
		UserProfile user = this.getUserProfile();
		ContractEffectReviewCon entity = this.contractEffectReviewConService.saveOrUpdate(user, contractEffectReviewCon);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, null);
	}

	/**
	 * 删除操作
	 * 
	 * @param contractEffectRevConIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > batchDel(String contractEffectRevConIds) {
		UserProfile user = this.getUserProfile();
		this.contractEffectReviewConService.delete(user, contractEffectRevConIds);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, null);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param contractEffectRevConId
	 * @param contractEffectRevConName
	 * @return
	 */
	@RequestMapping("nameIsValid")
	public @ResponseBody Boolean nameIsValid(String contractEffectRevConId,String contractEffectRevConName) {
		return this.contractEffectReviewConService.nameIsValid(contractEffectRevConId, contractEffectRevConName);
	}

}
