package com.supporter.prj.ppm.contract.sign.review_ver.controller;

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
import com.supporter.prj.ppm.contract.sign.review_ver.entity.ContractSignRevVer;
import com.supporter.prj.ppm.contract.sign.review_ver.service.ContractSignRevVerService;
import com.supporter.prj.ppm.ecc.OperResultConstant;
import com.supporter.spring_mvc.AbstractController;

/**
 * @Title: Controller
 * @Description: 评审验证表.
 * @author YAN
 * @date 2019-09-09 10:46:27
 * @version V1.0
 */
@Controller
@RequestMapping("contract/sign/review_ver/contractSignRevVer")
public class ContractSignRevVerController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ContractSignRevVerService service;

	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 *
	 * @param reviewVerId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody ContractSignRevVer initEditOrViewPage(String reviewVerId) {
		ContractSignRevVer entity = service.initEditOrViewPage(reviewVerId);
		return entity;
	}
	@RequestMapping("initPageByReviewId")
	public @ResponseBody ContractSignRevVer initPageByReviewId(String contractSignReviewId) {
		ContractSignRevVer entity = service.initPageByReviewId(contractSignReviewId);
		return entity;
	}

	/**
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, ContractSignRevVer contractSignRevVer) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.service.getGrid(user, jqGrid, contractSignRevVer);
		return jqGrid;
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param contractSignRevVer 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > saveOrUpdate(ContractSignRevVer contractSignRevVer) {
		UserProfile user = this.getUserProfile();
		ContractSignRevVer entity = this.service.saveOrUpdate(user, contractSignRevVer);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, entity);
	}
	@RequestMapping("commit")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > commit(String  reviewVerId) {
		UserProfile user = this.getUserProfile();
		ContractSignRevVer entity = this.service.commit(user, reviewVerId);
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
		this.service.delete(user, reviewVerIds);
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
		return this.service.nameIsValid(reviewVerId, reviewVerName);
	}

}
