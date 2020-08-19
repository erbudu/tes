package com.supporter.prj.ppm.contract.sign.review.controller;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.supporter.prj.ppm.ecc.OperResultConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.spring_mvc.AbstractController;
import com.supporter.prj.ppm.contract.sign.review.service.ContractSignReviewEmpService;
import com.supporter.prj.ppm.contract.sign.review.entity.ContractSignReviewEmp;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.eip_service.authority.annotation.AuthCheck;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.exception.ExceptionCode;

/**
 * @Title: Controller
 * @Description: 评审人员.
 * @author YAN
 * @date 2019-09-06 18:35:30
 * @version V1.0
 */
@Controller
@RequestMapping("ppm/contract/sign/review/contractSignReviewEmp")
public class ContractSignReviewEmpController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ContractSignReviewEmpService contractSignReviewEmpService;

	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param reviewEmpId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody ContractSignReviewEmp initEditOrViewPage(String reviewEmpId) {
		ContractSignReviewEmp entity = contractSignReviewEmpService.initEditOrViewPage(reviewEmpId);
		return entity;
	}

	/**
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, ContractSignReviewEmp contractSignReviewEmp) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.contractSignReviewEmpService.getGrid(user, jqGrid, contractSignReviewEmp);
		return jqGrid;
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param contractSignReviewEmp 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > saveOrUpdate(ContractSignReviewEmp contractSignReviewEmp) {
		UserProfile user = this.getUserProfile();
		ContractSignReviewEmp entity = this.contractSignReviewEmpService.saveOrUpdate(user, contractSignReviewEmp);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, null);
	}

	/**
	 * 删除操作
	 * 
	 * @param reviewEmpIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	@AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
	public @ResponseBody OperResult< ? > batchDel(String reviewEmpIds) {
		UserProfile user = this.getUserProfile();
		this.contractSignReviewEmpService.delete(user, reviewEmpIds);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, null);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param reviewEmpId
	 * @param reviewEmpName
	 * @return
	 */
	@RequestMapping("nameIsValid")
	public @ResponseBody Boolean nameIsValid(String reviewEmpId,String reviewEmpName) {
		return this.contractSignReviewEmpService.nameIsValid(reviewEmpId, reviewEmpName);
	}

}
