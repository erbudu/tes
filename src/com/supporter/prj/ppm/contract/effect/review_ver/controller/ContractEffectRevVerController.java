package com.supporter.prj.ppm.contract.effect.review_ver.controller;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.supporter.prj.ppm.ecc.OperResultConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.spring_mvc.AbstractController;
import com.supporter.prj.ppm.contract.effect.review_ver.service.ContractEffectRevVerService;
import com.supporter.prj.ppm.contract.effect.review_ver.entity.ContractEffectRevVer;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.eip_service.authority.annotation.AuthCheck;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.exception.ExceptionCode;

/**
 * @Title: Controller
 * @Description: 评审验证表.
 * @author YAN
 * @date 2019-09-09 10:46:27
 * @version V1.0
 */
@Controller
@RequestMapping("contract/effect/review_ver/contractEffectRevVer")
public class ContractEffectRevVerController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ContractEffectRevVerService contractEffectRevVerService;

	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param reviewVerId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody ContractEffectRevVer initEditOrViewPage(String reviewVerId) {
		ContractEffectRevVer entity = contractEffectRevVerService.initEditOrViewPage(reviewVerId);
		return entity;
	}
	@RequestMapping("initPageByReviewId")
	public @ResponseBody ContractEffectRevVer initPageByReviewId(String contractEffectReviewId) {
		ContractEffectRevVer entity = contractEffectRevVerService.initPageByReviewId(contractEffectReviewId);
		return entity;
	}

	/**
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, ContractEffectRevVer contractEffectRevVer) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.contractEffectRevVerService.getGrid(user, jqGrid, contractEffectRevVer);
		return jqGrid;
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param contractEffectRevVer 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > saveOrUpdate(ContractEffectRevVer contractEffectRevVer) {
		UserProfile user = this.getUserProfile();
		ContractEffectRevVer entity = this.contractEffectRevVerService.saveOrUpdate(user, contractEffectRevVer);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, entity);
	}
	@RequestMapping("commit")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > commit(ContractEffectRevVer contractEffectRevVer) {
		UserProfile user = this.getUserProfile();
		ContractEffectRevVer entity = this.contractEffectRevVerService.commit(user, contractEffectRevVer);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, entity);
	}

	/**
	 * 删除操作
	 * 
	 * @param reviewVerIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > batchDel(String reviewVerIds) {
		UserProfile user = this.getUserProfile();
		this.contractEffectRevVerService.delete(user, reviewVerIds);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, null);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param reviewVerId
	 * @param reviewVerName
	 * @return
	 */
	@RequestMapping("nameIsValid")
	public @ResponseBody Boolean nameIsValid(String reviewVerId,String reviewVerName) {
		return this.contractEffectRevVerService.nameIsValid(reviewVerId, reviewVerName);
	}

}
